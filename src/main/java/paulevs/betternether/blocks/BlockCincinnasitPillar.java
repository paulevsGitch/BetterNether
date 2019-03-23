package paulevs.betternether.blocks;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCincinnasitPillar extends BlockCincinnasite
{
	public static enum EnumShape implements IStringSerializable
	{
		SMALL("small"),
		TOP("top"),
		MIDDLE("middle"),
		BOTTOM("bottom");

		private final String name;
		private final int index;

		private EnumShape(String name)
		{
			this.name = name;
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
	
	public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.<EnumShape>create("shape", EnumShape.class);
	
	public BlockCincinnasitPillar(String name)
	{
		super(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumShape.SMALL));
	}
	
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {SHAPE});
	}
	
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		boolean up = worldIn.getBlockState(pos.up()).getBlock() == this;
		boolean down = worldIn.getBlockState(pos.down()).getBlock() == this;
		if (up && !down)
			return state.withProperty(SHAPE, EnumShape.BOTTOM);
		else if (!up && down)
			return state.withProperty(SHAPE, EnumShape.TOP);
		else if (up && down)
			return state.withProperty(SHAPE, EnumShape.MIDDLE);
		else
			return state.withProperty(SHAPE, EnumShape.SMALL);
    }
}
