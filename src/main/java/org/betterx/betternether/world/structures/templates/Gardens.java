package org.betterx.betternether.world.structures.templates;

import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.List;

public class Gardens extends TemplateStructureHelper {
    public static final Codec<Gardens> CODEC = simpleTemplateCodec(Gardens::new);

    protected Gardens(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public Gardens(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("garden_01", -3, StructurePlacementType.FLOOR, 1.0f),
                        cfg("garden_02", -2, StructurePlacementType.FLOOR, 1.0f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.GARDENS.structureType;
    }
}
