package org.betterx.betternether.world.structures.templates;

import net.minecraft.world.level.levelgen.structure.StructureType;

import com.mojang.serialization.Codec;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import java.util.List;

public class JungleTemples extends TemplateStructureHelper {
    public static final Codec<JungleTemples> CODEC = simpleTemplateCodec(JungleTemples::new);

    protected JungleTemples(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public JungleTemples(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("ruined_temple", -4, StructurePlacementType.CEIL, 1.0f),
                        cfg("jungle_temple_altar", -2, StructurePlacementType.CEIL, 1.0f),
                        cfg("jungle_temple_2", -2, StructurePlacementType.CEIL, 1.0f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.JUNGLE_TEMPLES.structureType;
    }
}
