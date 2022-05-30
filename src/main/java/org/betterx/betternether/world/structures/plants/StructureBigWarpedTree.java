package org.betterx.betternether.world.structures.plants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.world.structures.NetherStructureWorld;
import org.betterx.betternether.world.structures.StructureObjScatter;

public class StructureBigWarpedTree extends StructureObjScatter {
    private static final NetherStructureWorld[] TREES = new NetherStructureWorld[]{
            new NetherStructureWorld("trees/warped_tree_01", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/warped_tree_02", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/warped_tree_03", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/warped_tree_04", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/warped_tree_05", -4, StructurePlacementType.FLOOR)
    };

    public StructureBigWarpedTree() {
        super(10, TREES);
    }

    protected boolean isGround(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }

    protected boolean isStructure(BlockState state) {
        return state.getBlock() == Blocks.WARPED_STEM ||
                state.getBlock() == Blocks.WARPED_WART_BLOCK ||
                state.getBlock() == Blocks.SHROOMLIGHT;
    }
}
