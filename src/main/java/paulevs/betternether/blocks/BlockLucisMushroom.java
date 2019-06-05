package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLucisMushroom extends Block
{
	public static final PropertyEnum<BlockLucisMushroom.EnumDir> FACING = PropertyEnum.<BlockLucisMushroom.EnumDir>create("facing", EnumDir.class);
	public static final PropertyEnum<BlockLucisMushroom.EnumShape> SHAPE = PropertyEnum.<BlockLucisMushroom.EnumShape>create("shape", BlockLucisMushroom.EnumShape.class);
	private static final AxisAlignedBB MUSHROOM_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.5625, 1);

	public BlockLucisMushroom()
	{
		super(Material.WOOD, MapColor.YELLOW);
		this.setRegistryName("lucis_mushroom");
		this.setUnlocalizedName("lucis_mushroom");
		this.setHardness(1.0F);
		this.setLightLevel(1F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumDir.NORTH).withProperty(SHAPE, BlockLucisMushroom.EnumShape.CENTER));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.GLOWSTONE_DUST;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(3);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(BlocksRegister.BLOCK_LUCIS_SPORE);
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote && worldIn.rand.nextBoolean())
			spawnSeeds(worldIn, pos);
		worldIn.destroyBlock(pos, true);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		if (!worldIn.isRemote && worldIn.rand.nextInt(4) == 0)
			spawnSeeds(worldIn, pos);
		worldIn.destroyBlock(pos, true);
	}

	private void spawnSeeds(World world, BlockPos pos)
	{
		ItemStack drop = new ItemStack(BlocksRegister.BLOCK_LUCIS_SPORE, 1);
		EntityItem itemEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
		world.spawnEntity(itemEntity);
	}

	public static enum EnumShape implements IStringSerializable
	{
		CORNER(0, "corner"),
		SIDE(1, "side"),
		CENTER(2, "center");

		private final String name;
		private final int index;

		private EnumShape(int index, String name)
		{
			this.name = name;
			this.index = index;
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

	public static enum EnumDir implements IStringSerializable
	{
		NORTH(0, "north"),
		SOUTH(1, "south"),
		EAST(2, "east"),
		WEST(3, "west");

		private final String name;
		private final int index;

		private EnumDir(int index, String name)
		{
			this.name = name;
			this.index = index;
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

	//public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	//{
	//	updateState(worldIn, pos, state);
	//}

	private void updateState(World worldIn, BlockPos pos, IBlockState state)
	{
		boolean north = worldIn.getBlockState(pos.north()).getBlock() == this;
		boolean south = worldIn.getBlockState(pos.south()).getBlock() == this;
		boolean east = worldIn.getBlockState(pos.east()).getBlock() == this;
		boolean west = worldIn.getBlockState(pos.west()).getBlock() == this;
		if (north && south && east && west)
			worldIn.setBlockState(pos, state.withProperty(SHAPE, BlockLucisMushroom.EnumShape.CENTER));
		else if (north && south)
		{
			EnumDir dir = null;
			if (east)
				dir = EnumDir.WEST;
			else
				dir = EnumDir.EAST;
			worldIn.setBlockState(pos, state.withProperty(SHAPE, BlockLucisMushroom.EnumShape.SIDE).withProperty(FACING, dir));
		}
		else if (east && west)
		{
			EnumDir dir = null;
			if (north)
				dir = EnumDir.SOUTH;
			else
				dir = EnumDir.NORTH;
			worldIn.setBlockState(pos, state.withProperty(SHAPE, BlockLucisMushroom.EnumShape.SIDE).withProperty(FACING, dir));
		}
		else
		{
			EnumDir dir = EnumDir.NORTH;
			if (north)
				dir = east ? EnumDir.NORTH : EnumDir.WEST;
			else if (south)
				dir = west ? EnumDir.SOUTH : EnumDir.EAST;
			worldIn.setBlockState(pos, state.withProperty(SHAPE, BlockLucisMushroom.EnumShape.CORNER).withProperty(FACING, dir));
		}
	}

	public IBlockState getStateFromMeta(int meta)
	{
		int type = meta >> 2;
		int face = meta & 3;
		EnumDir direc = null;
		EnumShape shape = null;
		switch(type)
		{
		case 0:
			shape = EnumShape.CORNER;
			break;
		case 1:
			shape = EnumShape.SIDE;
			break;
		case 2:
			shape = EnumShape.CENTER;
			break;
		}
		switch(face)
		{
		case 0:
			direc = EnumDir.NORTH;
			break;
		case 1:
			direc = EnumDir.SOUTH;
			break;
		case 2:
			direc = EnumDir.EAST;
			break;
		case 3:
			direc = EnumDir.WEST;
			break;
		}
		return this.getDefaultState().withProperty(FACING, direc).withProperty(SHAPE, shape);
	}

	public int getMetaFromState(IBlockState state)
	{
		return (((EnumDir)state.getValue(FACING)).getIndex()) | (state.getValue(SHAPE).getIndex() << 2);
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING, SHAPE});
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return MUSHROOM_AABB;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		boolean central = state.getValue(SHAPE) == EnumShape.CENTER;
		if (face == EnumFacing.DOWN)
			return central ? BlockFaceShape.SOLID : BlockFaceShape.CENTER_BIG;
		else
			return BlockFaceShape.CENTER_BIG;
	}
}
