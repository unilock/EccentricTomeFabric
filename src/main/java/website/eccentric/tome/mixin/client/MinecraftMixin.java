/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package website.eccentric.tome.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import website.eccentric.tome.events.ClientPreAttackCallback;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	private boolean fabric_attackCancelled;

	@Shadow
	public LocalPlayer player;

	@Shadow
	@Final
	public Options options;

	@Shadow
	@Nullable
	public MultiPlayerGameMode gameMode;

	@Inject(
		method = "handleKeybinds",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z",
			ordinal = 0
		)
	)
	private void injectHandleKeybindsForPreAttackCallback(CallbackInfo ci) {
		int attackKeyPressCount = ((KeyMappingAccessor) options.keyAttack).fabric_getTimesPressed();

		if (options.keyAttack.isDown() || attackKeyPressCount != 0) {
			fabric_attackCancelled = ClientPreAttackCallback.EVENT.invoker().onClientPlayerPreAttack(
				(Minecraft) (Object) this, player, attackKeyPressCount
			);
		} else {
			fabric_attackCancelled = false;
		}
	}

	@Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
	private void injectStartAttackForCancelling(CallbackInfoReturnable<Boolean> cir) {
		if (fabric_attackCancelled) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "continueAttack", at = @At("HEAD"), cancellable = true)
	private void injectContinueAttackForCancelling(boolean bl, CallbackInfo ci) {
		if (fabric_attackCancelled) {
			if (gameMode != null) {
				gameMode.stopDestroyBlock();
			}

			ci.cancel();
		}
	}
}
