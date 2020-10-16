package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.shapes.TripleShape;

public class BlockNeonEquisetum extends BlockBaseNotFull implements Fertilizable {
	protected static final VoxelShape SHAPE_SELECTION = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);

	public BlockNeonEquisetum() {
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.GREEN)
				.sounds(BlockSoundGroup.CROP)
				.noCollision()
				.dropsNothing()
				.breakInstantly()
				.nonOpaque()
				.lightLevel(15));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, TripleShape.BOTTOM));
		setDropItself(false);
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
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockState up = world.getBlockState(pos.up());
		return up.getBlock() == this || BlocksHelper.isNetherrack(up);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		BlockPos upPos = pos.up();
		if (world.getBlockState(upPos).getBlock() == this) {
			world.setBlockState(upPos, getDefaultState().with(SHAPE, TripleShape.MIDDLE));
			upPos = upPos.up();
			if (world.getBlockState(upPos).getBlock() == this) {
				world.setBlockState(upPos, getDefaultState().with(SHAPE, TripleShape.TOP));
			}
		}
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (tool != null && tool.getItem().isIn(FabricToolTags.SHEARS) || EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0) {
			return Lists.newArrayList(new ItemStack(this.asItem()));
		}
		else {
			return Lists.newArrayList();
		}
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		Mutable blockPos = new Mutable().set(pos);
		for (int y = pos.getY() - 1; y > 1; y--) {
			blockPos.setY(y);
			if (world.getBlockState(blockPos).getBlock() != this)
				return world.getBlockState(blockPos).getBlock() == Blocks.AIR;
		}
		return false;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		Mutable blockPos = new Mutable().set(pos);
		for (int y = pos.getY(); y > 1; y--) {
			blockPos.setY(y);
			if (world.getBlockState(blockPos).getBlock() != this)
				break;
		}
		BlocksHelper.setWithoutUpdate(world, blockPos, this.getDefaultState());
		this.onPlaced(world, blockPos, state, null, null);
	}
}
