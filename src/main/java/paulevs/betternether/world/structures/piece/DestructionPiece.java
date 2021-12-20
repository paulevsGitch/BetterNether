package paulevs.betternether.world.structures.piece;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;

public class DestructionPiece extends CustomPiece {
	private final MutableBlockPos POS = new MutableBlockPos();

	private final BlockPos center;
	private final int radius;
	private final int radSqr;
	private int minY;
	private int maxY;

	public DestructionPiece(BoundingBox bounds, Random random) {
		super(StructureTypes.DESTRUCTION, random.nextInt(), bounds);
		radius = random.nextInt(5) + 1;
		radSqr = radius * radius;
		center = new BlockPos(
				MHelper.randRange(bounds.minX(), bounds.maxX(), random),
				MHelper.randRange(bounds.minY(), bounds.maxY(), random),
				MHelper.randRange(bounds.minZ(), bounds.maxZ(), random));
		makeBoundingBox();
	}

	protected DestructionPiece(StructurePieceSerializationContext context, CompoundTag tag) {
		super(StructureTypes.DESTRUCTION, tag);
		this.center = NbtUtils.readBlockPos(tag.getCompound("center"));
		this.radius = tag.getInt("radius");
		radSqr = radius * radius;
		makeBoundingBox();
	}

	@Override
	protected void addAdditionalSaveData(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag tag) {
		tag.put("center", NbtUtils.writeBlockPos(center));
		tag.putInt("radius", radius);
	}

	@Override
	public void postProcess(WorldGenLevel world, StructureFeatureManager structureAccessor, ChunkGenerator chunkGenerator, Random random, BoundingBox blockBox, ChunkPos chunkPos, BlockPos blockPos) {
		for (int x = blockBox.maxZ(); x <= blockBox.minZ(); x++) {
			int px = x - center.getX();
			px *= px;
			for (int z = blockBox.minY(); z <= blockBox.maxY(); z++) {
				int pz = z - center.getZ();
				pz *= pz;
				for (int y = minY; y <= maxY; y++) {
					int py = (y - center.getY()) << 1;
					py *= py;
					if (px + py + pz <= radSqr + random.nextInt(radius)) {
						POS.set(x, y, z);
						if (!world.isEmptyBlock(POS)) {
							if (random.nextBoolean())
								BlocksHelper.setWithoutUpdate(world, POS, CAVE_AIR);
							else {
								int dist = BlocksHelper.downRay(world, POS, maxY - 5);
								if (dist > 0) {
									BlockState state = world.getBlockState(POS);
									BlocksHelper.setWithoutUpdate(world, POS, CAVE_AIR);
									POS.setY(POS.getY() - dist);
									BlocksHelper.setWithoutUpdate(world, POS, state);
								}
							}
						}
					}
				}
			}
		}
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
		this.boundingBox = new BoundingBox(x1, minY, z1, x2, maxY, z2);
	}
}