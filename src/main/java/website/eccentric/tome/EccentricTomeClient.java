package website.eccentric.tome;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import website.eccentric.tome.client.RenderGuiOverlayHandler;
import website.eccentric.tome.client.TomeHandler;
import website.eccentric.tome.events.ClientPreAttackCallback;
import website.eccentric.tome.events.OpenTomeCallback;
import website.eccentric.tome.network.TomeChannel;

public class EccentricTomeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPreAttackCallback.EVENT.register(EccentricTomeClient::onLeftClickEmpty);
        HudRenderCallback.EVENT.register(RenderGuiOverlayHandler::onRender);
        OpenTomeCallback.EVENT.register(TomeHandler::onOpenTome);
    }

    private static boolean onLeftClickEmpty(Minecraft client, LocalPlayer player, int clickCount) {
        var stack = player.getMainHandItem();

        if (Tome.isTome(stack) && !(stack.getItem() instanceof TomeItem)) {
            ClientPlayNetworking.send(TomeChannel.REVERT_ID, PacketByteBufs.empty());
            client.gameRenderer.itemInHandRenderer.itemUsed(InteractionHand.MAIN_HAND);
            return true;
        }

        return false;
    }
}
