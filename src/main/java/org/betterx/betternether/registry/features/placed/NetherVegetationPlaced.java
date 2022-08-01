package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v3.levelgen.features.blockpredicates.BlockPredicates;
import org.betterx.bclib.api.v3.levelgen.features.config.PlaceFacingBlockConfig;
import org.betterx.betternether.BN;
import org.betterx.betternether.registry.features.configured.NetherVegetation;
import org.betterx.betternether.world.features.WartCapFeature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.RandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class NetherVegetationPlaced {
    public static final BCLFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> VEGETATION_BONE_REEF = NetherVegetation
            .VEGETATION_BONE_REEF
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(24)
            .buildAndRegister();

    public static final BCLFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> VEGETATION_SULFURIC_BONE_REEF = NetherVegetation
            .VEGETATION_SULFURIC_BONE_REEF
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_MAGMA_LAND = NetherVegetation
            .VEGETATION_MAGMA_LAND
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherGround(8)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> VEGETATION_CRIMSON_GLOWING_WOODS = NetherVegetation
            .VEGETATION_CRIMSON_GLOWING_WOODS
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_GRASSLANDS = NetherVegetation
            .VEGETATION_GRASSLANDS
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_GRAVEL_DESERT = NetherVegetation
            .VEGETATION_GRAVEL_DESERT
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(9)
            .onceEvery(5)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_JUNGLE = NetherVegetation
            .VEGETATION_JUNGLE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(18)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomSelectorFeature, RandomFeatureConfiguration> VEGETATION_MUSHROOM_FORREST = NetherVegetation
            .VEGETATION_MUSHROOM_FORREST
            .place(BN.id("vegetation_mushroom_forrest_edge"))
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomSelectorFeature, RandomFeatureConfiguration> VEGETATION_MUSHROOM_FORREST_EDGE = NetherVegetation
            .VEGETATION_MUSHROOM_FORREST
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .onceEvery(2)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomSelectorFeature, RandomFeatureConfiguration> VEGETATION_POOR_GRASSLANDS = NetherVegetation
            .VEGETATION_POOR_GRASSLAND
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .onceEvery(3)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_SOUL_PLAIN = NetherVegetation
            .VEGETATION_SOUL_PLAIN
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_WART_FOREST = NetherVegetation
            .VEGETATION_WART_FOREST
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(10)
            .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_WART_FOREST_EDGE = NetherVegetation
            .VEGETATION_WART_FOREST_EDGE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(5)
            .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_SWAMPLAND = NetherVegetation
            .VEGETATION_SWAMPLAND
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(20)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_OLD_SWAMPLAND = NetherVegetation
            .VEGETATION_OLD_SWAMPLAND
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(22)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_OLD_WARPED_WOODS = NetherVegetation
            .VEGETATION_OLD_WARPED_WOODS
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .isEmptyAndOn(BlockPredicates.ONLY_NYLIUM)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> JELLYFISH_MUSHROOM = NetherVegetation
            .PATCH_JELLYFISH_MUSHROOM
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(6)
            .onceEvery(4)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> JELLYFISH_MUSHROOM_DENSE = NetherVegetation
            .PATCH_JELLYFISH_MUSHROOM
            .place(BN.id("patch_jellyfish_mushroom_dense"))
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(4)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> NETHER_CACTUS = NetherVegetation
            .PATCH_NETHER_CACTUS
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherGround(4)
            .onceEvery(5)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WALL_MUSHROOM_RED_WITH_MOSS = NetherVegetation
            .PATCH_WALL_MUSHROOM_RED_WITH_MOSS
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherInWall(25)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WALL_MUSHROOMS_WITH_MOSS = NetherVegetation
            .PATCH_WALL_MUSHROOMS_WITH_MOSS
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherInWall(25)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WALL_MUSHROOMS = NetherVegetation
            .PATCH_WALL_MUSHROOMS
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherInWall(25)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WALL_JUNGLE = NetherVegetation
            .PATCH_WALL_JUNGLE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherInWall(50)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WALL_UPSIDE_DOWN = NetherVegetation
            .PATCH_WALL_UPSIDE_DOWN
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherInWall(50)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WALL_LUCIS = NetherVegetation
            .PATCH_WALL_LUCIS
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherOnWall(5)
            .onceEvery(2)
            .isEmpty()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> NETHER_REED = NetherVegetation
            .PATCH_NETHER_REED
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(6)
            .buildAndRegister();


    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BLACK_BUSH = NetherVegetation
            .PATCH_BLACK_BUSH
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(6)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BLACK_BUSH_SPARSE = NetherVegetation
            .PATCH_BLACK_BUSH
            .place(BN.id("patch_black_bush_sparse"))
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(3)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WART_BUSH = NetherVegetation
            .PATCH_WART_BUSH
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(4)
            .onceEvery(3)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WILLOW_BUSH = NetherVegetation
            .PATCH_WILLOW_BUSH
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(4)
            .onceEvery(3)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> RUBEUS_BUSH = NetherVegetation
            .PATCH_RUBEUS_BUSH
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(4)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> SAKURA_BUSH = NetherVegetation
            .PATCH_SAKURA_BUSH
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(4)
            .onceEvery(6)
            .buildAndRegister();

    public static final BCLFeature<WartCapFeature, NoneFeatureConfiguration> WART_CAP = NetherVegetation
            .WART_CAP
            .place()
            .count(32)
            .squarePlacement()
            .randomHeight10FromFloorCeil()
            .findSolidSurface(PlaceFacingBlockConfig.HORIZONTAL, 12, false)
            .buildAndRegister();
    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> SCULK_VEGETATION = NetherVegetation
            .SCULK_VEGETATION
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(12)
            .onceEvery(2)
            .isEmptyAndOn(BlockPredicate.matchesBlocks(Blocks.SCULK))
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> HOOK_MUSHROOM = NetherVegetation
            .PATCH_HOOK_MUSHROOM
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(8)
            .onceEvery(2)
            .isEmptyAndUnderNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> MOSS_COVER = NetherVegetation
            .PATCH_MOSS_COVER
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
