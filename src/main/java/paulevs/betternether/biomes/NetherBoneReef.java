package paulevs.betternether.biomes;

public class NetherBoneReef extends NetherGrasslands
{
	/*private static StructureNBT[] bones = new StructureNBT[]{
		new StructureNBT("bone_01"),
		new StructureNBT("bone_02"),
		new StructureNBT("bone_03")
	};*/
	
	public NetherBoneReef(String name)
	{
		super(name);
	}
	
	/*@Override
	public void genFloorObjects(World chunk, BlockPos pos, Random random)
	{
		Block ground = chunk.getBlockState(pos).getBlock();
		if (random.nextFloat() <= plantDensity)
		{
			if (ground instanceof BlockNetherrack || ground == Blocks.SOUL_SAND)
			{
				if (random.nextInt(20) == 0)
					bones[random.nextInt(bones.length)].generateCentered(
							chunk,
							pos.down(random.nextInt(4)),
							random
							);
				else if (BlocksRegister.BLOCK_NETHER_GRASS != Blocks.AIR && random.nextInt(4) != 0)
					chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_NETHER_GRASS.getDefaultState());
			}
			else if (ground instanceof BlockBone || ground instanceof BNBlockBone)
				if (BlocksRegister.BLOCK_BONE_MUSHROOM != Blocks.AIR && random.nextBoolean())
					chunk.setBlockState(pos.up(), BlocksRegister
							.BLOCK_BONE_MUSHROOM
							.getDefaultState()
							.withProperty(BlockBoneMushroom.AGE, random.nextInt(3)));
		}
	}*/
	
	/*@Override
	public void genWallObjects(World world, BlockPos origin, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && BlocksRegister.BLOCK_BONE_MUSHROOM != Blocks.AIR && random.nextBoolean() &&
				(world.getBlockState(origin).getBlock() == Blocks.BONE_BLOCK || world.getBlockState(origin).getBlock() == BlocksRegister.BLOCK_BONE))
		{
			BlockPos dir = pos.subtract(origin);
			EnumFacing facing = EnumFacing.getFacingFromVector(dir.getX(), dir.getY(), dir.getZ());
			world.setBlockState(pos, BlocksRegister
					.BLOCK_BONE_MUSHROOM
					.getDefaultState()
					.withProperty(BlockBoneMushroom.FACING, facing)
					.withProperty(BlockBoneMushroom.AGE, random.nextInt(3)));
		}
	}*/
}
