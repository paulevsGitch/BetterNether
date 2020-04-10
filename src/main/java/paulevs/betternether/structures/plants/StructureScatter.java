package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.IStructure;

public class StructureScatter implements IStructure
{
	private Mutable npos = new Mutable();
	private final Block plantBlock;
	private final Property<Integer> ageProp;
	private final int maxAge;
	
	
	public StructureScatter(Block plantBlock, Property<Integer> ageProperty, int maxAge)
	{
		this.plantBlock = plantBlock;
		this.ageProp = ageProperty;
		this.maxAge = maxAge;
	}
	
	public StructureScatter(Block plantBlock)
	{
		this.plantBlock = plantBlock;
		this.ageProp = null;
		this.maxAge = 0;
	}
	
	private boolean canPlaceAt(IWorld world, BlockPos pos)
	{
		return plantBlock.canPlaceAt(plantBlock.getDefaultState(), world, pos);
	}
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		if (world.isAir(pos) && canPlaceAt(world, pos))
		{
			BlockState state = plantBlock.getDefaultState();
			int rndState = random.nextInt(2);
			for (int i = 0; i < 20; i++)
			{
				int x = pos.getX() + (int) (random.nextGaussian() * 4);
				int z = pos.getZ() + (int) (random.nextGaussian() * 4);
				if (((x + z + rndState) & 1) == 0)
				{
					if (random.nextBoolean())
					{
						x += random.nextBoolean() ? 1 : -1;
					}
					else
					{
						z += random.nextBoolean() ? 1 : -1;
					}
				}
				int y = pos.getY() + random.nextInt(8);
				for (int j = 0; j < 8; j++)
				{
					npos.set(x, y - j, z);
					if (world.isAir(npos) && canPlaceAt(world, npos))
					{
						if (ageProp != null)
							BlocksHelper.setWithoutUpdate(world, npos, state.with(ageProp, random.nextInt(maxAge)));
						else
							BlocksHelper.setWithoutUpdate(world, npos, state);
						break;
					}
				}
			}
		}
	}
}
