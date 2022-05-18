package org.betterx.betternether.world.structures.plants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureObjScatter;
import org.betterx.betternether.world.structures.StructureType;
import org.betterx.betternether.world.structures.StructureWorld;

public class StructureOldRedMushrooms extends StructureObjScatter {
    private static final StructureWorld[] TREES = new StructureWorld[]{
            new StructureWorld("trees/red_mushroom_01", -2, StructureType.FLOOR),
            new StructureWorld("trees/red_mushroom_02", -1, StructureType.FLOOR),
            new StructureWorld("trees/red_mushroom_03", -1, StructureType.FLOOR),
            new StructureWorld("trees/red_mushroom_04", -4, StructureType.FLOOR),
            new StructureWorld("trees/red_mushroom_05", -4, StructureType.FLOOR),
            new StructureWorld("trees/red_mushroom_06", -1, StructureType.FLOOR),
            new StructureWorld("trees/red_mushroom_07", -4, StructureType.FLOOR)
    };

    public StructureOldRedMushrooms() {
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
