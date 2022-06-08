package org.betterx.betternether.world.features;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockSoulLily;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SoulLilyFeature extends ContextFeature<NoneFeatureConfiguration> {
    public SoulLilyFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    protected boolean place(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            NoneFeatureConfiguration config,
            int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        final float scale_factor = MAX_HEIGHT / 128.0f;
        final int RANDOM_BOUND = (int) (6 * scale_factor);

        Block under;
        if (world.getBlockState(pos.below()).getBlock() == Blocks.SOUL_SAND) {
            int x = pos.getX();
            int z = pos.getZ();
            int y = pos.getY() + random.nextInt(RANDOM_BOUND);
            for (int j = 0; j < RANDOM_BOUND; j++) {
                context.POS.set(x, y - j, z);
                if (context.POS.getY() > 31) {
                    under = world.getBlockState(context.POS.below()).getBlock();
                    if (under == Blocks.SOUL_SAND && world.isEmptyBlock(context.POS)) {
                        growTree(world, context.POS, random);
                    }
                } else {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    private boolean growTree(ServerLevelAccessor world, BlockPos pos, RandomSource random) {
        if (world.getBlockState(pos.below()).getBlock() == Blocks.SOUL_SAND) {
            if (world.isEmptyBlock(pos.above())) {
                if (world.isEmptyBlock(pos.above(2)) && isAirSides(world, pos.above(2))) {
                    growBig(world, pos);
                } else {
                    growMedium(world, pos);
                }
            } else {
                growSmall(world, pos);
            }
            return true;
        }
        return false;
    }

    public void growSmall(LevelAccessor world, BlockPos pos) {
        BlocksHelper.setWithUpdate(world, pos, NetherBlocks.SOUL_LILY.defaultBlockState());
    }

    public void growMedium(LevelAccessor world, BlockPos pos) {
        BlocksHelper.setWithUpdate(world, pos,
                NetherBlocks.SOUL_LILY
                        .defaultBlockState()
                        .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.MEDIUM_BOTTOM)
        );
        BlocksHelper.setWithUpdate(world, pos.above(),
                NetherBlocks.SOUL_LILY
                        .defaultBlockState()
                        .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.MEDIUM_TOP)
        );
    }

    public void growBig(LevelAccessor world, BlockPos pos) {
        BlocksHelper.setWithUpdate(world, pos, NetherBlocks.SOUL_LILY
                .defaultBlockState()
                .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.BIG_BOTTOM));
        BlocksHelper.setWithUpdate(world, pos.above(),
                NetherBlocks.SOUL_LILY
                        .defaultBlockState()
                        .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.BIG_MIDDLE)
        );
        BlockPos up = pos.above(2);
        BlocksHelper.setWithUpdate(world, up,
                NetherBlocks.SOUL_LILY
                        .defaultBlockState()
                        .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.BIG_TOP_CENTER)
        );
        BlocksHelper.setWithUpdate(world, up.north(), NetherBlocks.SOUL_LILY
                .defaultBlockState()
                .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.BIG_TOP_SIDE_S));
        BlocksHelper.setWithUpdate(world, up.south(),
                NetherBlocks.SOUL_LILY
                        .defaultBlockState()
                        .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.BIG_TOP_SIDE_N)
        );
        BlocksHelper.setWithUpdate(world, up.east(),
                NetherBlocks.SOUL_LILY
                        .defaultBlockState()
                        .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.BIG_TOP_SIDE_W)
        );
        BlocksHelper.setWithUpdate(world, up.west(),
                NetherBlocks.SOUL_LILY
                        .defaultBlockState()
                        .setValue(BlockSoulLily.SHAPE, BlockSoulLily.SoulLilyShape.BIG_TOP_SIDE_E)
        );
    }

    private boolean isAirSides(LevelAccessor world, BlockPos pos) {
        return world.isEmptyBlock(pos.north()) && world.isEmptyBlock(pos.south()) && world.isEmptyBlock(pos.east()) && world.isEmptyBlock(
                pos.west());
    }
}
