package paulevs.betternether.structures.big;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;

public class StructureChunk
{
	private int[] arrayID = new int[4096];
	
	public StructureChunk()
	{
		for (int i = 0; i < arrayID.length; i++)
			arrayID[i] = -1;
	}
	
	public StructureChunk(CompoundTag tag)
	{
		arrayID = tag.getIntArray("id");
	}
	
	private int getIndex(int x, int y, int z)
	{
		return (((x << 4) | y) << 4) | z;
	}
	
	public void setBlock(BlockState state, int x, int y, int z)
	{
		int index = getIndex(x, y, z);
		arrayID[index] = Block.getRawIdFromState(state);
	}
	
	public BlockState getBlockState(int x, int y, int z)
	{
		int index = getIndex(x, y, z);
		if (arrayID[index] == -1)
			return null;
		else
			return Block.getStateFromRawId(arrayID[index]);
	}
	
	public void writeNBT(CompoundTag tag)
	{
		tag.putIntArray("id", arrayID);
	}
}
