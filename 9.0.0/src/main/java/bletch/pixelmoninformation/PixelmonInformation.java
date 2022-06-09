package bletch.pixelmoninformation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import bletch.pixelmoninformation.core.ModClientProxy;
import bletch.pixelmoninformation.core.ModCommonConfig;
import bletch.pixelmoninformation.core.ModCommonProxy;
import bletch.pixelmoninformation.core.ModClientConfig;
import bletch.pixelmoninformation.core.ModDetails;

@Mod(ModDetails.MOD_ID)
public class PixelmonInformation
{
	public static ModCommonProxy proxy;

	public PixelmonInformation() {    	
		if (FMLEnvironment.dist == Dist.CLIENT) {            
			proxy = new ModClientProxy();
		} else {
			proxy = new ModCommonProxy();
		}

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		ModCommonConfig.initialize(FMLPaths.CONFIGDIR.get().resolve(ModDetails.MOD_ID + "-common.toml"));

		proxy.registerWaila();
		proxy.registerTheOneProbe();
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		ModClientConfig.initialize(FMLPaths.CONFIGDIR.get().resolve(ModDetails.MOD_ID + "-client.toml"));

		proxy.registerTooltips();

		//LootHelper.initialize();
	}
}
