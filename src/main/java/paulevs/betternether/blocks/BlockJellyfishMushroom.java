package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class BlockJellyfishMushroom extends BlockBaseNotFull
{
	private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);
	private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);
	public static final EnumProperty<JellyShape> VISUAL = EnumProperty.of("visual", JellyShape.class);
	
	public BlockJellyfishMushroom()
	{
		super(Materials.makeWood(MaterialColor.CYAN).hardness(0.1F).sounds(BlockSoundGroup.FUNGUS).nonOpaque().lightLevel(13));
		boolean sodium = FabricLoader.getInstance().isModLoaded("sodium"); // Fix incorrect sodium render
		this.setRenderLayer(sodium ? BNRenderLayer.CUTOUT : BNRenderLayer.TRANSLUCENT);
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE, VISUAL);
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return state.get(SHAPE) == TripleShape.TOP ? TOP_SHAPE : MIDDLE_SHAPE;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		return new ItemStack(BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING);
	}
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		switch (state.get(SHAPE))
		{
		case BOTTOM:
			return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) ? state : Blocks.AIR.getDefaultState();
		case MIDDLE:
			return world.getBlockState(pos.up()).getBlock() == this && world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) ? state : Blocks.AIR.getDefaultState();
		case TOP:
		default:
			return world.getBlockState(pos.down()).getBlock() == this ? state : Blocks.AIR.getDefaultState();
		}
	}
	
	@Override
	public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance)
	{
		if (world.getBlockState(pos).get(SHAPE) != TripleShape.TOP)
			return;
		if (entity.bypassesLandingEffects())
			super.onLandedUpon(world, pos, entity, distance);
		else
			entity.handleFallDamage(distance, 0.0F);
	}

	@Override
	public void onEntityLand(BlockView world, Entity entity)
	{
		if (entity.bypassesLandingEffects())
			super.onEntityLand(world, entity);
		else
			this.bounce(entity);
	}

	private void bounce(Entity entity)
	{
		Vec3d vec3d = entity.getVelocity();
		if (vec3d.y < 0.0D)
		{
			double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
			entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
		}
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, Entity entity)
	{
		if (world.getBlockState(pos).get(SHAPE) != TripleShape.TOP)
		{
			super.onSteppedOn(world, pos, entity);
			return;
		}
		
		double d = Math.abs(entity.getVelocity().y);
		if (d < 0.1D && !entity.bypassesSteppingEffects())
		{
			double e = 0.4D + d * 0.2D;
			entity.setVelocity(entity.getVelocity().multiply(e, 1.0D, e));
		}
		super.onSteppedOn(world, pos, entity);
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (state.get(SHAPE) == TripleShape.TOP)
		{
			return Lists.newArrayList(new ItemStack(BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING, MHelper.randRange(1, 2, MHelper.RANDOM)),
									  new ItemStack(ItemsRegistry.GLOWSTONE_PILE, MHelper.randRange(0, 2, MHelper.RANDOM)),
									  new ItemStack(Items.SLIME_BALL, MHelper.randRange(0, 1, MHelper.RANDOM)));
		}
		else if (state.get(SHAPE) == TripleShape.BOTTOM)
			return Lists.newArrayList(new ItemStack(BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING, MHelper.randRange(1, 2, MHelper.RANDOM)));
		else
			return Lists.newArrayList(new ItemStack(BlocksRegistry.MUSHROOM_STEM));
	}
	
	public static enum JellyShape implements StringIdentifiable
	{
		NORMAL("normal"),
		SEPIA("sepia"),
		POOR("poor");
		
		final String name;
		
		JellyShape(String name)
		{
			this.name = name;
		}
		
		@Override
		public String asString()
		{
			return name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}