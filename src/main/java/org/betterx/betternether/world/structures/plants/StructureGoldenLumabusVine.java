package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockLumabusVine;
import org.betterx.betternether.blocks.BlockProperties;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.features.NetherChunkPopulatorFeature;
import org.betterx.betternether.world.structures.IGrowableStructure;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureGoldenLumabusVine implements IStructure, IGrowableStructure {
    @Override
    public void grow(ServerLevelAccessor world, BlockPos pos, RandomSource random) {
        generate(world, pos, random, 128, NetherChunkPopulatorFeature.generatorForThread().context);
    }

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        final float scale_factor = MAX_HEIGHT / 128.0f;
        final int RANDOM_BOUND = (int) (19 * scale_factor);

        int h = random.nextInt(RANDOM_BOUND) + 5;
        int h2 = BlocksHelper.downRay(world, pos, h);
        h2 -= 2;

        if (h2 < 3)
            return;

        BlockState vineState = NetherBlocks.GOLDEN_LUMABUS_VINE.defaultBlockState()
                                                               .setValue(BlockLumabusVine.SHAPE,
                                                                         BlockProperties.TripleShape.MIDDLE);

        BlocksHelper.setWithUpdate(world, pos, NetherBlocks.GOLDEN_LUMABUS_VINE.defaultBlockState());

        for (int y = 1; y < h2; y++)
            BlocksHelper.setWithUpdate(world, pos.below(y), vineState);

        BlocksHelper.setWithUpdate(world,
                                   pos.below(h2),
                                   NetherBlocks.GOLDEN_LUMABUS_VINE.defaultBlockState()
                                                                   .setValue(BlockLumabusVine.SHAPE,
                                                                             BlockProperties.TripleShape.BOTTOM));
    }
}