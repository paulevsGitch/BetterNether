package org.betterx.betternether.world.biomes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.surface.rules.SwitchRuleSource;
import org.betterx.bclib.interfaces.NumericProvider;
import org.betterx.bclib.mixin.common.SurfaceRulesContextAccessor;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import java.util.List;

class NetherGrasslandsNumericProvider implements NumericProvider {
    public static final NetherGrasslandsNumericProvider DEFAULT = new NetherGrasslandsNumericProvider();
    public static final Codec<NetherGrasslandsNumericProvider> CODEC = Codec.BYTE.fieldOf("nether_grasslands")
                                                                                 .xmap((obj) -> DEFAULT,
                                                                                         obj -> (byte) 0)
                                                                                 .codec();

    @Override
    public int getNumber(SurfaceRulesContextAccessor ctx) {
        final int depth = ctx.getStoneDepthAbove();
        if (depth <= 1) return MHelper.RANDOM.nextInt(3);
        if (depth <= MHelper.RANDOM.nextInt(3) + 1) return 0;
        return 2;
    }

    @Override
    public Codec<? extends NumericProvider> pcodec() {
        return CODEC;
    }

    static {
        Registry.register(NumericProvider.NUMERIC_PROVIDER,
                BetterNether.makeID("nether_grasslands"),
                NetherGrasslandsNumericProvider.CODEC);
    }
}

public class NetherGrasslands extends NetherBiome {
    static final SurfaceRules.RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
    static final SurfaceRules.RuleSource SOUL_SAND = SurfaceRules.state(Blocks.SOUL_SAND.defaultBlockState());
    static final SurfaceRules.RuleSource MOSS = SurfaceRules.state(NetherBlocks.NETHERRACK_MOSS.defaultBlockState());

    private static final SurfaceRules.RuleSource BLUE = SurfaceRules.state(Blocks.BLUE_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource LIGHT_BLUE = SurfaceRules.state(Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource CYAN = SurfaceRules.state(Blocks.CYAN_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource GREEN = SurfaceRules.state(Blocks.GREEN_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource LIME_GREEN = SurfaceRules.state(Blocks.LIME_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource YELLOW = SurfaceRules.state(Blocks.YELLOW_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource ORANGE = SurfaceRules.state(Blocks.ORANGE_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource RED = SurfaceRules.state(Blocks.RED_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource PINK = SurfaceRules.state(Blocks.PINK_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource PURPLE = SurfaceRules.state(Blocks.PURPLE_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource MAGENTA = SurfaceRules.state(Blocks.MAGENTA_CONCRETE.defaultBlockState());
    private static final SurfaceRules.RuleSource BLACK = SurfaceRules.state(Blocks.BLACK_CONCRETE.defaultBlockState());
    //List.of(BLUE, LIGHT_BLUE, CYAN, GREEN, LIME_GREEN, YELLOW, ORANGE, RED, PINK, MAGENTA, PURPLE, BLACK)


    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(113, 73, 133)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_NETHER_WASTES)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(BiomeFeatures.NETHER_GRASSLANDS_FLOOR)
                   .feature(BiomeFeatures.NETHER_GRASSLANDS_CEIL)
                   .feature(BiomeFeatures.NETHER_GRASSLANDS_WALL)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherGrasslands::new;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface()
                        .rule(SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_FLOOR,
                                        new SwitchRuleSource(NetherGrasslandsNumericProvider.DEFAULT,
                                                List.of(SOUL_SOIL, SOUL_SAND, NETHERRACK))
                                ),
                                new SwitchRuleSource(NetherGrasslandsNumericProvider.DEFAULT,
                                        List.of(SOUL_SOIL, SOUL_SAND, MOSS, NETHERRACK))
                        ));
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case FIREFLY -> res = type.weight * 3;
            }
            return res;
        }
    }

    public NetherGrasslands(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }
}
