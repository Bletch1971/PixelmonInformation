package bletch.pixelmoninformation.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;

import bletch.pixelmoninformation.core.ModCommonConfig;

public class SyncCommonConfigPacket {
	private final byte[] configData;

	public SyncCommonConfigPacket(PacketBuffer buf) {
		this.configData = buf.readByteArray();
	}

	public SyncCommonConfigPacket(final byte[] configFileData) {
		this.configData = configFileData;
	}

	public static void buffer(SyncCommonConfigPacket message, PacketBuffer buf) {
		buf.writeByteArray(message.configData);
	}

	public static void handler(SyncCommonConfigPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			if (context.getDirection() != NetworkDirection.PLAY_TO_CLIENT) {
				return;
			}

			ModCommonConfig.setConfig(message.configData);
		});
		context.setPacketHandled(true);
	}
}
