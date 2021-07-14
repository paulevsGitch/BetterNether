package paulevs.betternether.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import paulevs.betternether.tab.CreativeTab;

public class ItemBlackApple extends Item {
	public static final FoodProperties BLACK_APPLE = new FoodProperties.Builder().nutrition(6).saturationMod(0.5F).build();

	public ItemBlackApple() {
		super(new Item.Properties()
				.tab(CreativeTab.BN_TAB)
				.food(BLACK_APPLE)
				.food(Foods.APPLE));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 1));
		return super.finishUsingItem(stack, world, user);
	}
}