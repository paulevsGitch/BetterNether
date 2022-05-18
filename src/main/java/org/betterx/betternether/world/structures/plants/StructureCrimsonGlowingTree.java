package org.betterx.betternether.world.structures.plants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.world.structures.StructureObjScatter;
import org.betterx.betternether.world.structures.StructureType;
import org.betterx.betternether.world.structures.StructureWorld;

public class StructureCrimsonGlowingTree extends StructureObjScatter {
    private static final StructureWorld[] TREES = new StructureWorld[]{
            new StructureWorld("trees/crimson_glow_tree_01", -1, StructureType.FLOOR),
            new StructureWorld("trees/crimson_glow_tree_02", -1, StructureType.FLOOR),
            new StructureWorld("trees/crimson_glow_tree_03", -1, StructureType.FLOOR),
            new StructureWorld("trees/crimson_glow_tree_04", -1, StructureType.FLOOR)
    };

    public StructureCrimsonGlowingTree() {
        super(7, TREES);
    }

    protected boolean isGround(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }

    protected boolean isStructure(BlockState state) {
        return state.getBlock() == Blocks.CRIMSON_STEM;
    }
}
