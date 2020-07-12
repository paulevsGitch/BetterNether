package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureEye implements IStructure
{
	@Override
	public void generate(WorldAccess world, BlockPos pos, Random random)
	{
		int h = random.nextInt(19) + 5;
		int h2 = BlocksHelper.downRay(world, pos, h);
		
		if (h2 < 5)
			return;
		
		h2 -= 1;
		
		BlockState vineState = BlocksRegistry.EYE_VINE.getDefaultState();
		BlockState eyeState = random.nextBoolean() ? BlocksRegistry.EYEBALL.getDefaultState() : BlocksRegistry.EYEBALL_SMALL.getDefaultState();
		
		for (int y = 0; y < h2; y++)
			BlocksHelper.setWithoutUpdate(world, pos.down(y), vineState);
		
		BlocksHelper.setWithoutUpdate(world, pos.down(h2), eyeState);
	}
}