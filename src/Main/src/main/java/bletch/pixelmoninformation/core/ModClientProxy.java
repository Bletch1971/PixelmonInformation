package bletch.pixelmoninformation.core;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.jei.common.LootHelper;
import bletch.pixelmoninformation.tooltips.PixelmonItemTooltip;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

@ParametersAreNonnullByDefault
public class ModClientProxy extends ModCommonProxy {

	@Override
	public boolean isRemote() {
		return true;
	}

	@Override
	public File getMinecraftDirectory() {
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	@Override
	public void registerLootHelper() {
		LootHelper.postInitialise();
	}
	
	@Override
	public void registerTooltips() {
    	if (ModConfig.tooltips.enableTooltipIntegration) {
	    	ModDetails.MOD_LOGGER.info("Registering Item Tooltip");
			MinecraftForge.EVENT_BUS.register(new PixelmonItemTooltip());
			ModDetails.MOD_LOGGER.info("Registered Item Tooltip");
    	}
	}
	
}
