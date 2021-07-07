package paulevs.betternether.mixin.common;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.blockentities.ChangebleCookTime;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Inject(method = "getCookTime", at = @At("RETURN"), cancellable = true)
    private static void betternether$getCookTime(Level world, RecipeType<? extends AbstractCookingRecipe> recipeType, Container inventory, CallbackInfoReturnable<Integer> cir) {
        if (inventory instanceof ChangebleCookTime) {
            ChangebleCookTime cct = (ChangebleCookTime)inventory;
            int val = cir.getReturnValue();
            cir.setReturnValue(cct.changeCookTime(val));
        }
    }
}
