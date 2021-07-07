package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;
import java.util.function.ToIntFunction;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.ItemsRegistry;

public class BlockLumabusVine extends BlockBaseNotFull {
	private static final VoxelShape MIDDLE_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
	private static final VoxelShape BOTTOM_SHAPE = Block.box(2, 4, 2, 14, 16, 14);
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final Random RANDOM = new Random();
	private final Block seed;

	public BlockLumabusVine(Block seed) {
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.COLOR_CYAN)
				.sound(SoundType.CROP)
				.noCollission()
				.instabreak()
				.noOcclusion()
				.lightLevel(getLuminance()));
		this.seed = seed;
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.registerDefaultState(getStateDefinition().any().setValue(SHAPE, TripleShape.TOP));
	}

	private static ToIntFunction<BlockState> getLuminance() {
		return (blockState) -> {
			return blockState.getValue(SHAPE) == TripleShape.BOTTOM ? 15 : 0;
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return state.getValue(SHAPE) == TripleShape.BOTTOM ? BOTTOM_SHAPE : MIDDLE_SHAPE;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState upState = world.getBlockState(pos.above());
		return upState.getBlock() == this || upState.isFaceSturdy(world, pos, Direction.DOWN);
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
		return true;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		return canSurvive(state, world, pos) && (world.getBlockState(pos.below()).getBlock() == this || state.getValue(SHAPE) == TripleShape.BOTTOM) ? state : Blocks.AIR.defaultBlockState();
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (state.getValue(SHAPE) == TripleShape.BOTTOM) {
			return Lists.newArrayList(new ItemStack(seed, MHelper.randRange(1, 3, RANDOM)), new ItemStack(ItemsRegistry.GLOWSTONE_PILE, MHelper.randRange(1, 3, RANDOM)));
		}
		return Lists.newArrayList();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(seed);
	}
}