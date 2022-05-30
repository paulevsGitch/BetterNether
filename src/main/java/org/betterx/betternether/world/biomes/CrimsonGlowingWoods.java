package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.plants.*;
import org.betterx.betternether.world.surface.CrimsonWoodNoiseCondition;

public class CrimsonGlowingWoods extends NetherBiome {
    public static final SurfaceRules.RuleSource NETHER_WART_BLOCK = SurfaceRules.state(Blocks.NETHER_WART_BLOCK.defaultBlockState());
    public static final SurfaceRules.RuleSource CRIMSON_NYLIUM = SurfaceRules.state(Blocks.CRIMSON_NYLIUM.defaultBlockState());


    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(51, 3, 3)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .particles(ParticleTypes.CRIMSON_SPORE, 0.025F)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .genChance(0.3f)
            ;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case HOGLIN, FLYING_PIG -> res = type.weight;
                case NAGA -> res = 0;
            }
            return res;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return CrimsonGlowingWoods::new;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super
                    .surface()
                    .chancedFloor(
                            Blocks.NETHER_WART_BLOCK.defaultBlockState(),
                            Blocks.CRIMSON_NYLIUM.defaultBlockState(),
                            CrimsonWoodNoiseCondition.DEFAULT
                    )
//						.rule(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
//							SurfaceRules.ifTrue(new CrimsonWoodNoiseCondition(), NETHER_WART_BLOCK),
//							CRIMSON_NYLIUM
//						)))
                    ;
        }
    }

    //private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(614);

    public CrimsonGlowingWoods(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        addStructure("crimson_glowing_tree",
                new StructureCrimsonGlowingTree(),
                StructurePlacementType.FLOOR,
                0.2F,
                false);
        addStructure("wart_bush", new StructureWartBush(), StructurePlacementType.FLOOR, 0.05F, false);
        addStructure("wart_seed", new StructureWartSeed(), StructurePlacementType.FLOOR, 0.02F, true);
        addStructure("crimson_fungus", new StructureCrimsonFungus(), StructurePlacementType.FLOOR, 0.05F, true);
        addStructure("crimson_roots", new StructureCrimsonRoots(), StructurePlacementType.FLOOR, 0.2F, true);
        addStructure("golden_vine", new StructureGoldenVine(), StructurePlacementType.CEIL, 0.3F, true);
        addStructure("wall_moss", new StructureWallMoss(), StructurePlacementType.WALL, 0.8F, true);
        addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructurePlacementType.WALL, 0.4F, true);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
//		if (TERRAIN.eval(pos.getX() * 0.1, pos.getZ() * 0.1) > MHelper.randRange(0.5F, 0.7F, random))
//			BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.defaultBlockState());
//		else
//			BlocksHelper.setWithoutUpdate(world, pos, Blocks.CRIMSON_NYLIUM.defaultBlockState());
    }
}
