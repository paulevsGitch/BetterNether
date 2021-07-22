package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.NetherBlocks;

public class BlockRubeusLeaves extends BlockBase {
	private static final int MAX_DIST = 10;
	public static final IntegerProperty DISTANCE_CUSTOM = IntegerProperty.create("dist_custom", 1, MAX_DIST);
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	public BlockRubeusLeaves() {
		super(Materials.makeLeaves(MaterialColor.COLOR_LIGHT_BLUE));
		this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE_CUSTOM, 1).setValue(PERSISTENT, false));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(PERSISTENT, DISTANCE_CUSTOM);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return updateDistanceFromLogs((BlockState) this.defaultBlockState().setValue(PERSISTENT, true), ctx.getLevel(), ctx.getClickedPos());
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(DISTANCE_CUSTOM) == MAX_DIST && !(Boolean) state.getValue(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE_CUSTOM) == MAX_DIST) {
			dropResources(state, world, pos);
			world.removeBlock(pos, false);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		world.setBlock(pos, updateDistanceFromLogs(state, world, pos), 3);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos posFrom) {
		int dist = getDistanceFromLog(newState) + 1;
		if (dist != 1 || state.getValue(DISTANCE_CUSTOM) != dist) {
			world.getBlockTicks().scheduleTick(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, LevelAccessor world, BlockPos pos) {
		int dist = MAX_DIST;
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		Direction[] dirs = Direction.values();
		int count = dirs.length;

		for (int n = 0; n < count; ++n) {
			Direction dir = dirs[n];
			mutable.setWithOffset(pos, dir);
			dist = Math.min(dist, getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (dist == 1) {
				break;
			}
		}

		return (BlockState) state.setValue(DISTANCE_CUSTOM, dist);
	}

	private static int getDistanceFromLog(BlockState state) {
		if (state.getBlock() == NetherBlocks.RUBEUS_LOG || state.getBlock() == NetherBlocks.RUBEUS_BARK) {
			return 0;
		}
		else {
			return state.getBlock() instanceof BlockRubeusLeaves ? state.getValue(DISTANCE_CUSTOM) : MAX_DIST;
		}
	}

	@Environment(EnvType.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		if (world.isRainingAt(pos.above())) {
			if (random.nextInt(15) == 1) {
				BlockPos blockPos = pos.below();
				BlockState blockState = world.getBlockState(blockPos);
				if (!blockState.canOcclude() || !blockState.isFaceSturdy(world, blockPos, Direction.UP)) {
					double d = (double) ((float) pos.getX() + random.nextFloat());
					double e = (double) pos.getY() - 0.05D;
					double f = (double) ((float) pos.getZ() + random.nextFloat());
					world.addParticle(ParticleTypes.DRIPPING_WATER, d, e, f, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
