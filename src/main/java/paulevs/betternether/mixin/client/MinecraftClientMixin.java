package paulevs.betternether.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import paulevs.betternether.registry.BiomesRegistry;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin
{
	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onInit(CallbackInfo info)
	{
		BiomesRegistry.registerAllOtherBiomes();
	}
}