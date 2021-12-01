package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;

public class NetherSwamplandTerracesData extends NetherSwamplandData {
	public NetherSwamplandTerracesData(String name) {
		super(name);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		if (validWall(world, pos.below()) && validWall(world, pos.north()) && validWall(world, pos.south()) && validWall(world, pos.east()) && validWall(world, pos.west())) {
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.LAVA.defaultBlockState());
		}
		else {
			double value = TERRAIN.eval(pos.getX() * 0.2, pos.getY() * 0.2, pos.getZ() * 0.2);
			if (value > -0.3)
				BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.SWAMPLAND_GRASS.defaultBlockState());
			else {
				value = TERRAIN.eval(pos.getX() * 0.5, pos.getZ() * 0.5);
				BlocksHelper.setWithoutUpdate(world, pos, value > 0 ? Blocks.SOUL_SAND.defaultBlockState() : Blocks.SOUL_SOIL.defaultBlockState());
			}
		}
	}
}
