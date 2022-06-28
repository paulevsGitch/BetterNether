package org.betterx.betternether.world.features;

import org.betterx.bclib.api.v3.levelgen.features.UserGrowableFeature;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BNBlockProperties;
import org.betterx.betternether.blocks.BlockLucisMushroom;
import org.betterx.betternether.blocks.BlockLucisSpore;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LucisFeature extends ContextFeature<NoneFeatureConfiguration> implements UserGrowableFeature<NoneFeatureConfiguration> {

    private static final BlockState CENTER = NetherBlocks.LUCIS_MUSHROOM.defaultBlockState()
                                                                        .setValue(
                                                                                BlockLucisMushroom.SHAPE,
                                                                                BNBlockProperties.EnumLucisShape.CENTER
                                                                        );
    private static final BlockState SIDE = NetherBlocks.LUCIS_MUSHROOM.defaultBlockState()
                                                                      .setValue(
                                                                              BlockLucisMushroom.SHAPE,
                                                                              BNBlockProperties.EnumLucisShape.SIDE
                                                                      );
    private static final BlockState CORNER = NetherBlocks.LUCIS_MUSHROOM.defaultBlockState()
                                                                        .setValue(
                                                                                BlockLucisMushroom.SHAPE,
                                                                                BNBlockProperties.EnumLucisShape.CORNER
                                                                        );

    public LucisFeature() {
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
        return grow(world, pos, random, false);
    }

    protected boolean grow(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            boolean force
    ) {
        if (force || canGenerate(world, pos)) {

            if (random.nextInt(3) == 0) {
                if (canReplace(world.getBlockState(pos)))
                    BlocksHelper.setWithUpdate(world, pos, CENTER);
                if (canReplace(world.getBlockState(pos.north())))
                    BlocksHelper.setWithUpdate(
                            world,
                            pos.north(),
                            SIDE.setValue(BlockLucisMushroom.FACING, Direction.NORTH)
                    );
                if (canReplace(world.getBlockState(pos.south())))
                    BlocksHelper.setWithUpdate(
                            world,
                            pos.south(),
                            SIDE.setValue(BlockLucisMushroom.FACING, Direction.SOUTH)
                    );
                if (canReplace(world.getBlockState(pos.east())))
                    BlocksHelper.setWithUpdate(
                            world,
                            pos.east(),
                            SIDE.setValue(BlockLucisMushroom.FACING, Direction.EAST)
                    );
                if (canReplace(world.getBlockState(pos.west())))
                    BlocksHelper.setWithUpdate(
                            world,
                            pos.west(),
                            SIDE.setValue(BlockLucisMushroom.FACING, Direction.WEST)
                    );

                if (canReplace(world.getBlockState(pos.north().east())))
                    BlocksHelper.setWithUpdate(
                            world,
                            pos.north().east(),
                            CORNER.setValue(BlockLucisMushroom.FACING, Direction.SOUTH)
                    );
                if (canReplace(world.getBlockState(pos.north().west())))
                    BlocksHelper.setWithUpdate(
                            world,
                            pos.north().west(),
                            CORNER.setValue(BlockLucisMushroom.FACING, Direction.EAST)
                    );
                if (canReplace(world.getBlockState(pos.south().east())))
                    BlocksHelper.setWithUpdate(
                            world,
                            pos.south().east(),
                            CORNER.setValue(BlockLucisMushroom.FACING, Direction.WEST)
                    );
                if (canReplace(world.getBlockState(pos.south().west())))
                    BlocksHelper.setWithUpdate(
                            world,
                            pos.south().west(),
                            CORNER.setValue(BlockLucisMushroom.FACING, Direction.NORTH)
                    );
            } else {
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() == NetherBlocks.LUCIS_SPORE) {
                    if (state.getValue(BlockLucisSpore.FACING) == Direction.SOUTH) pos = pos.north();
                    else if (state.getValue(BlockLucisSpore.FACING) == Direction.WEST) pos = pos.east();
                } else {
                    if (!world.getBlockState(pos.north()).isAir()) {
                        pos = pos.north();
                    } else if (!world.getBlockState(pos.east()).isAir()) {
                        pos = pos.east();
                    }
                }

                if (canReplace(world.getBlockState(pos)))
                    BlocksHelper.setWithUpdate(world, pos, CORNER.setValue(BlockLucisMushroom.FACING, Direction.SOUTH));
                if (canReplace(world.getBlockState(pos.west()))) BlocksHelper.setWithUpdate(
                        world,
                        pos.west(),
                        CORNER.setValue(
                                BlockLucisMushroom.FACING,
                                Direction.EAST
                        )
                );
                if (canReplace(world.getBlockState(pos.south()))) BlocksHelper.setWithUpdate(
                        world,
                        pos.south(),
                        CORNER.setValue(
                                BlockLucisMushroom.FACING,
                                Direction.WEST
                        )
                );
                if (canReplace(world.getBlockState(pos.south().west()))) BlocksHelper.setWithUpdate(
                        world,
                        pos.south().west(),
                        CORNER.setValue(
                                BlockLucisMushroom.FACING,
                                Direction.NORTH
                        )
                );
            }
            return true;
        }
        return false;
    }

    private boolean canReplace(BlockState state) {
        return state.getBlock() == NetherBlocks.LUCIS_SPORE || state.getMaterial().isReplaceable();
    }

    private boolean canGenerate(ServerLevelAccessor world, BlockPos pos) {
        BlockState state;
        for (Direction dir : HorizontalDirectionalBlock.FACING.getPossibleValues())
            if (BlocksHelper.isNetherrack(state = world.getBlockState(pos.relative(dir))) || NetherBlocks.MAT_ANCHOR_TREE.isTreeLog(
                    state.getBlock()))
                return true;
        return false;
    }

    @Override
    public boolean grow(
            ServerLevelAccessor level,
            BlockPos pos,
            RandomSource random,
            NoneFeatureConfiguration configuration
    ) {
        return grow(level, pos, random, true);
    }
}
