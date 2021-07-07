package paulevs.betternether.mixin.common;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.recipes.BNRecipeManager;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Shadow
	private ServerResources serverResourceManager;

	@Inject(method = "reloadResources", at = @At(value = "RETURN"), cancellable = true)
	private void onReload(Collection<String> collection, CallbackInfoReturnable<CompletableFuture<Void>> info) {
		injectRecipes();
	}

	@Inject(method = "loadWorld", at = @At(value = "RETURN"), cancellable = true)
	private void onLoadWorld(CallbackInfo info) {
		injectRecipes();
	}

	private void injectRecipes() {
		if (FabricLoader.getInstance().isModLoaded("kubejs")) {
			RecipeManagerAccessor accessor = (RecipeManagerAccessor) serverResourceManager.getRecipeManager();
			accessor.setRecipes(BNRecipeManager.getMap(accessor.getRecipes()));
		}
	}
}
