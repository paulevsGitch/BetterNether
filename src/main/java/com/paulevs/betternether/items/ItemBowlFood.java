package com.paulevs.betternether.items;

import com.paulevs.betternether.BetterNether;
import com.paulevs.betternether.BlocksHelper;
import com.paulevs.betternether.blocks.BlockStalagnateBowl;
import com.paulevs.betternether.blocks.shapes.FoodShape;
import com.paulevs.betternether.registry.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


public class ItemBowlFood extends Item {
	private FoodShape bowlFood;

	public ItemBowlFood(Food component, FoodShape food) {
		super(new Item.Properties().group(BetterNether.BN_TAB).food(component).maxStackSize(16));
		food.setItem(this);
		this.bowlFood = food;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos().offset(context.getFace());
		if (context.getPlayer().isSneaking() && world.isAirBlock(pos) && RegistryHandler.STALAGNATE_BOWL.isValidPosition(world.getBlockState(pos), world, pos)) {
			if (!world.isRemote()) {
				BlockState state = RegistryHandler.STALAGNATE_BOWL.getDefaultState().with(BlockStalagnateBowl.FOOD, bowlFood);
				BlocksHelper.setWithoutUpdate((ServerWorld) world, pos, state);
			}
			if (!context.getPlayer().isCreative()) {
				context.getPlayer().getHeldItemMainhand().shrink(1);
			}
			world.playSound(
					pos.getX() + 0.5,
					pos.getY() + 0.25,
					pos.getZ() + 0.5,
					SoundEvents.BLOCK_WOOD_PLACE,
					SoundCategory.BLOCKS,
					0.8F,
					1.0F,
					true);
			return ActionResultType.CONSUME;
		}
		return super.onItemUse(context);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity user) {
		if (stack.getCount() == 1) {
			super.onItemUseFinish(stack, world, user);
			return new ItemStack(RegistryHandler.STALAGNATE_BOWL, stack.getCount());
		}
		else {
			if (user instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) user;
				if (!player.isCreative())
					player.addItemStackToInventory(new ItemStack(RegistryHandler.STALAGNATE_BOWL));
			}
			return super.onItemUseFinish(stack, world, user);
		}
	}
}
