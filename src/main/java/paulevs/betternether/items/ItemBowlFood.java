package paulevs.betternether.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockProperties.FoodShape;
import paulevs.betternether.blocks.BlockStalagnateBowl;
import paulevs.betternether.blocks.complex.NetherWoodenMaterial;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherItems;
import paulevs.betternether.tab.CreativeTabs;

public class ItemBowlFood extends Item {
	private FoodShape bowlFood;

	public ItemBowlFood(FoodProperties component, FoodShape food) {
		super(new Item.Properties().tab(CreativeTabs.BN_TAB).food(component).stacksTo(16));
		food.setItem(this);
		this.bowlFood = food;
	}

	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
		if (context.getPlayer().isShiftKeyDown() && world.isEmptyBlock(pos) && NetherBlocks.MAT_STALAGNATE.getBowl().canSurvive(world.getBlockState(pos), world, pos)) {
			if (!world.isClientSide()) {
				BlockState state = NetherBlocks.MAT_STALAGNATE.getBowl().defaultBlockState().setValue(BlockStalagnateBowl.FOOD, bowlFood);
				BlocksHelper.setWithoutUpdate((ServerLevel) world, pos, state);
			}
			if (!context.getPlayer().isCreative()) {
				context.getPlayer().getMainHandItem().shrink(1);
			}
			world.playLocalSound(
					pos.getX() + 0.5,
					pos.getY() + 0.25,
					pos.getZ() + 0.5,
					SoundEvents.WOOD_PLACE,
					SoundSource.BLOCKS,
					0.8F,
					1.0F,
					true);
			return InteractionResult.CONSUME;
		}
		return super.useOn(context);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		if (stack.getCount() == 1) {
			super.finishUsingItem(stack, world, user);
			return new ItemStack(NetherItems.STALAGNATE_BOWL, 1);
		}
		else {
			if (user instanceof Player) {
				Player player = (Player) user;
				if (!player.isCreative())
					player.addItem(new ItemStack(NetherItems.STALAGNATE_BOWL));
			}
			return super.finishUsingItem(stack, world, user);
		}
	}
}