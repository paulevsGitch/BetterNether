package org.betterx.betternether.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BiomeAPI;
import org.betterx.bclib.api.tag.TagAPI;
import org.betterx.bclib.world.structures.BCLStructure;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.world.structures.city.CityFeature;
import org.betterx.betternether.world.structures.templates.*;

public class NetherStructures {
    public static final int CITY_SPACING = Configs.GENERATOR.getInt("generator.world.cities", "distance", 64);
    // Nether City
    public static final BCLStructure<CityFeature> CITY_STRUCTURE = new BCLStructure<>(
            BetterNether.makeID("nether_city"),
            CityFeature::new,
            Decoration.STRONGHOLDS,
            CITY_SPACING,
            CITY_SPACING >> 1,
            true
    );

    public static final BCLStructure<Pyramids> PYRAMIDS = new BCLStructure<>(
            BetterNether.makeID("pyramid"),
            Pyramids::new,
            Decoration.SURFACE_STRUCTURES,
            32,
            8,
            true,
            Pyramids.CODEC,
            null
    );

    public static final BCLStructure<GhastHive> GHAST_HIVE = new BCLStructure<>(
            BetterNether.makeID("ghast_hive"),
            GhastHive::new,
            Decoration.SURFACE_STRUCTURES,
            80,
            32,
            true,
            GhastHive.CODEC,
            null
    );


    public static final BCLStructure<SpawnAltarLadder> SPAWN_ALTAR_LADDER = new BCLStructure<>(
            BetterNether.makeID("spawn_altar_ladder"),
            SpawnAltarLadder::new,
            Decoration.SURFACE_STRUCTURES,
            40,
            20,
            true,
            SpawnAltarLadder.CODEC,
            NetherTags.BETTER_NETHER_DECORATIONS
    );


    public static final BCLStructure<RespawnPoints> RESPAWN_POINTS = new BCLStructure<>(
            BetterNether.makeID("respawn_points"),
            RespawnPoints::new,
            Decoration.SURFACE_STRUCTURES,
            32,
            24,
            true,
            RespawnPoints.CODEC,
            NetherTags.BETTER_NETHER_DECORATIONS
    );

    public static final BCLStructure<Pillars> PILLARS = new BCLStructure<>(
            BetterNether.makeID("pillars"),
            Pillars::new,
            Decoration.SURFACE_STRUCTURES,
            20,
            8,
            true,
            Pillars.CODEC,
            NetherTags.BETTER_NETHER_DECORATIONS
    );

    public static final BCLStructure<Gardens> GARDENS = new BCLStructure<>(
            BetterNether.makeID("gardens"),
            Gardens::new,
            Decoration.SURFACE_STRUCTURES,
            50,
            16,
            true,
            Gardens.CODEC,
            NetherTags.BETTER_NETHER_DECORATIONS
    );

    public static final BCLStructure<Portals> PORTALS = new BCLStructure<>(
            BetterNether.makeID("portals"),
            Portals::new,
            Decoration.SURFACE_STRUCTURES,
            40,
            15,
            true,
            Portals.CODEC,
            NetherTags.BETTER_NETHER_DECORATIONS
    );

    public static final BCLStructure<Altars> ALTARS = new BCLStructure<>(
            BetterNether.makeID("altars"),
            Altars::new,
            Decoration.SURFACE_STRUCTURES,
            40,
            16,
            true,
            Altars.CODEC,
            NetherTags.BETTER_NETHER_DECORATIONS
    );

    public static final BCLStructure<JungleTemples> JUNGLE_TEMPLES = new BCLStructure<>(
            BetterNether.makeID("jungle_temples"),
            JungleTemples::new,
            Decoration.SURFACE_STRUCTURES,
            32,
            8,
            true,
            JungleTemples.CODEC,
            null
    );


    public static void register() {
        NetherStructurePieces.ensureStaticLoad();

        TagAPI.addBiomeTag(CITY_STRUCTURE.biomeTag, BiomeAPI.NETHER_WASTES_BIOME.getBiome());
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
            TagAPI.addBiomeTag(CITY_STRUCTURE.biomeTag, biome.value());
        }
    }


    public static void addDefaultStructures(BCLBiomeBuilder builder) {
        builder.structure(CITY_STRUCTURE);
    }

}
