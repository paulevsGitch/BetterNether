package paulevs.betternether.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.world.structures.city.CityFeature;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BiomeAPI;
import ru.bclib.api.tag.TagAPI;
import ru.bclib.world.structures.BCLStructure;

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
        if (Configs.GENERATOR.getBoolean("generator.world.cities", "overworld", false)) {
            BiomeAPI.registerOverworldBiomeModification((biomeID, biome) -> {
                if (!biomeID.getNamespace().equals(BetterNether.MOD_ID)) {
                    modifyNonBNBiome(biomeID, biome);
                }
            });
        }
    }

    public static void modifyNonBNBiome(ResourceLocation biomeID, Holder<Biome> biome) {
        if (biomeID != null && !biomeID.equals(BiomeAPI.BASALT_DELTAS_BIOME.getID()) && !biomeID.equals(Biomes.THE_VOID.location())) {
            TagAPI.addBiomeTag(CITY_STRUCTURE.biomeTag, biome.value());
        }
    }

    public static void addDefaultFeatures(BCLBiomeBuilder builder) {
        builder.structure(CITY_STRUCTURE);
    }
}
