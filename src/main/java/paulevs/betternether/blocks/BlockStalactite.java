package paulevs.betternether.blocks;

import javax.annotation.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockStalactite extends BlockBaseNotFull {
	public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 7);	
	private static final VoxelShape[] SHAPES;

	public BlockStalactite(Block source) {
		super(FabricBlockSettings.copy(source).noOcclusion());
		this.registerDefaultState(getStateDefinition().any().setValue(SIZE, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(SIZE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPES[state.getValue(SIZE)];
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		final MutableBlockPos POS = new MutableBlockPos();
		
		if (world.getBlockState(pos.below()).getBlock() instanceof BlockStalactite) {
			POS.setX(pos.getX());
			POS.setZ(pos.getZ());
			for (int i = 1; i < 8; i++) {
				POS.setY(pos.getY() - i);
				if (world.getBlockState(POS).getBlock() instanceof BlockStalactite) {
					BlockState state2 = world.getBlockState(POS);
					int size = state2.getValue(SIZE);
					if (size < i) {
						world.setBlockAndUpdate(POS, state2.setValue(SIZE, i));
					}
					else
						break;
				}
				else
					break;
			}
		}
		if (world.getBlockState(pos.above()).getBlock() instanceof BlockStalactite) {
			POS.setX(pos.getX());
			POS.setZ(pos.getZ());
			for (int i = 1; i < 8; i++) {
				POS.setY(pos.getY() + i);
				if (world.getBlockState(POS).getBlock() instanceof BlockStalactite) {
					BlockState state2 = world.getBlockState(POS);
					int size = state2.getValue(SIZE);
					if (size < i) {
						world.setBlockAndUpdate(POS, state2.setValue(SIZE, i));
					}
					else
						break;
				}
				else
					break;
			}
		}
	}

	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		BlockPos pos2 = pos.above();
		BlockState state2 = world.getBlockState(pos2);
		if (state2.getBlock() instanceof BlockStalactite && state2.getValue(SIZE) < state.getValue(SIZE)) {
			state2.getBlock().destroy(world, pos2, state2);
			world.destroyBlock(pos2, true);
		}

		pos2 = pos.below();
		state2 = world.getBlockState(pos2);
		if (state2.getBlock() instanceof BlockStalactite && state2.getValue(SIZE) < state.getValue(SIZE)) {
			state2.getBlock().destroy(world, pos2, state2);
			world.destroyBlock(pos2, true);
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return canPlace(world, pos, Direction.UP) || canPlace(world, pos, Direction.DOWN);
	}

	private boolean canPlace(LevelReader world, BlockPos pos, Direction dir) {
		return world.getBlockState(pos.relative(dir)).getBlock() instanceof BlockStalactite || Block.canSupportCenter(world, pos.relative(dir), dir.getOpposite());
	}

	static {
		SHAPES = new VoxelShape[8];
		for (int i = 0; i < 8; i++)
			SHAPES[i] = Block.box(7 - i, 0, 7 - i, 9 + i, 16, 9 + i);
	}
}