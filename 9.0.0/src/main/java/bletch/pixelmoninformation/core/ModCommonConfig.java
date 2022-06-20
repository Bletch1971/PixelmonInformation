package bletch.pixelmoninformation.core;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ModCommonConfig {

	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ModCommonConfig instance = new ModCommonConfig();

	private final ForgeConfigSpec configSpec;
	//private final ForgeConfigSpec.BooleanValue enableJeiIntegration;
	private final ForgeConfigSpec.BooleanValue enableWailaIntegration;
	private final ForgeConfigSpec.BooleanValue enableTopIntegration;
	
	private final ForgeConfigSpec.BooleanValue wailaUseCrouchKey;
	private final ForgeConfigSpec.BooleanValue wailaShowBlockInformation;
	private final ForgeConfigSpec.BooleanValue wailaBlocksShowTooltip;
	private final ForgeConfigSpec.BooleanValue wailaBlocksShowInformation;
	//private final ForgeConfigSpec.BooleanValue wailaBlocksShowGrowthStages;
	private final ForgeConfigSpec.BooleanValue wailaShowEntityInformation;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonDescription;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonOwner;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonLevel;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonPokedexNumber;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonGeneration;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonEVYield;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonTypeInformation;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonNatureInformation;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonGrowthInformation;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonEVs;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonIVs;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonTypeMatchupInformation;
	private final ForgeConfigSpec.BooleanValue wailaEntityShowPokemonCaught;
	
	private final ForgeConfigSpec.BooleanValue topUseCrouchKey;
	private final ForgeConfigSpec.BooleanValue topShowBlockInformation;
	private final ForgeConfigSpec.BooleanValue topBlocksShowTooltip;
	private final ForgeConfigSpec.BooleanValue topBlocksShowInformation;
	//private final ForgeConfigSpec.BooleanValue topBlocksShowGrowthStages;
	private final ForgeConfigSpec.BooleanValue topShowEntityInformation;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonDescription;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonOwner;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonLevel;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonPokedexNumber;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonGeneration;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonEVYield;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonTypeInformation;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonNatureInformation;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonGrowthInformation;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonEVs;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonIVs;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonTypeMatchupInformation;
	private final ForgeConfigSpec.BooleanValue topEntityShowPokemonCaught;

	public static void initialize(Path file) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(file)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();

		configData.load();

		instance.configSpec.setConfig(configData);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, instance.configSpec);
	}

	private ModCommonConfig() {

//		BUILDER.push("jei");
//		{
//			BUILDER.comment("If true, will integrate with JEI.");
//			enableJeiIntegration = BUILDER.define("enableJeiIntegration", true);
//		}
//		BUILDER.pop();

		BUILDER.push("waila");
		{
			BUILDER.comment("If true, will integrate with Waila.");
			enableWailaIntegration = BUILDER.define("enableWailaIntegration", true);
			BUILDER.comment("If true, will only show information when crouching.");
			wailaUseCrouchKey = BUILDER.define("wailaUseCrouchKey", false);
			
			BUILDER.push("blocks");
			{
				BUILDER.comment("If true, will show the block information in Waila.");
				wailaShowBlockInformation = BUILDER.define("wailaShowBlockInformation", true);
				BUILDER.comment("If true, will show the block tooltip in Waila.");
				wailaBlocksShowTooltip = BUILDER.define("wailaBlocksShowTooltip", true);
				BUILDER.comment("If true, will show the block information in Waila.");
				wailaBlocksShowInformation = BUILDER.define("wailaBlocksShowInformation", true);
//				BUILDER.comment("If true, will show any growth stages.");
//				wailaBlocksShowGrowthStages = BUILDER.define("wailaBlocksShowGrowthStages", true);
			}
			BUILDER.pop();
			
			BUILDER.push("entities");
			{
				BUILDER.comment("If true, will show the entity information in Waila.");
				wailaShowEntityInformation = BUILDER.define("wailaShowEntityInformation", true);
				BUILDER.comment("If true, will show the description of the pokémon.");
				wailaEntityShowPokemonDescription = BUILDER.define("wailaEntityShowPokemonDescription", true);
				BUILDER.comment("If true, will show the owner's name of the pokémon.");
				wailaEntityShowPokemonOwner = BUILDER.define("wailaEntityShowPokemonOwner", true);
				BUILDER.comment("If true, will show the level of the pokémon.");
				wailaEntityShowPokemonLevel = BUILDER.define("wailaEntityShowPokemonLevel", true);
				BUILDER.comment("If true, will show the Pokédex Number of the pokémon.");
				wailaEntityShowPokemonPokedexNumber = BUILDER.define("wailaEntityShowPokemonPokedexNumber", true);
				BUILDER.comment("If true, will show the Generation of the pokémon.");
				wailaEntityShowPokemonGeneration = BUILDER.define("wailaEntityShowPokemonGeneration", true);
				BUILDER.comment("If true, will show the EV (effort value) yield of the pokémon.");
				wailaEntityShowPokemonEVYield = BUILDER.define("wailaEntityShowPokemonEVYield", true);
				BUILDER.comment("If true, will show the type information of the pokémon.");
				wailaEntityShowPokemonTypeInformation = BUILDER.define("wailaEntityShowPokemonTypeInformation", true);
				BUILDER.comment("If true, will show the nature information of the pokémon.");
				wailaEntityShowPokemonNatureInformation = BUILDER.define("wailaEntityShowPokemonNatureInformation", true);
				BUILDER.comment("If true, will show the growth information of the pokémon.");
				wailaEntityShowPokemonGrowthInformation = BUILDER.define("wailaEntityShowPokemonGrowthInformation", true);
				BUILDER.comment("If true, will show the EV (effort value) stats of the pokémon.");
				wailaEntityShowPokemonEVs = BUILDER.define("wailaEntityShowPokemonEVs", true);
				BUILDER.comment("If true, will show the IV (individual values) stats of the pokémon.");
				wailaEntityShowPokemonIVs = BUILDER.define("wailaEntityShowPokemonIVs", true);
				BUILDER.comment("If true, will show the type match-up information of the pokémon.");
				wailaEntityShowPokemonTypeMatchupInformation = BUILDER.define("wailaEntityShowPokemonTypeMatchupInformation", true);
				BUILDER.comment("If true, will show if the pokémon has been caught.");
				wailaEntityShowPokemonCaught = BUILDER.define("wailaEntityShowPokemonCaught", true);
			}
			BUILDER.pop();
		}
		BUILDER.pop();

		BUILDER.push("theoneprobe");
		{
			BUILDER.comment("If true, will integrate with The One Probe.");
			enableTopIntegration = BUILDER.define("enableTopIntegration", true);
			BUILDER.comment("If true, will only show information when crouching.");
			topUseCrouchKey = BUILDER.define("topUseCrouchKey", true);
			
			BUILDER.push("blocks");
			{
				BUILDER.comment("If true, will show the block information in The One Probe.");
				topShowBlockInformation = BUILDER.define("topShowBlockInformation", true);
				BUILDER.comment("If true, will show the block tooltip in The One Probe.");
				topBlocksShowTooltip = BUILDER.define("topBlocksShowTooltip", true);
				BUILDER.comment("If true, will show the block information in The One Probe.");
				topBlocksShowInformation = BUILDER.define("topBlocksShowInformation", true);
//				BUILDER.comment("If true, will show any growth stages.");
//				topBlocksShowGrowthStages = BUILDER.define("topBlocksShowGrowthStages", true);
			}
			BUILDER.pop();
			
			BUILDER.push("entities");
			{
				BUILDER.comment("If true, will show the entity information in The One Probe.");
				topShowEntityInformation = BUILDER.define("topShowEntityInformation", true);
				BUILDER.comment("If true, will show the description of the pokémon.");
				topEntityShowPokemonDescription = BUILDER.define("topEntityShowPokemonDescription", true);
				BUILDER.comment("If true, will show the owner's name of the pokémon.");
				topEntityShowPokemonOwner = BUILDER.define("topEntityShowPokemonOwner", true);
				BUILDER.comment("If true, will show the level of the pokémon.");
				topEntityShowPokemonLevel = BUILDER.define("topEntityShowPokemonLevel", true);
				BUILDER.comment("If true, will show the Pokédex Number of the pokémon.");
				topEntityShowPokemonPokedexNumber = BUILDER.define("topEntityShowPokemonPokedexNumber", true);
				BUILDER.comment("If true, will show the Generation of the pokémon.");
				topEntityShowPokemonGeneration = BUILDER.define("topEntityShowPokemonGeneration", true);
				BUILDER.comment("If true, will show the EV (effort value) yield of the pokémon.");
				topEntityShowPokemonEVYield = BUILDER.define("topEntityShowPokemonEVYield", true);
				BUILDER.comment("If true, will show the type information of the pokémon.");
				topEntityShowPokemonTypeInformation = BUILDER.define("topEntityShowPokemonTypeInformation", true);
				BUILDER.comment("If true, will show the nature information of the pokémon.");
				topEntityShowPokemonNatureInformation = BUILDER.define("topEntityShowPokemonNatureInformation", true);
				BUILDER.comment("If true, will show the growth information of the pokémon.");
				topEntityShowPokemonGrowthInformation = BUILDER.define("topEntityShowPokemonGrowthInformation", true);
				BUILDER.comment("If true, will show the EV (effort value) stats of the pokémon.");
				topEntityShowPokemonEVs = BUILDER.define("topEntityShowPokemonEVs", true);
				BUILDER.comment("If true, will show the IV (individual values) stats of the pokémon.");
				topEntityShowPokemonIVs = BUILDER.define("topEntityShowPokemonIVs", true);
				BUILDER.comment("If true, will show the type match-up information of the pokémon.");
				topEntityShowPokemonTypeMatchupInformation = BUILDER.define("topEntityShowPokemonTypeMatchupInformation", true);
				BUILDER.comment("If true, will show if the pokémon has been caught.");
				topEntityShowPokemonCaught = BUILDER.define("topEntityShowPokemonCaught", true);
			}
			BUILDER.pop();
		}
		BUILDER.pop();

		this.configSpec = BUILDER.build();
	}

