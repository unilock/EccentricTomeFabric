package website.eccentric.tome.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import website.eccentric.tome.EccentricTome;
import website.eccentric.tome.network.packet.ConvertC2SPacket;
import website.eccentric.tome.network.packet.RevertC2SPacket;

public class TomeChannel {
	public static final ResourceLocation CONVERT_ID = EccentricTome.id("convert");
	public static final ResourceLocation REVERT_ID = EccentricTome.id("revert");

    public static void register() {
		ServerPlayNetworking.registerGlobalReceiver(CONVERT_ID, ConvertC2SPacket::receive);
		ServerPlayNetworking.registerGlobalReceiver(REVERT_ID, RevertC2SPacket::receive);
    }
}
