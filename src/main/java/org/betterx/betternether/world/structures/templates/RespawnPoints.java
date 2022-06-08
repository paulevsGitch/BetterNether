package org.betterx.betternether.world.structures.templates;

import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.List;

public class RespawnPoints extends TemplateStructureHelper {
    public static final Codec<RespawnPoints> CODEC = simpleTemplateCodec(RespawnPoints::new);

    protected RespawnPoints(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public RespawnPoints(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("respawn_point_01", -3, StructurePlacementType.FLOOR, 1.0f),
                        cfg("respawn_point_02", -2, StructurePlacementType.FLOOR, 1.0f),
                        cfg("respawn_point_03", -3, StructurePlacementType.FLOOR, 0.6f),
                        cfg("respawn_point_04", -2, StructurePlacementType.FLOOR, 0.3f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.RESPAWN_POINTS.structureType;
    }
}
