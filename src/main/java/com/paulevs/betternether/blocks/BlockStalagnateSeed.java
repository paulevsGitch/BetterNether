package com.paulevs.betternether.blocks;

import java.util.Random;

import com.paulevs.betternether.BlocksHelper;
import com.paulevs.betternether.structures.plants.StructureStalagnate;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlockStalagnateSeed extends BlockBaseNotFull implements IGrowable {
	protected static final VoxelShape SHAPE_TOP = Block.makeCuboidShape(4, 6, 4, 12, 16, 12);
	protected static final VoxelShape SHAPE_BOTTOM = Block.makeCuboidShape(4, 0, 4, 12, 12, 12);
	private static final StructureStalagnate STRUCTURE = new StructureStalagnate();
	public static final BooleanProperty TOP = BooleanProperty.create("top");

	public BlockStalagnateSeed() {
		super(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.CYAN)
				.sound(SoundType.CROP)
				.notSolid()
				.zeroHardnessAndResistance()
				.doesNotBlockMovement()
				.tickRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateContainer().getBaseState().with(TOP, true));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateManager) {
		stateManager.add(TOP);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		BlockState blockState = this.getDefaultState();
		if (ctx.getFace() == Direction.DOWN)
			return blockState;
		else if (ctx.getFace() == Direction.UP)
			return blockState.with(TOP, false);
		else
			return null;
	}

	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return state.get(TOP).booleanValue() ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random random, BlockPos pos, BlockState state) {
		if (random.nextInt(16) == 0) {
			if (state.get(TOP).booleanValue())
				return BlocksHelper.downRay(world, pos, StructureStalagnate.MIN_LENGTH) > 0;
			else
				return BlocksHelper.upRay(world, pos, StructureStalagnate.MIN_LENGTH) > 0;
		}
		return false;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		if (state.get(TOP).booleanValue())
			STRUCTURE.generateDown(world, pos, random);
		else
			STRUCTURE.generate(world, pos, random);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos.up())) || BlocksHelper.isNetherrack(world.getBlockState(pos.down()));
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(TOP).booleanValue()) {
			if (BlocksHelper.isNetherrack(world.getBlockState(pos.up())))
				return state;
			else
				return Blocks.AIR.getDefaultState();
		}
		else {
			if (BlocksHelper.isNetherrack(world.getBlockState(pos.down())))
				return state;
			else
				return Blocks.AIR.getDefaultState();
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.tick(state, world, pos, random);
		if (canUseBonemeal(world, random, pos, state)) {
			grow(world, random, pos, state);
		}
	}
}
