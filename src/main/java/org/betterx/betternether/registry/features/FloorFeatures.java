package org.betterx.betternether.registry.features;

import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v2.levelgen.features.FastFeatures;
import org.betterx.bclib.api.v2.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.bclib.api.v2.levelgen.structures.StructureWorldNBT;
import org.betterx.bclib.api.v2.tag.CommonBlockTags;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BlockCommonPlant;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.features.JellyfishMushroomFeature;
import org.betterx.betternether.world.features.NetherSakuraBushFeature;
import org.betterx.betternether.world.features.WillowBushFeature;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class FloorFeatures {


    public static final BCLFeature VANILLA_WARPED_FUNGUS
            = FastFeatures.patch(BetterNether.makeID("vanilla_warped_fungus"), Blocks.WARPED_FUNGUS, 8, 7, 3);

    public static final BCLFeature VANILLA_WARPED_ROOTS
            = FastFeatures.patch(BetterNether.makeID("vanilla_warped_roots"), Blocks.WARPED_ROOTS, 10, 7, 3);

    public static final BCLFeature MOSS_COVER
            = FastFeatures.patch(BetterNether.makeID("moss_cover"), NetherBlocks.MOSS_COVER);

    public static final BCLFeature HOOK_MUSHROOM
            = FastFeatures.patch(BetterNether.makeID("hook_mushroom"), NetherBlocks.HOOK_MUSHROOM);
    public static final BCLFeature SOUL_GRASS_PATCH
            = FastFeatures.patch(BetterNether.makeID("soul_grass"), NetherBlocks.SOUL_GRASS);

    public static final BCLFeature SWAMP_GRASS
            = FastFeatures.patch(BetterNether.makeID("swamp_grass"), NetherBlocks.SWAMP_GRASS);

    public static final BCLFeature SCULK_VEIN
            = FastFeatures.patch(BetterNether.makeID("sculk_vein"),
            BlockStateProvider.simple(Blocks
                    .SCULK_VEIN
                    .defaultBlockState()
                    .setValue(MultifaceBlock.getFaceProperty(Direction.DOWN), true)
            ),
            8, 4, 3
    );

    public static final BCLFeature SCULK_CATALYST = BCLFeatureBuilder
            .start(BetterNether.makeID("sculk_catalyst"), Blocks.SCULK_CATALYST)
            .offset(Direction.DOWN)
            .inBasinOf(BlockPredicate.anyOf(
                    BlockPredicate.matchesTag(CommonBlockTags.TERRAIN),
                    BlockPredicate.matchesBlocks(Blocks.SCULK)
            ))
            .buildAndRegister();

    public static final BCLFeature SCULK_SHRIEKER = BCLFeatureBuilder
            .start(
                    BetterNether.makeID("sculk_shrieker"),
                    Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, true)
            )
            .offset(Direction.DOWN)
            .buildAndRegister();

    public static final BCLFeature NETHER_GRASS_PATCH
            = FastFeatures.patch(BetterNether.makeID("nether_grass"), NetherBlocks.NETHER_GRASS);

    public static final BCLFeature FEATHER_FERN_PATCH
            = FastFeatures.patch(BetterNether.makeID("feather_fern"), NetherBlocks.FEATHER_FERN);

    public static final BCLFeature BLACK_BUSH_PATCH
            = FastFeatures.patch(BetterNether.makeID("black_bush"), NetherBlocks.BLACK_BUSH);

    public static final BCLFeature WART_SEED_PATCH
            = FastFeatures.patch(BetterNether.makeID("wart_seed"), NetherBlocks.MAT_WART.getSeed(), 32, 2, 1);

    public static final BCLFeature WILLOW_BUSH_PATCH
            = FastFeatures.patch(BetterNether.makeID("swillow_bush"), 2, 5, 3, new WillowBushFeature());

    public static final BCLFeature NETHER_SAKURA_BUSH_PATCH
            = FastFeatures.patch(BetterNether.makeID("snether_sakura_bush"), 2, 5, 3, new NetherSakuraBushFeature());

    public static final BCLFeature JELLYFISH_MUSHROOM_SINGLE
            = FastFeatures.simple(
            BetterNether.makeID("jellyfish_mushroom_old"),
            3,
            false,
            new JellyfishMushroomFeature()
    );
    public static final BCLFeature JELLYFISH_MUSHROOM
            = FastFeatures.patch(BetterNether.makeID("jellyfish_mushroom_old"), 64, 5, 3, JELLYFISH_MUSHROOM_SINGLE);


    public static final BCLFeature FORREST_LITTER = BCLFeatureBuilder
            .start(BetterNether.makeID("forrest_litter"), BCLFeature.TEMPLATE)
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("upside_down_forest/tree_fallen"), 1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("upside_down_forest/tree_needle"), 1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("upside_down_forest/tree_root"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("upside_down_forest/tree_stump"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(
                            BetterNether.makeID("upside_down_forest/tree_small_branch"),
                            1,
                            StructurePlacementType.FLOOR,
                            1.0f
                    )
            )));

    public static final BCLFeature MAGMA_FLOWER = FastFeatures.patchWitRandomInt(
            BetterNether.makeID("magma_flower"),
            NetherBlocks.MAGMA_FLOWER, BlockCommonPlant.AGE,
            16, 4, 1
    );
    public static final BCLFeature INK_BUSH = FastFeatures.patchWitRandomInt(
            BetterNether.makeID("ink_bush"),
            NetherBlocks.INK_BUSH, BlockCommonPlant.AGE,
            4, 3, 2
    );
    public static final BCLFeature NETHER_WART = FastFeatures.patchWitRandomInt(
            BetterNether.makeID("nether_wart"),
            Blocks.NETHER_WART, BlockStateProperties.AGE_3,
            16, 3, 1
    );

    public static final BCLFeature BLACK_APPLE = FastFeatures.patchWitRandomInt(
            BetterNether.makeID("black_apple"),
            NetherBlocks.BLACK_APPLE, BlockCommonPlant.AGE,
            8, 7, 2
    );

    public static void ensureStaticInitialization() {
    }

    static StructureWorldNBT cfg(
            ResourceLocation location,
            int offsetY,
            StructurePlacementType type,
            float chance
    ) {
        return TemplateFeatureConfig.cfg(location, offsetY, type, chance);
    }

}
