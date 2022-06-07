package org.betterx.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.bclib.api.v2.levelgen.features.UserGrowableFeature;
import org.betterx.bclib.api.v2.tag.CommonBlockTags;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockMushroomFir;
import org.betterx.betternether.blocks.BlockNetherMycelium;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class MushroomFirFeature extends ContextFeature<NoneFeatureConfiguration> implements UserGrowableFeature<NoneFeatureConfiguration> {

    private static final BlockState SIDE_BIG_N = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                              .defaultBlockState()
                                                                              .setValue(
                                                                                      BlockMushroomFir.SHAPE,
                                                                                      BlockMushroomFir.MushroomFirShape.SIDE_BIG_N);
    private static final BlockState SIDE_BIG_W = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                              .defaultBlockState()
                                                                              .setValue(
                                                                                      BlockMushroomFir.SHAPE,
                                                                                      BlockMushroomFir.MushroomFirShape.SIDE_BIG_W);
    private static final BlockState SIDE_BIG_E = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                              .defaultBlockState()
                                                                              .setValue(
                                                                                      BlockMushroomFir.SHAPE,
                                                                                      BlockMushroomFir.MushroomFirShape.SIDE_BIG_E);
    private static final BlockState SIDE_SMALL_S = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                                .defaultBlockState()
                                                                                .setValue(
                                                                                        BlockMushroomFir.SHAPE,
                                                                                        BlockMushroomFir.MushroomFirShape.SIDE_SMALL_S);
    private static final BlockState SIDE_SMALL_N = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                                .defaultBlockState()
                                                                                .setValue(
                                                                                        BlockMushroomFir.SHAPE,
                                                                                        BlockMushroomFir.MushroomFirShape.SIDE_SMALL_N);
    private static final BlockState SIDE_SMALL_W = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                                .defaultBlockState()
                                                                                .setValue(
                                                                                        BlockMushroomFir.SHAPE,
                                                                                        BlockMushroomFir.MushroomFirShape.SIDE_SMALL_W);
    private static final BlockState SIDE_SMALL_E = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                                .defaultBlockState()
                                                                                .setValue(
                                                                                        BlockMushroomFir.SHAPE,
                                                                                        BlockMushroomFir.MushroomFirShape.SIDE_SMALL_E);
    private static final BlockState END = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                       .defaultBlockState()
                                                                       .setValue(BlockMushroomFir.SHAPE,
                                                                               BlockMushroomFir.MushroomFirShape.END);
    private static final BlockState BLUE_MYCELIUM = NetherBlocks.NETHER_MYCELIUM.defaultBlockState()
                                                                                .setValue(BlockNetherMycelium.IS_BLUE,
                                                                                        true);
    private static final BlockState SIDE_BIG_S = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                              .defaultBlockState()
                                                                              .setValue(
                                                                                      BlockMushroomFir.SHAPE,
                                                                                      BlockMushroomFir.MushroomFirShape.SIDE_BIG_S);
    private static final BlockState TOP = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                       .defaultBlockState()
                                                                       .setValue(BlockMushroomFir.SHAPE,
                                                                               BlockMushroomFir.MushroomFirShape.TOP);
    private static final BlockState MIDDLE = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                          .defaultBlockState()
                                                                          .setValue(BlockMushroomFir.SHAPE,
                                                                                  BlockMushroomFir.MushroomFirShape.MIDDLE);
    private static final BlockState BOTTOM = NetherBlocks.MAT_MUSHROOM_FIR.getTrunk()
                                                                          .defaultBlockState()
                                                                          .setValue(BlockMushroomFir.SHAPE,
                                                                                  BlockMushroomFir.MushroomFirShape.BOTTOM);

    public MushroomFirFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    protected boolean place(ServerLevelAccessor level,
                            BlockPos pos,
                            RandomSource random,
                            NoneFeatureConfiguration config,
                            int MAX_HEIGHT,
                            StructureGeneratorThreadContext context) {
        if (!level.getBlockState(pos.below()).is(CommonBlockTags.MYCELIUM)) return false;

        final float scale_factor = MAX_HEIGHT / 128.0f;

        if (level.getBlockState(pos.below()).getBlock() == NetherBlocks.NETHER_MYCELIUM) {
            return grow(level, pos, random, scale_factor, context);
        }
        return false;
    }

    protected boolean grow(ServerLevelAccessor level,
                           BlockPos pos,
                           RandomSource random,
                           float scale_factor,
                           StructureGeneratorThreadContext context) {
        final int RANDOM_BOUND = (int) (5 * scale_factor);
        int h = 3 + random.nextInt(RANDOM_BOUND);
        for (int y = 1; y < h; y++)
            if (!level.isEmptyBlock(pos.above(y))) {
                h = y;
                break;
            }
        if (h < 3)
            return false;

        BlocksHelper.setWithUpdate(level, pos, BOTTOM);
        int h2 = (h + 1) >> 1;
        h += pos.getY();
        h2 += pos.getY();
        context.POS.set(pos);
        for (int y = pos.getY() + 1; y < h2; y++) {
            context.POS.setY(y);
            BlocksHelper.setWithUpdate(level, context.POS, MIDDLE);
        }
        for (int y = h2; y < h; y++) {
            context.POS.setY(y);
            BlocksHelper.setWithUpdate(level, context.POS, TOP);
        }
        int h3 = (h2 + h) >> 1;
        for (int y = h2 - 1; y < h3; y++) {
            context.POS.setY(y);
            BlockPos branch;
            if (random.nextBoolean()) {
                branch = context.POS.north();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, SIDE_BIG_S);
            }
            if (random.nextBoolean()) {
                branch = context.POS.south();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, SIDE_BIG_N);
            }
            if (random.nextBoolean()) {
                branch = context.POS.east();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, SIDE_BIG_W);
            }
            if (random.nextBoolean()) {
                branch = context.POS.west();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, SIDE_BIG_E);
            }
        }
        for (int y = h3; y < h; y++) {
            context.POS.setY(y);
            BlockPos branch;
            if (random.nextBoolean()) {
                branch = context.POS.north();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, SIDE_SMALL_S);
            }
            if (random.nextBoolean()) {
                branch = context.POS.south();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, SIDE_SMALL_N);
            }
            if (random.nextBoolean()) {
                branch = context.POS.east();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, SIDE_SMALL_W);
            }
            if (random.nextBoolean()) {
                branch = context.POS.west();
                if (level.isEmptyBlock(branch))
                    BlocksHelper.setWithUpdate(level, branch, SIDE_SMALL_E);

            }
        }
        context.POS.setY(h);
        if (level.isEmptyBlock(context.POS))
            BlocksHelper.setWithUpdate(level, context.POS, END);

        BlocksHelper.cover(level,
                pos.below(),
                NetherBlocks.NETHER_MYCELIUM,
                BLUE_MYCELIUM,
                5,
                random);
        return true;
    }

    @Override
    public boolean grow(ServerLevelAccessor level,
                        BlockPos pos,
                        RandomSource random,
                        NoneFeatureConfiguration configuration) {
        return grow(level, pos, random, 1, NetherChunkPopulatorFeature.generatorForThread().context);
    }
}
