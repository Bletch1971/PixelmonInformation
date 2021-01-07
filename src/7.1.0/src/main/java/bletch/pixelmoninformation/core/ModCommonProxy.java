package bletch.pixelmoninformation.core;

import java.io.File;
import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.utils.DebugUtils;
import bletch.pixelmoninformation.waila.PixelmonBlockWaila;
import bletch.pixelmoninformation.waila.PixelmonEntityWaila;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

@ParametersAreNonnullByDefault
public class ModCommonProxy {
	
	public boolean isRemote() {
		return false;
	}

	public File getMinecraftDirectory()	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().getFile("");
	}
	
	public void registerGui() {
	}
	
	public void registerLootHelper() {
	}
	
	public void registerTheOneProbe() {
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
	
	public void resetDebug() {
		if (ModConfig.debug.enableDebug) {
			DebugUtils.resetDebug();
		}		
	}
	
}
