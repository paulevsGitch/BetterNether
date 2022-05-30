package org.betterx.betternether.world.structures;

import org.betterx.bclib.world.structures.StructureNBT;
import org.betterx.betternether.BetterNether;

public class NetherStructureNBT {
    public static StructureNBT create(String name) {
        return StructureNBT.create(BetterNether.makeID(name));
    }

}
