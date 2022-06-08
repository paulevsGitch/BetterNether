package org.betterx.betternether.world.structures.templates;

import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.List;

public class GhastHive extends TemplateStructureHelper {
    public static final Codec<GhastHive> CODEC = simpleTemplateCodec(GhastHive::new);

    protected GhastHive(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public GhastHive(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("ghast_hive", 0, StructurePlacementType.CEIL, 1.0f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.GHAST_HIVE.structureType;
    }
}