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
	private final ForgeConfigSpec.BooleanValue enableJeiIntegration;
	private final ForgeConfigSpec.BooleanValue enableWailaIntegration;
	private final ForgeConfigSpec.BooleanValue enableTopIntegration;

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

		BUILDER.push("tooltips");
		{
			BUILDER.comment("If true, will integrate with JEI.");
			enableJeiIntegration = BUILDER.define("enableJeiIntegration", true);
		}
		BUILDER.pop();

		BUILDER.push("waila");
		{
			BUILDER.comment("If true, will integrate with Waila.");
			enableWailaIntegration = BUILDER.define("enableWailaIntegration", true);
		}
		BUILDER.pop();

		BUILDER.push("theoneprobe");
		{
			BUILDER.comment("If true, will integrate with The One Probe.");
			enableTopIntegration = BUILDER.define("enableTopIntegration", true);
		}
		BUILDER.pop();

		this.configSpec = BUILDER.build();
	}

	public boolean enableJeiIntegration() {
		return this.enableJeiIntegration.get();
	}

	public boolean enableWailaIntegration() {
		return this.enableWailaIntegration.get();
	}

	public boolean enableTopIntegration() {
		return this.enableTopIntegration.get();
	}
}
