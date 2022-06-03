package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.plants.StructureGoldenLumabusVine;
import org.betterx.betternether.world.structures.plants.StructureJellyfishMushroom;
import org.betterx.betternether.world.structures.plants.StructureSepiaBoneGrass;

public class NetherSulfuricBoneReef extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(154, 144, 49)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .particles(ParticleTypes.ASH, 0.01F)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(NetherFeatures.STALACTITE_BONE_CLUSTER)
                   .feature(NetherFeatures.STALAGMITE_BONE_CLUSTER)
                   .feature(NetherFeatures.BONES)
                   .feature(NetherFeatures.NETHER_REEED)
                   .genChance(0.3f);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherSulfuricBoneReef::new;
        }

        @Override
        public boolean hasStalactites() {
            return false;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().floor(NetherBlocks.SEPIA_MUSHROOM_GRASS.defaultBlockState());
        }
    }

    public NetherSulfuricBoneReef(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {

        addStructure("jellyfish_mushroom", new StructureJellyfishMushroom(), StructurePlacementType.FLOOR, 0.02F, true);
        addStructure("sulfuric_bone_grass", new StructureSepiaBoneGrass(), StructurePlacementType.FLOOR, 0.1F, false);

        addStructure("golden_lumabus_vine", new StructureGoldenLumabusVine(), StructurePlacementType.CEIL, 0.3F, true);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        //BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.SEPIA_MUSHROOM_GRASS.defaultBlockState());
    }
}
