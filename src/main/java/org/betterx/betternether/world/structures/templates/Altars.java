package org.betterx.betternether.world.structures.templates;

import net.minecraft.world.level.levelgen.structure.StructureType;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import java.util.List;

public class Altars extends TemplateStructureHelper {
    public static final Codec<Altars> CODEC = simpleTemplateCodec(Altars::new);

    protected Altars(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public Altars(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("altar_01", -2, StructurePlacementType.FLOOR, 1.0f),
                        cfg("altar_02", -4, StructurePlacementType.FLOOR, 1.0f),
                        cfg("altar_03", -3, StructurePlacementType.FLOOR, 1.0f),
                        cfg("altar_04", -3, StructurePlacementType.FLOOR, 1.0f),
                        cfg("altar_05", -2, StructurePlacementType.FLOOR, 1.0f),
                        cfg("altar_06", -2, StructurePlacementType.FLOOR, 1.0f),
                        cfg("altar_07", -2, StructurePlacementType.FLOOR, 1.0f),
                        cfg("altar_08", -2, StructurePlacementType.FLOOR, 1.0f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.ALTARS.structureType;
    }
}
