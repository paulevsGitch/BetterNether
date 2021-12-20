package paulevs.betternether.world.structures.plants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockWartSeed;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.StructureFuncScatter;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureWartTree extends StructureFuncScatter {
    private static final BlockState WART_BLOCK = Blocks.NETHER_WART_BLOCK.defaultBlockState();
    private static final Direction[] HORIZONTAL = HorizontalDirectionalBlock.FACING.getPossibleValues().toArray(new Direction[]{});

    public StructureWartTree() {
        super(7);
    }



    @Override
    public void grow(ServerLevelAccessor world, BlockPos pos, Random random, boolean natural, StructureGeneratorThreadContext context) {
        if (world.isEmptyBlock(pos.above(1)) && world.isEmptyBlock(pos.above(2))) {
            if (world.isEmptyBlock(pos.above(2).north()) && world.isEmptyBlock(pos.above(2).south()) && world.isEmptyBlock(pos.above(2).east()) && world.isEmptyBlock(pos.above(2).west()))
                if (world.isEmptyBlock(pos.above(3).north(2)) && world.isEmptyBlock(pos.above(3).south(2)) && world.isEmptyBlock(pos.above(3).east(2)) && world.isEmptyBlock(pos.above(3).west(2))) {
                    int height = 5 + random.nextInt(5);
                    int h2 = height - 1;
                    int width = (height >>> 2) + 1;
                    int offset = width >>> 1;
                    List<BlockPos> seedBlocks = new ArrayList<>();
                    for (int x = 0; x < width; x++) {
                        int px = x + pos.getX() - offset;
                        for (int z = 0; z < width; z++) {
                            int pz = z + pos.getZ() - offset;
                            for (int y = 0; y < height; y++) {
                                int py = y + pos.getY();
                                context.POS.set(px, py, pz);
                                if (isReplaceable(world.getBlockState(context.POS))) {
                                    if (y == 0 && !isReplaceable(world.getBlockState(context.POS.below())))
                                        BlocksHelper.setWithUpdate(world, context.POS, NetherBlocks.MAT_WART.getRoot().defaultBlockState());
                                    else if (y < h2)
                                        BlocksHelper.setWithUpdate(world, context.POS, NetherBlocks.MAT_WART.getLog().defaultBlockState());
                                    else
                                        BlocksHelper.setWithUpdate(world, context.POS, WART_BLOCK);
                                    if (random.nextInt(8) == 0) {
                                        Direction dir = HORIZONTAL[random.nextInt(HORIZONTAL.length)];
                                        seedBlocks.add(new BlockPos(context.POS).relative(dir));
                                    }
                                }
                            }
                        }
                    }

                    for (int x = 0; x < width; x++) {
                        int px = x + pos.getX() - offset;
                        for (int z = 0; z < width; z++) {
                            int pz = z + pos.getZ() - offset;
                            for (int y = 1; y < height >> 1; y++) {
                                int py = pos.getY() - y;
                                context.POS.set(px, py, pz);
                                if (isReplaceable(world.getBlockState(context.POS))) {
                                    if (isReplaceable(world.getBlockState(context.POS.below())))
                                        BlocksHelper.setWithUpdate(world, context.POS, NetherBlocks.MAT_WART.getLog().defaultBlockState());
                                    else {
                                        BlocksHelper.setWithUpdate(world, context.POS, NetherBlocks.MAT_WART.getRoot().defaultBlockState());
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    int headWidth = width + 2;
                    offset++;
                    height = height - width - 1 + pos.getY();
                    for (int x = 0; x < headWidth; x++) {
                        int px = x + pos.getX() - offset;
                        for (int z = 0; z < headWidth; z++) {
                            if (x != z && x != (headWidth - z - 1)) {
                                int pz = z + pos.getZ() - offset;
                                for (int y = 0; y < width; y++) {
                                    int py = y + height;
                                    context.POS.set(px, py, pz);
                                    if (world.isEmptyBlock(context.POS)) {
                                        BlocksHelper.setWithUpdate(world, context.POS, WART_BLOCK);
                                        for (int i = 0; i < 4; i++)
                                            seedBlocks.add(new BlockPos(context.POS).relative(Direction.values()[random.nextInt(6)]));
                                    }
                                }
                            }
                        }
                    }
                    for (BlockPos pos2 : seedBlocks)
                        PlaceRandomSeed(world, pos2);
                }
        }
    }

    private void PlaceRandomSeed(LevelAccessor world, BlockPos pos) {
        BlockState seed = NetherBlocks.MAT_WART.getSeed().defaultBlockState();
        if (isReplaceable(world.getBlockState(pos))) {
            if (isWart(world.getBlockState(pos.above())))
                seed = seed.setValue(BlockWartSeed.FACING, Direction.DOWN);
            else if (isWart(world.getBlockState(pos.below())))
                seed = seed.setValue(BlockWartSeed.FACING, Direction.UP);
            else if (isWart(world.getBlockState(pos.north())))
                seed = seed.setValue(BlockWartSeed.FACING, Direction.SOUTH);
            else if (isWart(world.getBlockState(pos.south())))
                seed = seed.setValue(BlockWartSeed.FACING, Direction.NORTH);
            else if (isWart(world.getBlockState(pos.east())))
                seed = seed.setValue(BlockWartSeed.FACING, Direction.WEST);
            else if (isWart(world.getBlockState(pos.west())))
                seed = seed.setValue(BlockWartSeed.FACING, Direction.EAST);
            BlocksHelper.setWithUpdate(world, pos, seed);
        }
    }

    private boolean isReplaceable(BlockState state) {
        Block block = state.getBlock();
        return state.getMaterial().isReplaceable() ||
                block == Blocks.AIR ||
                block == NetherBlocks.MAT_WART.getSeed() ||
                block == NetherBlocks.BLACK_BUSH ||
                block == NetherBlocks.SOUL_VEIN ||
                block == NetherBlocks.SOUL_LILY ||
                block == NetherBlocks.SOUL_LILY_SAPLING ||
                block == Blocks.NETHER_WART;
    }

    private boolean isWart(BlockState state) {
        return state == WART_BLOCK || state.getBlock() == NetherBlocks.MAT_WART.getLog();
    }

    @Override
    protected boolean isStructure(BlockState state) {
        return isWart(state);
    }

    @Override
    protected boolean isGround(BlockState state) {
        return BlocksHelper.isSoulSand(state);
    }
}