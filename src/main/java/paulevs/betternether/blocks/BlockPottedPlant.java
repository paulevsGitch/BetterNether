package paulevs.betternether.blocks;

import java.util.List;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;
import paulevs.betternether.registers.BlocksRegister;

public class BlockPottedPlant extends BlockBaseNotFull
{
	//private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 8, 14);
	public static final EnumProperty<PottedPlantShape> PLANT = EnumProperty.of("plant", PottedPlantShape.class);
	
	public BlockPottedPlant()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.BLACK)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.build());
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(PLANT, PottedPlantShape.AGAVE));
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		Block block = state.get(PLANT).getBlock();
		Vec3d vec3d = block.getDefaultState().getOffsetPos(view, pos);
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
		return world.getBlockState(pos.down()).getBlock() == BlocksRegister.CINCINNASITE_POT;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
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
		return block.getDroppedStacks(block.getDefaultState(), builder);
	}

	public static BlockState getPlant(Item item)
	{
		for (PottedPlantShape shape: PottedPlantShape.values())
		{
			if (shape.getItem().equals(item))
				return BlocksRegister.POTTED_PLANT.getDefaultState().with(PLANT, shape);
		}
		return null;
	}
	
	public static enum PottedPlantShape implements StringIdentifiable
	{
		AGAVE(BlocksRegister.AGAVE),
		BARREL_CACTUS(BlocksRegister.BARREL_CACTUS),
		BLACK_APPLE(BlocksRegister.BLACK_APPLE_SEED),
		BLACK_BUSH(BlocksRegister.BLACK_BUSH),
		EGG_PLANT(BlocksRegister.EGG_PLANT),
		INK_BUSH(BlocksRegister.INK_BUSH_SEED),
		REEDS(BlocksRegister.NETHER_REED),
		NETHER_CACTUS(BlocksRegister.NETHER_CACTUS),
		NETHER_GRASS(BlocksRegister.NETHER_GRASS),
		ORANGE_MUSHROOM(BlocksRegister.ORANGE_MUSHROOM),
		RED_MOLD(BlocksRegister.RED_MOLD),
		GRAY_MOLD(BlocksRegister.GRAY_MOLD),
		MAGMA_FLOWER(BlocksRegister.MAGMA_FLOWER),
		NETHER_WART(BlocksRegister.WART_SEED);
		
		private final Block block;

		private PottedPlantShape(Block block)
		{
			this.block = block;
		}
		
		@Override
		public String asString()
		{
			return this.toString().toLowerCase();
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
