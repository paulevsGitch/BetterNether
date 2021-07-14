package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureNetherSakuraBush implements IStructure {
	private static final MutableBlockPos POS = new MutableBlockPos();

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (!world.isEmptyBlock(pos) || !world.isEmptyBlock(pos.above()) || !world.isEmptyBlock(pos.above(15)))
			return;

		float r = random.nextFloat() * 1.5F + 0.5F;
		int count = (int) r;

		for (int i = 0; i < count; i++) {
			float fr = r - i;
			int ir = (int) Math.ceil(fr);
			float r2 = fr * fr;

			int x1 = pos.getX() - ir;
			int x2 = pos.getX() + ir;
			int z1 = pos.getZ() - ir;
			int z2 = pos.getZ() + ir;

			POS.setY(pos.getY() + i);

			for (int x = x1; x < x2; x++) {
				POS.setX(x);
				int sqx = x - pos.getX();
				sqx *= sqx;
				for (int z = z1; z < z2; z++) {
					int sqz = z - pos.getZ();
					sqz *= sqz;
					POS.setZ(z);
					if (sqx + sqz < r2 + random.nextFloat() * r) {
						setIfAir(world, POS, BlocksRegistry.NETHER_SAKURA_LEAVES.defaultBlockState());
					}
				}
			}
		}

		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.NETHER_SAKURA.bark.defaultBlockState());
		setIfAir(world, pos.above(), BlocksRegistry.NETHER_SAKURA_LEAVES.defaultBlockState());
		setIfAir(world, pos.north(), BlocksRegistry.NETHER_SAKURA_LEAVES.defaultBlockState());
		setIfAir(world, pos.south(), BlocksRegistry.NETHER_SAKURA_LEAVES.defaultBlockState());
		setIfAir(world, pos.east(), BlocksRegistry.NETHER_SAKURA_LEAVES.defaultBlockState());
		setIfAir(world, pos.west(), BlocksRegistry.NETHER_SAKURA_LEAVES.defaultBlockState());
	}

	private void setIfAir(LevelAccessor world, BlockPos pos, BlockState state) {
		if (world.getBlockState(pos).getMaterial().isReplaceable())
			BlocksHelper.setWithoutUpdate(world, pos, state);
	}
}