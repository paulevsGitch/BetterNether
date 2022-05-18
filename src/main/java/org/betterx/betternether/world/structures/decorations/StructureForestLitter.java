package org.betterx.betternether.world.structures.decorations;

import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureObjScatter;
import org.betterx.betternether.world.structures.StructureType;
import org.betterx.betternether.world.structures.StructureWorld;

public class StructureForestLitter extends StructureObjScatter {
    private static final StructureWorld[] STRUCTURES = new StructureWorld[]{
            new StructureWorld("upside_down_forest/tree_fallen", 0, StructureType.FLOOR),
            new StructureWorld("upside_down_forest/tree_needle", 0, StructureType.FLOOR),
            new StructureWorld("upside_down_forest/tree_root", -2, StructureType.FLOOR),
            new StructureWorld("upside_down_forest/tree_stump", -2, StructureType.FLOOR),
            new StructureWorld("upside_down_forest/tree_small_branch", 0, StructureType.FLOOR)
    };

    public StructureForestLitter() {
        super(8, STRUCTURES);
    }

    @Override
    protected boolean isStructure(BlockState state) {
        return NetherBlocks.MAT_ANCHOR_TREE.isTreeLog(state.getBlock());
    }

    @Override
    protected boolean isGround(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }
}
