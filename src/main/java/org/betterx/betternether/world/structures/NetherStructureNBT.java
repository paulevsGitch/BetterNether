package org.betterx.betternether.world.structures;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import org.betterx.bclib.world.structures.StructureNBT;
import org.betterx.betternether.BetterNether;

public class NetherStructureNBT extends StructureNBT {

    public NetherStructureNBT(String name) {
        super(BetterNether.makeID(name));
    }

    protected NetherStructureNBT(ResourceLocation location,
                                 StructureTemplate structure) {
        super(location, structure);
    }
}
