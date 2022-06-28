package org.betterx.betternether.world.features;

import org.betterx.bclib.api.v3.levelgen.features.UserGrowableFeature;
import org.betterx.bclib.blocks.BlockProperties.TripleShape;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.blocks.BlockPlantWall;
import org.betterx.betternether.blocks.BlockStalagnateSeed;
import org.betterx.betternether.blocks.RubeusLog;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Iterator;
import java.util.Map;

public class RubeusTreeFeature extends NonOverlappingFeature<NaturalTreeConfiguration> implements UserGrowableFeature<NaturalTreeConfiguration> {
    public RubeusTreeFeature() {
        super(NaturalTreeConfiguration.CODEC);
    }

    @Override
    protected boolean isStructure(BlockState state) {
        return state.getBlock() == NetherBlocks.MAT_RUBEUS.getLog();
    }

    @Override
    protected boolean isGround(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }

    @Override
    protected boolean place(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            NaturalTreeConfiguration config,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        int length = BlocksHelper.upRay(world, pos, BlockStalagnateSeed.MAX_SEARCH_LENGTH + 2);
        if (length >= BlockStalagnateSeed.MAX_SEARCH_LENGTH)
            return super.place(world, pos, random, config, MAX_HEIGHT, context);
        return false;
    }

    private static final float[] CURVE_X = new float[]{9F, 7F, 1.5F, 0.5F, 3F, 7F};
    private static final float[] CURVE_Y = new float[]{20F, 17F, 12F, 4F, 0F, -2F};
    private static final int MIDDLE_Y = 10;

    private void updateSDFFrom(BlockPos bpos, StructureGeneratorThreadContext context) {
        for (int x = -7; x <= 7; x++) {
            for (int y = -7; y <= 7; y++) {
                for (int z = -7; z <= 7; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    final int dist = Math.abs(x) + Math.abs(y) + Math.abs(z);
                    if (dist <= 7) {
                        final BlockPos blPos = bpos.offset(x, y, z);
                        context.LOGS_DIST.merge(
                                blPos,
                                (byte) dist,
                                (oldDist, newDist) -> (byte) Math.min(oldDist, dist)
                        );
                    }
                }
            }
        }
    }

