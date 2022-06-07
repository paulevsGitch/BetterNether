package org.betterx.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBiomes;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import java.util.Set;

public class CleanupFeature extends DefaultFeature {

    private final static BlockPos rel(BlockPos pos, int x, int y, int z) {
        return new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        if (!NetherBiomes.useLegacyGeneration) return false;
        final StructureGeneratorThreadContext ctx = NetherChunkPopulatorFeature.generatorForThread().context;
        final int MAX_HEIGHT = featurePlaceContext.chunkGenerator().getGenDepth();
        final MutableBlockPos popPos = ctx.POS;
        final BlockPos worldPos = featurePlaceContext.origin();
        final WorldGenLevel level = featurePlaceContext.level();

        final int sx = (worldPos.getX() >> 4) << 4;
        final int sz = (worldPos.getZ() >> 4) << 4;


        final Set<BlockPos> pos = ctx.BLOCKS;
        pos.clear();
        BlockPos up;
        BlockPos down;
        BlockPos north;
        BlockPos south;
        BlockPos east;
        BlockPos west;
        for (int y = 32; y < MAX_HEIGHT - 18; y++) {
            popPos.setY(y);
            for (int x = 0; x < 16; x++) {
                popPos.setX(x | sx);
                for (int z = 0; z < 16; z++) {
                    popPos.setZ(z | sz);
                    if (canReplace(level, popPos)) {
                        up = popPos.above();
                        down = popPos.below();
                        north = popPos.north();
                        south = popPos.south();
                        east = popPos.east();
                        west = popPos.west();
                        if (level.isEmptyBlock(north) && level.isEmptyBlock(south))
                            pos.add(new BlockPos(popPos));
                        else if (level.isEmptyBlock(east) && level.isEmptyBlock(west))
                            pos.add(new BlockPos(popPos));
                        else if (level.isEmptyBlock(up) && level.isEmptyBlock(down))
                            pos.add(new BlockPos(popPos));
                        else if (level.isEmptyBlock(rel(popPos, 1, -1, -1)) && level.isEmptyBlock(rel(popPos,
                                -1,
                                1,
                                1)))
                            pos.add(new BlockPos(popPos));
                        else if (level.isEmptyBlock(rel(popPos, 1, -1, 1)) && level.isEmptyBlock(rel(popPos,
                                -1,
                                1,
                                -1)))
                            pos.add(new BlockPos(popPos));
                        else if (level.isEmptyBlock(rel(popPos, -1, -1, -1)) && level.isEmptyBlock(rel(popPos,
                                1,
                                1,
                                1)))
                            pos.add(new BlockPos(popPos));
                        else if (level.isEmptyBlock(rel(popPos, -1, -1, 1)) && level.isEmptyBlock(rel(popPos,
                                1,
                                1,
                                -1)))
                            pos.add(new BlockPos(popPos));
//						else if (level.isEmptyBlock(north.east().below()) && level.isEmptyBlock(south.west().above()))
//							pos.add(new BlockPos(popPos));
//						else if (level.isEmptyBlock(south.east().below()) && level.isEmptyBlock(north.west().above()))
//							pos.add(new BlockPos(popPos));
//						else if (level.isEmptyBlock(north.west().below()) && level.isEmptyBlock(south.east().above()))
//							pos.add(new BlockPos(popPos));
//						else if (level.isEmptyBlock(south.west().below()) && level.isEmptyBlock(north.east().above()))
//							pos.add(new BlockPos(popPos));
                    }
                }
            }
        }

        for (BlockPos p : pos) {
            BlocksHelper.setWithoutUpdate(level, p, AIR);
            up = p.above();
            BlockState state = level.getBlockState(up);
            if (!state.getBlock().canSurvive(state, level, up))
                BlocksHelper.setWithoutUpdate(level, up, AIR);

            down = p.below();
            state = level.getBlockState(p);
            if (level.getBlockState(down).is(Blocks.NETHERRACK) && BlocksHelper.isNetherGround(state)) {
                BlocksHelper.setWithoutUpdate(level, down, state);
            }
        }
        pos.clear();

        return true;
    }

    private static boolean canReplace(WorldGenLevel world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return BlocksHelper.isNetherGround(state) || state.getBlock() == Blocks.GRAVEL;
    }
}
