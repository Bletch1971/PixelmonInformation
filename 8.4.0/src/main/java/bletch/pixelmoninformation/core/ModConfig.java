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
		
	@Config.LangKey("config.tooltips")
	public static final Tooltips tooltips = new Tooltips();
	
	@Config.LangKey("config.jei")
	public static final Jei jei = new Jei();
	
	@Config.LangKey("config.waila")
	public static final Waila waila = new Waila();
	
	@Config.LangKey("config.top")
	public static final TheOneProbe top = new TheOneProbe();
		
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

			@Config.Comment("If true, will show the nature information of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonNatureInformation")
			public boolean showPokemonNatureInformation = true;

			@Config.Comment("If true, will show the growth information of the pokémon.")
			@Config.LangKey("config.waila.entities.showPokemonGrowthInformation")
			public boolean showPokemonGrowthInformation = true;
			
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

			@Config.Comment("If true, will show the nature information of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonNatureInformation")
			public boolean showPokemonNatureInformation = true;

			@Config.Comment("If true, will show the growth information of the pokémon.")
			@Config.LangKey("config.top.entities.showPokemonGrowthInformation")
			public boolean showPokemonGrowthInformation = true;
			
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

}
