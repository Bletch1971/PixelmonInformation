package bletch.pixelmoninformation;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModCommonProxy;
import bletch.pixelmoninformation.core.ModConfig;
import bletch.pixelmoninformation.core.ModDetails;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=ModDetails.MOD_ID, name=ModDetails.MOD_NAME, version=ModDetails.MOD_VERSION, dependencies=ModDetails.MOD_DEPENDENCIES, acceptableRemoteVersions="*", acceptedMinecraftVersions="[1.12.2]", updateJSON=ModDetails.MOD_UPDATE_URL)
@ParametersAreNonnullByDefault
public class PixelmonInformation {
	
	@Instance(ModDetails.MOD_ID)
	public static PixelmonInformation instance;

	@SidedProxy(clientSide = ModDetails.MOD_CLIENT_PROXY_CLASS, serverSide = ModDetails.MOD_SERVER_PROXY_CLASS)
	public static ModCommonProxy proxy;
	
	@Mod.EventHandler
	public void preInitialize(FMLPreInitializationEvent event) {
		instance = this;
		
		MinecraftForge.EVENT_BUS.register(new ModConfig());
		
		proxy.resetDebug();
	}
	  
	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event) {
		proxy.registerTooltips();
		proxy.registerWaila();
		proxy.registerTheOneProbe();
		proxy.registerGui();
	}
	  
	@Mod.EventHandler
	public void postInitialize(FMLPostInitializationEvent event) {
		proxy.registerLootHelper();
	}
	
}
