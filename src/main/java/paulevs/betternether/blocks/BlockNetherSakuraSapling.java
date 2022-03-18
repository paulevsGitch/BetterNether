package paulevs.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.interfaces.SurvivesOnNetherrack;
import paulevs.betternether.world.structures.plants.StructureNetherSakura;
import ru.bclib.blocks.FeatureHangingSaplingBlock;
import ru.bclib.world.features.DefaultFeature;

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
		super((state)->FEATURE);
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
