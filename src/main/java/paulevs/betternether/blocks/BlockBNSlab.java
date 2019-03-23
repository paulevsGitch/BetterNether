package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.BetterNether;

public abstract class BlockBNSlab extends BlockSlab
{
	private Item drop;
	
	public BlockBNSlab(String name, Material material, MapColor color, SoundType sound)
	{
		super(material, color);
		this.setSoundType(sound);
		this.setHardness(2.0F);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		IBlockState state = this.blockState.getBaseState();
		if(!this.isDouble())
		{
			state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
		}
		this.setDefaultState(state);
		this.useNeighborBrightness = !this.isDouble();
	}
	
	@Override
	public String getUnlocalizedName(int meta) 
	{
		return this.getUnlocalizedName();
	}

	@Override
	public IProperty<?> getVariantProperty() 
	{
		return HALF;
	}
	
	@Override
	public Comparable<?> getTypeForItem(ItemStack stack)
	{
		return EnumBlockHalf.BOTTOM;
	}
	
	@Override
	public int damageDropped(IBlockState state) 
	{
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		if(!this.isDouble())
		{
			//System.out.println(meta);
			return this.getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta]);
			//return this.getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta % EnumBlockHalf.values().length]);
		}
		return this.getDefaultState();
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		if(this.isDouble())
		{
			return 0;
		}
		//System.out.println((EnumBlockHalf)state.getValue(HALF));
		return ((EnumBlockHalf) state.getValue(HALF)).equals(EnumBlockHalf.BOTTOM) ? 0 : 1;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		return this.isDouble() ? drop : Item.getItemFromBlock(this);
	}
	
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return this.isDouble() ? new ItemStack(drop) : new ItemStack(this, 1, 0);
    }
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {HALF});
	}
	
	public void setDrop(Block block)
	{
		this.drop = Item.getItemFromBlock(block);
	}
}