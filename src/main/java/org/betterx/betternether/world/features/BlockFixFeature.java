package org.betterx.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockStalactite;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class BlockFixFeature extends DefaultFeature {
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        final BlockPos worldPos = featurePlaceContext.origin();
        final WorldGenLevel level = featurePlaceContext.level();
        final int sx = (worldPos.getX() >> 4) << 4;
        final int sz = (worldPos.getZ() >> 4) << 4;

        fixBlocks(level, sx, 30, sz, sx + 15, 110, sz + 15);
        return true;
    }

    private static void fixBlocks(WorldGenLevel world, int x1, int y1, int z1, int x2, int y2, int z2) {
        final StructureGeneratorThreadContext ctx = NetherChunkPopulatorFeature.generatorForThread().context;
        final MutableBlockPos popPos = ctx.POS;
        final MutableBlockPos abovePos = ctx.POS2;
        final MutableBlockPos belowPos = ctx.POS3;
        BlockState state, aboveState;

        for (int y = y1; y <= y2; y++) {
            popPos.setY(y);
            abovePos.setY(y + 1);
            belowPos.setY(y - 1);
            for (int x = x1; x <= x2; x++) {
                popPos.setX(x);
                abovePos.setX(x);
                belowPos.setX(x);
                for (int z = z1; z <= z2; z++) {
                    popPos.setZ(z);
                    abovePos.setZ(z);
                    belowPos.setZ(z);

                    state = world.getBlockState(popPos);

                    if (!state.canSurvive(world, popPos)) {
                        BlocksHelper.setWithoutUpdate(world, popPos, AIR);
                        continue;
                    }
                    aboveState = world.getBlockState(abovePos);
                    if (!state.canOcclude() && aboveState.is(Blocks.NETHER_BRICKS)) {
                        BlocksHelper.setWithoutUpdate(world, popPos, Blocks.NETHER_BRICKS.defaultBlockState());
                        continue;
                    }

                    if (org.betterx.bclib.util.BlocksHelper.isLava(state) && world.isEmptyBlock(abovePos) && world.isEmptyBlock(
                            belowPos)) {
                        BlocksHelper.setWithoutUpdate(world, popPos, AIR);
                        continue;
                    }

                    if (state.getBlock() == Blocks.NETHER_WART_BLOCK || state.getBlock() == Blocks.WARPED_WART_BLOCK) {
                        if (world.isEmptyBlock(belowPos) && world.isEmptyBlock(abovePos) && world.isEmptyBlock(popPos.north()) && world.isEmptyBlock(
                                popPos.south()) && world.isEmptyBlock(popPos.east()) && world.isEmptyBlock(popPos.west()))
                            BlocksHelper.setWithoutUpdate(world, popPos, AIR);
                        continue;
                    }

                    if (state.getBlock() instanceof BlockStalactite && !(state = world.getBlockState(belowPos)).isCollisionShapeFullBlock(
                            world,
                            belowPos) && !(state.getBlock() instanceof BlockStalactite)) {
                        MutableBlockPos sp = new MutableBlockPos().set(popPos);
                        while (world.getBlockState(sp).getBlock() instanceof BlockStalactite) {
                            BlocksHelper.setWithoutUpdate(world, sp, AIR);
                            sp.relative(Direction.UP);
                        }
                        continue;
                    }
                }
            }
        }
    }
}
