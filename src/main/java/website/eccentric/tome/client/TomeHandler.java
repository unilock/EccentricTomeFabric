package website.eccentric.tome.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class TomeHandler {
    public static void onOpenTome(ItemStack tome) {
        Minecraft.getInstance().setScreen(new TomeScreen(tome));
    }
}
