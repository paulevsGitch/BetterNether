package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.biomes.NetherWartForest;
import paulevs.betternether.biomes.NetherWartForestEdge;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.BlocksRegistry;

public class StructurePath implements IStructure
{
	private static final Mutable B_POS = new Mutable();
	private OpenSimplexNoise heightNoise;
	private OpenSimplexNoise rigidNoise;
	private OpenSimplexNoise distortX;
	private OpenSimplexNoise distortY;
	
	public StructurePath(long seed)
	{
		Random random = new Random(seed);
		heightNoise = new OpenSimplexNoise(random.nextLong());
		rigidNoise = new OpenSimplexNoise(random.nextLong());
		distortX = new OpenSimplexNoise(random.nextLong());
		distortY = new OpenSimplexNoise(random.nextLong());
	}
	
	@Override
	public void generate(WorldAccess world, BlockPos pos, Random random)
	{
		for (int x = 0; x < 16; x++)
		{
			int wx = pos.getX() + x;
			B_POS.setX(wx);
			for (int z = 0; z < 16; z++)
			{
				int wz = pos.getZ() + z;
				B_POS.setZ(wz);
				double rigid = getRigid(wx, wz) + MHelper.randRange(0, 0.015F, random);
				
				if (rigid < 0.015)
				{
					int height = getHeight(wx, wz);
					B_POS.setY(height);
					height -= BlocksHelper.downRay(world, B_POS, height);
					B_POS.setY(height);
					if (world.isAir(B_POS) && world.getBlockState(B_POS.move(Direction.DOWN)).isFullCube(world, B_POS) && isHeightValid(world, B_POS.up()))
					{
						Biome biome = world.getBiome(B_POS);
						BlocksHelper.setWithoutUpdate(world, B_POS, getRoadMaterial(world, B_POS, biome));
						if (needsSlab(world, B_POS.up()))
							BlocksHelper.setWithoutUpdate(world, B_POS.up(), getSlabMaterial(world, B_POS, biome));
						else if (rigid > 0.01 && ((x & 3) == 0) && ((z & 3) == 0) && random.nextInt(8) == 0)
							makeLantern(world, B_POS.up());
					}
				}
			}
		}
	}
	
	private int getHeight(int x, int z)
	{
		return (int) (heightNoise.eval(x * 0.001, z * 0.001) * 32 + 64);
	}

	private double getRigid(double x, double z)
	{
		x *= 0.1;
		z *= 0.1;
		return Math.abs(rigidNoise.eval(
				x * 0.02 + distortX.eval(x * 0.05, z * 0.05) * 0.2,
				z * 0.02 + distortY.eval(x * 0.05, z * 0.05) * 0.2)
				);
	}
	
	private boolean isHeightValid(WorldAccess world, BlockPos pos)
	{
		return  Math.abs(BlocksHelper.downRay(world, pos.north(2), 5) - BlocksHelper.downRay(world, pos.south(2), 5)) < 3 &&
				Math.abs(BlocksHelper.downRay(world, pos.east(2), 5) - BlocksHelper.downRay(world, pos.west(2), 5)) < 3;
	}
	
	private void makeLantern(WorldAccess world, BlockPos pos)
	{
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.NETHER_BRICK_WALL.getDefaultState());
		BlocksHelper.setWithoutUpdate(world, pos.up(), Blocks.NETHER_BRICK_FENCE.getDefaultState());
		BlocksHelper.setWithoutUpdate(world, pos.up(2), Blocks.NETHER_BRICK_FENCE.getDefaultState());
		Direction dir = Direction.NORTH;
		double d = 1000;
		double v = getRigid(pos.getX(), pos.getZ());
		for (Direction face: BlocksHelper.HORIZONTAL)
		{
			BlockPos p = pos.offset(face);
			double v2 = getRigid(p.getX(), p.getZ());
			double d2 = v - v2;
			if (d2 < d)
			{
				d = d2;
				dir = face;
			}
		}
		
		BlockPos p = pos.up(3);
		BlocksHelper.setWithoutUpdate(world, p, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		world.getChunk(p).markBlockForPostProcessing(new BlockPos(p.getX() & 15, p.getY(), p.getZ() & 15));
		p = p.offset(dir.getOpposite());
		BlocksHelper.setWithoutUpdate(world, p, Blocks.NETHER_BRICK_FENCE.getDefaultState());
		world.getChunk(p).markBlockForPostProcessing(new BlockPos(p.getX() & 15, p.getY(), p.getZ() & 15));
		BlocksHelper.setWithoutUpdate(world, p.down(), Blocks.LANTERN.getDefaultState().with(LanternBlock.HANGING, true));
	}
	
	private BlockState getRoadMaterial(WorldAccess world, BlockPos pos, Biome biome)
	{
		if (biome == Biomes.SOUL_SAND_VALLEY || biome instanceof NetherWartForest || biome instanceof NetherWartForestEdge)
		{
			return BlocksRegistry.SOUL_SANDSTONE.getDefaultState();
		}
		return Blocks.BASALT.getDefaultState();
	}
	
	private BlockState getSlabMaterial(WorldAccess world, BlockPos pos, Biome biome)
	{
		if (biome == Biomes.SOUL_SAND_VALLEY || biome instanceof NetherWartForest || biome instanceof NetherWartForestEdge)
		{
			return BlocksRegistry.SOUL_SANDSTONE_SLAB.getDefaultState();
		}
		return Blocks.COBBLESTONE_SLAB.getDefaultState();
	}
	
	private boolean needsSlab(WorldAccess world, BlockPos pos)
	{
		BlockState state;
		for (Direction dir: BlocksHelper.HORIZONTAL)
		{
			if ((BlocksHelper.isNetherGround(state = world.getBlockState(pos.offset(dir))) ||
				 state.getBlock() == Blocks.BASALT || state.getBlock() == BlocksRegistry.SOUL_SANDSTONE) &&
				 !world.getBlockState(pos.down().offset(dir.getOpposite())).isAir())
				return true;
		}
		return false;
	}
}