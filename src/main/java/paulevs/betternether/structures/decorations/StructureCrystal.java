package paulevs.betternether.structures.decorations;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IStructure;

public class StructureCrystal implements IStructure {
	private static final Block[] PALETTES = new Block[] {
			NetherBlocks.OBSIDIAN_GLASS,
			Blocks.OBSIDIAN,
			NetherBlocks.BLUE_OBSIDIAN_GLASS,
			NetherBlocks.BLUE_OBSIDIAN
	};
	private static final double SQRT05 = Math.sqrt(0.5);
	private static final MutableBlockPos POS = new MutableBlockPos();
	private static final float MAX_ANGLE_X = (float) Math.toRadians(45);
	private static final float MAX_ANGLE_Y = (float) (Math.PI * 2);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
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
					Vec3 v = new Vec3(x, y, z).xRot(angleX).yRot(angleY);
					if (isInside(v.x, v.y, v.z, radius)) {
						POS.setX(pos.getX() + x);
						POS.setY(pos.getY() + y);
						POS.setZ(pos.getZ() + z);
						BlockState state = random.nextInt(40) == 0 && isNotEdge(v.x, v.y, v.z, radius) ? Blocks.GLOWSTONE.defaultBlockState() : getState(index, v);
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

	private double rigidNoise(Vec3 pos, double scale) {
		double val = NOISE.eval(pos.x * scale, pos.y * scale, pos.z * scale);
		return Math.abs(val);
	}

	private BlockState getState(int index, Vec3 pos) {
		int subindex = rigidNoise(pos, 0.2) > 0.2 ? 0 : 1;
		int blockIndex = (index << 1) | subindex;
		return PALETTES[blockIndex].defaultBlockState();
	}
}