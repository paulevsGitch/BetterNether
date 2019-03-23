package paulevs.betternether.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPottedPlant1 extends Block
{
	public static enum EnumPlant implements IStringSerializable
	{
		AGAVE(BlocksRegister.BLOCK_AGAVE),
		BARREL_CACTUS(BlocksRegister.BLOCK_BARREL_CACTUS),
		BLACK_APPLE(BlocksRegister.BLOCK_BLACK_APPLE_SEED),
		BLACK_BUSH(BlocksRegister.BLOCK_BLACK_BUSH),
		EGG_PLANT(BlocksRegister.BLOCK_EGG_PLANT),
		INK_BUSH(BlocksRegister.BLOCK_INK_BUSH_SEED),
		REEDS(BlocksRegister.BLOCK_NETHER_REED),
		NETHER_CACTUS(BlocksRegister.BLOCK_NETHER_CACTUS),
		NETHER_GRASS(BlocksRegister.BLOCK_NETHER_GRASS),
		ORANGE_MUSHROOM(BlocksRegister.BLOCK_ORANGE_MUSHROOM),
		RED_MOLD(BlocksRegister.BLOCK_RED_MOLD),
		GRAY_MOLD(BlocksRegister.BLOCK_GRAY_MOLD),
		MAGMA_FLOWER(BlocksRegister.BLOCK_MAGMA_FLOWER),
		NETHER_WART(BlocksRegister.BLOCK_WART_SEED);

		private final String name;
		private final int index;
		private Block block;

		private EnumPlant(Block block)
		{
			this.name = Integer.toString(this.ordinal());
			if (block != null)
			{
				this.block = block;
			}
			else
			{
				this.block = Blocks.AIR;
			}
			this.index = this.ordinal();
			
		}

		public String toString()
		{
			return this.name;
		}

		public String getName()
		{
			return this.name;
		}

		public int getIndex()
		{
			return this.index;
		}
	}
	
	public static final PropertyEnum<EnumPlant> PLANT = PropertyEnum.<EnumPlant>create("plant", EnumPlant.class);
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, -0.5, 0.125, 0.875, 0.875, 0.875);
	private static Map<String, EnumPlant> plants;
	
	public BlockPottedPlant1()
	{
		super(Material.PLANTS);
		this.setHardness(0.5F);
		this.setResistance(0.5F);
		this.setSoundType(SoundType.PLANT);
		this.setUnlocalizedName("potted_plant_1");
		this.setRegistryName("potted_plant_1");
		plants = new HashMap<String, EnumPlant>();
		for (EnumPlant plant: EnumPlant.values())
			if (plant.block != null)
				plants.put(plant.block.getUnlocalizedName(), plant);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public int damageDropped(IBlockState state)
    {
        return 0;
    }
	
	public static boolean hasPlant(Block block)
	{
		return block != null && plants != null && plants.containsKey(block.getUnlocalizedName());
	}
	
	public IBlockState getBlockState(Block plant)
	{
		return this.getDefaultState().withProperty(PLANT, plants.get(plant.getUnlocalizedName()));
	}
	
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {PLANT});
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PLANT, EnumPlant.values()[meta]);
	}

	public int getMetaFromState(IBlockState state)
	{
		return ((EnumPlant) state.getValue(PLANT)).getIndex();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}
	
	@Override
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockCincinnasitePot))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(((EnumPlant) state.getValue(PLANT)).block);
    }
	
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
		return new ItemStack(((EnumPlant) state.getValue(PLANT)).block);
    }
}
