package bletch.pixelmoninformation.top;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.npcs.EntityNPC;
import com.pixelmonmod.pixelmon.entities.npcs.NPCChatting;
import com.pixelmonmod.pixelmon.entities.npcs.NPCNurseJoy;
import com.pixelmonmod.pixelmon.entities.npcs.NPCRelearner;
import com.pixelmonmod.pixelmon.entities.npcs.NPCShopkeeper;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrader;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTutor;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.BaseStats;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Level;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import com.pixelmonmod.pixelmon.pokedex.Pokedex;

import bletch.pixelmoninformation.PixelmonInformation;
import bletch.pixelmoninformation.core.ModConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.PixelmonUtils;
import bletch.pixelmoninformation.utils.StringUtils;
import bletch.pixelmoninformation.utils.TextUtils;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import static mcjty.theoneprobe.api.TextStyleClass.MODNAME;

import java.util.List;
import java.util.Map.Entry;

public class PixelmonEntityTop {
	
	public static class getTheOneProbe implements com.google.common.base.Function<ITheOneProbe, Void> {

		public static ITheOneProbe probe;

		@Override
		public Void apply(ITheOneProbe theOneProbe) {
			probe = theOneProbe;
			
			probe.registerEntityProvider(new IProbeInfoEntityProvider() {
				
				@Override
				public String getID() {
					return ModDetails.MOD_ID + ":default";
				}

				@Override
				public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, Entity entity, IProbeHitEntityData data) {
					
					if (entity instanceof EntityPixelmon) {
						PixelmonEntityTop.getTheOneProbe.addEntityPixelmonInfo(mode, probeInfo, player, world, (EntityPixelmon)entity, data);
					}	
					
					if (entity instanceof EntityNPC) {
						PixelmonEntityTop.getTheOneProbe.addEntityNPCInfo(mode, probeInfo, player, world, (EntityNPC)entity, data);
					}	
					
				}
				
			});
			
			probe.registerEntityDisplayOverride((mode, probeInfo, player, world, entity, data) -> {
				
				if (entity instanceof EntityPixelmon) {
					return PixelmonEntityTop.getTheOneProbe.overrideEntityPixelmonInfo(mode, probeInfo, player, world, (EntityPixelmon)entity, data);
				}
				
				if (entity instanceof EntityNPC) {
					return PixelmonEntityTop.getTheOneProbe.overrideEntityNPCInfo(mode, probeInfo, player, world, (EntityNPC)entity, data);
				}
				
				return false;
			});
			
			return null;
		}
		
