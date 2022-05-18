package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.world.biomes.BCLBiomeSettings;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.StructureType;
import org.betterx.betternether.world.structures.plants.*;

public class OldWarpedWoods extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(26, 5, 26)
                   .loop(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_WARPED_FOREST_MOOD)
                   .particles(ParticleTypes.WARPED_SPORE, 0.025F)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(NetherFeatures.NETHER_RUBY_ORE);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return OldWarpedWoods::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case GHAST, ZOMBIFIED_PIGLIN, MAGMA_CUBE, PIGLIN, HOGLIN, PIGLIN_BRUTE -> res = 0;
                case ENDERMAN, STRIDER -> res = type.weight;
            }
            return res;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().floor(Blocks.WARPED_NYLIUM.defaultBlockState());
        }
    }

    public OldWarpedWoods(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        addStructure("big_warped_tree", new StructureBigWarpedTree(), StructureType.FLOOR, 0.1F, false);
        addStructure("warped_fungus", new StructureWarpedFungus(), StructureType.FLOOR, 0.05F, true);
        addStructure("warped_roots", new StructureWarpedRoots(), StructureType.FLOOR, 0.2F, true);
        addStructure("twisted_vine", new StructureTwistedVines(), StructureType.FLOOR, 0.1F, true);
        addStructure("black_vine", new StructureBlackVine(), StructureType.CEIL, 0.3F, true);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        //BlocksHelper.setWithoutUpdate(world, pos, Blocks.WARPED_NYLIUM.defaultBlockState());
    }
}
