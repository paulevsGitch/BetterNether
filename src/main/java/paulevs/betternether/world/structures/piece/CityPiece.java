package paulevs.betternether.world.structures.piece;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.structures.city.StructureCityBuilding;

public class CityPiece extends CustomPiece
{
	private static final Mutable POS = new Mutable();

	private StructureCityBuilding building;
	private BlockPos pos;

	public CityPiece(StructureCityBuilding building, BlockPos pos, int id)
	{
		super(StructureTypes.NETHER_CITY, id);
		this.building = building;
		this.pos = new BlockPos(pos);
		this.boundingBox = building.getBoundingBox(pos);
	}

	protected CityPiece(StructureManager manager, CompoundTag tag)
	{
		super(StructureTypes.NETHER_CITY, tag);
		this.building = new StructureCityBuilding(tag.getString("building"), tag.getInt("offset"));
		this.building = this.building.getRotated(BlockRotation.values()[tag.getInt("rotation")]);
		this.building.setMirror(BlockMirror.values()[tag.getInt("mirror")]);
		this.pos = NbtHelper.toBlockPos(tag.getCompound("pos"));
		this.boundingBox = building.getBoundingBox(pos);
	}

	@Override
	protected void toNbt(CompoundTag tag)
	{
		tag.putString("building", building.getName());
		tag.putInt("rotation", building.getRotation().ordinal());
		tag.putInt("mirror", building.getMirror().ordinal());
		tag.putInt("offset", building.getYOffset());
		tag.put("pos", NbtHelper.fromBlockPos(pos));
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos)
	{
		if (!this.boundingBox.intersects(blockBox))
			return true;
		
		BlockBox clamped = new BlockBox(boundingBox);
		
		clamped.minX = Math.max(clamped.minX, blockBox.minX);
		clamped.maxX = Math.min(clamped.maxX, blockBox.maxX);
		
		clamped.minY = Math.max(clamped.minY, blockBox.minY);
		clamped.maxY = Math.min(clamped.maxY, blockBox.maxY);
		
		clamped.minZ = Math.max(clamped.minZ, blockBox.minZ);
		clamped.maxZ = Math.min(clamped.maxZ, blockBox.maxZ);

		building.placeInChunk(world, pos, clamped);
		
		Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);

		BlockState state;
		for (int x = clamped.minX; x <= clamped.maxX; x++)
			for (int z = clamped.minZ; z <= clamped.maxZ; z++)
			{
				POS.set(x, clamped.minY, z);
				state = world.getBlockState(POS);
				if (!state.isAir() && state.isFullCube(world, POS))
				{
					for (int y = clamped.minY - 1; y > 4; y--)
					{
						POS.setY(y);
						BlocksHelper.setWithoutUpdate(world, POS, state);
						if (BlocksHelper.isNetherGroundMagma(world.getBlockState(POS.down())))
							break;
					}
				}
				
				//POS.set(x - clamped.minX, clamped.minY - clamped.minZ, z);
				for (int y = clamped.minY; y <= clamped.maxY; y++)
				{
					POS.setY(y);
					chunk.markBlockForPostProcessing(POS);
				}
			}

		return true;
	}
}
