package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockNetherGrass;
import paulevs.betternether.blocks.BlockWillowLeaves;
import paulevs.betternether.blocks.complex.WillowMaterial;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureWillowBush implements IStructure {
	public StructureWillowBush() {}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
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

			context.POS.setY(pos.getY() + i);

			for (int x = x1; x < x2; x++) {
				context.POS.setX(x);
				int sqx = x - pos.getX();
				sqx *= sqx;
				for (int z = z1; z < z2; z++) {
					int sqz = z - pos.getZ();
					sqz *= sqz;
					context.POS.setZ(z);
					if (sqx + sqz < r2 + random.nextFloat() * r) {
						int dx = context.POS.getX() - pos.getX();
						int dy = context.POS.getY() - pos.getY();
						int dz = context.POS.getZ() - pos.getZ();
						int ax = Math.abs(dx);
						int ay = Math.abs(dy);
						int az = Math.abs(dz);
						int max = Math.max(ax, Math.max(ay, az));
						Direction dir;
						if (max == ax) dir = Direction.fromAxisAndDirection(Axis.X, dx > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						else if (max == ay) dir = Direction.fromAxisAndDirection(Axis.Y, dy > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						else
							dir = Direction.fromAxisAndDirection(Axis.Z, dz > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						setIfAir(world, context.POS, NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, dir).setValue(LeavesBlock.PERSISTENT, true));
					}
				}
			}
		}

		BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.MAT_WILLOW.getBlock(WillowMaterial.BLOCK_BARK).defaultBlockState());
		setIfAir(world, pos.above(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.UP).setValue(LeavesBlock.DISTANCE, 1));
		setIfAir(world, pos.north(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.NORTH).setValue(LeavesBlock.DISTANCE, 1));
		setIfAir(world, pos.south(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.SOUTH).setValue(LeavesBlock.DISTANCE, 1));
		setIfAir(world, pos.east(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.EAST).setValue(LeavesBlock.DISTANCE, 1));
		setIfAir(world, pos.west(), NetherBlocks.WILLOW_LEAVES.defaultBlockState().setValue(BlockWillowLeaves.FACING, Direction.WEST).setValue(LeavesBlock.DISTANCE, 1));
	}

	private void setIfAir(LevelAccessor world, BlockPos pos, BlockState state) {
		if (world.isEmptyBlock(pos) || world.getBlockState(pos).getBlock() instanceof BlockNetherGrass)
			BlocksHelper.setWithoutUpdate(world, pos, state);
	}
}
