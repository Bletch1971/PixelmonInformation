package bletch.pixelmoninformation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import bletch.pixelmoninformation.core.ModCommonConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bletch.pixelmoninformation.core.ModClientConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.network.NetworkHandler;
import bletch.pixelmoninformation.tooltips.PixelmonItemTooltip;
import bletch.pixelmoninformation.top.PixelmonBlockTop;
import bletch.pixelmoninformation.top.PixelmonEntityTop;

@Mod(ModDetails.MOD_ID)
public class PixelmonInformation
{
	public static final Logger LOGGER = LogManager.getLogger();

	public PixelmonInformation() {

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueueIMC);

		MinecraftForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		ModCommonConfig.initialize();

		NetworkHandler.initialize();
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		ModClientConfig.initialize();

		MinecraftForge.EVENT_BUS.register(new PixelmonItemTooltip());

		//LootHelper.initialize();
	}

	private void onEnqueueIMC(final InterModEnqueueEvent event) {
		if (ModList.get().isLoaded(ModDetails.MOD_ID_TOP)) {
			InterModComms.sendTo(ModDetails.MOD_ID_TOP, "getTheOneProbe", PixelmonBlockTop.getTheOneProbe::new);
			InterModComms.sendTo(ModDetails.MOD_ID_TOP, "getTheOneProbe", PixelmonEntityTop.getTheOneProbe::new);
		}		
	}

	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getPlayer().level.isClientSide) {
			ModCommonConfig.sendConfig(event.getPlayer());
		}
	}
}
