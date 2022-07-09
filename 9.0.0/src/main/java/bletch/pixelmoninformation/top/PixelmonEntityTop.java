package bletch.pixelmoninformation.top;

import com.google.common.base.Function;
import com.pixelmonmod.pixelmon.api.pokedex.PlayerPokedex;
import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.api.pokemon.Nature;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.boss.BossTier;
import com.pixelmonmod.pixelmon.api.pokemon.boss.BossTiers;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.pokemon.species.Stats;
import com.pixelmonmod.pixelmon.api.pokemon.species.evs.EVYields;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.pokemon.stats.EVStore;
import com.pixelmonmod.pixelmon.api.pokemon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.npcs.IndexedNPCEntity;
import com.pixelmonmod.pixelmon.entities.npcs.NPCChatting;
import com.pixelmonmod.pixelmon.entities.npcs.NPCEntity;
import com.pixelmonmod.pixelmon.entities.npcs.NPCFisherman;
import com.pixelmonmod.pixelmon.entities.npcs.NPCNurseJoy;
import com.pixelmonmod.pixelmon.entities.npcs.NPCQuestGiver;
import com.pixelmonmod.pixelmon.entities.npcs.NPCRelearner;
import com.pixelmonmod.pixelmon.entities.npcs.NPCShopkeeper;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrader;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTutor;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;

import bletch.pixelmoninformation.core.ModCommonConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.ModUtils;
import bletch.pixelmoninformation.utils.StringUtils;
import bletch.pixelmoninformation.utils.TextUtils;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import static mcjty.theoneprobe.api.TextStyleClass.MODNAME;

public class PixelmonEntityTop {

	public static class getTheOneProbe implements Function<ITheOneProbe, Void> {

		public static ITheOneProbe PROBE;
		public static IProbeConfig CONFIG;

		@Override
		public Void apply(ITheOneProbe theOneProbe) {
			PROBE = theOneProbe;
			CONFIG = PROBE.createProbeConfig();

			if (WrappedTextElement.ELEMENT_ID == -1) {
				WrappedTextElement.ELEMENT_ID = PROBE.registerElementFactory(new WrappedTextElement.Factory());
			}

			PROBE.registerEntityProvider(new IProbeInfoEntityProvider() {

				@Override
				public String getID() {
					return ModDetails.MOD_ID + ":default";
				}

				@Override
				public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data) {

					if (entity instanceof PixelmonEntity) {
						PixelmonEntityTop.getTheOneProbe.addPixelmonEntityInfo(mode, probeInfo, player, world, (PixelmonEntity)entity, data);
					}	

					if (entity instanceof NPCEntity) {
						PixelmonEntityTop.getTheOneProbe.addNPCEntityInfo(mode, probeInfo, player, world, (NPCEntity)entity, data);
					}	

				}

			});

			PROBE.registerEntityDisplayOverride((mode, probeInfo, player, world, entity, data) -> {

				if (entity instanceof PixelmonEntity) {
					return PixelmonEntityTop.getTheOneProbe.overridePixelmonEntityInfo(mode, probeInfo, player, world, (PixelmonEntity)entity, data, CONFIG);
				}

				if (entity instanceof NPCEntity) {
					return PixelmonEntityTop.getTheOneProbe.overrideNPCEntityInfo(mode, probeInfo, player, world, (NPCEntity)entity, data, CONFIG);
				}

				return false;
			});

			return null;
		}

