package paulevs.betternether.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import paulevs.betternether.config.Config;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Inject(method = "<init>*", at = @At(value = "TAIL"))
	private void onInit(RunArgs args, CallbackInfo info) {
		Config.save();
	}
}
