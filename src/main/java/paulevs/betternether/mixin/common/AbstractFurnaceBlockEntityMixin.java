package paulevs.betternether.mixin.common;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.blockentities.ChangebleCookTime;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Inject(method = "getCookTime", at = @At("RETURN"), cancellable = true)
    private static void betternether$getCookTime(World world, RecipeType<? extends AbstractCookingRecipe> recipeType, Inventory inventory, CallbackInfoReturnable<Integer> cir) {
        if (inventory instanceof ChangebleCookTime) {
            ChangebleCookTime cct = (ChangebleCookTime)inventory;
            int val = cir.getReturnValue();
            cir.setReturnValue(cct.changeCookTime(val));
        }
    }
}
