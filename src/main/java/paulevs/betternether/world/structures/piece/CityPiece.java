package paulevs.betternether.world.structures.piece;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.StructureManager;
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

public class CityPiece extends CustomPiece {
	private static final Mutable POS = new Mutable();

	private StructureProcessor paletteProcessor;
	private StructureCityBuilding building;
	private CityPalette palette;
	private BlockPos pos;

	public CityPiece(StructureCityBuilding building, BlockPos pos, int id, CityPalette palette) {
		super(StructureTypes.NETHER_CITY, id);
		this.building = building;
		this.pos = pos.toImmutable();
		this.boundingBox = building.getBoundingBox(pos);
		this.palette = palette;
		this.paletteProcessor = new BuildingStructureProcessor(palette);
	}

	protected CityPiece(StructureManager manager, NbtCompound tag) {
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
	protected void writeNbt(NbtCompound tag) {
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

		BlockBox clamped = new BlockBox(boundingBox);

		clamped.maxZ = Math.max(clamped.maxZ, blockBox.maxZ);
		clamped.minZ = Math.min(clamped.minZ, blockBox.minZ);

		clamped.minX = Math.max(clamped.minX, blockBox.minX);
		clamped.maxX = Math.min(clamped.maxX, blockBox.maxX);

		clamped.minY = Math.max(clamped.minY, blockBox.minY);
		clamped.maxY = Math.min(clamped.maxY, blockBox.maxY);

		building.placeInChunk(world, pos, clamped, paletteProcessor);

		Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);

		BlockState state;
		for (int x = clamped.maxZ; x <= clamped.minZ; x++)
			for (int z = clamped.minY; z <= clamped.maxY; z++) {
				POS.set(x, clamped.minX, z);
				state = world.getBlockState(POS);
				if (!state.isAir() && state.isFullCube(world, POS)) {
					for (int y = clamped.minX - 1; y > 4; y--) {
						POS.setY(y);
						BlocksHelper.setWithoutUpdate(world, POS, state);
						if (BlocksHelper.isNetherGroundMagma(world.getBlockState(POS.down())))
							break;
					}
				}

				// POS.set(x - clamped.minX, clamped.minY - clamped.minZ, z);
				for (int y = clamped.minX; y <= clamped.maxX; y++) {
					POS.setY(y);
					chunk.markBlockForPostProcessing(POS);
				}
			}

		return true;
	}
}
