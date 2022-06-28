package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.betternether.registry.features.placed.NetherTerrainPlaced;
import org.betterx.betternether.world.NetherBiome;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class NetherSwamplandTerraces extends NetherSwampland {
    public static class Config extends NetherSwampland.Config {
        public Config(String name) {
            super(name);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherSwamplandTerraces::new;
        }

        @Override
        protected void addCustomSwamplandBuildData(BCLBiomeBuilder builder) {
            builder.feature(NetherTerrainPlaced.LAVA_TERRACE);
        }
    }

    public NetherSwamplandTerraces(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }
}
