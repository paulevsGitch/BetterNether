package paulevs.betternether.structures.big;

import java.util.HashMap;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;

public class BigStructure
{
	protected HashMap<StructurePos, StructureChunk> data = new HashMap<StructurePos, StructureChunk>();
	protected int chunksGenerated = 0;
	protected int chunksMax = 0;
	protected BlockPos pos;
	protected int maxHeight = 0;
	protected int minHeight = 0;
	protected int chunkMinX = 0;
	protected int chunkMaxX = 0;
	protected int chunkMinZ = 0;
	protected int chunkMaxZ = 0;
	protected int cx;
	protected int cz;
	
	public BigStructure(BlockPos pos, int cx, int cz)
	{
		this.pos = pos;
		maxHeight = pos.getY();
		minHeight = pos.getY();
		this.cx = cx;
		this.cz = cz;
		chunkMinX = pos.getX() >> 4;
		chunkMaxX = chunkMinX;
		chunkMinZ = pos.getZ() >> 4;
		chunkMaxZ = chunkMinZ;
	}
	
	public BigStructure(CompoundTag structure)
	{
		chunksGenerated = structure.getInt("chunksGenerated");
		chunksMax = structure.getInt("chunksMax");
		maxHeight = structure.getInt("maxHeight");
		minHeight = structure.getInt("minHeight");
		chunkMinX = structure.getInt("chunkMinX");
		chunkMaxX = structure.getInt("chunkMaxX");
		chunkMinZ = structure.getInt("chunkMinZ");
		chunkMaxZ = structure.getInt("chunkMaxZ");
		cx = structure.getInt("cx");
		cz = structure.getInt("cz");
		int[] pos = structure.getIntArray("pos");
		this.pos = new BlockPos(pos[0], pos[1], pos[2]);
		ListTag chunkData = structure.getList("chunks", 10);
		for (int i = 0; i < chunkData.size(); i++)
		{
			CompoundTag chunk = chunkData.getCompound(i);
			StructureChunk c = new StructureChunk(chunk);
			pos = chunk.getIntArray("pos");
			data.put(new StructurePos(pos[0], pos[1], pos[2]), c);
		}
	}
	
	protected StructureChunk getStructureChunk(int x, int y, int z)
	{
		StructurePos pos = new StructurePos(x >> 4, y >> 4, z >> 4);
		if (data.containsKey(pos))
			return data.get(pos);
		else
		{
			StructureChunk chunk = new StructureChunk();
			data.put(pos, chunk);
			return chunk;
		}
	}
	
	public void setBlock(BlockState state, BlockPos pos)
	{
		maxHeight = Math.min(Math.max(maxHeight, pos.getY() + this.pos.getY()), 255);
		minHeight = Math.max(Math.min(minHeight, pos.getY() + this.pos.getY()), 0);
		StructureChunk chunk = getStructureChunk(pos.getX(), pos.getY(), pos.getZ());
		chunk.setBlock(state, pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
		updateBounds(pos);
	}
	
	public BlockState getBlockState(int x, int y, int z)
	{
		StructureChunk chunk = getStructureChunk(x, y, z);
		return chunk.getBlockState(x & 15, y & 15, z & 15);
	}
	
	public boolean setChunk(IWorld world, int cx, int cz)
	{
		if (cx >= chunkMinX && cx <= chunkMaxX && cz >= chunkMinZ && cz <= chunkMaxZ)
		{
			int sx = (cx << 4) | 8;
			int sz = (cz << 4) | 8;
			for (int y = minHeight; y <= maxHeight; y++)
			{
				int py = y - pos.getY();
				for (int x = 0; x < 16; x++)
				{
					int wx = sx + x;
					int px = wx - pos.getX();
					for (int z = 0; z < 16; z++)
					{
						int wz = sz + z;
						int pz = wz - pos.getZ();
						BlockState state = getBlockState(px, py, pz);
						if (state != null)
						{
							BlocksHelper.setWithoutUpdate(world, new BlockPos(wx, y, wz), state);
						}
					}
				}
			}
			chunksGenerated ++;
			return true;
		}
		return false;
	}
	
	public boolean generationComplete()
	{
		return chunksGenerated >= chunksMax;
	}
	
	public CompoundTag toNBT()
	{
		CompoundTag structure = new CompoundTag();
		structure.putInt("chunksGenerated", chunksGenerated);
		structure.putInt("chunksMax", chunksMax);
		structure.putInt("maxHeight", maxHeight);
		structure.putInt("minHeight", minHeight);
		structure.putInt("chunkMinX", chunkMinX);
		structure.putInt("chunkMaxX", chunkMaxX);
		structure.putInt("chunkMinZ", chunkMinZ);
		structure.putInt("chunkMaxZ", chunkMaxZ);
		structure.putInt("cx", cx);
		structure.putInt("cz", cz);
		structure.putIntArray("pos", new int[] {pos.getX(), pos.getY(), pos.getZ()});
		ListTag chunkData = new ListTag();
		structure.put("chunks", chunkData);
		for (StructurePos p: data.keySet())
		{
			StructureChunk c = data.get(p);
			CompoundTag chunk = new CompoundTag();
			chunk.putIntArray("pos", new int[] {p.getX(), p.getY(), p.getZ()});
			c.writeNBT(chunk);
			chunkData.add(chunk);
		}
		return structure;
	}
	
	private void updateBounds(BlockPos pos)
	{
		int cx = (pos.getX() + this.pos.getX()) >> 4;
		int y = pos.getY() + this.pos.getY();
		int cz = (pos.getZ() + this.pos.getZ()) >> 4;
		maxHeight = y > maxHeight ? y : maxHeight;
		minHeight = y < minHeight ? y : minHeight;
		boolean update = false;
		if (cx < chunkMinX)
		{
			chunkMinX = cx;
			update = true;
		}
		if (cx > chunkMaxX)
		{
			chunkMaxX = cx;
			update = true;
		}
		if (cz < chunkMinZ)
		{
			chunkMinZ = cz;
			update = true;
		}
		if (cz > chunkMaxZ)
		{
			chunkMaxZ = cz;
			update = true;
		}
		if (update)
			chunksMax = (chunkMaxX - chunkMinX + 1) * (chunkMaxZ - chunkMinZ + 1);
	}
}
