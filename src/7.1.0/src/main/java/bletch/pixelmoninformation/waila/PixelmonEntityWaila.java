package bletch.pixelmoninformation.waila;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.ParametersAreNonnullByDefault;

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
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import com.pixelmonmod.pixelmon.pokedex.Pokedex;

import bletch.pixelmoninformation.core.ModConfig;
import bletch.pixelmoninformation.utils.DebugUtils;
import bletch.pixelmoninformation.utils.PixelmonUtils;
import bletch.pixelmoninformation.utils.StringUtils;
import bletch.pixelmoninformation.utils.TextUtils;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

@ParametersAreNonnullByDefault
public class PixelmonEntityWaila implements IWailaEntityProvider {

	private static String NBT_TAG_EVSTORE = "pokemon_evstore";
	private static String NBT_TAG_IVSTORE = "pokemon_ivstore";
	
	@Override
    public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return accessor.getEntity();
    }

	@Override
	public List<String> getWailaHead(Entity entity, List<String> currentTip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		
		if (entity instanceof EntityPixelmon) {
			EntityPixelmon pixelmon = ((EntityPixelmon)entity);
			
			if (pixelmon != null) {				
				String tooltip = TextFormatting.RESET.toString();
				
				// get boss information
				if (pixelmon.isBossPokemon()) {
					EnumBossMode bossMode = pixelmon.getBossMode();
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
				tooltip += TextFormatting.WHITE + pixelmon.getLocalizedName();
				
				Pokemon pokemon = pixelmon.getPokemonData();

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
				
				if (currentTip.size() > 0) {
					currentTip.set(0, tooltip);
				} else {
					currentTip.add(tooltip);
				}
			}
		}
		
		if (entity instanceof EntityNPC) {
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
//				BaseTrainer baseTrainer = npc.getBaseTrainer();
//				if (!StringUtils.isNullOrWhitespace(baseTrainer.name) ) {
//					tooltip += TextFormatting.WHITE + baseTrainer.name;	
//				}
				
				// get trainer level
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
				if (currentTip.size() > 0) {
					currentTip.set(0, tooltip);
				} else {
					currentTip.add(tooltip);
				}
			} else {
				if (currentTip.size() > 0) {
					currentTip.remove(0);
				}
			}
		}
		
		return currentTip;
	}

	@Override
	public List<String> getWailaBody(Entity entity, List<String> currentTip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

		if (ModConfig.waila.useSneaking && !accessor.getPlayer().isSneaking()) {
			return currentTip;
		}
		
		NBTTagCompound tag = accessor.getNBTData();
		
		if (entity instanceof EntityPixelmon) {
			EntityPixelmon pixelmon = ((EntityPixelmon)entity);
			EnumSpecies pokemonSpecies = pixelmon.getSpecies();
			BaseStats baseStats = null;		
			
			if (ModConfig.waila.entities.showPokemonDescription) {
				// show pokemon description	
				String output = EntityPixelmon.getLocalizedDescription(pokemonSpecies.getPokemonName());
				List<String> results = StringUtils.split(output, Minecraft.getMinecraft(), 4);
				
				if (results != null && !results.isEmpty()) {
					currentTip.addAll(results);
				}
			}
			
			if (ModConfig.waila.entities.showPokemonOwner) {
				if (((EntityPixelmon)entity).hasOwner()) {
					String ownerName = ((EntityPixelmon)entity).getOwner().getName();
					String output = TextUtils.translate("gui.pokemon.owner") + " " + TextFormatting.WHITE + ownerName;
					currentTip.add(TextFormatting.DARK_AQUA + output);
				}
			}
						
			if (ModConfig.waila.entities.showPokemonLevel) {
				Level level = ((EntityPixelmon)entity).getLvl();
				String output = TextUtils.translate("gui.pokemon.level") + " " + TextFormatting.WHITE + level.getLevel();
				currentTip.add(TextFormatting.DARK_AQUA + output);	
			}
			
			if (ModConfig.waila.entities.showPokemonNationalPokedexNumber) {
				// show the national pokedex number
				String output = String.format(TextUtils.translate("gui.pokemon.dexnum"), TextFormatting.WHITE + pokemonSpecies.getNationalPokedexNumber());
				if (!StringUtils.isNullOrWhitespace(output)) {
					currentTip.add(TextFormatting.DARK_AQUA + output);
				}				
			}

			if (ModConfig.waila.entities.showPokemonGeneration) {
				// show the generation
				String output = TextUtils.translate("gui.pokemon.generation") + " " + TextFormatting.WHITE + pokemonSpecies.getGeneration();
				if (!StringUtils.isNullOrWhitespace(output)) {
					currentTip.add(TextFormatting.DARK_AQUA + output);
				}
			}		
			
			if (tag.hasKey(NBT_TAG_EVSTORE) && ModConfig.waila.entities.showPokemonEVs) {
				// show the EV stats 
		        String delimiter = " ";
		        String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.evstats") + TextFormatting.YELLOW;
		        String output2 = "";
		        		
	        	EVStore store = new EVStore();
	        	store.readFromNBT(tag.getCompoundTag(NBT_TAG_EVSTORE));
	        	
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
	        	
				if (!StringUtils.isNullOrWhitespace(output)) {
					currentTip.add(output);
				}
			}		
			
			if (tag.hasKey(NBT_TAG_IVSTORE) && ModConfig.waila.entities.showPokemonIVs) {
				// show the IV stats 
		        String delimiter = " ";
		        String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.ivstats") + TextFormatting.YELLOW;
		        String output2 = "";
		        
	        	IVStore store = new IVStore();
	        	store.readFromNBT(tag.getCompoundTag(NBT_TAG_IVSTORE));
	        	
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
	        	
				if (!StringUtils.isNullOrWhitespace(output)) {
					currentTip.add(output);
				}
			}
			
			if (ModConfig.waila.entities.showPokemonEVYield) {
				// show the EV Yield 
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
						currentTip.add(output);
					}		        	
		        }
			}			

			if (ModConfig.waila.entities.showPokemonTypeInformation) {
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
						currentTip.add(output);
					}		        	
		        }
			}
			
			if (ModConfig.waila.entities.showPokemonTypeMatchupInformation) {
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
		        			currentTip.add(TextFormatting.DARK_AQUA + typeName + ": " + output);
		        		}
		        		output = TextUtils.translate("pokemontype." + typeValue + ".weak");
		        		if (!StringUtils.isNullOrWhitespace(output)) {
		        			currentTip.add(TextFormatting.DARK_AQUA + typeName + ": " + output);
		        		}
		        		output = TextUtils.translate("pokemontype." + typeValue + ".bad");
		        		if (!StringUtils.isNullOrWhitespace(output)) {
		        			currentTip.add(TextFormatting.DARK_AQUA + typeName + ": " + output);
		        		}
			        }
		        }
			}
			
			if (ModConfig.waila.entities.showPokemonCaught) {
				try {
					// show if we have caught this pixelmon
					String caughtStatus = TextUtils.SYMBOL_UNKNOWN;
					Pokedex pokedex = PixelmonUtils.getPokedex();
					
					if (pokedex != null) {
						caughtStatus = pokedex.hasCaught(pokemonSpecies.getNationalPokedexInteger()) ? TextUtils.SYMBOL_GREENTICK : TextUtils.SYMBOL_REDCROSS;
					}	
					
					String output = TextFormatting.DARK_AQUA + TextUtils.translate("gui.pokemon.caught") + " " + caughtStatus;
					currentTip.add(output);
				}
				catch (Exception e) {
				}
			}
		}
		
		return currentTip;
	}
	
	@Override
	public List<String> getWailaTail(Entity entity, List<String> currentTip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		return currentTip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {
		if (entity != null) {
			entity.writeToNBT(tag);
		}
		
		if (entity instanceof EntityPixelmon) {
			EntityPixelmon pixelmon = ((EntityPixelmon)entity);
			Pokemon pokemon = pixelmon.getPokemonData();
			
			if (pokemon != null) {
				NBTTagCompound evTag = new NBTTagCompound();
				EVStore evStore = pokemon.getEVs();
				evStore.writeToNBT(evTag);
				
				NBTTagCompound ivTag = new NBTTagCompound();
				IVStore ivStore = pokemon.getIVs();
				ivStore.writeToNBT(ivTag);
				
				tag.setTag(NBT_TAG_EVSTORE, evTag);
				tag.setTag(NBT_TAG_IVSTORE, ivTag);				
			}
		}
		
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar registrar) {
		PixelmonEntityWaila entityProvider = new PixelmonEntityWaila();
		ArrayList<String> processed = new ArrayList<String>();
		
		for (Class<?> entity : getPixelmonEntities()) {
        	String key = entity.getTypeName();
        	
        	if (processed.contains(key)) {
        		continue;
        	}
        	processed.add(key);
        	
        	registrar.registerNBTProvider(entityProvider, entity);
        	
        	registrar.registerHeadProvider(entityProvider, entity);
			registrar.registerBodyProvider(entityProvider, entity);
			
			if (ModConfig.debug.enableDebug && ModConfig.debug.showWailaEntitiesRegistered) {
				DebugUtils.writeLine("Registered WAILA information for entity " + key, true);
			} 
		}
	}
	
	private static ArrayList<Class<?>> getPixelmonEntities() {	
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		
		list.add(EntityPixelmon.class);
		list.add(EntityNPC.class);
		
		// remove any entities that are inherited from other pixelmon mod entities
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.contains(list.get(i).getSuperclass())) {
				list.remove(i);
			}
		}
		
		list.sort((c1, c2) -> c1.getTypeName().compareTo(c2.getTypeName()));
		list.trimToSize();
		
		return list;
	}
}
