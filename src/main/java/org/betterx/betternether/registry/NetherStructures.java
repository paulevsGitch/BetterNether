package org.betterx.betternether.registry;

import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI;
import org.betterx.bclib.api.v2.levelgen.structures.BCLStructure;
import org.betterx.bclib.api.v2.levelgen.structures.BCLStructureBuilder;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.world.structures.city.CityStructure;
import org.betterx.betternether.world.structures.templates.*;
import org.betterx.worlds.together.tag.v3.TagManager;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class NetherStructures {
    public static final int CITY_SPACING = Configs.GENERATOR.getInt("generator.world.cities", "distance", 64);
    // Nether City
    public static final BCLStructure<CityStructure> CITY_STRUCTURE = BCLStructureBuilder
            .start(BetterNether.makeID("nether_city"), CityStructure::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(CITY_SPACING, CITY_SPACING >> 1)
            .build();

    public static final BCLStructure<Pyramids> PYRAMIDS = BCLStructureBuilder
            .start(BetterNether.makeID("pyramid"), Pyramids::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(32, 8)
            .codec(Pyramids.CODEC)
            .build();

    public static final BCLStructure<GhastHive> GHAST_HIVE = BCLStructureBuilder
            .start(BetterNether.makeID("ghast_hive"), GhastHive::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(80, 32)
            .codec(GhastHive.CODEC)
            .build();


    public static final BCLStructure<SpawnAltarLadder> SPAWN_ALTAR_LADDER = BCLStructureBuilder
            .start(BetterNether.makeID("spawn_altar_ladder"), SpawnAltarLadder::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(40, 20)
            .codec(SpawnAltarLadder.CODEC)
            .biomeTag(NetherTags.BETTER_NETHER_DECORATIONS)
            .build();

    public static final BCLStructure<RespawnPoints> RESPAWN_POINTS = BCLStructureBuilder
            .start(BetterNether.makeID("respawn_points"), RespawnPoints::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(32, 24)
            .codec(RespawnPoints.CODEC)
            .biomeTag(NetherTags.BETTER_NETHER_DECORATIONS)
            .build();


    public static final BCLStructure<Pillars> PILLARS = BCLStructureBuilder
            .start(BetterNether.makeID("pillars"), Pillars::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(20, 8)
            .codec(Pillars.CODEC)
            .biomeTag(NetherTags.BETTER_NETHER_DECORATIONS)
            .build();


    public static final BCLStructure<Gardens> GARDENS = BCLStructureBuilder
            .start(BetterNether.makeID("gardens"), Gardens::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(50, 16)
            .codec(Gardens.CODEC)
            .biomeTag(NetherTags.BETTER_NETHER_DECORATIONS)
            .build();

    public static final BCLStructure<Portals> PORTALS = BCLStructureBuilder
            .start(BetterNether.makeID("portals"), Portals::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(40, 15)
            .codec(Portals.CODEC)
            .biomeTag(NetherTags.BETTER_NETHER_DECORATIONS)
            .build();

    public static final BCLStructure<Altars> ALTARS = BCLStructureBuilder
            .start(BetterNether.makeID("altars"), Altars::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(40, 16)
            .codec(Altars.CODEC)
            .biomeTag(NetherTags.BETTER_NETHER_DECORATIONS)
            .build();

    public static final BCLStructure<JungleTemples> JUNGLE_TEMPLES = BCLStructureBuilder
            .start(BetterNether.makeID("jungle_temples"), JungleTemples::new)
            .step(Decoration.SURFACE_STRUCTURES)
            .randomPlacement(32, 8)
            .codec(JungleTemples.CODEC)
            .biomeTag(NetherTags.BETTER_NETHER_DECORATIONS)
            .build();


    public static void register() {
        NetherStructurePieces.ensureStaticLoad();

        TagManager.BIOMES.add(CITY_STRUCTURE.biomeTag, BiomeAPI.NETHER_WASTES_BIOME.getBiomeKey());
        if (Configs.GENERATOR.getBoolean("generator.world.cities", "overworld", false)) {
            BiomeAPI.registerOverworldBiomeModification((biomeID, biome) -> {
                if (!biomeID.getNamespace().equals(BetterNether.MOD_ID)) {
                    addNonBNBiomeTags(biomeID, biome);
                }
            });
        }
    }

    public static void addNonBNBiomeTags(ResourceLocation biomeID, Holder<Biome> biome) {
        if (biomeID != null && !biomeID.equals(BiomeAPI.BASALT_DELTAS_BIOME.getID()) && !biomeID.equals(Biomes.THE_VOID.location())) {
            TagManager.BIOMES.add(CITY_STRUCTURE.biomeTag, biome.value());
        }
    }


}
