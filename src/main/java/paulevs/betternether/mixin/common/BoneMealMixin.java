package paulevs.betternether.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;

import java.util.Random;

@Mixin(BoneMealItem.class)
public class BoneMealMixin {
	private static final Direction[] DIR = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };
	private static final MutableBlockPos POS = new MutableBlockPos();

	@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
	private void onUse(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
		Level world = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		if (!world.isClientSide) {
			if (BlocksHelper.isNetherrack(world.getBlockState(blockPos))) {
				BlockState nylium = bnGetNylium(world, blockPos);
				boolean consume = true;
				if (nylium != null && world.getBlockState(blockPos).getBlock() == Blocks.NETHERRACK) {
					BlocksHelper.setWithUpdate(world, blockPos, nylium);
				}
				else {
					consume = bnGrowGrass(world, blockPos);
				}
				if (consume) {
					if (!context.getPlayer().isCreative())
						context.getItemInHand().shrink(1);
					world.levelEvent(2005, blockPos, 0);
					info.setReturnValue(InteractionResult.SUCCESS);
					info.cancel();
				}
			}
			else if (BlocksHelper.isSoulSand(world.getBlockState(blockPos))) {
				if (bnGrowGrass(world, blockPos)) {
					world.levelEvent(2005, blockPos, 0);
					if (!context.getPlayer().isCreative())
						context.getItemInHand().shrink(1);
					info.setReturnValue(InteractionResult.SUCCESS);
					info.cancel();
				}
			}
		}
	}

	private boolean bnGrowGrass(Level world, BlockPos pos) {
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
				BlockPos down = POS.below();
				if (world.isEmptyBlock(POS) && !world.isEmptyBlock(down)) {
					BlockState grass = bnGetGrassState(world, down);
					if (grass != null) {
						BlocksHelper.setWithUpdate(world, POS, grass);
						if (world.random.nextInt(3) == 0 && world.getBlockState(down).getBlock() == Blocks.NETHERRACK)
							BlocksHelper.setWithUpdate(world, down, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
						result = true;
					}
					break;
				}
			}
		}
		return result;
	}

	private BlockState bnGetGrassState(Level world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() == NetherBlocks.JUNGLE_GRASS)
			return NetherBlocks.JUNGLE_PLANT.defaultBlockState();
		else if (BlocksHelper.isSoulSand(state))
			return NetherBlocks.SOUL_GRASS.defaultBlockState();
		else if (state.getBlock() == NetherBlocks.MUSHROOM_GRASS)
			return NetherBlocks.BONE_GRASS.defaultBlockState();
		else if (state.getBlock() == NetherBlocks.SWAMPLAND_GRASS)
			return NetherBlocks.SWAMP_GRASS.defaultBlockState();
		else if (BlocksHelper.isNetherrack(state) && !BlocksHelper.isNylium(state))
			return NetherBlocks.NETHER_GRASS.defaultBlockState();
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

	private BlockState bnGetNylium(Level world, BlockPos pos) {
		bnShuffle(world.random);
		for (Direction dir : DIR) {
			BlockState state = world.getBlockState(pos.relative(dir));
			if (BlocksHelper.isNylium(state))
				return state;
		}
		return null;
	}
}