package paulevs.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureAnchorTreeBranch;
import ru.bclib.blocks.FeatureHangingSaplingBlock;
import ru.bclib.world.features.DefaultFeature;

import java.util.function.Function;

class AnchorTreeFeature extends DefaultFeature {
	private static final StructureAnchorTreeBranch STRUCTURE = new StructureAnchorTreeBranch();
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		STRUCTURE.grow(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.random());
		return true;
	}
}

public class BlockAnchorTreeSapling extends FeatureHangingSaplingBlock implements BonemealableBlock {
	private static final DefaultFeature FEATURE = new AnchorTreeFeature();
	
	public BlockAnchorTreeSapling() {
		super((state)->FEATURE);
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return BlocksHelper.isNetherrack(blockState);
	}
}
