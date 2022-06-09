package bletch.pixelmoninformation.core;

import bletch.pixelmoninformation.tooltips.PixelmonItemTooltip;
import net.minecraftforge.common.MinecraftForge;

public class ModClientProxy extends ModCommonProxy {

	@Override
	public void registerTooltips() {
		if (ModClientConfig.instance.enableTooltipIntegration()) {
			ModDetails.MOD_LOGGER.info("Registering Item Tooltip");
			MinecraftForge.EVENT_BUS.register(new PixelmonItemTooltip());
			ModDetails.MOD_LOGGER.info("Registered Item Tooltip");
		}
	}

}
