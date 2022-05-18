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

public class NetherStructures {
    public static final int CITY_SPACING = Configs.GENERATOR.getInt("generator.world.cities", "distance", 64);
    // Nether City
    public static final BCLStructure<CityFeature> CITY_STRUCTURE = new BCLStructure<>(
            new ResourceLocation(BetterNether.MOD_ID, "nether_city"),
            CityFeature::new,
            Decoration.STRONGHOLDS,
            CITY_SPACING,
            CITY_SPACING >> 1,
            true
    );

    public static void register() {
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

    public static void addDefaultFeatures(BCLBiomeBuilder builder) {
        builder.structure(CITY_STRUCTURE);
    }
}
