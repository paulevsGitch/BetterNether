package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.bclib.blocks.FeatureHangingSaplingBlock;
import org.betterx.bclib.world.features.DefaultFeature;
import org.betterx.betternether.interfaces.SurvivesOnNetherrack;
import org.betterx.betternether.world.structures.plants.StructureAnchorTreeBranch;

class AnchorTreeFeature extends DefaultFeature {
    private static final StructureAnchorTreeBranch STRUCTURE = new StructureAnchorTreeBranch();

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        STRUCTURE.grow(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.random());
        return true;
    }
}

public class BlockAnchorTreeSapling extends FeatureHangingSaplingBlock implements BonemealableBlock, SurvivesOnNetherrack {
    private static final DefaultFeature FEATURE = new AnchorTreeFeature();

    public BlockAnchorTreeSapling() {
        super((state) -> FEATURE);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return isSurvivable(blockState);
    }
}
