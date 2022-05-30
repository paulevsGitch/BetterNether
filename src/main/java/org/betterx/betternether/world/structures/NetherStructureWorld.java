package org.betterx.betternether.world.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;

import com.google.common.collect.Maps;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;

import java.util.Map;

public class NetherStructureWorld extends StructureWorldNBT implements IStructure {
    public final StructurePlacementType type;

    public NetherStructureWorld(String name, int offsetY, StructurePlacementType type) {
        super(BetterNether.makeID(name), offsetY, type);
        this.type = type;
    }

    private static final Map<String, NetherStructureWorld> READER_CACHE = Maps.newHashMap();

    public static NetherStructureWorld create(String name, int offsetY, StructurePlacementType type) {
        String key = name + "::" + offsetY + "::" + type.getSerializedName();
        return READER_CACHE.computeIfAbsent(key, r -> new NetherStructureWorld(name, offsetY, type));
    }

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        generateInRandomOrientation(world, pos, random);
    }
}
