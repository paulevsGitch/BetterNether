package org.betterx.betternether.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import org.betterx.betternether.BetterNether;

public class SoundsRegistry {
    public static final SoundEvent AMBIENT_MUSHROOM_FOREST = register("betternether.ambient.mushroom_forest");
    public static final SoundEvent AMBIENT_GRAVEL_DESERT = register("betternether.ambient.gravel_desert");
    public static final SoundEvent AMBIENT_NETHER_JUNGLE = register("betternether.ambient.nether_jungle");
    public static final SoundEvent AMBIENT_SWAMPLAND = register("betternether.ambient.swampland");

    public static final SoundEvent MOB_FIREFLY_FLY = register("betternether.mob.firefly.fly");
    public static final SoundEvent MOB_JELLYFISH = register("betternether.mob.jellyfish");
    public static final SoundEvent MOB_NAGA_IDLE = register("betternether.mob.naga_idle");
    public static final SoundEvent MOB_NAGA_ATTACK = register("betternether.mob.naga_attack");
    public static final SoundEvent MOB_SKULL_FLIGHT = register("betternether.mob.skull_flight");

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT,
                                 id,
                                 new SoundEvent(new ResourceLocation(BetterNether.MOD_ID, id)));
    }

    public static void register() {
    }
}
