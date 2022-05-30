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
import org.betterx.betternether.registry.SoundsRegistry;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.plants.*;

public class OldFungiwoods extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(166, 38, 95)
                   .loop(SoundsRegistry.AMBIENT_MUSHROOM_FOREST)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .particles(ParticleTypes.MYCELIUM, 0.1F)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .genChance(0.3f)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return OldFungiwoods::new;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().floor(NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
        }
    }

    public OldFungiwoods(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        this.setNoiseDensity(0.5F);

        addStructure("old_red_mushrooms", new StructureOldRedMushrooms(), StructurePlacementType.FLOOR, 0.1F, false);
        addStructure("old_brown_mushrooms",
                new StructureOldBrownMushrooms(),
                StructurePlacementType.FLOOR,
                0.1F,
                false);
        addStructure("large_red_mushroom", new StructureMedRedMushroom(), StructurePlacementType.FLOOR, 0.12F, true);
        addStructure("large_brown_mushroom",
                new StructureMedBrownMushroom(),
                StructurePlacementType.FLOOR,
                0.12F,
                true);
        addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructurePlacementType.FLOOR, 0.5F, false);
        addStructure("red_mold", new StructureRedMold(), StructurePlacementType.FLOOR, 0.9F, true);
        addStructure("gray_mold", new StructureGrayMold(), StructurePlacementType.FLOOR, 0.9F, true);
        addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructurePlacementType.WALL, 0.9F, true);
        addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructurePlacementType.WALL, 0.9F, true);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        //BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
    }
}
