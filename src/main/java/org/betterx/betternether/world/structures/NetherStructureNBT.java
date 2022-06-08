package org.betterx.betternether.world.structures;

import org.betterx.bclib.api.v2.levelgen.structures.StructureNBT;
import org.betterx.betternether.BetterNether;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

import com.google.common.collect.Maps;

import java.util.Map;

public class NetherStructureNBT extends StructureNBT {
    protected Rotation rotation = Rotation.NONE;
    protected Mirror mirror = Mirror.NONE;

    protected NetherStructureNBT(ResourceLocation location) {
        super(location);
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public NetherStructureNBT setRotation(Rotation rotation) {
        this.rotation = rotation;
        return this;
    }

    public Mirror getMirror() {
        return mirror;
    }

    public NetherStructureNBT setMirror(Mirror mirror) {
        this.mirror = mirror;
        return this;
    }


    private static final Map<String, NetherStructureNBT> STRUCTURE_CACHE = Maps.newHashMap();

    public static NetherStructureNBT create(String location) {
        return STRUCTURE_CACHE.computeIfAbsent(location, r -> new NetherStructureNBT(BetterNether.makeID(r)));
    }


}
