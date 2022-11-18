package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v3.bonemeal.BonemealAPI;
import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v3.levelgen.features.blockpredicates.BlockPredicates;
import org.betterx.betternether.BN;
import org.betterx.betternether.blocks.*;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.features.WartCapFeature;

import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class NetherVegetation {
    public static final BCLConfigureFeature<SimpleBlockFeature, SimpleBlockConfiguration> DEBUG = BCLFeatureBuilder
            .start(BN.id("debug"), Blocks.YELLOW_CONCRETE)
            .buildAndRegister();

    public static final BCLConfigureFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> VEGETATION_BONE_REEF = BCLFeatureBuilder
            .startNetherVegetation(BN.id("vegetation_bone_reef"))
            .add(NetherBlocks.BONE_GRASS, 180)
            .addAllStatesFor(BlockFeatherFern.AGE, NetherBlocks.FEATHER_FERN, 20)
            .buildAndRegister();

    public static final BCLConfigureFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> VEGETATION_SULFURIC_BONE_REEF = BCLFeatureBuilder
            .startNetherVegetation(BN.id("vegetation_sulfuric_bone_reef"))
            .add(NetherBlocks.SEPIA_BONE_GRASS, 180)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_MAGMA_LAND = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_magma_land"))
            .add(NetherBlocks.GEYSER, 40)
            .addAllStatesFor(BlockMagmaFlower.AGE, NetherBlocks.MAGMA_FLOWER, 120)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("vegetation_magma_land"))
            .buildAndRegister();

    public static final BCLConfigureFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> VEGETATION_CRIMSON_GLOWING_WOODS = BCLFeatureBuilder
            .startNetherVegetation(BN.id("vegetation_crimson_glowing_woods"))
            .add(Blocks.CRIMSON_ROOTS, 120)
            .add(Blocks.CRIMSON_FUNGUS, 80)
            .add(NetherBlocks.MAT_WART.getSeed(), 80)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_GRASSLANDS = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_nether_grasslands"))
            .addAllStatesFor(BlockMagmaFlower.AGE, NetherBlocks.MAGMA_FLOWER, 30)
            .addAllStatesFor(BlockInkBush.AGE, NetherBlocks.INK_BUSH, 80)
            .addAllStatesFor(NetherWartBlock.AGE, Blocks.NETHER_WART, 40)
            .add(NetherBlocks.NETHER_GRASS, 200)
            .addAllStatesFor(BlockBlackApple.AGE, NetherBlocks.BLACK_APPLE, 50)
            .add(NetherBlocks.MAT_WART.getSeed(), 60)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("vegetation_nether_grasslands"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_GRAVEL_DESERT = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_nether_gravel_desert"))
            .addAllStatesFor(BlockAgave.AGE, NetherBlocks.AGAVE, 80)
            .addAllStatesFor(BlockBarrelCactus.AGE, NetherBlocks.BARREL_CACTUS, 20)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("vegetation_nether_gravel_desert"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_JUNGLE = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_nether_jungle"))
            .addAllStatesFor(BlockEggPlant.AGE, NetherBlocks.EGG_PLANT, 80)
            .add(NetherBlocks.JUNGLE_PLANT, 80)
            .addAllStatesFor(BlockMagmaFlower.AGE, NetherBlocks.MAGMA_FLOWER, 30)
            .addAllStatesFor(BlockFeatherFern.AGE, NetherBlocks.FEATHER_FERN, 20)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("vegetation_nether_jungle"))
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> SCULK_VEGETATION = BCLFeatureBuilder
            .startWeighted(BN.id("temp_sculk_vegetation"))
            .add(NetherBlocks.SWAMP_GRASS, 200)
            .addAllStatesFor(BlockMagmaFlower.AGE, NetherBlocks.MAGMA_FLOWER, 80)
            .inlinePlace()
            .isEmptyAndOn(BlockPredicate.matchesBlocks(Blocks.SCULK))
            .inRandomPatch(BN.id("sculk_vegetation"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_OLD_WARPED_WOODS = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_old_warped_woods"))
            .add(Blocks.WARPED_FUNGUS, 50)
            .add(Blocks.WARPED_ROOTS, 200)
            .inlinePlace()
            .isEmptyAndOn(BlockPredicates.ONLY_NYLIUM)
            .inRandomPatch(BN.id("vegetation_old_warped_woods"))
            .likeDefaultNetherVegetation()
            .buildAndRegister();

    private static final int GRAY_MOLD_ID = 42;
    private static final int MUSHROOM_ID = 23;
    public static final BCLConfigureFeature<RandomSelectorFeature, RandomFeatureConfiguration> VEGETATION_MUSHROOM_FORREST = BCLFeatureBuilder
            .startRandomSelect(
                    BN.id("vegetation_mushroom_forrest"),
                    (placer, id) -> placer
                            .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
                            .inRandomPatch(BN.id("temp"))
                            .tries(id == GRAY_MOLD_ID ? 140 : 96)
                            .spreadXZ(id == GRAY_MOLD_ID ? 8 : 7)
                            .spreadY(id == MUSHROOM_ID ? 6 : 3)
                            .inlinePlace()
                            .build()
            )
            .add(NetherBlocks.GRAY_MOLD, 200, GRAY_MOLD_ID)
            .add(NetherBlocks.RED_MOLD, 180)
            .addAllStatesFor(BlockCommonPlant.AGE, NetherBlocks.ORANGE_MUSHROOM, 100)
            .addAll(60, MUSHROOM_ID, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM)
            .add(Blocks.CRIMSON_FUNGUS, 80, MUSHROOM_ID)
            .add(Blocks.WARPED_FUNGUS, 80, MUSHROOM_ID)
            .addAll(30, MUSHROOM_ID, NetherBlocks.SEPIA_BONE_GRASS, NetherBlocks.BONE_GRASS, NetherBlocks.JUNGLE_PLANT)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomSelectorFeature, RandomFeatureConfiguration> VEGETATION_POOR_GRASSLAND = BCLFeatureBuilder
            .startRandomSelect(
                    BN.id("vegetation_nether_poor_grassland"),
                    (placer, id) -> placer
                            .isEmptyAndOnNetherGround()
                            .inRandomPatch(BN.id("temp"))
                            .inlinePlace()
                            .build()
            )
            .addAllStatesFor(NetherWartBlock.AGE, Blocks.NETHER_WART, 40)
            .addAllStatesFor(BlockMagmaFlower.AGE, NetherBlocks.MAGMA_FLOWER, 120)
            .add(NetherBlocks.NETHER_GRASS, 200)
            .addAllStatesFor(BlockInkBush.AGE, NetherBlocks.INK_BUSH, 80)
            .addAllStatesFor(BlockBlackApple.AGE, NetherBlocks.BLACK_APPLE, 50)
            .add(NetherBlocks.MAT_WART.getSeed(), 80)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_SOUL_PLAIN = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_soul_plain"))
            .add(NetherBlocks.SOUL_VEIN, 80)
            .add(NetherBlocks.SOUL_GRASS, 200)
            .inlinePlace()
            .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
            .inRandomPatch(BN.id("vegetation_soul_plain"))
            .buildAndRegister();


    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_WART_FOREST = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_wart_forest"))
            .addAllStatesFor(NetherWartBlock.AGE, Blocks.NETHER_WART, 120)
            .add(NetherBlocks.MAT_WART.getSeed(), 60)
            .inlinePlace()
            .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
            .inRandomPatch(BN.id("vegetation_wart_forest"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_WART_FOREST_EDGE = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_wart_forest_edge"))
            .addAllStatesFor(NetherWartBlock.AGE, Blocks.NETHER_WART, 120)
            .add(NetherBlocks.MAT_WART.getSeed(), 60)
            .add(NetherBlocks.SOUL_GRASS, 200)
            .inlinePlace()
            .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
            .inRandomPatch(BN.id("vegetation_wart_forest_edge"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_SWAMPLAND = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_nether_swampland"))
            .add(NetherBlocks.SOUL_VEIN, 80)
            .add(NetherBlocks.SWAMP_GRASS, 200)
            .add(NetherBlocks.FEATHER_FERN, 80)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("vegetation_nether_swampland"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> VEGETATION_OLD_SWAMPLAND = BCLFeatureBuilder
            .startWeighted(BN.id("temp_vegetation_old-swampland"))
            .add(NetherBlocks.SOUL_VEIN, 80)
            .add(NetherBlocks.SWAMP_GRASS, 100)
            .add(Blocks.SCULK_VEIN, 40)
            .add(NetherBlocks.FEATHER_FERN, 80)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("vegetation_old_swampland"))
            .buildAndRegister();

    public static final BCLConfigureFeature<Feature<NoneFeatureConfiguration>, NoneFeatureConfiguration> JELLYFISH_MUSHROOM = BCLFeatureBuilder
            .start(BN.id("jellyfish_mushroom"), org.betterx.betternether.registry.NetherFeatures.JELLYFISH_MUSHROOM)
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_JELLYFISH_MUSHROOM = JELLYFISH_MUSHROOM
            .place()
            .findSolidFloor(4)
            .isEmptyAndOnNylium()
            .inRandomPatch(BN.id("patch_jellyfish_mushroom"))
            .tries(6)
            .spreadXZ(6)
            .spreadY(4)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_BLACK_BUSH = BCLFeatureBuilder
            .start(BN.id("temp_black_bush"), NetherBlocks.BLACK_BUSH)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_back_bush"))
            .likeDefaultNetherVegetation()
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WART_BUSH = BCLFeatureBuilder
            .start(BN.id("temp_wart_bush"), org.betterx.betternether.registry.NetherFeatures.WART_BUSH)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_wart_bush"))
            .likeDefaultNetherVegetation()
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WILLOW_BUSH = BCLFeatureBuilder
            .start(BN.id("temp_willow_bush"), org.betterx.betternether.registry.NetherFeatures.WILLOW_BUSH)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_willow_bush"))
            .likeDefaultNetherVegetation()
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_RUBEUS_BUSH = BCLFeatureBuilder
            .start(BN.id("temp_rubeus_bush"), org.betterx.betternether.registry.NetherFeatures.RUBEUS_BUSH)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_rubeus_bush"))
            .likeDefaultNetherVegetation()
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_SAKURA_BUSH = BCLFeatureBuilder
            .start(BN.id("temp_sakura_bush"), org.betterx.betternether.registry.NetherFeatures.SAKURA_BUSH)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_sakura_bush"))
            .likeDefaultNetherVegetation()
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WALL_MUSHROOM_RED_WITH_MOSS = BCLFeatureBuilder
            .startFacing(BN.id("temp_wall_mushroom_red_with_moss"))
            .add(NetherBlocks.WALL_MUSHROOM_RED, 40)
            .add(NetherBlocks.WALL_MOSS, 20)
            .allHorizontal()
            .inlinePlace()
            .is(BlockPredicate.solid())
            .inRandomPatch(BN.id("patch_wall_mushroom_red_with_moss"))
            .tries(120)
            .spreadXZ(4)
            .spreadY(7)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WALL_MUSHROOMS_WITH_MOSS = BCLFeatureBuilder
            .startFacing(BN.id("temp_wall_mushrooms_with_moss"))
            .add(NetherBlocks.WALL_MUSHROOM_RED, 40)
            .add(NetherBlocks.WALL_MUSHROOM_BROWN, 35)
            .add(NetherBlocks.WALL_MOSS, 20)
            .allHorizontal()
            .inlinePlace()
            .is(BlockPredicate.solid())
            .inRandomPatch(BN.id("patch_wall_mushrooms_with_moss"))
            .tries(120)
            .spreadXZ(4)
            .spreadY(7)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WALL_MUSHROOMS = BCLFeatureBuilder
            .startFacing(BN.id("temp_wall_mushrooms"))
            .add(NetherBlocks.WALL_MUSHROOM_RED, 40)
            .add(NetherBlocks.WALL_MUSHROOM_BROWN, 35)
            .allHorizontal()
            .inlinePlace()
            .is(BlockPredicate.solid())
            .inRandomPatch(BN.id("patch_wall_mushrooms"))
            .tries(120)
            .spreadXZ(4)
            .spreadY(7)
            .buildAndRegister();


    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WALL_JUNGLE = BCLFeatureBuilder
            .startFacing(BN.id("temp_wall_jungle"))
            .add(NetherBlocks.WALL_MUSHROOM_RED, 20)
            .add(NetherBlocks.WALL_MUSHROOM_BROWN, 15)
            .add(NetherBlocks.JUNGLE_MOSS, 40)
            .add(NetherBlocks.WALL_MOSS, 40)
            .allHorizontal()
            .inlinePlace()
            .inRandomPatch(BN.id("patch_wall_jungle"))
            .tries(120)
            .spreadXZ(7)
            .spreadY(7)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WALL_UPSIDE_DOWN = BCLFeatureBuilder
            .startFacing(BN.id("temp_wall_upside_down"))
            .add(NetherBlocks.WALL_MUSHROOM_RED, 20)
            .add(NetherBlocks.WALL_MUSHROOM_BROWN, 15)
            .add(NetherBlocks.JUNGLE_MOSS, 90)
            .allHorizontal()
            .inlinePlace()
            .is(BlockPredicate.solid())
            .inRandomPatch(BN.id("patch_upside_down"))
            .tries(120)
            .spreadXZ(4)
            .spreadY(7)
            .buildAndRegister();

    public static final BCLConfigureFeature<Feature<NoneFeatureConfiguration>, NoneFeatureConfiguration> WALL_LUCIS = BCLFeatureBuilder
            .start(BN.id("lucis"), org.betterx.betternether.registry.NetherFeatures.LUCIS)
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WALL_LUCIS = WALL_LUCIS
            .place()
            .isEmpty()
            .inRandomPatch(BN.id("patch_lucis"))
            .tries(120)
            .spreadXZ(12)
            .spreadY(10)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_NETHER_REED = BCLFeatureBuilder
            .startColumn(BN.id("temp_patch_nether_reed"))
            .direction(Direction.UP)
            .prioritizeTip()
            .addTopShape(NetherBlocks.MAT_REED.getStem().defaultBlockState(), BiasedToBottomInt.of(0, 3))
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .belowIsNextTo(BlockPredicates.ONLY_LAVA)
            .inRandomPatch(BN.id("patch_nether_reed"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_NETHER_CACTUS = BCLFeatureBuilder
            .startColumn(BN.id("temp_patch_nether_cactus"))
            .direction(Direction.UP)
            .prioritizeTip()
            .addTopShape(NetherBlocks.NETHER_CACTUS.defaultBlockState(), BiasedToBottomInt.of(1, 4))
            .inlinePlace()
            .isEmptyAndOn(BlockPredicates.ONLY_GRAVEL_OR_SAND)
            .inRandomPatch(BN.id("patch_nether_cactus"))
            .tries(16)
            .buildAndRegister();

    public static final BCLConfigureFeature<WartCapFeature, NoneFeatureConfiguration> WART_CAP = BCLFeatureBuilder
            .start(BN.id("wart_cap"), NetherFeatures.WART_CAP)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_HOOK_MUSHROOM = BCLFeatureBuilder
            .start(BN.id("hook_mushroom"), NetherBlocks.HOOK_MUSHROOM)
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_hook_mushroom"))
            .likeDefaultNetherVegetation()
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_MOSS_COVER = BCLFeatureBuilder
            .start(BN.id("hook_moss_cover"), NetherBlocks.MOSS_COVER)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_moss_cover"))
            .tries(120)
            .spreadXZ(8)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> BONEMEAL_NETHERRACK_MOSS = BCLFeatureBuilder
            .startBonemealPatch(BN.id("bonemeal_netherrack_moss"))
            .addAllStatesFor(BlockMagmaFlower.AGE, NetherBlocks.MAGMA_FLOWER, 30)
            .addAllStatesFor(BlockInkBush.AGE, NetherBlocks.INK_BUSH, 80)
            .addAllStatesFor(NetherWartBlock.AGE, Blocks.NETHER_WART, 40)
            .add(NetherBlocks.NETHER_GRASS, 200)
            .addAllStatesFor(BlockBlackApple.AGE, NetherBlocks.BLACK_APPLE, 50)
            .add(NetherBlocks.MAT_WART.getSeed(), 60)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> BONEMEAL_NETHER_MYCELIUM = BCLFeatureBuilder
            .startBonemealPatch(BN.id("temp_bonemeal_nether_mycelium"))
            .add(NetherBlocks.GRAY_MOLD, 200)
            .add(NetherBlocks.RED_MOLD, 180)
            .addAllStatesFor(BlockCommonPlant.AGE, NetherBlocks.ORANGE_MUSHROOM, 40)
            .add(Blocks.RED_MUSHROOM, 60)
            .add(Blocks.BROWN_MUSHROOM, 60)
            .add(Blocks.CRIMSON_FUNGUS, 80)
            .add(Blocks.WARPED_FUNGUS, 80)
            .add(NetherBlocks.SEPIA_BONE_GRASS, 30)
            .add(NetherBlocks.BONE_GRASS, 30)
            .add(NetherBlocks.JUNGLE_PLANT, 30)
            .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> BONEMEAL_JUNGLE_GRASS = BCLFeatureBuilder
            .startBonemealPatch(BN.id("bonemeal_jungle_grass"))
            .addAllStatesFor(BlockEggPlant.AGE, NetherBlocks.EGG_PLANT, 80)
            .add(NetherBlocks.JUNGLE_PLANT, 80)
            .addAllStatesFor(BlockMagmaFlower.AGE, NetherBlocks.MAGMA_FLOWER, 30)
            .addAllStatesFor(BlockFeatherFern.AGE, NetherBlocks.FEATHER_FERN, 20)
            .buildAndRegister();

    public static final BCLConfigureFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> BONEMEAL_MUSHROOM_GRASS = BCLFeatureBuilder
            .startBonemealNetherVegetation(BN.id("bonemeal_mushroom_grass"))
            .add(NetherBlocks.BONE_GRASS, 180)
            .addAllStatesFor(BlockFeatherFern.AGE, NetherBlocks.FEATHER_FERN, 20)
            .buildAndRegister();

    public static final BCLConfigureFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> BONEMEAL_SEPIA_MUSHROOM_GRASS = BCLFeatureBuilder
            .startBonemealNetherVegetation(BN.id("bonemeal_sepia_mushroom_grass"))
            .add(NetherBlocks.SEPIA_BONE_GRASS, 180)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> BONEMEAL_SWAMPLAND_GRASS = BCLFeatureBuilder
            .startBonemealPatch(BN.id("bonemeal_swampland_grass"))
            .add(NetherBlocks.SOUL_VEIN, 80)
            .add(NetherBlocks.SWAMP_GRASS, 200)
            .add(NetherBlocks.FEATHER_FERN, 80)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> BONEMEAL_CEILING_MUSHROOMS = BCLFeatureBuilder
            .startBonemealPatch(BN.id("bonemeal_ceiling_mushrooms"))
            .add(NetherBlocks.NETHER_GRASS, 80)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> BONEMEAL_SOUL_SOIL = BCLFeatureBuilder
            .startBonemealPatch(BN.id("temp_bonemeal_soul_soil"))
            .add(NetherBlocks.SOUL_VEIN, 150)
            .add(NetherBlocks.SOUL_GRASS, 200)
            .isEmptyAndOn(BlockPredicates.ONLY_SOUL_GROUND)
            .buildAndRegister();

    public static void ensureStaticInitialization() {

    }

    public static void setupBonemealFeatures() {
        NetherBlocks.NETHERRACK_MOSS.setVegetationFeature(BONEMEAL_NETHERRACK_MOSS);
        NetherBlocks.NETHER_MYCELIUM.setVegetationFeature(BONEMEAL_NETHER_MYCELIUM);
        NetherBlocks.JUNGLE_GRASS.setVegetationFeature(BONEMEAL_JUNGLE_GRASS);
        NetherBlocks.MUSHROOM_GRASS.setVegetationFeature(BONEMEAL_MUSHROOM_GRASS);
        NetherBlocks.SEPIA_MUSHROOM_GRASS.setVegetationFeature(BONEMEAL_SEPIA_MUSHROOM_GRASS);
        NetherBlocks.SWAMPLAND_GRASS.setVegetationFeature(BONEMEAL_SWAMPLAND_GRASS);
        NetherBlocks.CEILING_MUSHROOMS.setVegetationFeature(BONEMEAL_CEILING_MUSHROOMS);

        BonemealAPI.INSTANCE.addSpreadableFeatures(Blocks.SOUL_SOIL, BONEMEAL_SOUL_SOIL);
        BonemealAPI.INSTANCE.addSpreadableFeatures(Blocks.SOUL_SAND, BONEMEAL_SOUL_SOIL);
    }
}
