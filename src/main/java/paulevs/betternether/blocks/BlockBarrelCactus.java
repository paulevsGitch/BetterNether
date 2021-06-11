package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.MHelper;

public class BlockBarrelCactus extends BlockCommonPlant implements Fertilizable {
	private static final VoxelShape EMPTY = Block.createCuboidShape(0, 0, 0, 0, 0, 0);
	private static final VoxelShape[] SHAPES = new VoxelShape[] {
			Block.createCuboidShape(5, 0, 5, 11, 5, 11),
			Block.createCuboidShape(3, 0, 3, 13, 9, 13),
			Block.createCuboidShape(2, 0, 2, 14, 12, 14),
			Block.createCuboidShape(1, 0, 1, 15, 14, 15)
	};

	public BlockBarrelCactus() {
		super(FabricBlockSettings.of(Material.CACTUS)
				.materialColor(MapColor.TERRACOTTA_BLUE)
				.sounds(BlockSoundGroup.WOOL)
				.nonOpaque()
				.hardness(0.4F)
				.ticksRandomly()
				.breakByTool(FabricToolTags.SHEARS)
				.dynamicBounds()
        );
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Block down = world.getBlockState(pos.down()).getBlock();
		return down == Blocks.GRAVEL;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (canPlaceAt(state, world, pos))
			return state;
		else
			return Blocks.AIR.getDefaultState();
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (state.get(BlockCommonPlant.AGE) > 1) entity.damage(DamageSource.CACTUS, 1.0F);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		Vec3d vec3d = state.getModelOffset(view, pos);
		return SHAPES[state.get(BlockCommonPlant.AGE)].offset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		if (state.get(BlockCommonPlant.AGE) < 2) return EMPTY;
		Vec3d vec3d = state.getModelOffset(view, pos);
		return SHAPES[state.get(BlockCommonPlant.AGE)].offset(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XYZ;
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		if (state.get(BlockCommonPlant.AGE) == 3) {
			return Lists.newArrayList(new ItemStack(this, MHelper.randRange(1, 3, MHelper.RANDOM)));
		}
		return Lists.newArrayList(new ItemStack(this));
	}
}
