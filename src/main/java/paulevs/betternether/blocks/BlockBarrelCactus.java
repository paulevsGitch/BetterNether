package paulevs.betternether.blocks;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.MHelper;
import ru.bclib.interfaces.tools.AddMineableShears;
import ru.bclib.interfaces.tools.AddMineableHoe;

import java.util.List;

public class BlockBarrelCactus extends BlockCommonPlant implements AddMineableShears, AddMineableHoe {
	private static final VoxelShape EMPTY = Block.box(0, 0, 0, 0, 0, 0);
	private static final VoxelShape[] SHAPES = new VoxelShape[] {
			Block.box(5, 0, 5, 11, 5, 11),
			Block.box(3, 0, 3, 13, 9, 13),
			Block.box(2, 0, 2, 14, 12, 14),
			Block.box(1, 0, 1, 15, 14, 15)
	};

	public BlockBarrelCactus() {
		super(FabricBlockSettings.of(Material.CACTUS)
				.mapColor(MaterialColor.TERRACOTTA_BLUE)
				.requiresTool()
				.sounds(SoundType.WOOL)
				.nonOpaque()
				.hardness(0.4F)
				.ticksRandomly()

				.dynamicShape()
        );
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		Block down = world.getBlockState(pos.below()).getBlock();
		return down == Blocks.GRAVEL;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (canSurvive(state, world, pos))
			return state;
		else
			return Blocks.AIR.defaultBlockState();
	}

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (state.getValue(BlockCommonPlant.AGE) > 1) entity.hurt(DamageSource.CACTUS, 1.0F);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		Vec3 vec3d = state.getOffset(view, pos);
		return SHAPES[state.getValue(BlockCommonPlant.AGE)].move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		if (state.getValue(BlockCommonPlant.AGE) < 2) return EMPTY;
		Vec3 vec3d = state.getOffset(view, pos);
		return SHAPES[state.getValue(BlockCommonPlant.AGE)].move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XYZ;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (state.getValue(BlockCommonPlant.AGE) == 3) {
			return Lists.newArrayList(new ItemStack(this, MHelper.randRange(1, 3, MHelper.RANDOM)));
		}
		return Lists.newArrayList(new ItemStack(this));
	}
}
