package org.betterx.betternether.world.structures.plants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.NetherStructureWorld;
import org.betterx.betternether.world.structures.StructureObjScatter;

public class StructureOldRedMushrooms extends StructureObjScatter {
    private static final NetherStructureWorld[] TREES = new NetherStructureWorld[]{
            new NetherStructureWorld("trees/red_mushroom_01", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/red_mushroom_02", -1, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/red_mushroom_03", -1, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/red_mushroom_04", -4, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/red_mushroom_05", -4, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/red_mushroom_06", -1, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/red_mushroom_07", -4, StructurePlacementType.FLOOR)
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
