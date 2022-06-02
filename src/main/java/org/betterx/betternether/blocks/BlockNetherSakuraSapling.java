package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.bclib.api.features.DefaultFeature;
import org.betterx.bclib.blocks.FeatureHangingSaplingBlock;
import org.betterx.betternether.interfaces.SurvivesOnNetherrack;
import org.betterx.betternether.world.structures.plants.StructureNetherSakura;

class NetherSakuraFeature extends DefaultFeature {
    private static final StructureNetherSakura STRUCTURE = new StructureNetherSakura();

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        STRUCTURE.grow(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.random());
        return true;
    }
}

public class BlockNetherSakuraSapling extends FeatureHangingSaplingBlock implements BonemealableBlock, SurvivesOnNetherrack {
    private static final DefaultFeature FEATURE = new NetherSakuraFeature();

    public BlockNetherSakuraSapling() {
        super((state) -> FEATURE);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return isSurvivable(blockState);
    }
//
//	@Override
//	protected Feature<?> getFeature() {
//		return FEATURE;
//	}
}
