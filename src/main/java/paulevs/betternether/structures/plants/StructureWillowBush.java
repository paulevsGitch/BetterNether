package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockNetherGrass;
import paulevs.betternether.blocks.BlockWillowLeaves;
import paulevs.betternether.blocks.complex.WillowMaterial;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IStructure;

public class StructureWillowBush implements IStructure {
	private static final MutableBlockPos POS = new MutableBlockPos();

	public StructureWillowBush() {}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (!world.isEmptyBlock(pos) || !world.isEmptyBlock(pos.above()) || !world.isEmptyBlock(pos.above(15)))
			return;

		float r = random.nextFloat() * 2F + 0.5F;
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
						int dx = POS.getX() - pos.getX();
						int dy = POS.getY() - pos.getY();
						int dz = POS.getZ() - pos.getZ();
						int ax = Math.abs(dx);
						int ay = Math.abs(dy);
						int az = Math.abs(dz);
						int max = Math.max(ax, Math.max(ay, az));
						Direction dir;
						if (max == ax) dir = Direction.fromAxisAndDirection(Axis.X, dx > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						else if (max == ay) dir = Direction.fromAxisAndDirection(Axis.Y, dy > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						else
							dir = Direction.fromAxisAndDirection(Axis.Z, dz > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						setIfAir(world, POS, NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, dir));
					}
				}
			}
		}

		BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.MAT_WILLOW.getBlock(WillowMaterial.BLOCK_BARK).defaultBlockState());
		setIfAir(world, pos.above(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.UP));
		setIfAir(world, pos.north(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.NORTH));
		setIfAir(world, pos.south(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.SOUTH));
		setIfAir(world, pos.east(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.EAST));
		setIfAir(world, pos.west(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.WEST));
	}

	private void setIfAir(LevelAccessor world, BlockPos pos, BlockState state) {
		if (world.isEmptyBlock(pos) || world.getBlockState(pos).getBlock() instanceof BlockNetherGrass)
			BlocksHelper.setWithoutUpdate(world, pos, state);
	}
}
