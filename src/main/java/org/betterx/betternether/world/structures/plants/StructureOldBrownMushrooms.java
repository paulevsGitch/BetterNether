package org.betterx.betternether.world.structures.plants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureObjScatter;
import org.betterx.betternether.world.structures.StructureType;
import org.betterx.betternether.world.structures.StructureWorld;

public class StructureOldBrownMushrooms extends StructureObjScatter {
    private static final StructureWorld[] TREES = new StructureWorld[]{
            new StructureWorld("trees/brown_mushroom_01", -4, StructureType.FLOOR),
            new StructureWorld("trees/brown_mushroom_02", -3, StructureType.FLOOR),
            new StructureWorld("trees/brown_mushroom_03", -3, StructureType.FLOOR),
            new StructureWorld("trees/brown_mushroom_04", -2, StructureType.FLOOR)
    };

    public StructureOldBrownMushrooms() {
        super(9, TREES);
    }

    protected boolean isGround(BlockState state) {
        return state.getBlock() == NetherBlocks.NETHER_MYCELIUM || BlocksHelper.isNetherGround(state);
    }

    protected boolean isStructure(BlockState state) {
        return state.getBlock() == Blocks.MUSHROOM_STEM ||
                state.getBlock() == Blocks.BROWN_MUSHROOM_BLOCK ||
                state.getBlock() == Blocks.RED_MUSHROOM_BLOCK;
    }
}
