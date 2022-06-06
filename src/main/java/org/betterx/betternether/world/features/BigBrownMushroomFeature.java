package org.betterx.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BNBlockProperties;
import org.betterx.betternether.blocks.BlockBrownLargeMushroom;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class BigBrownMushroomFeature extends ContextFeature<NoneFeatureConfiguration> {
    private static final BlockState MIDDLE = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.MIDDLE);
    private static final BlockState BOTTOM = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.BOTTOM);
    private static final BlockState TOP = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.TOP);
    private static final BlockState SIDE_N = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.SIDE_N);
    private static final BlockState SIDE_S = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.SIDE_S);
    private static final BlockState SIDE_E = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.SIDE_E);
    private static final BlockState SIDE_W = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.SIDE_W);
    private static final BlockState CORNER_N = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.CORNER_N);
    private static final BlockState CORNER_W = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.CORNER_W);
    private static final BlockState CORNER_E = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.CORNER_E);
    private static final BlockState CORNER_S = NetherBlocks.BROWN_LARGE_MUSHROOM
            .defaultBlockState()
            .setValue(BlockBrownLargeMushroom.SHAPE, BNBlockProperties.BrownMushroomShape.CORNER_S);

    public BigBrownMushroomFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    protected boolean place(ServerLevelAccessor level,
                            BlockPos pos,
                            RandomSource random,
                            NoneFeatureConfiguration config,
                            int MAX_HEIGHT,
                            StructureGeneratorThreadContext context) {
        int size = org.betterx.bclib.util.BlocksHelper.blockCount(level,
                pos,
                Direction.UP,
                2 + random.nextInt(3),
                org.betterx.bclib.util.BlocksHelper::isFree
        );
        if (size < 3) return false;

        boolean hasAir = true;
        for (int x = -1; x < 2; x++)
            for (int z = -1; z < 2; z++)
                hasAir = hasAir && level.isEmptyBlock(pos.above(size).offset(x, 0, z));

        if (hasAir) {
            BlocksHelper.setWithoutUpdate(level, pos, BOTTOM);
            for (int y = 1; y < size; y++)
                BlocksHelper.setWithoutUpdate(level, pos.above(y), MIDDLE);
            pos = pos.above(size);
            BlocksHelper.setWithUpdate(level, pos, TOP);
            BlocksHelper.setWithoutUpdate(level, pos.north(), SIDE_N);
            BlocksHelper.setWithoutUpdate(level, pos.south(), SIDE_S);
            BlocksHelper.setWithoutUpdate(level, pos.east(), SIDE_E);
            BlocksHelper.setWithoutUpdate(level, pos.west(), SIDE_W);
            BlocksHelper.setWithoutUpdate(level, pos.north().east(), CORNER_N);
            BlocksHelper.setWithoutUpdate(level, pos.north().west(), CORNER_W);
            BlocksHelper.setWithoutUpdate(level, pos.south().east(), CORNER_E);
            BlocksHelper.setWithoutUpdate(level, pos.south().west(), CORNER_S);

            return true;
        }
        return false;
    }

}
