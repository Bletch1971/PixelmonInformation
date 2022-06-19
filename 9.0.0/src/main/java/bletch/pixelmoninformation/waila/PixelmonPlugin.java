package bletch.pixelmoninformation.waila;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;

@ParametersAreNonnullByDefault
@WailaPlugin(ModDetails.MOD_ID)
public class PixelmonPlugin implements IWailaPlugin {

	@Override
	public void register(IRegistrar registrar) {
		PixelmonBlockWaila.INSTANCE.register(registrar);
		PixelmonEntityWaila.INSTANCE.register(registrar);
	}
	
}