    private void updateDistances(ServerLevelAccessor world, StructureGeneratorThreadContext context) {
        for (Map.Entry<BlockPos, Byte> entry : context.LOGS_DIST.entrySet()) {
            final int dist = entry.getValue();
            final BlockPos logPos = entry.getKey();

            BlockState currentState = world.getBlockState(logPos);
            if (currentState.hasProperty(BlockStateProperties.DISTANCE)) {
                int cDist = currentState.getValue(BlockStateProperties.DISTANCE);
                if (dist < cDist) {
                    BlocksHelper.setWithoutUpdate(
                            world,
                            logPos,
                            currentState.setValue(BlockStateProperties.DISTANCE, dist)
                    );
                    cDist = dist;
                }

                if (cDist >= 7) {
                    BlocksHelper.setWithoutUpdate(world, logPos, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    public boolean grow(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            NaturalTreeConfiguration config,
            StructureGeneratorThreadContext context
    ) {
        final boolean natural = config.natural;
        context.clear();
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        float scale = MHelper.randRange(0.5F, 1F, random);
        int minCount = scale < 0.75 ? 3 : 4;
        int maxCount = scale < 0.75 ? 5 : 7;
        int count = MHelper.randRange(minCount, maxCount, random);
        for (int n = 0; n < count; n++) {
            float branchSize = MHelper.randRange(0.5F, 0.8F, random) * scale;
            float angle = n * MHelper.PI2 / count;
            float radius = CURVE_X[0] * branchSize;
            int x1 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(
                    -2F,
                    2F,
                    random
            ) * branchSize);
            int y1 = Math.round(pos.getY() + CURVE_Y[0] * branchSize + MHelper.randRange(-2F, 2F, random) * branchSize);
            int z1 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(
                    -2F,
                    2F,
                    random
            ) * branchSize);
            float crownR = 5 * branchSize;
            if (crownR < 1.5F)
                crownR = 1.5F;
            crown(world, x1, y1 + 1, z1, crownR, random);

            int middle = Math.round(pos.getY() + (MIDDLE_Y + MHelper.randRange(-2, 2, random)) * branchSize);
            boolean generate = true;
            for (int i = 1; i < CURVE_X.length && generate; i++) {
                radius = CURVE_X[i] * branchSize;
                int x2 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(
                        -2F,
                        2F,
                        random
                ) * branchSize);
                int y2 = Math.round(pos.getY() + CURVE_Y[i] * branchSize + (CURVE_Y[i] > 0
                        ? MHelper.randRange(
                        -2F,
                        2F,
                        random
                ) * branchSize
                        : 0));
                int z2 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(
                        -2F,
                        2F,
                        random
                ) * branchSize);

                if (CURVE_Y[i] <= 0) {
                    if (!isGround(world.getBlockState(context.POS.set(x2, y2, z2)))) {
                        boolean noGround = true;
                        for (int d = 1; d < 3; d++) {
                            if (isGround(world.getBlockState(context.POS.set(x2, y2 - d, z2)))) {
                                y2 -= d;
                                noGround = false;
                                break;
                            }
                        }
                        if (noGround) {
                            x2 = pos.getX();
                            y2 = pos.getY();
                            generate = false;
                        }
                    }
                }

                line(world, x1, y1, z1, x2, y2, z2, middle, context);
                x1 = x2;
                y1 = y2;
                z1 = z2;
            }
        }

        BlockState state;
        Iterator<BlockPos> iterator = context.TOP.iterator();
        while (iterator.hasNext()) {
            BlockPos bpos = iterator.next();
            if (bpos != null) {
                if (context.POINTS.contains(bpos.above()) && !context.TOP.contains(bpos.above()))
                    iterator.remove();
            }
        }

        iterator = context.MIDDLE.iterator();
        while (iterator.hasNext()) {
            BlockPos bpos = iterator.next();
            if (bpos != null) {
                BlockPos up = bpos.above();
                if (context.MIDDLE.contains(up) || (!context.TOP.contains(up) && context.POINTS.contains(up)))
                    iterator.remove();
            } else
                iterator.remove();
        }

        for (BlockPos bpos : context.POINTS) {
            if (context.POINTS.contains(bpos.above()) && context.POINTS.contains(bpos.below())) {
                state = NetherBlocks.MAT_RUBEUS.getLog().defaultBlockState();
                if (context.MIDDLE.contains(bpos))
                    setCondition(
                            world,
                            bpos,
                            pos.getY(),
                            state.setValue(RubeusLog.SHAPE, TripleShape.MIDDLE),
                            false,
                            random
                    );
                else if (context.TOP.contains(bpos))
                    setCondition(
                            world,
                            bpos,
                            pos.getY(),
                            state.setValue(RubeusLog.SHAPE, TripleShape.TOP),
                            false,
                            random
                    );
                else
                    setCondition(
                            world,
                            bpos,
                            pos.getY(),
                            state.setValue(RubeusLog.SHAPE, TripleShape.BOTTOM),
                            natural,
                            random
                    );
            } else {
                state = NetherBlocks.MAT_RUBEUS.getBark().defaultBlockState();
                if (context.MIDDLE.contains(bpos))
                    setCondition(
                            world,
                            bpos,
                            pos.getY(),
                            state.setValue(RubeusLog.SHAPE, TripleShape.MIDDLE),
                            false,
                            random
                    );
                else if (context.TOP.contains(bpos))
                    setCondition(
                            world,
                            bpos,
                            pos.getY(),
                            state.setValue(RubeusLog.SHAPE, TripleShape.TOP),
                            false,
                            random
                    );
                else
                    setCondition(
                            world,
                            bpos,
                            pos.getY(),
                            state.setValue(RubeusLog.SHAPE, TripleShape.BOTTOM),
                            natural,
                            random
                    );
            }
            updateSDFFrom(bpos, context);
        }

        updateDistances(world, context);
        context.LOGS_DIST.clear();

        context.POINTS.clear();
        context.MIDDLE.clear();
        context.TOP.clear();

        return true;
    }


    private void line(
            LevelAccessor level,
            int x1,
            int y1,
            int z1,
            int x2,
            int y2,
            int z2,
            int middleY,
            StructureGeneratorThreadContext context
    ) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int dz = z2 - z1;
        int mx = Math.max(Math.max(Math.abs(dx), Math.abs(dy)), Math.abs(dz));
        float fdx = (float) dx / mx;
        float fdy = (float) dy / mx;
        float fdz = (float) dz / mx;
        float px = x1;
        float py = y1;
        float pz = z1;

        BlockPos pos = context.POS.set(x1, y1, z1).immutable();
        context.POINTS.add(pos);
        if (pos.getY() == middleY)
            context.MIDDLE.add(pos);
        else if (pos.getY() > middleY)
            context.TOP.add(pos);

        pos = context.POS.set(x2, y2, z2).immutable();
        context.POINTS.add(pos);
        if (pos.getY() == middleY)
            context.MIDDLE.add(pos);
        else if (pos.getY() > middleY)
            context.TOP.add(pos);

        for (int i = 0; i < mx; i++) {
            px += fdx;
            py += fdy;
            pz += fdz;

            context.POS.set(Math.round(px), Math.round(py), Math.round(pz));
            pos = context.POS.immutable();
            context.POINTS.add(pos);
            if (context.POS.getY() == middleY)
                context.MIDDLE.add(pos);
            else if (context.POS.getY() > middleY)
                context.TOP.add(pos);
        }
    }

