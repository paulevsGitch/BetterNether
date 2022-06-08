package org.betterx.betternether.world.structures.decorations;

import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.NetherStructureWorld;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class StructureWartDeadwood implements IStructure {
    private static final NetherStructureWorld[] TREES = new NetherStructureWorld[]{
            new NetherStructureWorld("trees/wart_root_01", 0, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/wart_root_02", 0, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/wart_root_03", -2, StructurePlacementType.FLOOR),
            new NetherStructureWorld("trees/wart_fallen_log", 0, StructurePlacementType.FLOOR)
    };

    @Override
    public void generate(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        if (isGround(world.getBlockState(pos.below())) && isGround(world.getBlockState(pos.below(2)))) {
            NetherStructureWorld tree = TREES[random.nextInt(TREES.length)];
            tree.generate(world, pos, random, MAX_HEIGHT, context);
        }
    }

    private boolean isGround(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }
}
