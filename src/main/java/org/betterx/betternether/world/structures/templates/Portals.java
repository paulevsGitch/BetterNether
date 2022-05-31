package org.betterx.betternether.world.structures.templates;

import net.minecraft.world.level.levelgen.structure.StructureType;

import com.mojang.serialization.Codec;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherStructures;

import java.util.List;

public class Portals extends TemplateStructureHelper {
    public static final Codec<Portals> CODEC = simpleTemplateCodec(Portals::new);

    protected Portals(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public Portals(StructureSettings structureSettings) {
        super(structureSettings, List.of(
                        cfg("portal_01", -4, StructurePlacementType.FLOOR, 1.0f),
                        cfg("portal_02", -3, StructurePlacementType.FLOOR, 1.0f)
                )
        );
    }

    @Override
    public StructureType<?> type() {
        return NetherStructures.PORTALS.structureType;
    }
}
