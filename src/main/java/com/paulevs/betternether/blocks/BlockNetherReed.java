package com.paulevs.betternether.blocks;

import com.paulevs.betternether.BlocksHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockNetherReed extends BlockBase {
	public static final BooleanProperty TOP = BooleanProperty.create("top");

	public BlockNetherReed() {
		super(Properties.create(Material.PLANTS, MaterialColor.CYAN)
				.sound(SoundType.CROP)
				.zeroHardnessAndResistance()
				.doesNotBlockMovement()
				.notSolid()
				.tickRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateContainer().getBaseState().with(TOP, true));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateManager) {
		stateManager.add(TOP);
	}

	@OnlyIn(Dist.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, IBlockReader view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		Block up = world.getBlockState(pos.up()).getBlock();
		BlockState down = world.getBlockState(pos.down());
		if (BlocksHelper.isNetherGround(down)) {
			BlockPos posDown = pos.down();
			boolean lava = BlocksHelper.isLava(world.getBlockState(posDown.north()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.south()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.east()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.west()));
			if (lava) {
				return up == this ? this.getDefaultState().with(TOP, false) : this.getDefaultState();
			}
			return Blocks.AIR.getDefaultState();
		}
		else if (down.getBlock() != this)
			return Blocks.AIR.getDefaultState();
		else if (up != this)
			return this.getDefaultState();
		else
			return this.getDefaultState().with(TOP, false);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		BlockPos posDown = pos.down();
		BlockState down = world.getBlockState(posDown);
		if (BlocksHelper.isNetherGround(down)) {
			boolean lava = BlocksHelper.isLava(world.getBlockState(posDown.north()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.south()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.east()));
			lava = lava || BlocksHelper.isLava(world.getBlockState(posDown.west()));
			return lava;
		}
		else
			return down.getBlock() == this;
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!isValidPosition(state, world, pos)) {
			world.destroyBlock(pos, true);
			return;
		}
		if (state.get(TOP).booleanValue()) {
			BlockPos up = pos.up();
			boolean grow = world.isAirBlock(up);
			if (grow) {
				int length = BlocksHelper.getLengthDown(world, pos, this);
				boolean isFertile = BlocksHelper.isFertile(world.getBlockState(pos.down(length)));
				if (isFertile)
					length -= 2;
				grow = (length < 3) && (isFertile ? (random.nextInt(8) == 0) : (random.nextInt(16) == 0));
				if (grow) {
					BlocksHelper.setWithUpdate(world, up, getDefaultState());
					BlocksHelper.setWithUpdate(world, pos, getDefaultState().with(TOP, false));
				}
			}
		}
	}
}