		private static boolean addPixelmonEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, PixelmonEntity entity, IProbeHitEntityData data) {
			if (entity == null) {
				return false;
			}
			
			if (!ModCommonConfig.INSTANCE.topShowEntityInformation() 
					|| ModCommonConfig.INSTANCE.topUseCrouchKey() && !player.isCrouching()) {
				return false;
			}	

			PixelmonEntity pixelmon = entity;
			Pokemon pokemon = pixelmon.getPokemon();
			Species pokemonSpecies = pixelmon.getSpecies();
			Stats stats = pokemon.getForm();

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonDescription()) {
				// show pokemon description
				String output = pokemonSpecies.getDescTranslation().getString();

				if (!StringUtils.isNullOrWhitespace(output)) {
					probeInfo.element(new WrappedTextElement(output));
				}
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonOwner()) {
				String translateKey = "gui.pokemon.owner";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pixelmon.hasOwner() && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					String ownerName = pixelmon.getOwner().getName().getString();

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + ownerName));
				}
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonLevel()) {
				String translateKey = "gui.pokemon.level";
				String output = new TranslationTextComponent(translateKey).getString();

				if (!StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					int level = pixelmon.getLvl().getPokemonLevel();

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + level));
				}	
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonPokedexNumber()) {
				// show the pokedex number
				String translateKey = "gui.pokemon.dexnum";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemonSpecies != null && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					String dexNumber = pokemonSpecies.getFormattedDex();

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + dexNumber));
				}				
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonGeneration()) {
				// show the generation
				String translateKey = "gui.pokemon.generation";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemonSpecies != null && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					int generation = pokemonSpecies.getGeneration();

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + generation));
				}
			}		

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonEVs()) {
				// show the EV stats 
				String translateKey = "gui.pokemon.evstats";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemon != null && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					String evStats = "";
					String delimiter = "";

					EVStore store = pokemon.getEVs();

					BattleStatsType[] statTypes = BattleStatsType.getStatValues();
					int total = 0;

					for (BattleStatsType type : statTypes) {
						int value = store.getStat(type);
						total += value;

						evStats += delimiter;
						evStats += type.getLocalizedName();
						evStats += " (" + value + ")";

						delimiter = ", ";
					}

					int maxTotal = EVStore.MAX_TOTAL_EVS;
					int percentage = (int) ((100.0 / maxTotal) * total);

					probeInfo.element(new WrappedTextElement(TextFormatting.DARK_AQUA + output + " " + TextFormatting.YELLOW + total + "/" + maxTotal + " (" + percentage + "%) " + TextFormatting.WHITE + "- " + evStats));
				}
			}		

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonIVs()) {
				// show the IV stats 
				String translateKey = "gui.pokemon.ivstats";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemon != null && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					String ivStats = "";
					String delimiter = "";

					IVStore store = pokemon.getIVs();

					BattleStatsType[] statTypes = BattleStatsType.getStatValues();
					int total = 0;

					for (BattleStatsType type : statTypes) {
						int value = store.getStat(type);
						total += value;

						ivStats += delimiter;
						ivStats += type.getTranslatedName().getString();
						ivStats += " (" + value + ")";

						delimiter = ", ";
					}

					int maxTotal = IVStore.MAX_IVS * statTypes.length;
					int percentage = (int) ((100.0 / maxTotal) * total);

					probeInfo.element(new WrappedTextElement(TextFormatting.DARK_AQUA + output + " " + TextFormatting.YELLOW + total + "/" + maxTotal + " (" + percentage + "%) " + TextFormatting.WHITE + "- " + ivStats));
				}
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonEVYield()) {
				// show the EV Yield 
				String translateKey = "gui.pokemon.evyield";
				String output = new TranslationTextComponent(translateKey).getString();

				if (stats != null && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					String evYield = "";
					String delimiter = "";

					BattleStatsType[] statValues = BattleStatsType.getStatValues();
					EVYields evYields = stats.getEVYields();

					for (int i = 0; i < statValues.length; i++) {
						int evYieldValue = evYields.getYield(statValues[i]);
						if (evYieldValue <= 0)
							continue;

						evYield += delimiter;
						evYield += statValues[i].getTranslatedName().getString();
						evYield += " (" + evYieldValue + ")";

						delimiter = ", ";
					}

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + evYield));		        	
				}
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonNatureInformation()) {
				// show the nature
				String translateKey = "gui.pokemon.nature";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemon != null && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					String natureName = "";

					Nature nature = pokemon.getNature();

					if (nature != null) {
						natureName += nature.getTranslatedName().getString();
					}

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + natureName));
				}
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonGrowthInformation()) {
				// show the type information	
				String translateKey = "gui.pokemon.growth";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemon != null && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					String growthName = "";

					EnumGrowth growth = pokemon.getGrowth();

					if (growth != null) {
						growthName += growth.getTranslatedName().getString();
					}

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + growthName));
				}
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonTypeInformation()) {
				// show the type information
				String translateKey = "gui.pokemon.type";
				String output = new TranslationTextComponent(translateKey).getString();

				if (stats != null && !StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {	
					String types = "";
					String delimiter = "";

					for (Element type : stats.getTypes()) {
						types += delimiter;
						types += type.getTranslatedName().getString();

						delimiter = ", ";
					}

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + types));		        	
				}
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonTypeMatchupInformation()) {
				// show the type match-up information

				if (stats != null) {
					for (Element type : stats.getTypes()) {
						String typeName = type.getTranslatedName().getString();
						String typeValue = type.name().toLowerCase();
						String output = "";
						String translateKey = "";

						translateKey = "pokemontype." + typeValue + ".strong";
						output = new TranslationTextComponent(translateKey).getString();
						if (!StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
							probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + typeName + ": " + output));
						}

						translateKey = "pokemontype." + typeValue + ".weak";
						output = new TranslationTextComponent(translateKey).getString();
						if (!StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
							probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + typeName + ": " + output));
						}

						translateKey = "pokemontype." + typeValue + ".bad";
						output = new TranslationTextComponent(translateKey).getString();
						if (!StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
							probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + typeName + ": " + output));
						}
					}
				}
			}

			if (ModCommonConfig.INSTANCE.topEntityShowPokemonCaught()) {
				// show if we have caught this pixelmon
				String translateKey = "gui.pokemon.caught";
				String output = new TranslationTextComponent(translateKey).getString();

				if (!StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					String caughtStatus = TextUtils.SYMBOL_UNKNOWN;

					PlayerPokedex pokedex = new PlayerPokedex(player.getUUID());

					if (pokedex != null) {
						caughtStatus = pokedex.hasCaught(pokemonSpecies) ? TextUtils.SYMBOL_GREENTICK : TextUtils.SYMBOL_REDCROSS;
					}

					probeInfo.text(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + caughtStatus));
				}
			}

			return false;
		}

		private static boolean addNPCEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, NPCEntity entity, IProbeHitEntityData data) {
			return false;
		}

		private static boolean overridePixelmonEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, PixelmonEntity entity, IProbeHitEntityData data, IProbeConfig config) {
			if (entity == null) {
				return false;
			}	
			
			if (!ModCommonConfig.INSTANCE.topShowEntityInformation()) {
				return false;
			}

			PixelmonEntity pixelmon = entity;
			Pokemon pokemon = pixelmon.getPokemon();

			String output = "";

			// get boss information
			BossTier bossTier = pixelmon.getBossTier();
			if (bossTier.isBoss()) {
				switch (bossTier.getID()) {
				case BossTiers.COMMON:
					output += TextFormatting.GRAY;
					break;
				case BossTiers.UNCOMMON:
					output += TextFormatting.GREEN;
					break;
				case BossTiers.RARE:
					output += TextFormatting.BLUE;
					break;
				case BossTiers.EPIC:
					output += TextFormatting.DARK_PURPLE;
					break;
				case BossTiers.LEGENDARY:
					output += TextFormatting.GOLD;
					break;
				case BossTiers.ULTIMATE:
					output += TextFormatting.AQUA;
					break;
				case BossTiers.HAUNTED:
					output += TextFormatting.LIGHT_PURPLE;
					break;
				case BossTiers.DROWNED:
					output += TextFormatting.DARK_AQUA;
					break;
				default:
					output += TextFormatting.WHITE;
					break;
				}								

				output += new TranslationTextComponent(bossTier.getName()).getString();
				output += " ";
			}

			// add pokemon species name
			output += TextFormatting.WHITE;
			output += pixelmon.getLocalizedName();

			if (pokemon != null) {
				// add pokemon nickname
				if (!StringUtils.isNullOrWhitespace(pokemon.getNickname())) {
					output += " ";
					output += TextFormatting.ITALIC;
					output += "(" + pokemon.getNickname() + ")";
				}

				// add pokemon gender
				switch (pokemon.getGender()) {
				case FEMALE:
					output += " ";
					output += TextUtils.SYMBOL_FEMALE;
					break;
				case MALE:
					output += " ";
					output += TextUtils.SYMBOL_MALE;
					break;
				default:
					break;
				}
			}

			String modName = ModUtils.getModName(entity.getType());
			if (show(mode, config.getShowModName())) {
				probeInfo.horizontal()
				.entity(entity)
				.vertical()
				.text(output)
				.text(CompoundText.create().style(MODNAME).text(modName));
			} else {
				probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
				.entity(entity)
				.text(output);
			}

			return true;
		}

		private static boolean overrideNPCEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, NPCEntity entity, IProbeHitEntityData data, IProbeConfig config) {
			if (entity == null) {
				return false;
			}	
			
			if (!ModCommonConfig.INSTANCE.topShowEntityInformation()) {
				return false;
			}

			String output = "";

			String langCode = "en-us";
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
			if (serverPlayer != null) {
				langCode = serverPlayer.getLanguage();
			}

			String npcName = "";
			String npcInfo = "";

			if (entity instanceof NPCFisherman) {
				NPCFisherman entityNPC = (NPCFisherman)entity;
				npcName = entityNPC.getDisplayText();
			}

			if (entity instanceof NPCNurseJoy) {
				NPCNurseJoy entityNPC = (NPCNurseJoy)entity;

				String nameKey = entityNPC.getTextureIndex() == 1 ? "gui.nursejoy.name" : "gui.doctorjohn.name";
				npcName = new TranslationTextComponent(nameKey).getString();
			}

			if (entity instanceof NPCQuestGiver) {
				NPCQuestGiver entityNPC = (NPCQuestGiver)entity;
				npcName = entityNPC.getName(langCode);
			}

			if (entity instanceof NPCRelearner) {
				NPCRelearner entityNPC = (NPCRelearner)entity;
				npcName = entityNPC.getDisplayText();
			}

			if (entity instanceof NPCShopkeeper) {
				NPCShopkeeper entityNPC = (NPCShopkeeper)entity;
				npcName = entityNPC.getShopkeeperName(langCode);
			}

			if (entity instanceof NPCTrader) {
				NPCTrader entityNPC = (NPCTrader)entity;
				npcName = entityNPC.getDisplayText();
			}

			if (entity instanceof NPCTrainer) {
				NPCTrainer entityNPC = (NPCTrainer)entity;
				npcName = entityNPC.getName(langCode);

				// get boss information
				BossTier bossTier = entityNPC.getBossTier();
				if (bossTier.isBoss()) {
					switch (bossTier.getID()) {
					case BossTiers.COMMON:
						output += TextFormatting.GRAY;
						break;
					case BossTiers.UNCOMMON:
						output += TextFormatting.GREEN;
						break;
					case BossTiers.RARE:
						output += TextFormatting.BLUE;
						break;
					case BossTiers.EPIC:
						output += TextFormatting.DARK_PURPLE;
						break;
					case BossTiers.LEGENDARY:
						output += TextFormatting.GOLD;
						break;
					case BossTiers.ULTIMATE:
						output += TextFormatting.AQUA;
						break;
					case BossTiers.HAUNTED:
						output += TextFormatting.LIGHT_PURPLE;
						break;
					case BossTiers.DROWNED:
						output += TextFormatting.DARK_AQUA;
						break;
					default:
						output += TextFormatting.WHITE;
						break;
					}								

					output += new TranslationTextComponent(bossTier.getName()).getString();
					output += " ";
				}

				// get trainer level
				String npcLevel = entityNPC.getSubTitleText() + " " + entityNPC.getTrainerLevel();
				npcInfo += TextFormatting.WHITE;
				npcInfo += "(" + npcLevel.trim() + ")";
			}

			if (entity instanceof NPCTutor) {
				NPCTutor entityNPC = (NPCTutor)entity;
				npcName = entityNPC.getDisplayText();
			}

			if (StringUtils.isNullOrWhitespace(npcName) && entity instanceof NPCChatting) {
				NPCChatting entityNPC = (NPCChatting)entity;
				npcName = entityNPC.getName(langCode);
			}

			if (StringUtils.isNullOrWhitespace(npcName) && entity instanceof IndexedNPCEntity) {
				IndexedNPCEntity entityNPC = (IndexedNPCEntity)entity;
				npcName = entityNPC.getName(langCode);
			}	

			if (!StringUtils.isNullOrWhitespace(npcName) ) {
				output += TextFormatting.WHITE;
				output += npcName;
				output += " ";
			}	

			if (!StringUtils.isNullOrWhitespace(npcInfo) ) {
				output += npcInfo;
				output += " ";
			}

			String modName = ModUtils.getModName(entity.getType());
			if (show(mode, config.getShowModName())) {
				probeInfo.horizontal()
				.entity(entity)
				.vertical()
				.text(output)
				.text(CompoundText.create().style(MODNAME).text(modName));
			} else {
				probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
				.entity(entity)
				.text(output);
			}

			return true;
		}
	}

	public static boolean show(ProbeMode mode, IProbeConfig.ConfigMode cfg) {
		return cfg == IProbeConfig.ConfigMode.NORMAL || (cfg == IProbeConfig.ConfigMode.EXTENDED && mode == ProbeMode.EXTENDED);
	}

}
