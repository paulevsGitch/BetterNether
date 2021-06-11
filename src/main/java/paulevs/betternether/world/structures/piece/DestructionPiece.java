package paulevs.betternether.world.structures.piece;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;

public class DestructionPiece extends CustomPiece {
	private static final Mutable POS = new Mutable();

	private BlockPos center;
	private int radius;
	private int radSqr;
	private int minY;
	private int maxY;

	public DestructionPiece(BlockBox bounds, Random random) {
		super(StructureTypes.DESTRUCTION, random.nextInt(), bounds);
		radius = random.nextInt(5) + 1;
		radSqr = radius * radius;
		center = new BlockPos(
				MHelper.randRange(bounds.getMaxZ(), bounds.getMinZ(), random),
				MHelper.randRange(bounds.getMinX(), bounds.getMaxX(), random),
				MHelper.randRange(bounds.getMinY(), bounds.getMaxY(), random));
		makeBoundingBox();
	}

	protected DestructionPiece(ServerWorld serverWorld, NbtCompound tag) {
		super(StructureTypes.DESTRUCTION, tag);
		this.center = NbtHelper.toBlockPos(tag.getCompound("center"));
		this.radius = tag.getInt("radius");
		radSqr = radius * radius;
		makeBoundingBox();
	}

	@Override
	protected void writeNbt(ServerWorld serverWorld, NbtCompound tag) {
		tag.put("center", NbtHelper.fromBlockPos(center));
		tag.putInt("radius", radius);
	}

	@Override
	public boolean generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos, BlockPos blockPos) {
		for (int x = blockBox.getMaxZ(); x <= blockBox.getMinZ(); x++) {
			int px = x - center.getX();
			px *= px;
			for (int z = blockBox.getMinY(); z <= blockBox.getMaxY(); z++) {
				int pz = z - center.getZ();
				pz *= pz;
				for (int y = minY; y <= maxY; y++) {
					int py = (y - center.getY()) << 1;
					py *= py;
					if (px + py + pz <= radSqr + random.nextInt(radius)) {
						POS.set(x, y, z);
						if (!world.isAir(POS)) {
							if (random.nextBoolean())
								BlocksHelper.setWithoutUpdate(world, POS, AIR);
							else {
								int dist = BlocksHelper.downRay(world, POS, maxY - 5);
								if (dist > 0) {
									BlockState state = world.getBlockState(POS);
									BlocksHelper.setWithoutUpdate(world, POS, AIR);
									POS.setY(POS.getY() - dist);
									BlocksHelper.setWithoutUpdate(world, POS, state);
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	private void makeBoundingBox() {
		int x1 = center.getX() - radius;
		int x2 = center.getX() + radius;
		minY = Math.max(22, center.getY() - radius);
		if (minY < 38)
			minY = 38;
		maxY = Math.min(96, center.getY() + radius);
		int z1 = center.getZ() - radius;
		int z2 = center.getZ() + radius;
		this.boundingBox = new BlockBox(x1, minY, z1, x2, maxY, z2);
	}
}