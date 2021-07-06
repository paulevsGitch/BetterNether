package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;

import java.util.function.ToIntFunction;

public class BlockAnchorTreeVine extends BlockBaseNotFull {
	protected static final VoxelShape SHAPE_SELECTION = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;

	public BlockAnchorTreeVine() {
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MapColor.GREEN)
				.sounds(BlockSoundGroup.CROP)
				.noCollision()
				.dropsNothing()
				.breakInstantly()
				.nonOpaque()
				.luminance(getLuminance()));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		setDropItself(false);
	}

	protected static ToIntFunction<BlockState> getLuminance() {
		return (state) -> {
			return state.get(SHAPE) == TripleShape.BOTTOM ? 15 : 0;
		};
	}

	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XZ;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		Vec3d vec3d = state.getModelOffset(view, pos);
		return SHAPE_SELECTION.offset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
		return true;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		Block up = world.getBlockState(pos.up()).getBlock();
		if (up != this && up != BlocksRegistry.ANCHOR_TREE_LEAVES && up != Blocks.NETHERRACK)
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(BlocksRegistry.ANCHOR_TREE_LEAVES);
	}
}