package org.betterx.betternether.world.structures.templates;

import net.minecraft.world.level.levelgen.structure.StructureType;

import com.mojang.serialization.Codec;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import java.util.List;

public class Pyramids extends TemplateStructureHelper {
    public static final Codec<Pyramids> CODEC = simpleTemplateCodec(Pyramids::new);

    protected Pyramids(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public Pyramids(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("lava/pyramid_1", -1, StructurePlacementType.LAVA, 1.0f),
                        cfg("lava/pyramid_2", -1, StructurePlacementType.LAVA, 1.0f),
                        cfg("lava/pyramid_3", -1, StructurePlacementType.LAVA, 0.6f),
                        cfg("lava/pyramid_4", -1, StructurePlacementType.LAVA, 0.3f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.PYRAMIDS.structureType;
    }
}
