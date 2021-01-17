package com.paulevs.betternether.blocks;

import com.paulevs.betternether.blocks.materials.MaterialBuilder;
import com.paulevs.betternether.blocks.shapes.TripleShape;
import com.paulevs.betternether.registry.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockStalagnate extends BlockBaseNotFull {
	private static final VoxelShape SELECT_SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);
	private static final VoxelShape COLLISION_SHAPE = Block.makeCuboidShape(5, 0, 5, 11, 16, 11);
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.create("shape", TripleShape.class);

	public BlockStalagnate() {
		super(MaterialBuilder.makeWood(MaterialColor.LIME_TERRACOTTA).notSolid());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateContainer().getBaseState().with(SHAPE, TripleShape.MIDDLE));
		this.setDropItself(false);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return SELECT_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return COLLISION_SHAPE;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state) {
		return new ItemStack(RegistryHandler.STALAGNATE_STEM);
	}
}
