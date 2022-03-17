package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.materials.Materials;

import java.util.Random;

public class BlockCommonPlant extends BlockBaseNotFull implements BonemealableBlock {
	public static final IntegerProperty AGE = BlockProperties.AGE_FOUR;

	public BlockCommonPlant(MaterialColor color) {
		super(FabricBlockSettings.of(Materials.NETHER_PLANT)
				.mapColor(color)
				.sounds(SoundType.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
	}

	public BlockCommonPlant(Properties settings) {
		super(settings);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
	}
	
	public int getMaxAge(){
		return 3;
	}
	
	public IntegerProperty getAgeProperty(){
		return AGE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(AGE);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return BlocksHelper.isNetherGround(world.getBlockState(pos.below()));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (!canSurvive(state, world, pos))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
		return state.getValue(AGE) < 3;
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		int age = state.getValue(AGE);
		if (age < 3)
			return BlocksHelper.isFertile(world.getBlockState(pos.below())) ? (random.nextBoolean()) : (random.nextInt(4) == 0);
		else
			return false;
	}

	protected boolean canGrowTerrain(Level world, Random random, BlockPos pos, BlockState state) {
		int age = state.getValue(AGE);
		if (age < 3)
			return BlocksHelper.isFertile(world.getBlockState(pos.below())) ? (random.nextInt(8) == 0) : (random.nextInt(16) == 0);
		else
			return false;
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		int age = state.getValue(AGE);
		world.setBlockAndUpdate(pos, state.setValue(AGE, age + 1));
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		super.tick(state, world, pos, random);
		if (canGrowTerrain(world, random, pos, state))
			performBonemeal(world, random, pos, state);
	}
}