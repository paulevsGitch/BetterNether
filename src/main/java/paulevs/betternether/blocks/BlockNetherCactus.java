package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;

import java.util.Random;

public class BlockNetherCactus extends BlockBaseNotFull {
	private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);
	private static final VoxelShape SIDE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);
	public static final BooleanProperty TOP = BooleanProperty.of("top");

	public BlockNetherCactus() {
		super(FabricBlockSettings.of(Material.CACTUS)
				.materialColor(MapColor.TERRACOTTA_ORANGE)
				.sounds(BlockSoundGroup.WOOL)
				.nonOpaque()
				.ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(TOP, true));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(TOP);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return state.get(TOP).booleanValue() ? TOP_SHAPE : SIDE_SHAPE;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (canPlaceAt(state, world, pos)) {
			Block up = world.getBlockState(pos.up()).getBlock();
			if (up == this)
				return state.with(TOP, false);
			else
				return this.getDefaultState();
		}
		else
			return Blocks.AIR.getDefaultState();
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Block down = world.getBlockState(pos.down()).getBlock();
		return down == Blocks.GRAVEL || down == this;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!canPlaceAt(state, world, pos)) {
			world.breakBlock(pos, true);
			return;
		}
		if (state.get(TOP).booleanValue() && random.nextInt(16) == 0) {
			BlockPos up = pos.up();
			boolean grow = world.getBlockState(up).getBlock() == Blocks.AIR;
			grow = grow && (BlocksHelper.getLengthDown(world, pos, this) < 3);
			if (grow) {
				BlocksHelper.setWithUpdate(world, up, getDefaultState());
				BlocksHelper.setWithUpdate(world, pos, getDefaultState().with(TOP, false));
			}
		}
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.damage(DamageSource.CACTUS, 1.0F);
	}
}