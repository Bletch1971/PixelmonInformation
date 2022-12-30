package bletch.pixelmoninformation.core;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.top.PixelmonBlockTop;
import bletch.pixelmoninformation.top.PixelmonEntityTop;
import bletch.pixelmoninformation.waila.PixelmonBlockWaila;
import bletch.pixelmoninformation.waila.PixelmonEntityWaila;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

@ParametersAreNonnullByDefault
public class ModCommonProxy {
	
	public boolean isRemote() {
		return false;
	}
	
	public void registerLootHelper() {
	}
	
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
	
	public void registerTooltips() {
	}
	
	public void registerWaila() {
		if (Loader.isModLoaded(ModDetails.MOD_ID_WAILA) && ModConfig.waila.enableWailaIntegration) {
			ModDetails.MOD_LOGGER.info("Registering blocks with Waila");
			FMLInterModComms.sendMessage(ModDetails.MOD_ID_WAILA, "register", PixelmonBlockWaila.class.getTypeName() + ".callbackRegister");
			ModDetails.MOD_LOGGER.info("Registered blocks with Waila");
			
			ModDetails.MOD_LOGGER.info("Registering entities with Waila");
			FMLInterModComms.sendMessage(ModDetails.MOD_ID_WAILA, "register", PixelmonEntityWaila.class.getTypeName() + ".callbackRegister");
			ModDetails.MOD_LOGGER.info("Registered entities with Waila");
		}
	}
	
}
