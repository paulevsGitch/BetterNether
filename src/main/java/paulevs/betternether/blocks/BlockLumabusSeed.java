package paulevs.betternether.blocks;

import java.util.Random;

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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.structures.IGrowableStructure;

public class BlockLumabusSeed extends BlockBaseNotFull implements BonemealableBlock {
	private static final VoxelShape SHAPE = Block.box(4, 6, 4, 12, 16, 12);
	private final IGrowableStructure structure;

	public BlockLumabusSeed(IGrowableStructure structure) {
		super(FabricBlockSettings.of(Material.PLANT)
				.mapColor(MaterialColor.COLOR_RED)
				.sounds(SoundType.CROP)
				.nonOpaque()
				.breakInstantly()
				.noCollision()
				.ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.structure = structure;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		return random.nextInt(4) == 0 && world.getBlockState(pos.below()).getBlock() == Blocks.AIR;
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		structure.grow(world, pos, random);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState upState = world.getBlockState(pos.above());
		return upState.isFaceSturdy(world, pos, Direction.DOWN);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (!canSurvive(state, world, pos))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		super.tick(state, world, pos, random);
		if (isBonemealSuccess(world, random, pos, state)) {
			performBonemeal(world, random, pos, state);
		}
	}
}