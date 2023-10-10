package website.eccentric.tome.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import website.eccentric.tome.Tome;

public class ConvertC2SPacket {
	public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
		var hand = Tome.inHand(player);

		if (hand != null) {
			var tome = player.getItemInHand(hand);
			player.setItemInHand(hand, Tome.convert(tome, buf.readItem()));
		}
	}
}
