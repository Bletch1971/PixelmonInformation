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
	private final ForgeConfigSpec.BooleanValue restrictToAdvancedTooltips;
	private final ForgeConfigSpec.BooleanValue useSneakKey;
	private final ForgeConfigSpec.BooleanValue showSneakKeyInfo;

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
			restrictToAdvancedTooltips = BUILDER.define("restrictToAdvancedTooltips", false);

			BUILDER.comment("If true, will only show tooltip information when sneak key pressed.");
			useSneakKey = BUILDER.define("useSneakKey", true);

			BUILDER.comment("If true, will show the hold sneak key for more information.");
			showSneakKeyInfo = BUILDER.define("showSneakKeyInfo", true);
		}
		BUILDER.pop();

		this.configSpec = BUILDER.build();
	}

	public boolean enableTooltipIntegration() {
		return this.enableTooltipIntegration.get();
	}

	public boolean restrictToAdvancedTooltips() {
		return this.restrictToAdvancedTooltips.get();
	}

	public boolean useSneakKey() {
		return this.useSneakKey.get();
	}

	public boolean showSneakKeyInfo() {
		return this.showSneakKeyInfo.get();
	}
}
