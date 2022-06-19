package bletch.pixelmoninformation.core;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ModClientConfig {

	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ModClientConfig instance = new ModClientConfig();

	private final ForgeConfigSpec configSpec;
	private final ForgeConfigSpec.BooleanValue enableTooltipIntegration;
	private final ForgeConfigSpec.BooleanValue tooltipsRestrictToAdvanced;
	private final ForgeConfigSpec.BooleanValue tooltipsUseCrouchKey;
	private final ForgeConfigSpec.BooleanValue tooltipsShowCrouchKeyInfo;

	public static void initialize(Path file) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(file)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();

		configData.load();

		instance.configSpec.setConfig(configData);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, instance.configSpec);
	}

	private ModClientConfig() {

		BUILDER.push("tooltips");
		{
			BUILDER.comment("If true, will integrate with item tooltips.");
			enableTooltipIntegration = BUILDER.define("enableTooltipIntegration", true);

			BUILDER.comment("If true, the item translation key will be show in the tooltip.");
			tooltipsRestrictToAdvanced = BUILDER.define("tooltipsRestrictToAdvanced", false);

			BUILDER.comment("If true, will only show tooltip information when crouch key pressed.");
			tooltipsUseCrouchKey = BUILDER.define("tooltipsUseCrouchKey", true);

			BUILDER.comment("If true, will show the hold crouch key for more information.");
			tooltipsShowCrouchKeyInfo = BUILDER.define("tooltipsShowCrouchKeyInfo", true);
		}
		BUILDER.pop();

		this.configSpec = BUILDER.build();
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
