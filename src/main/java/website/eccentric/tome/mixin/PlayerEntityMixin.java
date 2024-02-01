package website.eccentric.tome.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import website.eccentric.tome.Tome;
import website.eccentric.tome.TomeItem;

@Mixin(Player.class)
public class PlayerEntityMixin {
    @Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "RETURN"))
    public void drop(ItemStack stack, boolean bl, boolean bl2, CallbackInfoReturnable<ItemEntity> cir) {
        var entity = cir.getReturnValue();

        if (((Player) (Object) this).isShiftKeyDown() && Tome.isTome(stack) && !(stack.getItem() instanceof TomeItem)) {
            var detatchment = Tome.revert(stack);
            var level = entity.getCommandSenderWorld();

            if (!level.isClientSide) {
                level.addFreshEntity(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), detatchment));
            }

            entity.setItem(stack);
        }
    }
}
