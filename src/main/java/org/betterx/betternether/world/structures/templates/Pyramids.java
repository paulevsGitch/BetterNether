package org.betterx.betternether.world.structures.templates;

import net.minecraft.world.level.levelgen.structure.StructureType;

import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.TemplateStructure;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherStructures;

import java.util.List;

public class Pyramids {
    private static TemplateStructure.Config cfg(String name, int offsetY, StructurePlacementType type, float chance) {
        return new TemplateStructure.Config(BetterNether.makeID(name), offsetY, type, chance);
    }

    public static class Variant1 extends TemplateStructure {
        public Variant1(StructureSettings structureSettings) {
            super(structureSettings, List.of(
                    cfg("lava/pyramid_1", -1, StructurePlacementType.LAVA, 1.0f))
                 );
        }

        @Override
        public StructureType<?> type() {
            return NetherStructures.PYRAMID_1.structureType;
        }
    }
}
