package org.betterx.betternether.world.structures.templates;

import net.minecraft.world.level.levelgen.structure.StructureType;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import java.util.List;

public class Pillars extends TemplateStructureHelper {
    public static final Codec<Pillars> CODEC = simpleTemplateCodec(Pillars::new);

    protected Pillars(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public Pillars(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("pillar_01", -1, StructurePlacementType.FLOOR, 1.0f),
                        cfg("pillar_02", -1, StructurePlacementType.FLOOR, 1.0f),
                        cfg("pillar_03", -1, StructurePlacementType.FLOOR, 1.0f),
                        cfg("pillar_04", -1, StructurePlacementType.FLOOR, 1.0f),
                        cfg("pillar_05", -1, StructurePlacementType.FLOOR, 1.0f),
                        cfg("pillar_06", -1, StructurePlacementType.FLOOR, 1.0f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.PILLARS.structureType;
    }
}
