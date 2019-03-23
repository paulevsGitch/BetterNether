package paulevs.betternether.blocks;

import net.minecraft.util.math.AxisAlignedBB;

public class BlockRedMold extends BlockMold
{
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.5, 0.875);

	public BlockRedMold()
	{
		super("red_mold");
	}
}
