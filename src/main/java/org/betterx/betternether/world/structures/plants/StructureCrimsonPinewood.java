package org.betterx.betternether.world.structures.plants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.world.structures.NetherStructureWorld;
import org.betterx.betternether.world.structures.StructureObjScatter;

public class StructureCrimsonPinewood extends StructureObjScatter {
    private static final NetherStructureWorld[] TREES = new NetherStructureWorld[]{
            new NetherStructureWorld("trees/crimson_pine_01", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/crimson_pine_02", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/crimson_pine_03", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/crimson_pine_04", -1, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/crimson_pine_05", -1, StructurePlacementType.FLOOR)
    };

    public StructureCrimsonPinewood() {
        super(7, TREES);
    }

    protected boolean isGround(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }

    protected boolean isStructure(BlockState state) {
        return state.getBlock() == Blocks.CRIMSON_STEM;
    }
}