    private void crown(LevelAccessor world, int x, int y, int z, float radius, RandomSource random) {
        final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();

        BlockState leaves = NetherBlocks.RUBEUS_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true);
        BlockState cone = NetherBlocks.MAT_RUBEUS.getCone().defaultBlockState();
        float halfR = radius * 0.5F;
        float r2 = radius * radius;
        int start = (int) Math.floor(-radius);
        for (int cy = start; cy <= radius; cy++) {
            int cy2_out = cy * cy;
            float cy2_in = cy + halfR;
            cy2_in *= cy2_in;
            POS.setY((int) (y + cy - halfR));
            for (int cx = start; cx <= radius; cx++) {
                int cx2 = cx * cx;
                POS.setX(x + cx);
                for (int cz = start; cz <= radius; cz++) {
                    int cz2 = cz * cz;
                    if (cx2 + cy2_out + cz2 < r2 && cx2 + cy2_in + cz2 > r2) {
                        POS.setZ(z + cz);
                        setIfAirLeaves(world, POS, leaves);
                        if (((POS.getX() + POS.getZ()) & 1) == 0 && random.nextInt(6) == 0)
                            setIfAir(world, POS.below(), cone);
                    }
                }
            }
        }
    }

    private void setCondition(
            LevelAccessor world,
            BlockPos pos,
            int y,
            BlockState state,
            boolean moss,
            RandomSource random
    ) {
        if (pos.getY() > y)
            setIfAir(world, pos, state);
        else
            setIfGroundOrAir(world, pos, state);
        if (moss && Math.abs(pos.getY() - y) < 4) {
            for (Direction dir : BlocksHelper.HORIZONTAL) {
                if (random.nextInt(3) > 0)
                    setIfAir(world, pos.relative(dir), NetherBlocks.JUNGLE_MOSS.defaultBlockState().setValue(
                            BlockPlantWall.FACING, dir));
            }
        }
    }

    private void setIfAir(LevelAccessor world, BlockPos pos, BlockState state) {
        BlockState bState = world.getBlockState(pos);
        if (world.isEmptyBlock(pos) || bState.getMaterial()
                                             .isReplaceable() || bState.getBlock() == NetherBlocks.RUBEUS_LEAVES || bState.getBlock() == NetherBlocks.MAT_RUBEUS.getCone())
            BlocksHelper.setWithoutUpdate(world, pos, state);
    }

    private void setIfGroundOrAir(LevelAccessor world, BlockPos pos, BlockState state) {
        BlockState bState = world.getBlockState(pos);
        if (bState.isAir() || bState.getBlock() == NetherBlocks.RUBEUS_LEAVES || bState.getMaterial()
                                                                                       .isReplaceable() || BlocksHelper.isNetherGround(
                bState))
            BlocksHelper.setWithoutUpdate(world, pos, state);
    }

    private void setIfAirLeaves(LevelAccessor world, BlockPos pos, BlockState state) {
        BlockState bState = world.getBlockState(pos);
        if (world.isEmptyBlock(pos) || bState.getMaterial().isReplaceable())
            BlocksHelper.setWithoutUpdate(world, pos, state);
    }

    @Override
    public boolean grow(
            ServerLevelAccessor level,
            BlockPos pos,
            RandomSource random,
            NaturalTreeConfiguration configuration
    ) {
        return grow(
                level,
                pos,
                random,
                new NaturalTreeConfiguration(false, configuration.distance),
                NetherChunkPopulatorFeature.generatorForThread().context
        );
    }
}
