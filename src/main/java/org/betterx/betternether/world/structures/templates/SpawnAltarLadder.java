package org.betterx.betternether.world.structures.templates;

import net.minecraft.world.level.levelgen.structure.StructureType;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import java.util.List;

public class SpawnAltarLadder extends TemplateStructureHelper {
    public static final Codec<SpawnAltarLadder> CODEC = simpleTemplateCodec(SpawnAltarLadder::new);

    protected SpawnAltarLadder(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public SpawnAltarLadder(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("spawn_altar_ladder", -5, StructurePlacementType.FLOOR, 1.0f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.SPAWN_ALTAR_LADDER.structureType;
    }
}
