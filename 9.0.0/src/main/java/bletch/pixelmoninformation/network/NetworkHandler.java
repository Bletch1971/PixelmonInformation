package bletch.pixelmoninformation.network;

import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

	public static SimpleChannel INSTANCE;
	private static int ID = 0;
	private static final String PROTOCOL_VERSION = "1";

	public static int nextID() {
		return ID++;
	}

	public static void initialize() {
		INSTANCE = NetworkRegistry.newSimpleChannel(
				new ResourceLocation(ModDetails.MOD_ID, "network"), 
				() -> PROTOCOL_VERSION,
				PROTOCOL_VERSION::equals, 
				PROTOCOL_VERSION::equals);

		INSTANCE.registerMessage(
				nextID(), 
				SyncCommonConfigPacket.class, 
				SyncCommonConfigPacket::buffer,
				SyncCommonConfigPacket::new, 
				SyncCommonConfigPacket::handler);
	}
}
