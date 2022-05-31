package org.betterx.betternether.world.structures.templates;

import net.minecraft.resources.ResourceLocation;

import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.TemplateStructure;
import org.betterx.betternether.BetterNether;

import java.util.List;

abstract class TemplateStructureHelper extends TemplateStructure {

    protected TemplateStructureHelper(StructureSettings structureSettings,
                                      ResourceLocation location,
                                      int offsetY,
                                      StructurePlacementType type,
                                      float chance) {
        super(structureSettings, location, offsetY, type, chance);
    }

    protected TemplateStructureHelper(StructureSettings structureSettings, List<Config> configs) {
        super(structureSettings, configs);
    }

    public static TemplateStructure.Config cfg(String name, int offsetY, StructurePlacementType type, float chance) {
        return new TemplateStructure.Config(BetterNether.makeID(name), offsetY, type, chance);
    }
}
