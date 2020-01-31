package paulevs.betternether.world.structures.piece;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
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
		this.pos = pos;
		this.boundingBox = building.getBoundingBox(pos);
	}

	protected CityPiece(StructureManager manager, CompoundTag tag)
	{
		super(StructureTypes.NETHER_CITY, tag);
		this.building = new StructureCityBuilding(tag.getString("building"), tag.getInt("offset"));
		this.building.setRotation(BlockRotation.values()[tag.getInt("rotation")]);
		this.pos = NbtHelper.toBlockPos(tag.getCompound("pos"));
		this.boundingBox = building.getBoundingBox(pos);
	}

	@Override
	protected void toNbt(CompoundTag tag)
	{
		tag.putString("building", building.getName());
		tag.putInt("rotation", building.getRotation().ordinal());
		tag.putInt("offset", building.getYOffset());
		tag.put("pos", NbtHelper.fromBlockPos(pos));
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos)
	{
		if (!this.boundingBox.intersects(blockBox))
			return true;

		building.placeInChunk(world, pos, blockBox);

		for (BlockPos rep : building.ends)
		{
			BlockPos repPos = rep.add(pos);
			this.addBlock(world, AIR, repPos.getX(), repPos.getY(), repPos.getZ(), blockBox);
		}

		BlockState state;
		for (int x = 0; x < building.getBoungingBox().getMaxX(); x++)
			for (int z = 0; z < building.getBoungingBox().getMaxZ(); z++)
			{
				POS.set(pos.getX() + x, pos.getY(), pos.getZ() + z);
				if (blockBox.contains(POS))
				{
					state = world.getBlockState(POS);
					if (state.isFullCube(world, POS) && !world.getBlockState(POS.down()).isFullCube(world, POS.down()))
					{
						for (int y = 1; y < 64; y++)
						{
							POS.setY(POS.getY() - 1);
							world.setBlockState(POS, state, 0);
							if (BlocksHelper.isNetherGroundMagma(world.getBlockState(POS.down())) || POS.getY() < 5)
								break;
						}
					}
				}
			}

		return true;
	}
}
