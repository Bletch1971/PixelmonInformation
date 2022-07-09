package bletch.pixelmoninformation.core;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class ModClientConfig {

	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ModClientConfig INSTANCE = new ModClientConfig();
	public static ForgeConfigSpec CONFIG_SPEC;
	
	private final ForgeConfigSpec.BooleanValue enableTooltipIntegration;
	private final ForgeConfigSpec.BooleanValue tooltipsRestrictToAdvanced;
	private final ForgeConfigSpec.BooleanValue tooltipsUseCrouchKey;
	private final ForgeConfigSpec.BooleanValue tooltipsShowCrouchKeyInfo;

	public static void initialize() {
		final CommentedFileConfig configData = CommentedFileConfig
				.builder(getConfigPath())
				.sync()
				.preserveInsertionOrder()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();

		configData.load();

		CONFIG_SPEC.setConfig(configData);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CONFIG_SPEC);
	}
	
    public static Path getConfigPath() {
        return FMLPaths.CONFIGDIR.get().resolve(ModDetails.MOD_ID + "-client.toml").toAbsolutePath();
    }

	private ModClientConfig() {

		BUILDER.push("tooltips");
		{
			BUILDER.comment("If true, will integrate with item tooltips.");
			enableTooltipIntegration = BUILDER.define("enableTooltipIntegration", true);

			BUILDER.comment("If true, will only show tooltip information when advanced tooltip enabled.");
			tooltipsRestrictToAdvanced = BUILDER.define("tooltipsRestrictToAdvanced", false);

			BUILDER.comment("If true, will only show tooltip information when crouch key pressed.");
			tooltipsUseCrouchKey = BUILDER.define("tooltipsUseCrouchKey", true);

			BUILDER.comment("If true, will show the hold crouch key for more information.");
			tooltipsShowCrouchKeyInfo = BUILDER.define("tooltipsShowCrouchKeyInfo", true);
		}
		BUILDER.pop();

		CONFIG_SPEC = BUILDER.build();
	}

	public boolean enableTooltipIntegration() {
		return this.enableTooltipIntegration.get();
	}

	public boolean tooltipsRestrictToAdvanced() {
		return this.tooltipsRestrictToAdvanced.get();
	}

	public boolean tooltipsUseCrouchKey() {
		return this.tooltipsUseCrouchKey.get();
	}

	public boolean tooltipsShowCrouchKeyInfo() {
		return this.tooltipsShowCrouchKeyInfo.get();
	}
}
