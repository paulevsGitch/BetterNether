package paulevs.betternether.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import paulevs.betternether.tab.CreativeTab;

public class ItemBlackApple extends Item
{
	public static final FoodComponent BLACK_APPLE = new FoodComponent.Builder().hunger(6).saturationModifier(0.5F).build();
	
	public ItemBlackApple()
	{
		super(new Item.Settings()
				.group(CreativeTab.BN_TAB)
				.food(BLACK_APPLE)
				.food(FoodComponents.APPLE));
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
	{
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 1));
		return super.finishUsing(stack, world, user);
	}
}