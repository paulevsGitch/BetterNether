package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockWhisperingGourdVine extends BlockBaseNotFull implements BonemealableBlock {
	private static final VoxelShape SELECTION = Block.box(2, 0, 2, 14, 16, 14);
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;

	public BlockWhisperingGourdVine() {
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.COLOR_RED)
				.sound(SoundType.CROP)
				.noCollission()
				.instabreak()
				.noOcclusion()
				.randomTicks()
				.breakByTool(FabricToolTags.SHEARS)
				.instabreak());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.registerDefaultState(getStateDefinition().any().setValue(SHAPE, TripleShape.BOTTOM));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SELECTION;
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
		if (!canSurvive(state, world, pos))
			return Blocks.AIR.defaultBlockState();
		else if (world.getBlockState(pos.below()).getBlock() != this)
			return state.setValue(SHAPE, TripleShape.BOTTOM);
		else if (state.getValue(SHAPE) == TripleShape.BOTTOM)
			return state.setValue(SHAPE, TripleShape.TOP);
		else
			return state;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.above(3)).getBlock() != this && world.getBlockState(pos.below()).getBlock() != this;
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		return world.getBlockState(pos.above(3)).getBlock() != this && world.getBlockState(pos.below()).getMaterial().isReplaceable();
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		BlocksHelper.setWithUpdate(world, pos, state.setValue(SHAPE, TripleShape.TOP));
		BlocksHelper.setWithUpdate(world, pos.below(), defaultBlockState());
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.getParameter(LootContextParams.TOOL);
		if (tool != null && (FabricToolTags.SHEARS.contains(tool.getItem()) || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0))
			return Lists.newArrayList(new ItemStack(this.asItem()));
		else if (state.getValue(SHAPE) == TripleShape.BOTTOM || MHelper.RANDOM.nextBoolean())
			return Lists.newArrayList(new ItemStack(this.asItem()));
		else
			return Lists.newArrayList();
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		super.tick(state, world, pos, random);
		if (isBonemealSuccess(world, random, pos, state)) {
			performBonemeal(world, random, pos, state);
		}
		if (state.getValue(SHAPE) != TripleShape.MIDDLE && state.getValue(SHAPE) != TripleShape.BOTTOM && random.nextInt(16) == 0) {
			BlocksHelper.setWithUpdate(world, pos, state.setValue(SHAPE, TripleShape.MIDDLE));
		}
	}

	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack tool = player.getItemInHand(hand);
		if (FabricToolTags.SHEARS.contains(tool.getItem()) && state.getValue(SHAPE) == TripleShape.MIDDLE) {
			if (!world.isClientSide) {
				BlocksHelper.setWithUpdate(world, pos, state.setValue(SHAPE, TripleShape.BOTTOM));
				world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(BlocksRegistry.WHISPERING_GOURD)));
				if (world.random.nextBoolean()) {
					world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(BlocksRegistry.WHISPERING_GOURD)));
				}
			}
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		else {
			return super.use(state, world, pos, player, hand, hit);
		}
	}
}
