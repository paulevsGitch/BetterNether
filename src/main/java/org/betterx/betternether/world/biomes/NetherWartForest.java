package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.blocks.BlockSoulSandstone;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.StructureType;
import org.betterx.betternether.world.structures.decorations.StructureWartDeadwood;
import org.betterx.betternether.world.structures.plants.*;

public class NetherWartForest extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(151, 6, 6)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .particles(ParticleTypes.CRIMSON_SPORE, 0.05F)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .edgeSize(9)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherWartForest::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case FLYING_PIG -> res = type.weight;
                case NAGA -> res = 0;
            }
            return res;
        }
    }

    public NetherWartForest(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        this.setNoiseDensity(0.45F);

        addStructure("wart_deadwood", new StructureWartDeadwood(), StructureType.FLOOR, 0.02F, false);
        addStructure("wart_tree", new StructureWartTree(), StructureType.FLOOR, 0.1F, false);
        addStructure("nether_wart", new StructureNetherWart(), StructureType.FLOOR, 0.2F, false);
        addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.05F, false);
        addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.05F, false);
        addStructure("soul_lily", new StructureSoulLily(), StructureType.FLOOR, 0.2F, true);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
        final MutableBlockPos POS = new MutableBlockPos();
        switch (random.nextInt(4)) {
            case 0:
                super.genSurfColumn(world, pos, random);
                break;
            case 1:
                BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.defaultBlockState());
                break;
            case 2:
                BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
                break;
            case 3:
                BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
                break;
        }

        int d1 = MHelper.randRange(2, 4, random);
        POS.setX(pos.getX());
        POS.setZ(pos.getZ());

        for (int i = 1; i < d1; i++) {
            POS.setY(pos.getY() - i);
            if (BlocksHelper.isNetherGround(world.getBlockState(POS))) {
                switch (random.nextInt(3)) {
                    case 0:
                        BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.defaultBlockState());
                        break;
                    case 1:
                        BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
                        break;
                    case 2:
                        BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHERRACK.defaultBlockState());
                        break;
                }
            } else
                return;
        }

        int d2 = MHelper.randRange(5, 7, random);
        for (int i = d1; i < d2; i++) {
            POS.setY(pos.getY() - i);
            if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
                BlocksHelper.setWithoutUpdate(world, POS, NetherBlocks.SOUL_SANDSTONE.defaultBlockState().setValue(
                        BlockSoulSandstone.UP, i == d1));
            else
                return;
        }
    }
}
