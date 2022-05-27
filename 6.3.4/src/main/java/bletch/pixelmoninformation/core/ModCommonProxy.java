package bletch.pixelmoninformation.core;

import java.io.File;
import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.utils.DebugUtils;
import net.minecraftforge.fml.common.FMLCommonHandler;

@ParametersAreNonnullByDefault
public class ModCommonProxy {
	
	public boolean isRemote() {
		return false;
	}

	public File getMinecraftDirectory()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance().getFile("");
	}
	
	public void registerGui() {
	}
	
	public void registerTheOneProbe() {
	}
	
	public void registerTooltips() {
	}
	
	public void registerWaila() {
	}
	
	public void resetDebug() {
		if (ModConfig.debug.enableDebug) {
			DebugUtils.resetDebug();
		}		
	}
	
}
