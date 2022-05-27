package bletch.pixelmoninformation.core;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Config(modid=ModDetails.MOD_ID, category="")
@ParametersAreNonnullByDefault
public class ModConfig {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent event) {
		
		if (event.getModID().equals(ModDetails.MOD_ID)) {
			ConfigManager.sync(ModDetails.MOD_ID, Type.INSTANCE);
		}
		
	}
	
	@Config.LangKey("config.debug")
	public static final Debug debug = new Debug();
	
	@Config.LangKey("config.tooltips")
	public static final Tooltips tooltips = new Tooltips();
	
	@Config.LangKey("config.jei")
	public static final Jei jei = new Jei();
	
	@Config.LangKey("config.waila")
	public static final Waila waila = new Waila();
	
	@Config.LangKey("config.top")
	public static final TheOneProbe top = new TheOneProbe();
	
	@Config.LangKey("config.gui")
	//public static final GuiSettings gui = new GuiSettings();
	
	public static class Debug {
		
		@Config.Comment("If true, debug information will be output to the console.")
		@Config.LangKey("config.debug.enableDebug")
		public boolean enableDebug = false;		
	
		@Config.Comment("If true, the item translation key will be show in the tooltip.")
		@Config.LangKey("config.debug.showTooltipTranslationKey")
		public boolean showTooltipTranslationKey = false;	
		
		@Config.Comment("If true, any blocks/items that are missing an Information record will be output to the console.")
		@Config.LangKey("config.debug.showJeiBlocksMissingInformation")
		@Config.RequiresMcRestart
		public boolean showJeiMissingInformation = false;			
		
		@Config.Comment("If true, any blocks/items that register an Information record will be output to the console.")
		@Config.LangKey("config.debug.showJeiBlocksRegisteredInformation")
		@Config.RequiresMcRestart
		public boolean showJeiRegisteredInformation = false;
		
		@Config.Comment("If true, any blocks/items that are missing an Information record will be registered.")
		@Config.LangKey("config.debug.registerJeiBlocksMissingInformation")
		@Config.RequiresMcRestart
		public boolean registerJeiMissingInformation = false;		
		
		@Config.Comment("If true, the block translation key will be show in The One Probe.")
		@Config.LangKey("config.debug.showTopBlockTranslationKey")
		public boolean showTopBlockTranslationKey = false;	
		
		@Config.Comment("If true, any blocks that are registered will be output to the console.")
		@Config.LangKey("config.debug.showWailaBlocksRegistered")
		@Config.RequiresMcRestart
		public boolean showWailaBlocksRegistered = false;	
		
		@Config.Comment("If true, the block translation key will be show in Waila.")
		@Config.LangKey("config.debug.showWailaBlockTranslationKey")
		public boolean showWailaBlockTranslationKey = false;	
		
		@Config.Comment("If true, any entities that are registered will be output to the console.")
		@Config.LangKey("config.debug.showWailaEntitiesRegistered")
		@Config.RequiresMcRestart
		public boolean showWailaEntitiesRegistered = false;	
	}
	
	public static class Tooltips {
		
		@Config.Comment("If true, will integrate with item tooltips.")
		@Config.LangKey("config.tooltips.enableTooltipIntegration")
		@Config.RequiresMcRestart
		public boolean enableTooltipIntegration = true;

		@Config.Comment("If true, will only show tooltip information when Shift key pressed.")
		@Config.LangKey("config.tooltips.useShiftKey")
		public boolean useShiftKey = true;
		
		@Config.Comment("If true, will only show tooltip information when Advanced Tooltips enabled (F3+H).")
		@Config.LangKey("config.tooltips.restrictToAdvancedTooltips")
		public boolean restrictToAdvancedTooltips = false;

		@Config.Comment("If true, will show the hold shift key for more information.")
		@Config.LangKey("config.tooltips.showShiftKeyInfo")
		public boolean showShiftKeyInfo = true;
	}

	public static class Jei {
		
		@Config.Comment("If true, will integrate with JEI.")
		@Config.LangKey("config.jei.enableJeiIntegration")
		@Config.RequiresMcRestart
		public boolean enableJeiIntegration = true;
		
		@Config.Comment("If true, will show the information tab in JEI.")
		@Config.LangKey("config.jei.showJeiInformationTab")
		@Config.RequiresMcRestart
		public boolean showJeiInformationTab = true;	
		
		@Config.Comment("If true, will show the recipe tabs in JEI.")
		@Config.LangKey("config.jei.showJeiRecipeTabs")
		@Config.RequiresMcRestart
		public boolean showJeiRecipeTabs = true;		
		
		@Config.Comment("If true, will show the dungeon chest tab in JEI. If the Just Enough Resources (JER) mod is loaded, this will be disabled.")
		@Config.LangKey("config.jei.showJeiDungeonChestTab")
		@Config.RequiresMcRestart
		public boolean showJeiDungeonChestTab = true;
		
		@Config.Comment("If true, will not check for other installed mods when showing dungeon chest tab.")
		@Config.LangKey("config.jei.removeJeiDungeonChestTabModCheck")
		@Config.RequiresMcRestart
		public boolean removeJeiDungeonChestTabModCheck = true;
		
		@Config.Comment("If true, will show the poké loot chest tab in JEI.")
		@Config.LangKey("config.jei.showJeiPokeChestTab")
		@Config.RequiresMcRestart
		public boolean showJeiPokeChestTab = true;
		
		@Config.Comment("If true, will show the shop keeper tab in JEI.")
		@Config.LangKey("config.jei.showJeiShopKeeperTab")
		@Config.RequiresMcRestart
		public boolean showJeiShopKeeperTab = true;		
		
		@Config.Comment("If true, will show the pokémon boss tab in JEI.")
		@Config.LangKey("config.jei.showJeiPokemonBossTab")
		@Config.RequiresMcRestart
		public boolean showJeiPokemonBossTab = true;	
		
		@Config.Comment("If true, will show the pokémon tab in JEI.")
		@Config.LangKey("config.jei.showJeiPokemonTab")
		@Config.RequiresMcRestart
		public boolean showJeiPokemonTab = true;
	}

	public static class Waila {
		
		@Config.LangKey("config.waila.blocks")
		public final Blocks blocks = new Blocks();
		
		@Config.LangKey("config.waila.entities")
		public final Entities entities = new Entities();
		
		@Config.Comment("If true, will integrate with Waila.")
		@Config.LangKey("config.waila.enableWailaIntegration")
		@Config.RequiresMcRestart
		public boolean enableWailaIntegration = true;

		@Config.Comment("If true, will only show information when Sneaking.")
		@Config.LangKey("config.waila.useSneaking")
		public boolean useSneaking = false;

		public static class Blocks {
			
			@Config.Comment("If true, will show the block tooltip in Waila.")
			@Config.LangKey("config.waila.blocks.showBlockTooltip")
			public boolean showBlockTooltip = true;	
			
			@Config.Comment("If true, will show the block information in Waila.")
			@Config.LangKey("config.waila.blocks.showBlockInformation")
			public boolean showBlockInformation = true;			
			
			@Config.Comment("If true, will show any growth stages.")
			@Config.LangKey("config.waila.blocks.showGrowthStages")
			public boolean showGrowthStages = true;
		}

		public static class Entities {
		
			@Config.Comment("If true, will show the entity information in Waila.")
			@Config.LangKey("config.waila.entities.showEntityInformation")
			public boolean showEntityInformation = true;
			
			@Config.Comment("If true, will show the description of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonDescription")
			public boolean showPokemonDescription = true;	
			
			@Config.Comment("If true, will show the owner's name of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonOwner")
			public boolean showPokemonOwner = true;	
			
			@Config.Comment("If true, will show the level of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonLevel")
			public boolean showPokemonLevel = true;	
			
			@Config.Comment("If true, will show the National Pokédex Number of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonNationalPokedexNumber")
			public boolean showPokemonNationalPokedexNumber = true;		
			
			@Config.Comment("If true, will show the Generation of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonGeneration")
			public boolean showPokemonGeneration = true;			
			
			@Config.Comment("If true, will show the EV (effort value) yield of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonEVYield")
			public boolean showPokemonEVYield = true;
			
			@Config.Comment("If true, will show the type information of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonTypeInformation")
			public boolean showPokemonTypeInformation = true;			
			
			@Config.Comment("If true, will show the EV (effort value) stats of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonEVs")
			public boolean showPokemonEVs = true;			
			
			@Config.Comment("If true, will show the IV (individual values) stats of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonIVs")
			public boolean showPokemonIVs = true;
			
			@Config.Comment("If true, will show the type match-up information of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonTypeMatchupInformation")
			public boolean showPokemonTypeMatchupInformation = true;
			
			@Config.Comment("If true, will show if the pokémon has been caught.")
			@Config.LangKey("config.waila.entities.showPokemonCaught")
			public boolean showPokemonCaught = true;			
		}

	}

	public static class TheOneProbe {
		
		@Config.LangKey("config.top.blocks")
		public final Blocks blocks = new Blocks();
		
		@Config.LangKey("config.top.entities")
		public final Entities entities = new Entities();
		
		@Config.Comment("If true, will integrate with The One Probe.")
		@Config.LangKey("config.top.enableTopIntegration")
		@Config.RequiresMcRestart
		public boolean enableTopIntegration = true;

		@Config.Comment("If true, will only show information when Sneaking.")
		@Config.LangKey("config.top.useSneaking")
		public boolean useSneaking = true;
		
		public static class Blocks {	
			
			@Config.Comment("If true, will show the block tooltip in The One Probe.")
			@Config.LangKey("config.top.blocks.showBlockTooltip")
			public boolean showBlockTooltip = true;	
			
			@Config.Comment("If true, will show the block information in The One Probe.")
			@Config.LangKey("config.top.blocks.showBlockInformation")
			public boolean showBlockInformation = true;				
			
			@Config.Comment("If true, will show any growth stages.")
			@Config.LangKey("config.top.blocks.showGrowthStages")
			public boolean showGrowthStages = true;
			
		}

		public static class Entities {
		
			@Config.Comment("If true, will show the entity information in The One Probe.")
			@Config.LangKey("config.top.entities.showEntityInformation")
			public boolean showEntityInformation = true;
			
			@Config.Comment("If true, will show the description of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonDescription")
			public boolean showPokemonDescription = true;	
			
			@Config.Comment("If true, will show the owner's name of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonOwner")
			public boolean showPokemonOwner = true;	
			
			@Config.Comment("If true, will show the level of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonLevel")
			public boolean showPokemonLevel = true;	
			
			@Config.Comment("If true, will show the National Pokédex Number of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonNationalPokedexNumber")
			public boolean showPokemonNationalPokedexNumber = true;		
			
			@Config.Comment("If true, will show the Generation of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonGeneration")
			public boolean showPokemonGeneration = true;			
			
			@Config.Comment("If true, will show the EV (effort value) yield of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonEVYield")
			public boolean showPokemonEVYield = true;
			
			@Config.Comment("If true, will show the type information of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonTypeInformation")
			public boolean showPokemonTypeInformation = true;			
			
			@Config.Comment("If true, will show the EV (effort value) stats of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonEVs")
			public boolean showPokemonEVs = true;			
			
			@Config.Comment("If true, will show the IV (individual values) stats of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonIVs")
			public boolean showPokemonIVs = true;
			
			@Config.Comment("If true, will show the type match-up information of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonTypeMatchupInformation")
			public boolean showPokemonTypeMatchupInformation = true;
			
			@Config.Comment("If true, will show if the pokémon has been caught.")
			@Config.LangKey("config.top.entities.showPokemonCaught")
			public boolean showPokemonCaught = true;
			
		}
		
	}

	public static class GuiSettings {
		
		@Config.LangKey("config.gui.pokemonInformationBook")
		public final PokemonInformationBook pokemonInformationBook = new PokemonInformationBook();
		
		@Config.Comment("If true, will integrate with the GUI.")
		@Config.LangKey("config.gui.enableGuiIntegration")
		@Config.RequiresMcRestart
		public boolean enableGuiIntegration = true;

		public static class PokemonInformationBook {
			
			@Config.Comment("If true, will show the Pokémon Information Book when the player right-clicks a Pokémon with a book in hand.")
			@Config.LangKey("config.gui.pokemonInformationBook.enablePokemonInformationBook")
			public boolean enablePokemonInformationBook = true;
			
			@Config.Comment("If true, the Breeding pages will be shown in the Pokémon Information Book.")
			@Config.LangKey("config.gui.pokemonInformationBook.showBreedingPages")
			public boolean showBreedingPages = true;
			
			@Config.Comment("If true, the Drops pages will be shown in the Pokémon Information Book.")
			@Config.LangKey("config.gui.pokemonInformationBook.showDropsPages")
			public boolean showDropsPages = true;
			
			@Config.Comment("If true, the Moves pages will be shown in the Pokémon Information Book.")
			@Config.LangKey("config.gui.pokemonInformationBook.showMovesPages")
			public boolean showMovesPages = true;
			
			@Config.Comment("If true, the Spawn pages will be shown in the Pokémon Information Book.")
			@Config.LangKey("config.gui.pokemonInformationBook.showSpawnPages")
			public boolean showSpawnPages = true;
			
			@Config.Comment("If true, the Statistics pages will be shown in the Pokémon Information Book.")
			@Config.LangKey("config.gui.pokemonInformationBook.showStatsPages")
			public boolean showStatsPages = true;
			
			@Config.Comment("If true, the Types pages will be shown in the Pokémon Information Book.")
			@Config.LangKey("config.gui.pokemonInformationBook.showTypesPages")
			public boolean showTypesPages = true;
			
		}
		
	}

}
