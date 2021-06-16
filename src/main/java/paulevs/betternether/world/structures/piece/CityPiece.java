package paulevs.betternether.world.structures.piece;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.structures.city.BuildingStructureProcessor;
import paulevs.betternether.world.structures.city.StructureCityBuilding;
import paulevs.betternether.world.structures.city.palette.CityPalette;
import paulevs.betternether.world.structures.city.palette.Palettes;

import java.util.Random;

public class CityPiece extends CustomPiece {
	private static final Mutable POS = new Mutable();

	private StructureProcessor paletteProcessor;
	private StructureCityBuilding building;
	private CityPalette palette;
	private BlockPos pos;

	public CityPiece(StructureCityBuilding building, BlockPos pos, int id, CityPalette palette) {
		super(StructureTypes.NETHER_CITY, id, building.getBoundingBox(pos));
		this.building = building;
		this.pos = pos.toImmutable();
		this.boundingBox = building.getBoundingBox(pos);
		this.palette = palette;
		this.paletteProcessor = new BuildingStructureProcessor(palette);
	}

	public CityPiece(ServerWorld serverWorld, NbtCompound tag) {
		super(StructureTypes.NETHER_CITY, tag);
		this.building = new StructureCityBuilding(tag.getString("building"), tag.getInt("offset"));
		this.building = this.building.getRotated(BlockRotation.values()[tag.getInt("rotation")]);
		this.building.setMirror(BlockMirror.values()[tag.getInt("mirror")]);
		this.pos = NbtHelper.toBlockPos(tag.getCompound("pos"));
		this.boundingBox = building.getBoundingBox(pos);
		this.palette = Palettes.getPalette(tag.getString("palette"));
		this.paletteProcessor = new BuildingStructureProcessor(palette);
	}

	@Override
	protected void writeNbt(ServerWorld serverWorld, NbtCompound tag) {
		tag.putString("building", building.getName());
		tag.putInt("rotation", building.getRotation().ordinal());
		tag.putInt("mirror", building.getMirror().ordinal());
		tag.putInt("offset", building.getYOffset());
		tag.put("pos", NbtHelper.fromBlockPos(pos));
		tag.putString("palette", palette.getName());
	}

	@Override
	public boolean generate(StructureWorldAccess world, StructureAccessor arg, ChunkGenerator chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos, BlockPos blockPos) {
		if (!this.boundingBox.intersects(blockBox))
			return true;

		BlockBox clamped = new BlockBox(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ(), boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMaxZ());
		clamped.encompass(blockBox);
		/*clamped.maxZ = Math.max(clamped.getMaxZ(), blockBox.getMaxZ());
		clamped.minZ = Math.min(clamped.getMinZ(), blockBox.getMinZ());

		clamped.minX = Math.max(clamped.getMinX(), blockBox.getMinX());
		clamped.maxX = Math.min(clamped.getMaxX(), blockBox.getMaxX());

		clamped.minY = Math.max(clamped.getMinY(), blockBox.getMinY());
		clamped.maxY = Math.min(clamped.getMaxY(), blockBox.getMaxY());*/

		building.placeInChunk(world, pos, clamped, paletteProcessor);

		Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);

		BlockState state;
		for (int x = clamped.getMaxZ(); x <= clamped.getMinZ(); x++)
			for (int z = clamped.getMinY(); z <= clamped.getMaxY(); z++) {
				POS.set(x, clamped.getMinX(), z);
				state = world.getBlockState(POS);
				if (!state.isAir() && state.isFullCube(world, POS)) {
					for (int y = clamped.getMinX() - 1; y > 4; y--) {
						POS.setY(y);
						BlocksHelper.setWithoutUpdate(world, POS, state);
						if (BlocksHelper.isNetherGroundMagma(world.getBlockState(POS.down())))
							break;
					}
				}

				// POS.set(x - clamped.minX, clamped.minY - clamped.minZ, z);
				for (int y = clamped.getMinX(); y <= clamped.getMaxX(); y++) {
					POS.setY(y);
					chunk.markBlockForPostProcessing(POS);
				}
			}

		return true;
	}
}
