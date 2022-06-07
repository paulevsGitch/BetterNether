package org.betterx.betternether.world.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;

import com.google.common.collect.Maps;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.bclib.api.v2.levelgen.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;

import java.util.Map;

public class NetherStructureWorld extends StructureWorldNBT implements IStructure {
    @Deprecated(forRemoval = true)
    public NetherStructureWorld(String name, int offsetY, StructurePlacementType type) {
        this(name, offsetY, type, 1.0f);
    }

    @Deprecated(forRemoval = true)
    public NetherStructureWorld(String name, int offsetY, StructurePlacementType type, float chance) {
        super(BetterNether.makeID(name), offsetY, type, chance);
    }

    private static final Map<String, NetherStructureWorld> READER_CACHE = Maps.newHashMap();

    public static NetherStructureWorld create(String name, int offsetY, StructurePlacementType type) {
        String key = name + "::" + offsetY + "::" + type.getSerializedName();
        return READER_CACHE.computeIfAbsent(key, r -> new NetherStructureWorld(name, offsetY, type, 1.0f));
    }

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        generateIfPlaceable(world,
                pos,
                random
        );
    }
}
