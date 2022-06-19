package bletch.pixelmoninformation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import bletch.pixelmoninformation.core.ModCommonConfig;
import bletch.pixelmoninformation.core.ModClientConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.tooltips.PixelmonItemTooltip;
import bletch.pixelmoninformation.top.PixelmonBlockTop;
import bletch.pixelmoninformation.top.PixelmonEntityTop;
import bletch.pixelmoninformation.top.WrappedTextElement;
import mcjty.theoneprobe.TheOneProbe;

@Mod(ModDetails.MOD_ID)
public class PixelmonInformation
{

	public PixelmonInformation() {

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueueIMC);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		ModCommonConfig.initialize(FMLPaths.CONFIGDIR.get().resolve(ModDetails.MOD_ID + "-common.toml"));
		
		if (ModList.get().isLoaded(ModDetails.MOD_ID_TOP) && ModCommonConfig.instance.enableTopIntegration()) {
			WrappedTextElement.ELEMENT_ID = TheOneProbe.theOneProbeImp.registerElementFactory(new WrappedTextElement.Factory());
		}
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		ModClientConfig.initialize(FMLPaths.CONFIGDIR.get().resolve(ModDetails.MOD_ID + "-client.toml"));

		if (ModClientConfig.instance.enableTooltipIntegration()) {
			MinecraftForge.EVENT_BUS.register(new PixelmonItemTooltip());
		}

		//LootHelper.initialize();
	}

	private void onEnqueueIMC(final InterModEnqueueEvent event) {
		if (ModList.get().isLoaded(ModDetails.MOD_ID_TOP) && ModCommonConfig.instance.enableTopIntegration()) {
			InterModComms.sendTo(ModDetails.MOD_ID_TOP, "getTheOneProbe", PixelmonBlockTop.getTheOneProbe::new);
			InterModComms.sendTo(ModDetails.MOD_ID_TOP, "getTheOneProbe", PixelmonEntityTop.getTheOneProbe::new);
		}
    }
}
