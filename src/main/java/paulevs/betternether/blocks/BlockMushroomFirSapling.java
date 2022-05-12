package paulevs.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.interfaces.SurvivesOnNetherMycelium;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import paulevs.betternether.world.structures.plants.StructureMushroomFir;
import ru.bclib.blocks.FeatureSaplingBlock;
import ru.bclib.world.features.DefaultFeature;

import java.util.Random;
import net.minecraft.util.RandomSource;

class MushroomFirTreeFeature extends DefaultFeature {
	private static final StructureMushroomFir STRUCTURE = new StructureMushroomFir();
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		STRUCTURE.generate(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.random(), featurePlaceContext.chunkGenerator().getGenDepth(), NetherChunkPopulatorFeature.generatorForThread().context);
		return true;
	}
}


public class BlockMushroomFirSapling extends FeatureSaplingBlock implements BonemealableBlock, SurvivesOnNetherMycelium {
	private static final DefaultFeature FEATURE = new MushroomFirTreeFeature();
	
	public BlockMushroomFirSapling() {
		super((state)->FEATURE);
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return isSurvivable(blockState);
	}

	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return BlocksHelper.isFertile(world.getBlockState(pos.below())) ? (random.nextInt(8) == 0) : (random.nextInt(16) == 0);
	}
//
//	@Override
//	protected Feature<?> getFeature() {
//		return FEATURE;
//	}
}