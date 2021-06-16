package paulevs.betternether.structures.decorations;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

import java.util.Random;

public class StructureCrystal implements IStructure {
	private static final Block[] PALETTES = new Block[] {
			BlocksRegistry.OBSIDIAN_GLASS,
			Blocks.OBSIDIAN,
			BlocksRegistry.BLUE_OBSIDIAN_GLASS,
			BlocksRegistry.BLUE_OBSIDIAN
	};
	private static final double SQRT05 = Math.sqrt(0.5);
	private static final Mutable POS = new Mutable();
	private static final float MAX_ANGLE_X = (float) Math.toRadians(45);
	private static final float MAX_ANGLE_Y = (float) (Math.PI * 2);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		int index = random.nextInt(PALETTES.length >> 1);
		double a = random.nextDouble();
		double radius = 2 + a * a * 5;
		int sideXZ = (int) Math.ceil(radius * 2);
		int sideY = (int) Math.ceil(radius * 3);
		float angleX = random.nextFloat() * MAX_ANGLE_X;
		float angleY = random.nextFloat() * MAX_ANGLE_Y;
		for (int y = -sideY; y <= sideY; y++)
			for (int x = -sideXZ; x <= sideXZ; x++)
				for (int z = -sideXZ; z <= sideXZ; z++) {
					Vec3d v = new Vec3d(x, y, z).rotateX(angleX).rotateY(angleY);
					if (isInside(v.x, v.y, v.z, radius)) {
						POS.setX(pos.getX() + x);
						POS.setY(pos.getY() + y);
						POS.setZ(pos.getZ() + z);
						BlockState state = random.nextInt(40) == 0 && isNotEdge(v.x, v.y, v.z, radius) ? Blocks.GLOWSTONE.getDefaultState() : getState(index, v);
						BlocksHelper.setWithoutUpdate(world, POS, state);
					}
				}
	}

	private boolean isInside(double x, double y, double z, double size) {
		return dodecahedronSDF(x / size, y / size * 0.3, z / size) <= 0;
	}

	private boolean isNotEdge(double x, double y, double z, double size) {
		return isInside(x + 1, y, z, size) &&
				isInside(x - 1, y, z, size) &&
				isInside(x, y + 1, z, size) &&
				isInside(x, y - 1, z, size) &&
				isInside(x, y, z + 1, size) &&
				isInside(x, y, z - 1, size);
	}

	private double dodecahedronSDF(double x, double y, double z) {
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
		return (Math.max(Math.max(x + y, y + z), z + x) - 1) * SQRT05;
	}

	private double rigidNoise(Vec3d pos, double scale) {
		double val = NOISE.eval(pos.x * scale, pos.y * scale, pos.z * scale);
		return Math.abs(val);
	}

	private BlockState getState(int index, Vec3d pos) {
		int subindex = rigidNoise(pos, 0.2) > 0.2 ? 0 : 1;
		int blockIndex = (index << 1) | subindex;
		return PALETTES[blockIndex].getDefaultState();
	}
}