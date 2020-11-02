package paulevs.betternether.mixin.common;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;

@Mixin(BoneMealItem.class)
public class BoneMealMixin {
	private static final Direction[] DIR = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };
	private static final Mutable POS = new Mutable();

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void onUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		if (!world.isClient) {
			if (BlocksHelper.isNetherrack(world.getBlockState(blockPos))) {
				BlockState nylium = bnGetNylium(world, blockPos);
				boolean consume = true;
				if (nylium != null && world.getBlockState(blockPos).getBlock() == Blocks.NETHERRACK) {
					BlocksHelper.setWithoutUpdate(world, blockPos, nylium);
				}
				else {
					consume = bnGrowGrass(world, blockPos);
				}
				if (consume) {
					if (!context.getPlayer().isCreative())
						context.getStack().decrement(1);
					world.syncWorldEvent(2005, blockPos, 0);
					info.setReturnValue(ActionResult.SUCCESS);
					info.cancel();
				}
			}
			else if (BlocksHelper.isSoulSand(world.getBlockState(blockPos))) {
				if (bnGrowGrass(world, blockPos)) {
					world.syncWorldEvent(2005, blockPos, 0);
					if (!context.getPlayer().isCreative())
						context.getStack().decrement(1);
					info.setReturnValue(ActionResult.SUCCESS);
					info.cancel();
				}
			}
		}
	}

	private boolean bnGrowGrass(World world, BlockPos pos) {
		int y1 = pos.getY() + 3;
		int y2 = pos.getY() - 3;
		boolean result = false;
		for (int i = 0; i < 64; i++) {
			int x = (int) (pos.getX() + world.random.nextGaussian() * 2);
			int z = (int) (pos.getZ() + world.random.nextGaussian() * 2);
			POS.setX(x);
			POS.setZ(z);
			for (int y = y1; y >= y2; y--) {
				POS.setY(y);
				BlockPos down = POS.down();
				if (world.isAir(POS) && !world.isAir(down)) {
					BlockState grass = bnGetGrassState(world, down);
					if (grass != null) {
						BlocksHelper.setWithoutUpdate(world, POS, grass);
						if (world.random.nextInt(3) == 0 && world.getBlockState(down).getBlock() == Blocks.NETHERRACK)
							BlocksHelper.setWithoutUpdate(world, down, BlocksRegistry.NETHERRACK_MOSS.getDefaultState());
						result = true;
					}
					break;
				}
			}
		}
		return result;
	}

	private BlockState bnGetGrassState(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() == BlocksRegistry.JUNGLE_GRASS)
			return BlocksRegistry.JUNGLE_PLANT.getDefaultState();
		else if (BlocksHelper.isSoulSand(state))
			return BlocksRegistry.SOUL_GRASS.getDefaultState();
		else if (state.getBlock() == BlocksRegistry.MUSHROOM_GRASS)
			return BlocksRegistry.BONE_GRASS.getDefaultState();
		else if (state.getBlock() == BlocksRegistry.SWAMPLAND_GRASS)
			return BlocksRegistry.SWAMP_GRASS.getDefaultState();
		else if (BlocksHelper.isNetherrack(state) && !BlocksHelper.isNylium(state))
			return BlocksRegistry.NETHER_GRASS.getDefaultState();
		return null;
	}

	private void bnShuffle(Random random) {
		for (int i = 0; i < 4; i++) {
			int j = random.nextInt(4);
			Direction d = DIR[i];
			DIR[i] = DIR[j];
			DIR[j] = d;
		}
	}

	private BlockState bnGetNylium(World world, BlockPos pos) {
		bnShuffle(world.random);
		for (Direction dir : DIR) {
			BlockState state = world.getBlockState(pos.offset(dir));
			if (BlocksHelper.isNylium(state))
				return state;
		}
		return null;
	}
}