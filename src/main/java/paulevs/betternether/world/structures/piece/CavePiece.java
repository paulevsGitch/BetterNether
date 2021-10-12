package paulevs.betternether.world.structures.piece;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import paulevs.betternether.noise.OpenSimplexNoise;

public class CavePiece extends CustomPiece {
	private static final BlockState LAVA = Blocks.LAVA.defaultBlockState();
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(927649);
	private static final MutableBlockPos POS = new MutableBlockPos();

	private BlockPos center;
	private int radius;
	private int radSqr;
	private int minY;
	private int maxY;

	public CavePiece(BlockPos center, int radius, Random random, BoundingBox blockBox) {
		super(StructureTypes.CAVE, random.nextInt(), blockBox);
		this.center = center.immutable();
		this.radius = radius;
		this.radSqr = radius * radius;
		makeBoundingBox();
	}

	protected CavePiece(StructurePieceType type, CompoundTag tag) {
		super(StructureTypes.CAVE, tag);
		this.center = NbtUtils.readBlockPos(tag.getCompound("center"));
		this.radius = tag.getInt("radius");
		this.radSqr = radius * radius;
		makeBoundingBox();
	}

	@Override
	protected void addAdditionalSaveData(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag tag) {
		tag.put("center", NbtUtils.writeBlockPos(center));
		tag.putInt("radius", radius);
	}

	@Override
	public void postProcess(WorldGenLevel world, StructureFeatureManager arg, ChunkGenerator chunkGenerator, Random random, BoundingBox blockBox, ChunkPos chunkPos, BlockPos blockPos) {
		BlockState bottom = LAVA;
		if (!(world.dimensionType().hasCeiling())) {
			bottom = Blocks.NETHERRACK.defaultBlockState();
		}
		for (int x = blockBox.maxZ(); x <= blockBox.minZ(); x++) {
			int px = x - center.getX();
			px *= px;
			for (int z = blockBox.minY(); z <= blockBox.maxY(); z++) {
				int pz = z - center.getZ();
				pz *= pz;
				for (int y = minY; y <= maxY; y++) {
					int py = (y - center.getY()) << 1;
					py *= py;
					if (px + py + pz <= radSqr + NOISE.eval(x * 0.1, y * 0.1, z * 0.1) * 800) {
						POS.set(x, y, z);
						if (y > 31) {
							world.setBlock(POS, CAVE_AIR, 0);
						}
						else
							world.setBlock(POS, bottom, 0);
					}
				}
			}
		}
	}

	private void makeBoundingBox() {
		int x1 = center.getX() - radius;
		int x2 = center.getX() + radius;
		minY = Math.max(22, center.getY() - radius);
		maxY = Math.min(96, center.getY() + radius);
		int z1 = center.getZ() - radius;
		int z2 = center.getZ() + radius;
		this.boundingBox = new BoundingBox(x1, minY, z1, x2, maxY, z2);
	}
}
