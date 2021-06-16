package paulevs.betternether.structures.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

import java.util.Random;

public class StructureVanillaMushroom implements IStructure {
	private Mutable npos = new Mutable();

	private boolean canPlaceAt(WorldAccess world, BlockPos pos) {
		return world.getBlockState(pos.down()).getBlock() == BlocksRegistry.NETHER_MYCELIUM;
	}

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (canPlaceAt(world, pos)) {
			BlockState state = random.nextBoolean() ? Blocks.RED_MUSHROOM.getDefaultState() : Blocks.BROWN_MUSHROOM.getDefaultState();
			for (int i = 0; i < 16; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 4);
				int z = pos.getZ() + (int) (random.nextGaussian() * 4);
				int y = pos.getY() + random.nextInt(8);
				for (int j = 0; j < 8; j++) {
					npos.set(x, y - j, z);
					if (world.isAir(npos) && canPlaceAt(world, npos)) {
						BlocksHelper.setWithoutUpdate(world, npos, state);
					}
				}
			}
		}
	}
}
