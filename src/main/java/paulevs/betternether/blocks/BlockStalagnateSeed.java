package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import paulevs.betternether.world.structures.plants.StructureStalagnate;
import ru.bclib.blocks.FeatureSaplingBlock;
import ru.bclib.world.features.DefaultFeature;

class StalagnateTreeFeatureUp extends DefaultFeature {
	static final StructureStalagnate STRUCTURE = new StructureStalagnate();
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		STRUCTURE.generate(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.random(), featurePlaceContext.chunkGenerator().getGenDepth(), NetherChunkPopulatorFeature.generatorForThread().context);
		return true;
	}
}

class StalagnateTreeFeatureDown extends DefaultFeature {
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		StalagnateTreeFeatureUp.STRUCTURE.generateDown(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.random());
		return true;
	}
}

public class BlockStalagnateSeed extends FeatureSaplingBlock implements BonemealableBlock {
	protected static final VoxelShape SHAPE_TOP = Block.box(4, 6, 4, 12, 16, 12);
	protected static final VoxelShape SHAPE_BOTTOM = Block.box(4, 0, 4, 12, 12, 12);
	
	private static final DefaultFeature FEATURE_UP = new StalagnateTreeFeatureUp();
	private static final DefaultFeature FEATURE_DOWN = new StalagnateTreeFeatureDown();
	
	public static final BooleanProperty TOP = BooleanProperty.create("top");

	public BlockStalagnateSeed() {
		super((state)->growsDownward(state) ? FEATURE_DOWN : FEATURE_UP);
		this.registerDefaultState(getStateDefinition().any().setValue(TOP, true));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		super.createBlockStateDefinition(stateManager);
		stateManager.add(TOP);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState blockState = this.defaultBlockState();
		if (ctx.getClickedFace() == Direction.DOWN)
			return blockState;
		else if (ctx.getClickedFace() == Direction.UP)
			return blockState.setValue(TOP, false);
		else
			return null;
	}

	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return growsDownward(state) ? SHAPE_TOP : SHAPE_BOTTOM;
	}
	
	private static boolean growsDownward(BlockState state) {
		return state.getValue(TOP)
					.booleanValue();
	}
	
	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		if (super.isBonemealSuccess(world, random, pos, state)) {
			if (growsDownward(state))
				return BlocksHelper.downRay(world, pos, StructureStalagnate.MIN_LENGTH) > 0;
			else
				return BlocksHelper.upRay(world, pos, StructureStalagnate.MIN_LENGTH) > 0;
		}
		return false;
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return BlocksHelper.isNetherrack(blockState);
	}
	
	@Override
	public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		final BlockPos target;
		if (growsDownward(blockState)) {
		 	target = blockPos.above();
		} else {
			target = blockPos.below();
		}
		return this.mayPlaceOn(levelReader.getBlockState(target), levelReader, target);
	}
	
	@Override
	protected Feature<?> getFeature(BlockState state) {
		if (growsDownward(state)){
			return FEATURE_DOWN;
		} else {
			return FEATURE_UP;
		}
	}
}
