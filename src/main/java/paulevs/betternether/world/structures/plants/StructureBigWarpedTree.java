package paulevs.betternether.world.structures.plants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.structures.StructureObjScatter;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.StructureWorld;

public class StructureBigWarpedTree extends StructureObjScatter {
	private static final StructureWorld[] TREES = new StructureWorld[] {
			new StructureWorld("trees/warped_tree_01", -2, StructureType.FLOOR),
			new StructureWorld("trees/warped_tree_02", -2, StructureType.FLOOR),
			new StructureWorld("trees/warped_tree_03", -2, StructureType.FLOOR),
			new StructureWorld("trees/warped_tree_04", -2, StructureType.FLOOR),
			new StructureWorld("trees/warped_tree_05", -4, StructureType.FLOOR)
	};

	public StructureBigWarpedTree() {
		super(10, TREES);
	}

	protected boolean isGround(BlockState state) {
		return BlocksHelper.isNetherGround(state);
	}

	protected boolean isStructure(BlockState state) {
		return state.getBlock() == Blocks.WARPED_STEM ||
				state.getBlock() == Blocks.WARPED_WART_BLOCK ||
				state.getBlock() == Blocks.SHROOMLIGHT;
	}
}
