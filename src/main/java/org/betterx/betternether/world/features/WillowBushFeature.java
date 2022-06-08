package org.betterx.betternether.world.features;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockNetherGrass;
import org.betterx.betternether.blocks.BlockWillowLeaves;
import org.betterx.betternether.blocks.complex.WillowMaterial;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WillowBushFeature extends ContextFeature<NoneFeatureConfiguration> {
    public WillowBushFeature() {
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
        if (!world.isEmptyBlock(pos) || !world.isEmptyBlock(pos.above()) || !world.isEmptyBlock(pos.above(15)))
            return false;

        float r = random.nextFloat() * 2F + 0.5F;
        int count = (int) r;

        for (int i = 0; i < count; i++) {
            float fr = r - i;
            int ir = (int) Math.ceil(fr);
            float r2 = fr * fr;

            int x1 = pos.getX() - ir;
            int x2 = pos.getX() + ir;
            int z1 = pos.getZ() - ir;
            int z2 = pos.getZ() + ir;

            context.POS.setY(pos.getY() + i);

            for (int x = x1; x < x2; x++) {
                context.POS.setX(x);
                int sqx = x - pos.getX();
                sqx *= sqx;
                for (int z = z1; z < z2; z++) {
                    int sqz = z - pos.getZ();
                    sqz *= sqz;
                    context.POS.setZ(z);
                    if (sqx + sqz < r2 + random.nextFloat() * r) {
                        int dx = context.POS.getX() - pos.getX();
                        int dy = context.POS.getY() - pos.getY();
                        int dz = context.POS.getZ() - pos.getZ();
                        int ax = Math.abs(dx);
                        int ay = Math.abs(dy);
                        int az = Math.abs(dz);
                        int max = Math.max(ax, Math.max(ay, az));
                        Direction dir;
                        if (max == ax) dir = Direction.fromAxisAndDirection(
                                Direction.Axis.X,
                                dx > 0
                                        ? Direction.AxisDirection.POSITIVE
                                        : Direction.AxisDirection.NEGATIVE
                        );
                        else if (max == ay) dir = Direction.fromAxisAndDirection(
                                Direction.Axis.Y,
                                dy > 0
                                        ? Direction.AxisDirection.POSITIVE
                                        : Direction.AxisDirection.NEGATIVE
                        );
                        else
                            dir = Direction.fromAxisAndDirection(
                                    Direction.Axis.Z,
                                    dz > 0
                                            ? Direction.AxisDirection.POSITIVE
                                            : Direction.AxisDirection.NEGATIVE
                            );
                        setIfAir(world, context.POS, NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(
                                BlockWillowLeaves.FACING, dir).setValue(LeavesBlock.PERSISTENT, true));
                    }
                }
            }
        }

        BlocksHelper.setWithoutUpdate(
                world,
                pos,
                NetherBlocks.MAT_WILLOW.getBlock(WillowMaterial.BLOCK_BARK).defaultBlockState()
        );
        setIfAir(
                world,
                pos.above(),
                NetherBlocks.WILLOW_LEAVES.defaultBlockState()
                                          .setValue(BlockWillowLeaves.FACING, Direction.UP)
                                          .setValue(LeavesBlock.DISTANCE, 1)
        );
        setIfAir(
                world,
                pos.north(),
                NetherBlocks.WILLOW_LEAVES.defaultBlockState()
                                          .setValue(BlockWillowLeaves.FACING, Direction.NORTH)
                                          .setValue(LeavesBlock.DISTANCE, 1)
        );
        setIfAir(
                world,
                pos.south(),
                NetherBlocks.WILLOW_LEAVES.defaultBlockState()
                                          .setValue(BlockWillowLeaves.FACING, Direction.SOUTH)
                                          .setValue(LeavesBlock.DISTANCE, 1)
        );
        setIfAir(
                world,
                pos.east(),
                NetherBlocks.WILLOW_LEAVES.defaultBlockState()
                                          .setValue(BlockWillowLeaves.FACING, Direction.EAST)
                                          .setValue(LeavesBlock.DISTANCE, 1)
        );
        setIfAir(
                world,
                pos.west(),
                NetherBlocks.WILLOW_LEAVES.defaultBlockState()
                                          .setValue(BlockWillowLeaves.FACING, Direction.WEST)
                                          .setValue(LeavesBlock.DISTANCE, 1)
        );

        return true;
    }

    private void setIfAir(LevelAccessor world, BlockPos pos, BlockState state) {
        if (world.isEmptyBlock(pos) || world.getBlockState(pos).getBlock() instanceof BlockNetherGrass)
            BlocksHelper.setWithoutUpdate(world, pos, state);
    }
}