		private static boolean addEntityPixelmonInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, EntityPixelmon entity, IProbeHitEntityData data) {

			Minecraft minecraft = null;
			if (PixelmonInformation.proxy.isRemote()) {
				minecraft = Minecraft.getMinecraft();
			}

			if (ModConfig.top.useSneaking && mode == ProbeMode.NORMAL) {
				return false;
			}

			if (entity != null) {
				
				EnumSpecies pokemonSpecies = entity.getSpecies();
				Pokemon pokemon = null;
				BaseStats baseStats = null;	
				
				if (ModConfig.top.entities.showPokemonDescription) {
					// show pokemon description	
					String output = EntityPixelmon.getLocalizedDescription(pokemonSpecies.getPokemonName());
					List<String> results = StringUtils.split(output, minecraft, 4);
					
					if (results != null && !results.isEmpty()) {
						results.forEach(r -> probeInfo.text(r));
					}
				}
				
				if (ModConfig.top.entities.showPokemonOwner) {
					if (entity.hasOwner()) {
						String ownerName = entity.getOwner().getName();
						String output = TextUtils.translate("gui.pokemon.owner") + " " + TextFormatting.WHITE + ownerName;
						probeInfo.text(TextFormatting.DARK_AQUA + output);
					}
				}
							
				if (ModConfig.top.entities.showPokemonLevel) {
					Level level = entity.getLvl();
					String output = TextUtils.translate("gui.pokemon.level") + " " + TextFormatting.WHITE + level.getLevel();
					probeInfo.text(TextFormatting.DARK_AQUA + output);	
				}
				
				if (ModConfig.top.entities.showPokemonNationalPokedexNumber) {
					// show the national pokedex number
					String output = String.format(TextUtils.translate("gui.pokemon.dexnum"), TextFormatting.WHITE + pokemonSpecies.getNationalPokedexNumber());
					if (!StringUtils.isNullOrWhitespace(output)) {
						probeInfo.text(TextFormatting.DARK_AQUA + output);
					}				
				}

				if (ModConfig.top.entities.showPokemonGeneration) {
					// show the generation
					String output = TextUtils.translate("gui.pokemon.generation") + " " + TextFormatting.WHITE + pokemonSpecies.getGeneration();
					if (!StringUtils.isNullOrWhitespace(output)) {
						probeInfo.text(TextFormatting.DARK_AQUA + output);
					}
				}			
				
				if (ModConfig.top.entities.showPokemonEVs) {
					// show the EV stats 
			        String delimiter = " ";
			        String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.evstats") + TextFormatting.YELLOW;
			        String output2 = "";
			        
			        if (pokemon == null) {
			        	pokemon = entity.getPokemonData();
			        }
			        if (pokemon != null) {
			        	EVStore store = pokemon.getEVs();
			        	StatsType[] statTypes = StatsType.getStatValues();
				        int total = 0;
			        	
			        	for (StatsType type : statTypes) {
			        		int value = store.get(type);
			        		total += value;
			        		
			        		output2 += delimiter;
			        		output2 += type.getLocalizedName();
			        		output2 += " (" + value + ")";
				        	
				        	delimiter = ", ";
			        	}
			        	
			        	int maxTotal = EVStore.MAX_TOTAL_EVS;
			        	int percentage = (int) ((100.0 / maxTotal) * total);
			        	output += " " + total + "/" + maxTotal + TextFormatting.WHITE + " (" + percentage + "%) - " + output2;
			        	
						List<String> results = StringUtils.split(output, minecraft, 4, false);						
						if (results != null && !results.isEmpty()) {
							results.forEach(r -> probeInfo.text(TextFormatting.WHITE + r));
						}
			        }
				}		
				
				if (ModConfig.top.entities.showPokemonIVs) {
					// show the IV stats 
			        String delimiter = " ";
			        String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.ivstats") + TextFormatting.YELLOW;
			        String output2 = "";
			        
			        if (pokemon == null) {
			        	pokemon = entity.getPokemonData();
			        }
			        if (pokemon != null) {
			        	IVStore store = pokemon.getIVs();
			        	StatsType[] statTypes = StatsType.getStatValues();
			        	int total = 0;
			        	
			        	for (StatsType type : statTypes) {
			        		int value = store.get(type);
			        		total += value;
			        		
			        		output2 += delimiter;
			        		output2 += type.getLocalizedName();
			        		output2 += " (" + value + ")";
				        	
				        	delimiter = ", ";
			        	}
			        	
			        	int maxTotal = IVStore.MAX_IVS * statTypes.length;
			        	int percentage = (int) ((100.0 / maxTotal) * total);
			        	output += " " + total + "/" + maxTotal + TextFormatting.WHITE + " (" + percentage + "%) - " + output2;
			        	
						List<String> results = StringUtils.split(output, minecraft, 4, false);						
						if (results != null && !results.isEmpty()) {
							results.forEach(r -> probeInfo.text(TextFormatting.WHITE + r));
						}
			        }
				}
				
				if (ModConfig.top.entities.showPokemonEVYield) {
					// show the type information	
			        String delimiter = " ";
			        String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.evyield") + TextFormatting.WHITE;
			        
			        if (baseStats == null) {
			        	baseStats = pokemonSpecies.getBaseStats();
			        }
			        if (baseStats != null) {
				        for (Entry<StatsType, Integer> evYield : baseStats.evYields.entrySet()) {
				        	output += delimiter;
				        	output += evYield.getKey().getLocalizedName();
				        	output += " (" + evYield.getValue() + ")";
				        	
				        	delimiter = ", ";
				        }
		
						if (!StringUtils.isNullOrWhitespace(output)) {
							probeInfo.text(output);
						}		        	
			        }
				}

				if (minecraft != null && ModConfig.top.entities.showPokemonNatureInformation) {
					String delimiter = " ";
					String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.nature") + TextFormatting.WHITE;

					if (pokemon == null) {
						pokemon = entity.getPokemonData();
					}
					if (pokemon != null) {
						EnumNature nature = pokemon.getNature();

						if (nature != null) {
							output += delimiter;
							output += nature.getLocalizedName();
						}
					}

					if (!StringUtils.isNullOrWhitespace(output)) {
						probeInfo.text(output);
					}
				}
				
				if (ModConfig.top.entities.showPokemonTypeInformation) {
					// show the type information	
			        String delimiter = " ";
			        String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.type") + TextFormatting.WHITE;
			        
			        if (baseStats == null) {
			        	baseStats = pokemonSpecies.getBaseStats();
			        }
			        if (baseStats != null) {
				        for (EnumType type : baseStats.getTypeList()) {
				        	output += delimiter;
				        	output += type.getLocalizedName();
				        	
				        	delimiter = ", ";
				        }
		
						if (!StringUtils.isNullOrWhitespace(output)) {
							probeInfo.text(output);
						}		        	
			        }
				}		
				
				if (ModConfig.top.entities.showPokemonTypeMatchupInformation) {
					// show the type match-up information
			        if (baseStats == null) {
			        	baseStats = pokemonSpecies.getBaseStats();
			        }
			        if (baseStats != null) {
				        for (EnumType type : baseStats.getTypeList()) {
				        	String typeValue = type.name().toLowerCase();
				        	String typeName = type.getLocalizedName();
				        	
			        		String output = TextUtils.translate("pokemontype." + typeValue + ".strong");
			        		if (!StringUtils.isNullOrWhitespace(output)) {
			        			probeInfo.text(TextFormatting.DARK_AQUA + typeName + ": " + output);
			        		}
			        		output = TextUtils.translate("pokemontype." + typeValue + ".weak");
			        		if (!StringUtils.isNullOrWhitespace(output)) {
			        			probeInfo.text(TextFormatting.DARK_AQUA + typeName + ": " + output);
			        		}
			        		output = TextUtils.translate("pokemontype." + typeValue + ".bad");
			        		if (!StringUtils.isNullOrWhitespace(output)) {
			        			probeInfo.text(TextFormatting.DARK_AQUA + typeName + ": " + output);
			        		}
				        }
			        }
				}
				
				if (ModConfig.top.entities.showPokemonCaught) {
					try {
						// show if we have caught this pixelmon
						String caughtStatus = TextUtils.SYMBOL_UNKNOWN;
						Pokedex pokedex = PixelmonUtils.getClientPokedex();
						
						if (pokedex != null) {
							caughtStatus = pokedex.hasCaught(pokemonSpecies.getNationalPokedexInteger()) ? TextUtils.SYMBOL_GREENTICK : TextUtils.SYMBOL_REDCROSS;
						}	
						
						String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.caught") + " " + caughtStatus;
						probeInfo.text(output);
					}
					catch (Exception e) {
					}
				}
				
				return true;
			}

			return false;
		}
		
		private static boolean addEntityNPCInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, EntityNPC entity, IProbeHitEntityData data) {
			return false;
		}
		
		private static boolean overrideEntityPixelmonInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, EntityPixelmon entity, IProbeHitEntityData data) {

			if (entity != null) {
				String tooltip = TextFormatting.RESET.toString();
				
				// get boss information
				if (entity.isBossPokemon()) {
					EnumBossMode bossMode = entity.getBossMode();
					switch (bossMode) {
						case Uncommon:
							tooltip += TextFormatting.GREEN;
							break;
						case Rare:
							tooltip += TextFormatting.YELLOW;
							break;
						case Legendary:
							tooltip += TextFormatting.RED;
							break;
						case Ultimate:
							tooltip += TextFormatting.GOLD;
							break;
						default:
							tooltip += TextFormatting.WHITE;
							break;
					}
					tooltip += bossMode.getLocalizedName() + " ";
				}
				
				// add pokemon species name
				tooltip += TextFormatting.WHITE + entity.getLocalizedName();
				
				Pokemon pokemon = entity.getPokemonData();

				// add pokemon nickname
				if (pokemon != null && !StringUtils.isNullOrWhitespace(pokemon.getNickname())) {
					tooltip += TextFormatting.ITALIC + " (" + pokemon.getNickname() + ")";
				}
				
				// add pokemon gender
				if (pokemon != null) {
					switch (pokemon.getGender()) {
						case Female:
							tooltip += " " + TextUtils.SYMBOL_FEMALE;
							break;
						case Male:
							tooltip += " " + TextUtils.SYMBOL_MALE;
							break;
						default:
							break;
					}				
				}
				
				probeInfo.vertical()
					.text(tooltip)
					.text(MODNAME + mcjty.theoneprobe.Tools.getModName(entity));
			
				return true;
			}
			
			return false;
		}
		
		private static boolean overrideEntityNPCInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, EntityNPC entity, IProbeHitEntityData data) {
			
			if (entity != null) {
				String tooltip = TextFormatting.RESET.toString();
				
				if (entity instanceof NPCNurseJoy) {
					NPCNurseJoy npc = ((NPCNurseJoy)entity);
					
					//String nameKey = npc.getTextureIndex() == 1 ? "gui.nursejoy.name" : "gui.nursejohn.name";
					//npc.setName(nameKey);
					
					String npcText = npc.getDisplayText();
					if (!StringUtils.isNullOrWhitespace(npcText) ) {
						tooltip += TextFormatting.WHITE + npcText;
					}
				}
				
				if (entity instanceof NPCTrainer) {
					NPCTrainer npc = ((NPCTrainer)entity);
					
					// get boss information
					EnumBossMode bossMode = npc.getBossMode();
					switch (bossMode) {
						case Uncommon:
							tooltip += TextFormatting.GREEN + bossMode.getLocalizedName() + " ";
							break;
						case Rare:
							tooltip += TextFormatting.YELLOW + bossMode.getLocalizedName() + " ";
							break;
						case Legendary:
							tooltip += TextFormatting.RED + bossMode.getLocalizedName() + " ";
							break;
						case Ultimate:
							tooltip += TextFormatting.GOLD + bossMode.getLocalizedName() + " ";
							break;
						default:
							break;
					}

					// get trainer type
//					BaseTrainer baseTrainer = npc.getBaseTrainer();
//					if (!StringUtils.isNullOrWhitespace(baseTrainer.name) ) {
//						tooltip += TextFormatting.WHITE + baseTrainer.name;	
//					}
					
					String npcLevel = npc.getSubTitleText() + " " + npc.getLvl();
					if (!StringUtils.isNullOrWhitespace(npcLevel) ) {
						tooltip += TextFormatting.WHITE + npcLevel;
					}
				}
				
				if (entity instanceof NPCShopkeeper) {
					NPCShopkeeper npc = ((NPCShopkeeper)entity);
					
					String npcText = npc.getDisplayText();
					if (!StringUtils.isNullOrWhitespace(npcText) ) {
						tooltip += TextFormatting.WHITE + npcText;
					}
				}
				
				if (entity instanceof NPCRelearner) {
					NPCRelearner npc = ((NPCRelearner)entity);
					
					String npcText = npc.getDisplayText();
					if (!StringUtils.isNullOrWhitespace(npcText) ) {
						tooltip += TextFormatting.WHITE + npcText;
					}
				}
				
				if (entity instanceof NPCTrader) {
					NPCTrader npc = ((NPCTrader)entity);
					
					String npcText = npc.getDisplayText();
					if (!StringUtils.isNullOrWhitespace(npcText) ) {
						tooltip += TextFormatting.WHITE + npcText;
					}
				}
				
				if (entity instanceof NPCTutor) {
					NPCTutor npc = ((NPCTutor)entity);
					
					String npcText = npc.getDisplayText();
					if (!StringUtils.isNullOrWhitespace(npcText) ) {
						tooltip += TextFormatting.WHITE + npcText;
					}
				}
				
				if (entity instanceof NPCChatting) {
					NPCChatting npc = ((NPCChatting)entity);
					
					String npcText = npc.getDisplayText();
					if (!StringUtils.isNullOrWhitespace(npcText) ) {
						tooltip += TextFormatting.WHITE + npcText;
					}
				}
				
				if (!StringUtils.isNullOrWhitespace(tooltip) && !tooltip.equalsIgnoreCase(TextFormatting.RESET.toString())) {
					probeInfo.vertical()
					.text(tooltip)
					.text(MODNAME + mcjty.theoneprobe.Tools.getModName(entity));					
				} else {
					probeInfo.vertical()
					.text(MODNAME + mcjty.theoneprobe.Tools.getModName(entity));
				}
				
				return true;
			}
			
			return false;
		}
		
	}
	
}
