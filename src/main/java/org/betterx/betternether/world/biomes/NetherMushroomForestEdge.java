package org.betterx.betternether.world.biomes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.surface.rules.SwitchRuleSource;
import org.betterx.bclib.interfaces.NumericProvider;
import org.betterx.bclib.mixin.common.SurfaceRulesContextAccessor;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.structures.plants.StructureGrayMold;
import org.betterx.betternether.world.structures.plants.StructureOrangeMushroom;
import org.betterx.betternether.world.structures.plants.StructureRedMold;
import org.betterx.betternether.world.structures.plants.StructureVanillaMushroom;

import java.util.List;

class NetherMushroomForestEdgeNumericProvider implements NumericProvider {
    public static final NetherMushroomForestEdgeNumericProvider DEFAULT = new NetherMushroomForestEdgeNumericProvider();
    public static final Codec<NetherMushroomForestEdgeNumericProvider> CODEC = Codec.BYTE.fieldOf(
            "nether_mushroom_forrest_edge").xmap((obj) -> DEFAULT, obj -> (byte) 0).codec();

    @Override
    public int getNumber(SurfaceRulesContextAccessor ctx) {
        return MHelper.RANDOM.nextInt(4) > 0 ? 0 : (MHelper.RANDOM.nextBoolean() ? 1 : 2);
    }

    @Override
    public Codec<? extends NumericProvider> pcodec() {
        return CODEC;
    }

    static {
        Registry.register(NumericProvider.NUMERIC_PROVIDER,
                BetterNether.makeID("nether_mushroom_forrest_edge"),
                NetherMushroomForestEdgeNumericProvider.CODEC);
    }
}

public class NetherMushroomForestEdge extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(200, 121, 157)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .feature(NetherFeatures.NETHER_RUBY_ORE);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherMushroomForestEdge::new;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface()
                        .rule(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_FLOOR,
                                        new SwitchRuleSource(
                                                NetherMushroomForestEdgeNumericProvider.DEFAULT,
                                                List.of(
                                                        SurfaceRules.state(NetherBlocks.NETHER_MYCELIUM.defaultBlockState()),
                                                        SurfaceRules.state(NetherBlocks.NETHERRACK_MOSS.defaultBlockState()),
                                                        NETHERRACK
                                                )
                                        )
                                )
                        );
        }
    }

    public NetherMushroomForestEdge(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        addStructure("orange_mushroom", new StructureOrangeMushroom(), StructurePlacementType.FLOOR, 0.05F, true);
        addStructure("vanilla_mushrooms", new StructureVanillaMushroom(), StructurePlacementType.FLOOR, 0.1F, false);
        addStructure("red_mold", new StructureRedMold(), StructurePlacementType.FLOOR, 0.5F, false);
        addStructure("gray_mold", new StructureGrayMold(), StructurePlacementType.FLOOR, 0.5F, false);
    }

    @Override
    public void genSurfColumn(LevelAccessor world, BlockPos pos, RandomSource random) {
//		if (random.nextInt(4) > 0)
//			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
//		else if (random.nextBoolean())
//			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
    }
}