package paulevs.betternether.structures.plants;

import paulevs.betternether.registry.BlocksRegistry;

public class StructureMossCover extends StructureScatter
{
	public StructureMossCover()
	{
		super(BlocksRegistry.MOSS_COVER);
	}
	
	/*@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (pos.getY() > 61) super.generate(world, pos, random);
	}*/
}
