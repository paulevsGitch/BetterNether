package paulevs.betternether.structures.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.StructureObjScatter;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.StructureWorld;

public class StructureCrimsonPinewood extends StructureObjScatter
{
	private static final StructureWorld[] TREES = new StructureWorld[] {
			new StructureWorld("trees/crimson_pine_01", -2, StructureType.FLOOR),
			new StructureWorld("trees/crimson_pine_02", -2, StructureType.FLOOR),
			new StructureWorld("trees/crimson_pine_03", -2, StructureType.FLOOR),
			new StructureWorld("trees/crimson_pine_04", -1, StructureType.FLOOR),
			new StructureWorld("trees/crimson_pine_05", -1, StructureType.FLOOR)
		};
	
	public StructureCrimsonPinewood()
	{
		super(7, TREES);
	}
	
	protected boolean isGround(BlockState state)
	{
		return BlocksHelper.isNetherGround(state);
	}
	
	protected boolean isStructure(BlockState state)
	{
		return  state.getBlock() == Blocks.CRIMSON_STEM;/* ||
				state.getBlock() == Blocks.NETHER_WART_BLOCK ||
				state.getBlock() == Blocks.SHROOMLIGHT;*/
	}
}
