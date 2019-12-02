package paulevs.betternether.structures.big;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class StructureChunk
{
	private int[] arrayID = new int[4096];
	private byte[] arrayMeta = new byte[4096];
	
	public StructureChunk()
	{
		for (int i = 0; i < arrayID.length; i++)
			arrayID[i] = -1;
	}
	
	public StructureChunk(NBTTagCompound tag)
	{
		arrayID = tag.getIntArray("id");
		arrayMeta = tag.getByteArray("meta");
	}
	
	private int getIndex(int x, int y, int z)
	{
		return (((x << 4) | y) << 4) | z;
	}
	
	public void setBlock(int blockID, byte blockMeta, int x, int y, int z)
	{
		int index = getIndex(x, y, z);
		arrayID[index] = blockID;
		arrayMeta[index] = blockMeta;
	}
	
	public IBlockState getBlockState(int x, int y, int z)
	{
		int index = getIndex(x, y, z);
		if (arrayID[index] == -1)
			return null;
		else
			return Block.getBlockById(arrayID[index]).getStateFromMeta(arrayMeta[index]);
	}
	
	public void writeNBT(NBTTagCompound tag)
	{
		tag.setIntArray("id", arrayID);
		tag.setByteArray("meta", arrayMeta);
	}
}
