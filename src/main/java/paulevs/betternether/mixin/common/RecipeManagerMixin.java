package paulevs.betternether.mixin.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.recipes.BNRecipeManager;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	@Shadow
	private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

	@Inject(method = "apply", at = @At(value = "RETURN"))
	private void be_setRecipes(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo info) {
		recipes = BNRecipeManager.getMap(recipes);
	}

	@Inject(method = "deserialize", at = @At(value = "HEAD"), cancellable = true)
	private static void be_checkMissing(ResourceLocation id, JsonObject json, CallbackInfoReturnable<Recipe<?>> info) {
		if (id.getNamespace().equals("techreborn") && !FabricLoader.getInstance().isModLoaded("techreborn")) {
			info.setReturnValue(BNRecipeManager.makeEmtyRecipe(id));
			info.cancel();
		}
	}

	@Shadow
	private <C extends Container, T extends Recipe<C>> Map<ResourceLocation, Recipe<C>> getAllOfType(RecipeType<T> type) {
		return null;
	}
	
	@Inject(method = "getFirstMatch", at = @At(value = "HEAD"), cancellable = true)
	private <C extends Container, T extends Recipe<C>> void be_getFirstMatch(RecipeType<T> type, C inventory, Level world, CallbackInfoReturnable<Optional<T>> info) {
		Collection<Recipe<C>> values = getAllOfType(type).values();
		List<Recipe<C>> list = new ArrayList<Recipe<C>>(values);
		list.sort((v1, v2) -> {
			boolean b1 = v1.getId().getNamespace().equals("minecraft");
			boolean b2 = v2.getId().getNamespace().equals("minecraft");
			return b1 ^ b2 ? (b1 ? 1 : -1) : 0;
		});
		
		info.setReturnValue(list.stream().flatMap((recipe) -> {
			return Util.toStream(type.tryMatch(recipe, world, inventory));
		}).findFirst());
		info.cancel();
	}
}