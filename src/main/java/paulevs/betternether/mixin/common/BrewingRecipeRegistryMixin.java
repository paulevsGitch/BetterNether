package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import paulevs.betternether.registers.BrewingRegister;
import paulevs.betternether.registers.BrewingRegister.PotionRecipe;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin
{
	@Inject(method = "isPotionRecipeIngredient", at = @At("RETURN"), cancellable = true)
	private static void isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> info)
	{
		if (BrewingRegister.isIngredient(stack))
		{
			info.setReturnValue(true);
			info.cancel();
		}
	}
	
	@Inject(method = "hasRecipe", at = @At("HEAD"), cancellable = true)
	private static void checkRecipe(ItemStack input, ItemStack ingredient, CallbackInfoReturnable<Boolean> info)
	{
		if (BrewingRegister.getRecipe(ingredient, input) != null)
		{
			info.setReturnValue(true);
			info.cancel();
		}
	}
	
	/*@Inject(method = "isBrewable", at = @At("HEAD"), cancellable = true)
	public static void brewable(Potion potion, CallbackInfoReturnable<Boolean> info)
	{
		BrewingRegister.isPotion(potion.)
	}*/
	
	@Inject(method = "craft", at = @At("HEAD"), cancellable = true)
	private static void tryCraft(ItemStack input, ItemStack ingredient, CallbackInfoReturnable<ItemStack> info)
	{
		PotionRecipe recipe = BrewingRegister.getRecipe(ingredient, input);
		System.out.println("Craft? " + input + " " + ingredient);
		if (recipe != null)
		{
			//info.setReturnValue(PotionUtil.setPotion(input, recipe.getResult()));
			System.out.println("Craft!");
			info.setReturnValue(new ItemStack(Items.ACACIA_BOAT));
			info.cancel();
		}
	}
}
