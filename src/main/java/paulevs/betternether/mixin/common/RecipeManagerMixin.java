package paulevs.betternether.mixin.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.JsonObject;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.recipes.BNRecipeManager;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	@Inject(method = "fromJson", at = @At(value = "HEAD"), cancellable = true)
	private static void be_checkMissing(ResourceLocation id, JsonObject json, CallbackInfoReturnable<Recipe<?>> info) {
		if (id.getNamespace().equals("techreborn") && !FabricLoader.getInstance().isModLoaded("techreborn")) {
			info.setReturnValue(BNRecipeManager.makeEmptyRecipe(id));
			info.cancel();
		}
	}
}