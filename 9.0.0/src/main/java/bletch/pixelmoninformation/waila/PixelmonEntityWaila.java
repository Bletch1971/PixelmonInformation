package bletch.pixelmoninformation.waila;

import java.util.ArrayList;
import java.util.List;

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
import com.pixelmonmod.pixelmon.client.storage.ClientPlayerPokedex;
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
import bletch.pixelmoninformation.utils.PixelmonUtils;
import bletch.pixelmoninformation.utils.StringUtils;
import bletch.pixelmoninformation.utils.TextUtils;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IServerDataProvider;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class PixelmonEntityWaila implements IEntityComponentProvider , IServerDataProvider<Entity> {

	public static final PixelmonEntityWaila INSTANCE = new PixelmonEntityWaila();

	private static String NBT_TAG_EVSTORE = "pokemon_evstore";
	private static String NBT_TAG_IVSTORE = "pokemon_ivstore";
	private static String NBT_TAG_NATURE = "pokemon_nature";

	private static String NBT_TAG_NPCNAME = "npc_name";

	@Override
	public void appendHead(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
		Entity entity = accessor.getEntity();
		CompoundNBT data = accessor.getServerData();

		if (entity instanceof PixelmonEntity) {			
			PixelmonEntity pixelmon = ((PixelmonEntity)entity);
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

			if (tooltip.isEmpty()) {
				tooltip.add(new StringTextComponent(TextFormatting.RESET + output.trim()));
			} else {
				tooltip.set(0, new StringTextComponent(TextFormatting.RESET + output.trim()));
			}
		}

		if (entity instanceof NPCEntity) {
			String output = "";

			if (data.contains(NBT_TAG_NPCNAME)) {
				String npcText = data.getString(NBT_TAG_NPCNAME);				
				if (!StringUtils.isNullOrWhitespace(npcText) ) {
					output += TextFormatting.WHITE + npcText;
					output += " ";
				}
			}

			if (entity instanceof NPCTrainer) {
				NPCTrainer npc = ((NPCTrainer)entity);

				// get boss information
				BossTier bossTier = npc.getBossTier();
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
				String npcLevel = npc.getSubTitleText() + " " + npc.getTrainerLevel();
				output += TextFormatting.WHITE;
				output += "(" + npcLevel.trim() + ")";
			}

			if (tooltip.isEmpty()) {
				tooltip.add(new StringTextComponent(TextFormatting.RESET + output.trim()));
			} else {
				tooltip.set(0, new StringTextComponent(TextFormatting.RESET + output.trim()));
			}
		}
	}

	@Override
	public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
		Minecraft minecraft = Minecraft.getInstance();

		if (ModCommonConfig.instance.wailaUseCrouchKey() && !accessor.getPlayer().isCrouching()) {
			return;
		}

		Entity entity = accessor.getEntity();		
		CompoundNBT data = accessor.getServerData();

		if (entity instanceof PixelmonEntity) {		
			PixelmonEntity pixelmon = ((PixelmonEntity)entity);
			Pokemon pokemon = pixelmon.getPokemon();
			Species pokemonSpecies = pixelmon.getSpecies();
			Stats stats = pokemon.getForm();

			if (ModCommonConfig.instance.wailaEntityShowPokemonDescription()) {
				// show pokemon description
				String output = pokemonSpecies.getDescTranslation().getString();

				if (!StringUtils.isNullOrWhitespace(output)) {
					List<String> outputLines = StringUtils.split(output, minecraft, 4);
					if (outputLines != null && !outputLines.isEmpty()) {
						outputLines.forEach(l -> tooltip.add(new StringTextComponent(l)));
					}
				}
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonOwner()) {
				String translateKey = "gui.pokemon.owner";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pixelmon.hasOwner() && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					String ownerName = pixelmon.getOwner().getName().getString();

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + ownerName));
				}
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonLevel()) {
				String translateKey = "gui.pokemon.level";
				String output = new TranslationTextComponent(translateKey).getString();

				if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					int level = pixelmon.getLvl().getPokemonLevel();

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + level));
				}	
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonPokedexNumber()) {
				// show the pokedex number
				String translateKey = "gui.pokemon.dexnum";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemonSpecies != null && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					String dexNumber = pokemonSpecies.getFormattedDex();

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + dexNumber));
				}				
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonGeneration()) {
				// show the generation
				String translateKey = "gui.pokemon.generation";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemonSpecies != null && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					int generation = pokemonSpecies.getGeneration();

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + generation));
				}
			}		

			if (ModCommonConfig.instance.wailaEntityShowPokemonEVs()) {
				// show the EV stats 
				String translateKey = "gui.pokemon.evstats";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemon != null && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					String evStats = "";
					String delimiter = "";

					EVStore store = null;
					if (data.contains(NBT_TAG_EVSTORE)) {
						store = new EVStore();
						store.readFromNBT(data.getCompound(NBT_TAG_EVSTORE));
					} else {
						store = pokemon.getEVs();
					}

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

					String output2 = TextFormatting.DARK_AQUA + output + " " + TextFormatting.YELLOW + total + "/" + maxTotal + " (" + percentage + "%) " + TextFormatting.WHITE + "- " + evStats;

					List<String> outputLines = StringUtils.split(output2, minecraft, 4);
					if (outputLines != null && !outputLines.isEmpty()) {
						outputLines.forEach(l -> tooltip.add(new StringTextComponent(l)));
					}
				}
			}		

			if (ModCommonConfig.instance.wailaEntityShowPokemonIVs()) {
				// show the IV stats 
				String translateKey = "gui.pokemon.ivstats";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemon != null && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					String ivStats = "";
					String delimiter = "";

					IVStore store = null;
					if (data.contains(NBT_TAG_IVSTORE)) {
						store = new IVStore();
						store.readFromNBT(data.getCompound(NBT_TAG_IVSTORE));
					} else {
						store = pokemon.getIVs();
					}

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

					String output2 = TextFormatting.DARK_AQUA + output + " " + TextFormatting.YELLOW + total + "/" + maxTotal + " (" + percentage + "%) " + TextFormatting.WHITE + "- " + ivStats;

					List<String> outputLines = StringUtils.split(output2, minecraft, 4);
					if (outputLines != null && !outputLines.isEmpty()) {
						outputLines.forEach(l -> tooltip.add(new StringTextComponent(l)));
					}
				}
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonEVYield()) {
				// show the EV Yield 
				String translateKey = "gui.pokemon.evyield";
				String output = new TranslationTextComponent(translateKey).getString();

				if (stats != null && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
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

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + evYield));		        	
				}
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonNatureInformation()) {
				// show the nature
				String translateKey = "gui.pokemon.nature";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemon != null && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					String natureName = "";

					Nature nature = null;
					if (data.contains(NBT_TAG_NATURE)) {
						nature = Nature.getNatureFromIndex(data.getInt(NBT_TAG_NATURE));
					} else {
						nature = pokemon.getNature();
					}

					if (nature != null) {
						natureName += nature.getTranslatedName().getString();
					}

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + natureName));
				}
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonGrowthInformation()) {
				// show the type information	
				String translateKey = "gui.pokemon.growth";
				String output = new TranslationTextComponent(translateKey).getString();

				if (pokemon != null && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					String growthName = "";

					EnumGrowth growth = pokemon.getGrowth();

					if (growth != null) {
						growthName += growth.getTranslatedName().getString();
					}

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + growthName));
				}
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonTypeInformation()) {
				// show the type information
				String translateKey = "gui.pokemon.type";
				String output = new TranslationTextComponent(translateKey).getString();

				if (stats != null && I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {	
					String types = "";
					String delimiter = "";

					for (Element type : stats.getTypes()) {
						types += delimiter;
						types += type.getTranslatedName().getString();

						delimiter = ", ";
					}

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + types));		        	
				}
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonTypeMatchupInformation()) {
				// show the type match-up information

				if (stats != null) {
					for (Element type : stats.getTypes()) {
						String typeName = type.getTranslatedName().getString();
						String typeValue = type.name().toLowerCase();
						String output = "";
						String translateKey = "";

						translateKey = "pokemontype." + typeValue + ".strong";
						output = new TranslationTextComponent(translateKey).getString();
						if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
							tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + typeName + ": " + output));
						}

						translateKey = "pokemontype." + typeValue + ".weak";
						output = new TranslationTextComponent(translateKey).getString();
						if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
							tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + typeName + ": " + output));
						}

						translateKey = "pokemontype." + typeValue + ".bad";
						output = new TranslationTextComponent(translateKey).getString();
						if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
							tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + typeName + ": " + output));
						}
					}
				}
			}

			if (ModCommonConfig.instance.wailaEntityShowPokemonCaught()) {
				// show if we have caught this pixelmon
				String translateKey = "gui.pokemon.caught";
				String output = new TranslationTextComponent(translateKey).getString();

				if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
					String caughtStatus = TextUtils.SYMBOL_UNKNOWN;

					ClientPlayerPokedex pokedex = PixelmonUtils.getClientPokedex();

					if (pokedex != null) {
						caughtStatus = pokedex.hasCaught(pokemonSpecies) ? TextUtils.SYMBOL_GREENTICK : TextUtils.SYMBOL_REDCROSS;
					}

					tooltip.add(new StringTextComponent(TextFormatting.DARK_AQUA + output + " " + TextFormatting.WHITE + caughtStatus));
				}
			}
		}
	}

	@Override
	public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, Entity entity) {
		if (entity instanceof PixelmonEntity) {
			PixelmonEntity pixelmon = ((PixelmonEntity)entity);
			Pokemon pokemon = pixelmon.getPokemon();

			if (pokemon != null) {
				CompoundNBT evTag = new CompoundNBT();
				EVStore evStore = pokemon.getEVs();
				evStore.writeToNBT(evTag);

				CompoundNBT ivTag = new CompoundNBT();
				IVStore ivStore = pokemon.getIVs();
				ivStore.writeToNBT(ivTag);

				Nature nature = pokemon.getNature();

				data.put(NBT_TAG_EVSTORE, evTag);
				data.put(NBT_TAG_IVSTORE, ivTag);
				data.putInt(NBT_TAG_NATURE, nature.ordinal());
			}
		}

		if (entity instanceof NPCEntity) {
			String langCode = player.getLanguage();
			String npcName = "";

			if (entity instanceof NPCFisherman) {
				NPCFisherman entityNPC = (NPCFisherman)entity;
				npcName = entityNPC.getDisplayText();
			}

			if (entity instanceof NPCNurseJoy) {
				NPCNurseJoy entityNPC = (NPCNurseJoy)entity;
				//npcName = entityNPC.getDisplayText();

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

			if (!StringUtils.isNullOrWhitespace(npcName)) {
				data.putString(NBT_TAG_NPCNAME, npcName);
			}
		}
	}

	public void register(IRegistrar registrar) {
		if (!ModCommonConfig.instance.enableWailaIntegration() || !ModCommonConfig.instance.wailaShowEntityInformation())
			return;

		ArrayList<String> processed = new ArrayList<String>();

		ArrayList<Class<?>> entityClasses = new ArrayList<Class<?>>();
		entityClasses.addAll(PixelmonUtils.getPixelmonEntityClasses());

		// remove any entities that are inherited from other pixelmon mod entities
		for (int i = entityClasses.size() - 1; i >= 0; i--) {
			if (entityClasses.contains(entityClasses.get(i).getSuperclass())) {
				entityClasses.remove(i);
			}
		}

		for (Class<?> entityClass : entityClasses) {
			String key = entityClass.getTypeName();

			if (processed.contains(key)) {
				continue;
			}
			processed.add(key);

			registrar.registerComponentProvider(this, TooltipPosition.HEAD, entityClass);
			registrar.registerComponentProvider(this, TooltipPosition.BODY, entityClass);
			registrar.registerEntityDataProvider(this, entityClass);
		}
	}

}
