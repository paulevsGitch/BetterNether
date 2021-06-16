package paulevs.betternether.biomes;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;

import java.util.Random;

public class NetherSwamplandTerraces extends NetherSwampland {
	public NetherSwamplandTerraces(String name) {
		super(name);
	}

	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random) {
		if (validWall(world, pos.down()) && validWall(world, pos.north()) && validWall(world, pos.south()) && validWall(world, pos.east()) && validWall(world, pos.west())) {
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.LAVA.getDefaultState());
		}
		else {
			double value = TERRAIN.eval(pos.getX() * 0.2, pos.getY() * 0.2, pos.getZ() * 0.2);
			if (value > -0.3)
				BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.SWAMPLAND_GRASS.getDefaultState());
			else {
				value = TERRAIN.eval(pos.getX() * 0.5, pos.getZ() * 0.5);
				BlocksHelper.setWithoutUpdate(world, pos, value > 0 ? Blocks.SOUL_SAND.getDefaultState() : Blocks.SOUL_SOIL.getDefaultState());
			}
		}
	}
}
