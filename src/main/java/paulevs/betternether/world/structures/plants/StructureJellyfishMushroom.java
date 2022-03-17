package paulevs.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockJellyfishMushroom;
import paulevs.betternether.blocks.BlockProperties.JellyShape;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

import java.util.Random;

public class StructureJellyfishMushroom implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		final int RANDOM_BOUND = (int)(6*scale_factor);
		
		BlockState under;
		if (world.getBlockState(pos.below()).is(BlockTags.NYLIUM)) {
			for (int i = 0; i < 10; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(RANDOM_BOUND);
				for (int j = 0; j < RANDOM_BOUND; j++) {
					context.POS.set(x, y - j, z);
					if (context.POS.getY() > 32) {
						under = world.getBlockState(context.POS.below());
						if (under.is(BlockTags.NYLIUM) && world.isEmptyBlock(context.POS)) {
							grow(world, context.POS, random);
						}
					}
					else
						break;
				}
			}
		}
	}

	public void grow(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (random.nextBoolean() && world.isEmptyBlock(pos.above()))
			growMedium(world, pos);
		else
			growSmall(world, pos);
	}

	public void growSmall(ServerLevelAccessor world, BlockPos pos) {
		Block down = world.getBlockState(pos.below()).getBlock();
		JellyShape visual = down == NetherBlocks.MUSHROOM_GRASS ? JellyShape.NORMAL : down == NetherBlocks.SEPIA_MUSHROOM_GRASS ? JellyShape.SEPIA : JellyShape.POOR;
		BlocksHelper.setWithUpdate(world, pos, NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState().setValue(BlockJellyfishMushroom.SHAPE, TripleShape.BOTTOM).setValue(BlockJellyfishMushroom.VISUAL, visual));
	}

	public void growMedium(ServerLevelAccessor world, BlockPos pos) {
		Block down = world.getBlockState(pos.below()).getBlock();
		JellyShape visual = down == NetherBlocks.MUSHROOM_GRASS ? JellyShape.NORMAL : down == NetherBlocks.SEPIA_MUSHROOM_GRASS ? JellyShape.SEPIA : JellyShape.POOR;
		BlocksHelper.setWithUpdate(world, pos, NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState().setValue(BlockJellyfishMushroom.SHAPE, TripleShape.MIDDLE).setValue(BlockJellyfishMushroom.VISUAL, visual));
		BlocksHelper.setWithUpdate(world, pos.above(), NetherBlocks.JELLYFISH_MUSHROOM.defaultBlockState().setValue(BlockJellyfishMushroom.SHAPE, TripleShape.TOP).setValue(BlockJellyfishMushroom.VISUAL, visual));
	}
}