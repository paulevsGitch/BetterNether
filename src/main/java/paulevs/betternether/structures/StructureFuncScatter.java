package paulevs.betternether.structures;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public abstract class StructureFuncScatter implements IStructure {
	protected static final Mutable POS = new Mutable();

	final int distance;
	final int manDist;

	public StructureFuncScatter(int distance) {
		this.distance = distance;
		this.manDist = (int) Math.ceil(distance * 1.5);
	}

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (isGround(world.getBlockState(pos.down())) && noObjNear(world, pos)) {
			grow(world, pos, random);
		}
	}

	public abstract void grow(ServerWorldAccess world, BlockPos pos, Random random);

	protected abstract boolean isStructure(BlockState state);

	protected abstract boolean isGround(BlockState state);

	private boolean noObjNear(WorldAccess world, BlockPos pos) {
		int x1 = pos.getX() - distance;
		int z1 = pos.getZ() - distance;
		int x2 = pos.getX() + distance;
		int z2 = pos.getZ() + distance;
		POS.setY(pos.getY());
		for (int x = x1; x <= x2; x++) {
			POS.setX(x);
			for (int z = z1; z <= z2; z++) {
				POS.setZ(z);
				if (isInside(x - pos.getX(), z - pos.getZ()) && isStructure(world.getBlockState(POS)))
					return false;
			}
		}
		return true;
	}

	private boolean isInside(int x, int z) {
		return (Math.abs(x) + Math.abs(z)) <= manDist;
	}
}
