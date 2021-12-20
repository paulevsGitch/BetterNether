package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureSoulVein implements IStructure {
	private boolean canPlaceAt(LevelAccessor world, BlockPos pos) {
		return NetherBlocks.SOUL_VEIN.canSurvive(NetherBlocks.SOUL_VEIN.defaultBlockState(), world, pos);
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (world.isEmptyBlock(pos) && canPlaceAt(world, pos)) {
			BlockState state = NetherBlocks.SOUL_VEIN.defaultBlockState();
			BlockState sand = NetherBlocks.VEINED_SAND.defaultBlockState();
			int x1 = pos.getX() - 1;
			int x2 = pos.getX() + 1;
			int z1 = pos.getZ() - 1;
			int z2 = pos.getZ() + 1;
			for (int x = x1; x <= x2; x++)
				for (int z = z1; z <= z2; z++) {
					int y = pos.getY() + 2;
					for (int j = 0; j < 4; j++) {
						context.POS.set(x, y - j, z);
						if (world.isEmptyBlock(context.POS) && canPlaceAt(world, context.POS)) {
							BlocksHelper.setWithoutUpdate(world, context.POS, state);
							BlocksHelper.setWithoutUpdate(world, context.POS.below(), sand);
						}
					}
				}
		}
	}
}