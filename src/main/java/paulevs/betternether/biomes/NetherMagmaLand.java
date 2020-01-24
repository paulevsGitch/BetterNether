package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.structures.StructureGeyser;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureGoldenVine;
import paulevs.betternether.structures.plants.StructureMagmaFlower;

public class NetherMagmaLand extends NetherBiome
{
	private static final OpenSimplexNoise SURFACE = new OpenSimplexNoise(2315);
	private static final Mutable POS = new Mutable();
	
	public NetherMagmaLand(String name)
	{
		super(name);
		addStructure("geyser", new StructureGeyser(), StructureType.FLOOR, 0.1F, false);
		addStructure("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR, 0.4F, false);
		addStructure("golden_vine", new StructureGoldenVine(), StructureType.CEIL, 0.2F, true);
	}
	
	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		double noise = rigidNoise(pos);
		if (noise < 0.15)
		{
			POS.set(pos);
			for (int y = 0; y < random.nextInt(3) + 1; y++)
				if (random.nextInt(4) == 0)
				{
					POS.setY(POS.getY() - y);
					if (validWall(world, POS.down()) && validWall(world, POS.north()) && validWall(world, POS.south()) && validWall(world, POS.east()) && validWall(world, POS.west()))
						BlocksHelper.setWithoutUpdate(world, POS, Blocks.LAVA.getDefaultState());
					else
						BlocksHelper.setWithoutUpdate(world, POS, Blocks.MAGMA_BLOCK.getDefaultState());
				}
				else
					BlocksHelper.setWithoutUpdate(world, POS, Blocks.MAGMA_BLOCK.getDefaultState());
		}
		else if (random.nextBoolean() && random.nextDouble() > noise * 2)
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.MAGMA_BLOCK.getDefaultState());
	}
	
	private double rigidNoise(BlockPos pos)
	{
		return Math.abs(SURFACE.eval(pos.getX() * 0.1, pos.getY() * 0.1, pos.getZ() * 0.1));
	}
	
	protected boolean validWall(IWorld world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		return BlocksHelper.isLava(state) || BlocksHelper.isNetherGroundMagma(state);
	}
}
