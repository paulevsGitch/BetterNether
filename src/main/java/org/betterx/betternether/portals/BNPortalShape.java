package org.betterx.betternether.portals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BNPortalShape {
    private static final Direction[] DIR_X = {Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST};
    private static final Direction[] DIR_Z = {Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH};
    public static final int MAX_SEARCH_DIST = 21;

    private final LevelAccessor levelAccessor;
    private final BlockPos blockPos;
    private final Direction.Axis axis;

    BlockPos.MutableBlockPos min;
    BlockPos.MutableBlockPos max;
    private final boolean valid;
    private final List<BlockPos> portalBlocks = new LinkedList<>();
    private int numPortalBlocks = 0;

    public BNPortalShape(
            LevelAccessor levelAccessor,
            BlockPos blockPos,
            Direction.Axis axis
    ) {
        this.levelAccessor = levelAccessor;
        this.blockPos = blockPos;
        this.axis = axis;

        if (axis.equals(Direction.Axis.X))
            valid = getPortalBlocks(blockPos, DIR_X);
        else
            valid = getPortalBlocks(blockPos, DIR_Z);
    }

    private static final BlockPos[] stack = new BlockPos[MAX_SEARCH_DIST * 3];

    private boolean getPortalBlocks(BlockPos blockPos, Direction[] directions) {
        portalBlocks.clear();
        final BlockState[][] states = new BlockState[MAX_SEARCH_DIST * 2][MAX_SEARCH_DIST * 2];
        final byte[][] mask = new byte[MAX_SEARCH_DIST * 2][MAX_SEARCH_DIST * 2];
        min = blockPos.mutable();
        max = blockPos.mutable();
        int nextStackPos = 0;
        Stack<BlockPos> todo = new Stack<>();
        todo.add(blockPos);
        while (!todo.isEmpty()) {
            BlockPos p = todo.pop();
            BlockState tstate = levelAccessor.getBlockState(p);
            if (tstate.is(Blocks.NETHER_PORTAL)) numPortalBlocks++;

            portalBlocks.add(p);
            if (p.getX() < min.getX()) min.setX(p.getX());
            if (p.getY() < min.getY()) min.setY(p.getY());
            if (p.getZ() < min.getZ()) min.setZ(p.getZ());

            if (p.getX() > max.getX()) max.setX(p.getX());
            if (p.getY() > max.getY()) max.setY(p.getY());
            if (p.getZ() > max.getZ()) max.setZ(p.getZ());

            for (Direction d : directions) {
                BlockPos pp = p.relative(d, 1);
                if (!portalBlocks.contains(pp)) {
                    if (Math.abs(pp.getX() - blockPos.getX()) > MAX_SEARCH_DIST || Math.abs(pp.getZ() - blockPos.getZ()) > MAX_SEARCH_DIST || Math.abs(
                            pp.getY() - blockPos.getY()) > MAX_SEARCH_DIST) {
                        portalBlocks.clear();
                        return false;
                    }
                    BlockState state = levelAccessor.getBlockState(pp);
                    if (isEmpty(state)) {

                        todo.add(pp);
                    } else if (!isFrame(state)) {
                        portalBlocks.clear();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public BlockPos calculateBottomLeft(BlockPos blockPos) {
        return valid ? min : null;
    }

    public int calculateWidth() {
        if (axis == Direction.Axis.X)
            return valid ? max.getX() - min.getX() : 0;
        if (axis == Direction.Axis.Z)
            return valid ? max.getZ() - min.getZ() : 0;
        return 0;
    }

    public int calculateHeight() {
        if (axis == Direction.Axis.X)
            return valid ? max.getY() - min.getY() : 0;
        if (axis == Direction.Axis.Z)
            return valid ? max.getY() - min.getY() : 0;
        return 0;
    }

    private static boolean isFrame(BlockState blockState) {
        return blockState.is(Blocks.OBSIDIAN) || blockState.is(org.betterx.worlds.together.tag.v3.CommonBlockTags.NETHER_PORTAL_FRAME);
    }

    private static boolean isEmpty(BlockState blockState) {
        return blockState.isAir() || blockState.is(BlockTags.FIRE) || blockState.is(Blocks.NETHER_PORTAL);
    }

    public boolean isValid() {
        return valid;
    }

    public void createPortalBlocks() {
        BlockState blockState = Blocks.NETHER_PORTAL.defaultBlockState()
                                                    .setValue(NetherPortalBlock.AXIS, this.axis);
        portalBlocks.forEach(p -> this.levelAccessor.setBlock(p, blockState, 18));
    }

    public boolean isComplete() {
        return valid && this.numPortalBlocks == portalBlocks.size();
    }
}
