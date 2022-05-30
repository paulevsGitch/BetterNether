package org.betterx.betternether.world.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;

public class NetherStructureWorld extends StructureWorldNBT implements IStructure {
    public final StructurePlacementType type;

    public NetherStructureWorld(String name, int offsetY, StructurePlacementType type) {
        super(BetterNether.makeID(name), offsetY, type);
        this.type = type;
    }

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        generate(world, pos, random);
    }

    protected boolean canGenerate(LevelAccessor world, BlockPos pos) {
        if (type == StructurePlacementType.FLOOR)
            return canGenerateFloor(world, pos);
        else if (type == StructurePlacementType.LAVA)
            return canGenerateLava(world, pos);
        else if (type == StructurePlacementType.UNDER)
            return canGenerateUnder(world, pos);
        else if (type == StructurePlacementType.CEIL)
            return canGenerateCeil(world, pos);
        else
            return false;
    }
}