//	public boolean enableJeiIntegration() {
//		return this.enableJeiIntegration.get();
//	}
	public boolean enableWailaIntegration() {
		return this.enableWailaIntegration.get();
	}
	public boolean enableTopIntegration() {
		return this.enableTopIntegration.get();
	}
	
	public boolean wailaUseCrouchKey() {
		return this.wailaUseCrouchKey.get();
	}
	public boolean wailaShowBlockInformation() {
		return this.wailaShowBlockInformation.get();
	}
	public boolean wailaBlocksShowTooltip() {
		return this.wailaBlocksShowTooltip.get();
	}
	public boolean wailaBlocksShowInformation() {
		return this.wailaBlocksShowInformation.get();
	}
//	public boolean wailaBlocksShowGrowthStages() {
//		return this.wailaBlocksShowGrowthStages.get();
//	}
	public boolean wailaShowEntityInformation() {
		return this.wailaShowEntityInformation.get();
	}
	public boolean wailaEntityShowPokemonDescription() {
		return this.wailaEntityShowPokemonDescription.get();
	}
	public boolean wailaEntityShowPokemonOwner() {
		return this.wailaEntityShowPokemonOwner.get();
	}
	public boolean wailaEntityShowPokemonLevel() {
		return this.wailaEntityShowPokemonLevel.get();
	}
	public boolean wailaEntityShowPokemonPokedexNumber() {
		return this.wailaEntityShowPokemonPokedexNumber.get();
	}
	public boolean wailaEntityShowPokemonGeneration() {
		return this.wailaEntityShowPokemonGeneration.get();
	}
	public boolean wailaEntityShowPokemonEVYield() {
		return this.wailaEntityShowPokemonEVYield.get();
	}
	public boolean wailaEntityShowPokemonTypeInformation() {
		return this.wailaEntityShowPokemonTypeInformation.get();
	}
	public boolean wailaEntityShowPokemonNatureInformation() {
		return this.wailaEntityShowPokemonNatureInformation.get();
	}
	public boolean wailaEntityShowPokemonGrowthInformation() {
		return this.wailaEntityShowPokemonGrowthInformation.get();
	}
	public boolean wailaEntityShowPokemonEVs() {
		return this.wailaEntityShowPokemonEVs.get();
	}
	public boolean wailaEntityShowPokemonIVs() {
		return this.wailaEntityShowPokemonIVs.get();
	}
	public boolean wailaEntityShowPokemonTypeMatchupInformation() {
		return this.wailaEntityShowPokemonTypeMatchupInformation.get();
	}
	public boolean wailaEntityShowPokemonCaught() {
		return this.wailaEntityShowPokemonCaught.get();
	}
	
	public boolean topUseCrouchKey() {
		return this.topUseCrouchKey.get();
	}
	public boolean topShowBlockInformation() {
		return this.topShowBlockInformation.get();
	}
	public boolean topBlocksShowTooltip() {
		return this.topBlocksShowTooltip.get();
	}
	public boolean topBlocksShowInformation() {
		return this.topBlocksShowInformation.get();
	}
