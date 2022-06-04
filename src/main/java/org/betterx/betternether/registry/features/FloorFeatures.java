package org.betterx.betternether.registry.features;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;

import org.betterx.bclib.api.features.BCLFeature;
import org.betterx.bclib.api.features.BCLFeatureBuilder;
import org.betterx.bclib.api.features.FastFeatures;
import org.betterx.bclib.api.features.config.ScatterFeatureConfig;
import org.betterx.bclib.api.features.config.TemplateFeatureConfig;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.features.CrystalFeature;
import org.betterx.betternether.world.features.JellyfishMushroomFeature;
import org.betterx.betternether.world.features.ScatterFeatureConfigs;
import org.betterx.betternether.world.features.WartBushFeature;

import java.util.List;

public class FloorFeatures {
    public static final BCLFeature BONE_GRASS_PATCH
            = FastFeatures.patch(BetterNether.makeID("bone_grass"), NetherBlocks.BONE_GRASS);

    public static final BCLFeature SOUL_GRASS_PATCH
            = FastFeatures.patch(BetterNether.makeID("soul_grass"), NetherBlocks.SOUL_GRASS);
    public static final BCLFeature FEATHER_FERN_PATCH
            = FastFeatures.patch(BetterNether.makeID("feather_fern"), NetherBlocks.FEATHER_FERN);

    public static final BCLFeature BLACK_BUSH_PATCH
            = FastFeatures.patch(BetterNether.makeID("black_bush"), NetherBlocks.BLACK_BUSH);

    public static final BCLFeature WART_SEED_PATCH
            = FastFeatures.patch(BetterNether.makeID("wart_seed"), NetherBlocks.MAT_WART.getSeed(), 32, 2, 1);

    public static final BCLFeature WART_BUSH_PATCH
            = FastFeatures.patch(BetterNether.makeID("wart_bush"), 16, 5, 3, new WartBushFeature());

    public static final BCLFeature CRIMSON_FUNGUS
            = FastFeatures.patch(BetterNether.makeID("crimson_fungus"), Blocks.CRIMSON_FUNGUS, 32, 7, 2);

    public static final BCLFeature CRIMSON_ROOTS
            = FastFeatures.patch(BetterNether.makeID("crimson_roots"), Blocks.CRIMSON_ROOTS, 32, 7, 2);

    public static final BCLFeature BARREL_CACTUS
            = FastFeatures.patch(BetterNether.makeID("barrel_cactus"), NetherBlocks.BARREL_CACTUS, 12, 2, 2);


    public static final BCLFeature GEYSER
            = FastFeatures.patch(BetterNether.makeID("geyser"), NetherBlocks.GEYSER, 32, 3, 3);

    public static final BCLFeature JELLYFISH_MUSHROOM
            = FastFeatures.patch(BetterNether.makeID("jellyfish_mushroom"), 64, 5, 3, new JellyfishMushroomFeature());

    public static final BCLFeature CRYSTAL
            = FastFeatures.simple(BetterNether.makeID("crystal"), 3, true, new CrystalFeature());

    public static final BCLFeature BONES = BCLFeatureBuilder
            .start(BetterNether.makeID("bones"), BCLFeature.TEMPLATE)
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("bone_01"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("bone_02"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("bone_03"), 0, StructurePlacementType.FLOOR, 1.0f)
            )));


    public static final BCLFeature MAGMA_FLOWER = agedCluster(
            BetterNether.makeID("magma_flower"), true, false, ScatterFeatureConfigs.WithPlantAge
                    .startWithPlantAge()
                    .singleBlock(NetherBlocks.MAGMA_FLOWER)
                    .spread(4, 0, UniformInt.of(1, 16))
    );

//            BCLFeatureBuilder
//            .start(BetterNether.makeID("magma_flower"), NetherFeatures.SCATTER_WITH_PLANT_AGE)
//            .findSolidFloor(3)
//            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
//            //.offset(Direction.DOWN)
//            .buildAndRegister(ScatterFeatureConfigs.WithPlantAge
//                    .startWithPlantAge()
//                    .singleBlock(NetherBlocks.MAGMA_FLOWER)
//                    .spread(4, 0, UniformInt.of(1, 16))
//                    .onFloor()
//                    .build());

    public static final BCLFeature AGAVE = agedCluster(
            BetterNether.makeID("agave"), true, false, ScatterFeatureConfigs.WithPlantAge
                    .startWithPlantAge()
                    .singleBlock(NetherBlocks.AGAVE)
                    .spread(4, 0, UniformInt.of(1, 16))
    );

    public static void ensureStaticInitialization() {
    }

    static StructureWorldNBT cfg(ResourceLocation location,
                                 int offsetY,
                                 StructurePlacementType type,
                                 float chance) {
        return TemplateFeatureConfig.cfg(location, offsetY, type, chance);
    }

    static BCLFeature agedCluster(ResourceLocation location,
                                  boolean onFloor,
                                  boolean sparse,
                                  ScatterFeatureConfig.Builder builder) {
        return FastFeatures.scatter(location, onFloor, sparse, builder, NetherFeatures.SCATTER_WITH_PLANT_AGE);
    }
}
