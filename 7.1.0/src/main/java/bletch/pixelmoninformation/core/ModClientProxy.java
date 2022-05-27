package bletch.pixelmoninformation.core;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.jei.common.LootHelper;
import bletch.pixelmoninformation.tooltips.PixelmonItemTooltip;
import bletch.pixelmoninformation.top.PixelmonBlockTop;
import bletch.pixelmoninformation.top.PixelmonEntityTop;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

@ParametersAreNonnullByDefault
public class ModClientProxy extends ModCommonProxy {

	@Override
	public boolean isRemote() {
		return true;
	}

	@Override
	public File getMinecraftDirectory()	{
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	@Override
	public void registerLootHelper() {
		LootHelper.postInitialise();
	}
	
	@Override
	public void registerTheOneProbe() {
		if (Loader.isModLoaded(ModDetails.MOD_ID_TOP) && ModConfig.top.enableTopIntegration) {
			ModDetails.MOD_LOGGER.info("Registering blocks with The One Probe");
			FMLInterModComms.sendFunctionMessage(ModDetails.MOD_ID_TOP, "getTheOneProbe", PixelmonBlockTop.class.getTypeName() + "$getTheOneProbe");
			ModDetails.MOD_LOGGER.info("Registered blocks with The One Probe");
			
			ModDetails.MOD_LOGGER.info("Registering entities with The One Probe");
			FMLInterModComms.sendFunctionMessage(ModDetails.MOD_ID_TOP, "getTheOneProbe", PixelmonEntityTop.class.getTypeName() + "$getTheOneProbe");
			ModDetails.MOD_LOGGER.info("Registered entities with The One Probe");
		}
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
