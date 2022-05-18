package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.surface.rules.SurfaceNoiseCondition;
import org.betterx.bclib.world.biomes.BCLBiomeSettings;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.StructureType;
import org.betterx.betternether.world.structures.decorations.StructureForestLitter;
import org.betterx.betternether.world.structures.plants.*;

public class UpsideDownForestCleared extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(111, 188, 121)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .genChance(0.5f)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return UpsideDownForestCleared::new;
        }


        @Override
        public SurfaceRuleBuilder surface() {
            final SurfaceNoiseCondition noise = UpsideDownFloorCondition.DEFAULT;
            return super.surface().rule(3,
                                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING,
                                                            SurfaceRules.sequence(SurfaceRules.ifTrue(UpsideDownForest.NOISE_CEIL_LAYER,
                                                                                                      UpsideDownForest.CEILEING_MOSS),
                                                                                  NETHERRACK)
                                                           )
                                       ).rule(2,
                                              SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                                                  SurfaceRules.sequence(SurfaceRules.ifTrue(noise,
                                                                                                            UpsideDownForest.NETHERRACK_MOSS),
                                                                                        SurfaceRules.state(
                                                                                                NetherBlocks.MUSHROOM_GRASS.defaultBlockState()))
                                                                 )
                                             );
        }
    }

    @Override
    public boolean hasStalactites() {
        return false;
    }

    @Override
    public boolean hasBNStructures() {
        return false;
    }

    public UpsideDownForestCleared(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        this.setNoiseDensity(0.3F);

        addStructure("moss_cover", new StructureMossCover(), StructureType.FLOOR, 0.6F, false);
        addStructure("jungle_moss", new StructureJungleMoss(), StructureType.WALL, 0.4F, true);
        addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.4F, true);
        addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.4F, true);
        addStructure("forest_litter", new StructureForestLitter(), StructureType.FLOOR, 0.1F, false);
        //addStructure("ceiling_mushrooms", new StructureCeilingMushrooms(), StructureType.CEIL, 1F, false);
        addStructure("neon_equisetum", new StructureNeonEquisetum(), StructureType.CEIL, 0.1F, true);
        addStructure("hook_mushroom", new StructureHookMushroom(), StructureType.CEIL, 0.03F, true);
        addStructure("whispering_gourd", new StructureWhisperingGourd(), StructureType.CEIL, 0.02F, true);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        //BlocksHelper.setWithoutUpdate(world, pos, random.nextInt(3) == 0 ? NetherBlocks.NETHERRACK_MOSS.defaultBlockState() : Blocks.NETHERRACK.defaultBlockState());
    }
}
