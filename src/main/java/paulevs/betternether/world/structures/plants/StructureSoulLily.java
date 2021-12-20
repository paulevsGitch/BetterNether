package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockSoulLily;
import paulevs.betternether.blocks.BlockSoulLily.SoulLilyShape;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureSoulLily implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		final int RANDOM_BOUND = (int)(6*scale_factor);
		
		Block under;
		if (world.getBlockState(pos.below()).getBlock() == Blocks.SOUL_SAND) {
			for (int i = 0; i < 10; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(RANDOM_BOUND);
				for (int j = 0; j < RANDOM_BOUND; j++) {
					context.POS.set(x, y - j, z);
					if (context.POS.getY() > 31) {
						under = world.getBlockState(context.POS.below()).getBlock();
						if (under == Blocks.SOUL_SAND && world.isEmptyBlock(context.POS)) {
							growTree(world, context.POS, random);
						}
					}
					else
						break;
				}
			}
		}
	}

	private void growTree(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (world.getBlockState(pos.below()).getBlock() == Blocks.SOUL_SAND) {
			if (world.isEmptyBlock(pos.above())) {
				if (world.isEmptyBlock(pos.above(2)) && isAirSides(world, pos.above(2))) {
					growBig(world, pos);
				}
				else
					growMedium(world, pos);
			}
			else
				growSmall(world, pos);
		}
	}

	public void growSmall(LevelAccessor world, BlockPos pos) {
		BlocksHelper.setWithUpdate(world, pos, NetherBlocks.SOUL_LILY.defaultBlockState());
	}

	public void growMedium(LevelAccessor world, BlockPos pos) {
		BlocksHelper.setWithUpdate(world, pos,
				NetherBlocks.SOUL_LILY
						.defaultBlockState()
						.setValue(BlockSoulLily.SHAPE, SoulLilyShape.MEDIUM_BOTTOM));
		BlocksHelper.setWithUpdate(world, pos.above(),
				NetherBlocks.SOUL_LILY
						.defaultBlockState()
						.setValue(BlockSoulLily.SHAPE, SoulLilyShape.MEDIUM_TOP));
	}

	public void growBig(LevelAccessor world, BlockPos pos) {
		BlocksHelper.setWithUpdate(world, pos, NetherBlocks.SOUL_LILY
				.defaultBlockState()
				.setValue(BlockSoulLily.SHAPE, SoulLilyShape.BIG_BOTTOM));
		BlocksHelper.setWithUpdate(world, pos.above(),
				NetherBlocks.SOUL_LILY
						.defaultBlockState()
						.setValue(BlockSoulLily.SHAPE, SoulLilyShape.BIG_MIDDLE));
		BlockPos up = pos.above(2);
		BlocksHelper.setWithUpdate(world, up,
				NetherBlocks.SOUL_LILY
						.defaultBlockState()
						.setValue(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_CENTER));
		BlocksHelper.setWithUpdate(world, up.north(), NetherBlocks.SOUL_LILY
				.defaultBlockState()
				.setValue(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_SIDE_S));
		BlocksHelper.setWithUpdate(world, up.south(),
				NetherBlocks.SOUL_LILY
						.defaultBlockState()
						.setValue(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_SIDE_N));
		BlocksHelper.setWithUpdate(world, up.east(),
				NetherBlocks.SOUL_LILY
						.defaultBlockState()
						.setValue(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_SIDE_W));
		BlocksHelper.setWithUpdate(world, up.west(),
				NetherBlocks.SOUL_LILY
						.defaultBlockState()
						.setValue(BlockSoulLily.SHAPE, SoulLilyShape.BIG_TOP_SIDE_E));
	}

	private boolean isAirSides(LevelAccessor world, BlockPos pos) {
		return world.isEmptyBlock(pos.north()) && world.isEmptyBlock(pos.south()) && world.isEmptyBlock(pos.east()) && world.isEmptyBlock(pos.west());
	}
}