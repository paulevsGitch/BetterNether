package com.paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import com.paulevs.betternether.BlocksHelper;
import com.paulevs.betternether.blocks.materials.MaterialBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;



public class BlockNetherGrass extends BlockBase {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 12, 14);

	public BlockNetherGrass() {
		super(MaterialBuilder.makeGrass(MaterialColor.GRAY_TERRACOTTA).tickRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		Vector3d vec3d = state.getOffset(view, pos);
		return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XZ;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos.down())) || BlocksHelper.isNylium(world.getBlockState(pos.down()));
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		if (!isValidPosition(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (builder.get(LootParameters.TOOL).getItem().isIn(Tags.Items.SHEARS))
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return super.getDrops(state, builder);
	}
}



