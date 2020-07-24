package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockPottedPlant extends BlockBaseNotFull
{
	public static final EnumProperty<PottedPlantShape> PLANT = EnumProperty.of("plant", PottedPlantShape.class);
	
	public BlockPottedPlant()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.BLACK)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.lightLevel(getLuminance()));
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(PLANT, PottedPlantShape.AGAVE));
	}
	
	private static ToIntFunction<BlockState> getLuminance()
	{
		return (blockState) -> {
			if (blockState.get(PLANT) == PottedPlantShape.WILLOW)
				return 12;
			else if (blockState.get(PLANT) == PottedPlantShape.JELLYFISH_MUSHROOM)
				return 13;
			else
				return 0;
		};
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		Block block = state.get(PLANT).getBlock();
		Vec3d vec3d = block.getDefaultState().getModelOffset(view, pos);
		return block.getOutlineShape(block.getDefaultState(), view, pos, ePos).offset(-vec3d.x, -0.5 - vec3d.y, -vec3d.z);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(PLANT);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return world.getBlockState(pos.down()).getBlock() instanceof BlockBNPot;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		Block block = state.get(PLANT).getBlock();
		return Collections.singletonList(new ItemStack(block.asItem()));
	}

	public static BlockState getPlant(Item item)
	{
		for (PottedPlantShape shape: PottedPlantShape.values())
		{
			if (shape.getItem().equals(item))
				return BlocksRegistry.POTTED_PLANT.getDefaultState().with(PLANT, shape);
		}
		return null;
	}
	
	public static enum PottedPlantShape implements StringIdentifiable
	{
		AGAVE("agave", BlocksRegistry.AGAVE),
		BARREL_CACTUS("barrel_cactus", BlocksRegistry.BARREL_CACTUS),
		BLACK_APPLE("black_apple", BlocksRegistry.BLACK_APPLE_SEED),
		BLACK_BUSH("black_bush", BlocksRegistry.BLACK_BUSH),
		EGG_PLANT("egg_plant", BlocksRegistry.EGG_PLANT),
		INK_BUSH("ink_bush", BlocksRegistry.INK_BUSH_SEED),
		REEDS("reeds", BlocksRegistry.NETHER_REED),
		NETHER_CACTUS("nether_cactus", BlocksRegistry.NETHER_CACTUS),
		NETHER_GRASS("nether_grass", BlocksRegistry.NETHER_GRASS),
		ORANGE_MUSHROOM("orange_mushroom", BlocksRegistry.ORANGE_MUSHROOM),
		RED_MOLD("red_mold", BlocksRegistry.RED_MOLD),
		GRAY_MOLD("gray_mold", BlocksRegistry.GRAY_MOLD),
		MAGMA_FLOWER("magma_flower", BlocksRegistry.MAGMA_FLOWER),
		NETHER_WART("nether_wart", BlocksRegistry.WART_SEED),
		WILLOW("willow", BlocksRegistry.WILLOW_SAPLING),
		SMOKER("smoker", BlocksRegistry.SMOKER),
		WART("wart", Blocks.NETHER_WART),
		JUNGLE_PLANT("jungle_plant", BlocksRegistry.JUNGLE_PLANT),
		JELLYFISH_MUSHROOM("jellyfish_mushroom", BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING),
		SWAMP_GRASS("swamp_grass", BlocksRegistry.SWAMP_GRASS),
		SOUL_GRASS("soul_grass", BlocksRegistry.SOUL_GRASS),
		BONE_GRASS("bone_grass", BlocksRegistry.BONE_GRASS),
		BONE_MUSHROOM("bone_mushroom", BlocksRegistry.BONE_MUSHROOM);
		
		private final Block block;
		private final String name;

		private PottedPlantShape(String name, Block block)
		{
			this.name = name;
			this.block = block;
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
		
		public Item getItem()
		{
			return block.asItem();
		}
		
		public Block getBlock()
		{
			return block;
		}
	}
}
