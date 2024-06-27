package website.eccentric.tome.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import website.eccentric.tome.Tome;

public class RevertC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        var hand = Tome.inHand(player);

        if (hand != null) {
            var stack = player.getItemInHand(hand).copy();
            var tome = Tome.revert(stack);
            player.setItemInHand(hand, Tome.attach(tome, stack));
        }
    }
}