//	public boolean topBlocksShowGrowthStages() {
//		return this.topBlocksShowGrowthStages.get();
//	}
	public boolean topShowEntityInformation() {
		return this.topShowEntityInformation.get();
	}
	public boolean topEntityShowPokemonDescription() {
		return this.topEntityShowPokemonDescription.get();
	}
	public boolean topEntityShowPokemonOwner() {
		return this.topEntityShowPokemonOwner.get();
	}
	public boolean topEntityShowPokemonLevel() {
		return this.topEntityShowPokemonLevel.get();
	}
	public boolean topEntityShowPokemonPokedexNumber() {
		return this.topEntityShowPokemonPokedexNumber.get();
	}
	public boolean topEntityShowPokemonGeneration() {
		return this.topEntityShowPokemonGeneration.get();
	}
	public boolean topEntityShowPokemonEVYield() {
		return this.topEntityShowPokemonEVYield.get();
	}
	public boolean topEntityShowPokemonTypeInformation() {
		return this.topEntityShowPokemonTypeInformation.get();
	}
	public boolean topEntityShowPokemonNatureInformation() {
		return this.topEntityShowPokemonNatureInformation.get();
	}
	public boolean topEntityShowPokemonGrowthInformation() {
		return this.topEntityShowPokemonGrowthInformation.get();
	}
	public boolean topEntityShowPokemonEVs() {
		return this.topEntityShowPokemonEVs.get();
	}
	public boolean topEntityShowPokemonIVs() {
		return this.topEntityShowPokemonIVs.get();
	}
	public boolean topEntityShowPokemonTypeMatchupInformation() {
		return this.topEntityShowPokemonTypeMatchupInformation.get();
	}
	public boolean topEntityShowPokemonCaught() {
		return this.topEntityShowPokemonCaught.get();
	}
}
