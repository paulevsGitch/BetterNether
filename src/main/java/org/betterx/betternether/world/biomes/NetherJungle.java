package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.SoundsRegistry;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.plants.*;

public class NetherJungle extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(62, 169, 61)
                   .loop(SoundsRegistry.AMBIENT_NETHER_JUNGLE)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_WARPED_FOREST)
                   .structure(BiomeTags.HAS_BASTION_REMNANT);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherJungle::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case GHAST, ZOMBIFIED_PIGLIN, MAGMA_CUBE, ENDERMAN, PIGLIN, STRIDER, HOGLIN, PIGLIN_BRUTE -> res = 0;
                case JUNGLE_SKELETON -> res = 40;
            }
            return res;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().floor(NetherBlocks.JUNGLE_GRASS.defaultBlockState());
        }
    }

    public NetherJungle(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        addStructure("nether_reed", new StructureReeds(), StructurePlacementType.FLOOR, 0.5F, false);
        addStructure("stalagnate", new StructureStalagnate(), StructurePlacementType.FLOOR, 0.2F, false);
        addStructure("rubeus_tree", new StructureRubeus(), StructurePlacementType.FLOOR, 0.1F, false);
        addStructure("bush_rubeus", new StructureRubeusBush(), StructurePlacementType.FLOOR, 0.1F, false);
        addStructure("magma_flower", new StructureMagmaFlower(), StructurePlacementType.FLOOR, 0.5F, false);
        addStructure("egg_plant", new StructureEggPlant(), StructurePlacementType.FLOOR, 0.05F, true);
        addStructure("jellyfish_mushroom", new StructureJellyfishMushroom(), StructurePlacementType.FLOOR, 0.03F, true);
        addStructure("feather_fern", new StructureFeatherFern(), StructurePlacementType.FLOOR, 0.05F, true);
        addStructure("jungle_plant", new StructureJunglePlant(), StructurePlacementType.FLOOR, 0.1F, false);
        addStructure("lucis", new StructureLucis(), StructurePlacementType.WALL, 0.1F, false);
        addStructure("eye", new StructureEye(), StructurePlacementType.CEIL, 0.1F, true);
        addStructure("black_vine", new StructureBlackVine(), StructurePlacementType.CEIL, 0.1F, true);
        addStructure("golden_vine", new StructureGoldenVine(), StructurePlacementType.CEIL, 0.1F, true);
        addStructure("flowered_vine", new StructureBloomingVine(), StructurePlacementType.CEIL, 0.1F, true);
        addStructure("jungle_moss", new StructureJungleMoss(), StructurePlacementType.WALL, 0.8F, true);
        addStructure("wall_moss", new StructureWallMoss(), StructurePlacementType.WALL, 0.2F, true);
        addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructurePlacementType.WALL, 0.8F, true);
        addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructurePlacementType.WALL, 0.8F, true);

        addStructures(structureFormat("ruined_temple", -4, StructurePlacementType.FLOOR, 10F));
        addStructures(structureFormat("jungle_temple_altar", -2, StructurePlacementType.FLOOR, 10F));
        addStructures(structureFormat("jungle_temple_2", -2, StructurePlacementType.FLOOR, 10F));

        addStructures(structureFormat("jungle_bones_1", 0, StructurePlacementType.FLOOR, 20F));
        addStructures(structureFormat("jungle_bones_2", 0, StructurePlacementType.FLOOR, 20F));
        addStructures(structureFormat("jungle_bones_3", 0, StructurePlacementType.FLOOR, 20F));

        this.setNoiseDensity(0.5F);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        //BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.JUNGLE_GRASS.defaultBlockState());
    }
}