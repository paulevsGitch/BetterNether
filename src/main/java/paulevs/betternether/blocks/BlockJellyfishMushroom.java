package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockProperties.JellyShape;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class BlockJellyfishMushroom extends BlockBaseNotFull {
	private static final VoxelShape TOP_SHAPE = Block.box(1, 0, 1, 15, 16, 15);
	private static final VoxelShape MIDDLE_SHAPE = Block.box(5, 0, 5, 11, 16, 11);
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	public static final EnumProperty<JellyShape> VISUAL = BlockProperties.JELLY_MUSHROOM_VISUAL;

	public BlockJellyfishMushroom() {
		super(Materials.makeWood(MaterialColor.COLOR_CYAN).luminance(13).hardness(0.1F).sounds(SoundType.FUNGUS).nonOpaque());
		boolean sodium = FabricLoader.getInstance().isModLoaded("sodium");
		this.setRenderLayer(sodium ? BNRenderLayer.CUTOUT : BNRenderLayer.TRANSLUCENT);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE, VISUAL);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return state.getValue(SHAPE) == TripleShape.TOP ? TOP_SHAPE : MIDDLE_SHAPE;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING);
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
		switch (state.getValue(SHAPE)) {
			case BOTTOM:
				return world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP) ? state : Blocks.AIR.defaultBlockState();
			case MIDDLE:
				return world.getBlockState(pos.above()).getBlock() == this && world.getBlockState(pos.below()).isFaceSturdy(world, pos.below(), Direction.UP) ? state : Blocks.AIR.defaultBlockState();
			case TOP:
			default:
				return world.getBlockState(pos.below()).getBlock() == this ? state : Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (world.getBlockState(pos).getValue(SHAPE) != TripleShape.TOP)
			return;
		if (entity.isSuppressingBounce())
			super.fallOn(world, state, pos, entity, fallDistance);
		else
			entity.causeFallDamage(fallDistance, 0.0F, DamageSource.FALL);
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter world, Entity entity) {
		if (entity.isSuppressingBounce())
			super.updateEntityAfterFallOn(world, entity);
		else
			this.bounce(entity);
	}

	private void bounce(Entity entity) {
		Vec3 vec3d = entity.getDeltaMovement();
		if (vec3d.y < 0.0D) {
			double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
			entity.setDeltaMovement(vec3d.x, -vec3d.y * d, vec3d.z);
		}
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		if (world.getBlockState(pos).getValue(SHAPE) != TripleShape.TOP) {
			super.stepOn(world, pos, state, entity);
			return;
		}

		double d = Math.abs(entity.getDeltaMovement().y);
		if (d < 0.1D && !entity.isSteppingCarefully()) {
			double e = 0.4D + d * 0.2D;
			entity.setDeltaMovement(entity.getDeltaMovement().multiply(e, 1.0D, e));
		}
		super.stepOn(world, pos, state, entity);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (state.getValue(SHAPE) == TripleShape.TOP) {
			return Lists.newArrayList(new ItemStack(BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING, MHelper.randRange(1, 2, MHelper.RANDOM)),
					new ItemStack(ItemsRegistry.GLOWSTONE_PILE, MHelper.randRange(0, 2, MHelper.RANDOM)),
					new ItemStack(Items.SLIME_BALL, MHelper.randRange(0, 1, MHelper.RANDOM)));
		}
		else if (state.getValue(SHAPE) == TripleShape.BOTTOM)
			return Lists.newArrayList(new ItemStack(BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING, MHelper.randRange(1, 2, MHelper.RANDOM)));
		else
			return Lists.newArrayList(new ItemStack(BlocksRegistry.MUSHROOM_STEM));
	}
}