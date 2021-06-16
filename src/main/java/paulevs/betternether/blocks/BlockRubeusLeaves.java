package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.BlocksRegistry;

import java.util.Random;

public class BlockRubeusLeaves extends BlockBase {
	private static final int MAX_DIST = 10;
	public static final IntProperty DISTANCE_CUSTOM = IntProperty.of("dist_custom", 1, MAX_DIST);
	public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;

	public BlockRubeusLeaves() {
		super(Materials.makeLeaves(MapColor.LIGHT_BLUE));
		this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE_CUSTOM, 1).with(PERSISTENT, false));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(PERSISTENT, DISTANCE_CUSTOM);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return updateDistanceFromLogs((BlockState) this.getDefaultState().with(PERSISTENT, true), ctx.getWorld(), ctx.getBlockPos());
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(DISTANCE_CUSTOM) == MAX_DIST && !(Boolean) state.get(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.get(PERSISTENT) && state.get(DISTANCE_CUSTOM) == MAX_DIST) {
			dropStacks(state, world, pos);
			world.removeBlock(pos, false);
		}
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
		int dist = getDistanceFromLog(newState) + 1;
		if (dist != 1 || state.get(DISTANCE_CUSTOM) != dist) {
			world.getBlockTickScheduler().schedule(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int dist = MAX_DIST;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Direction[] dirs = Direction.values();
		int count = dirs.length;

		for (int n = 0; n < count; ++n) {
			Direction dir = dirs[n];
			mutable.set(pos, dir);
			dist = Math.min(dist, getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (dist == 1) {
				break;
			}
		}

		return (BlockState) state.with(DISTANCE_CUSTOM, dist);
	}

	private static int getDistanceFromLog(BlockState state) {
		if (state.getBlock() == BlocksRegistry.RUBEUS_LOG || state.getBlock() == BlocksRegistry.RUBEUS_BARK) {
			return 0;
		}
		else {
			return state.getBlock() instanceof BlockRubeusLeaves ? state.get(DISTANCE_CUSTOM) : MAX_DIST;
		}
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (world.hasRain(pos.up())) {
			if (random.nextInt(15) == 1) {
				BlockPos blockPos = pos.down();
				BlockState blockState = world.getBlockState(blockPos);
				if (!blockState.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
					double d = (double) ((float) pos.getX() + random.nextFloat());
					double e = (double) pos.getY() - 0.05D;
					double f = (double) ((float) pos.getZ() + random.nextFloat());
					world.addParticle(ParticleTypes.DRIPPING_WATER, d, e, f, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
