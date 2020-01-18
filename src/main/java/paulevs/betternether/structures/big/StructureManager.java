package paulevs.betternether.structures.big;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public abstract class StructureManager
{
	private static final long RANDOM_D = 132897987541L;
	private static final long RANDOM_C = 341873128712L;
	private static final long RANDOM_B = 132897987541L;
	private static final long RANDOM_A = 341873128712L;
	protected ArrayList<BigStructure> structures = new ArrayList<BigStructure>();
	protected ArrayList<BigStructure> toRemove = new ArrayList<BigStructure>();
	protected Random random = new Random();
	protected int distance;
	protected int variance;
	protected long seed;
	protected String name;

	public StructureManager(String name, int distance, long seed)
	{
		this.seed = seed;
		this.name = name;
		setDistance(distance);
	}
	
	public void setDistance(int distance)
	{
		this.distance = distance;
		this.variance = distance / 3;
	}
	
	protected void makeStructure(IWorld world, int cx, int cz)
	{
		{
			BlockPos nearest = getNearestStructure(world, cx, cz);
			if (!hasStructure(world, nearest.getX(), nearest.getZ(), cx, cz))
			{
				setSeed(nearest.getX(), nearest.getZ(), world.getSeed());
				structures.add(makeStructure(nearest.getX(), nearest.getZ()));
			}
		}
	}
	
	protected abstract BigStructure makeStructure(int cx, int cz);
	
	protected boolean hasStructure(IWorld world, int cx, int cz, int sourceX, int sourceZ)
	{
		for (BigStructure s: structures)
		{
			if (s.cx == cx && s.cz == cz)
			{
				return true;
			}
		}
		if (sourceX == cx && sourceZ == cz)
			return false;
		return world.getExistingChunk(cx, cz) != null;
	}
	
	public BlockPos getNearestStructure(IWorld world, int cx, int cz)
	{
		int fx = cx / distance;
		int fz = cz / distance;
		setSeed(fx, fz, world.getSeed());
		return new BlockPos(fx * distance + random.nextInt(variance) + variance, 0, fz * distance + random.nextInt(variance) + variance);
	}
	
	protected void placeStructures(IWorld world, int cx, int cz)
	{
		for (BigStructure structure: structures)
		{
			if (structure.generationComplete() && !toRemove.contains(toRemove))
				toRemove.add(structure);
			else
				structure.setChunk(world, cx, cz);
		}
		structures.removeAll(toRemove);
		toRemove.clear();
	}
	
	private void setSeed(int cx, int cz, long worldSeed)
	{
		long rseed = (long) cx * RANDOM_A + (long) cz * RANDOM_B + worldSeed + seed;
		random.setSeed(rseed);
	}
	
	protected void setSeed(int cx, int cz)
	{
		random.setSeed((long) cx * RANDOM_C + (long) cz * RANDOM_D);
	}
	
	protected CompoundTag toNBT()
	{
		CompoundTag root = new CompoundTag();
		ListTag structureData = new ListTag();
		root.put("structures", structureData);
		for (BigStructure s: structures)
			structureData.add(s.toNBT());
		return root;
	}
	
	public void save(IWorld world)
	{
		if (world instanceof ServerWorld)
		{
			ServerWorld sw = (ServerWorld) world;
			String path = sw.getSaveHandler().getWorldDir().getAbsolutePath() + "/data/bn_" + name + ".nbt";
			try
			{
				FileOutputStream fs = new FileOutputStream(new File(path));
				NbtIo.writeCompressed(this.toNBT(), fs);
				fs.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void load(IWorld world)
	{
		if (world instanceof ServerWorld)
		{
			ServerWorld sw = (ServerWorld) world;
			sw.dimension.saveWorldData();
			String path = sw.getSaveHandler().getWorldDir().getAbsolutePath() + "/data/bn_" + name + ".nbt";
			File file = new File(path);
			if (file.exists())
			{
				try
				{
					FileInputStream fs = new FileInputStream(file);
					CompoundTag root = NbtIo.readCompressed(fs);
					fs.close();
					ListTag structureData = root.getList("structures", 10);
					for (int i = 0; i < structureData.size(); i++)
						structures.add(loadStructure(structureData.getCompound(i)));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	protected BigStructure loadStructure(CompoundTag tag)
	{
		return new BigStructure(tag);
	}
	
	public void generate(IWorld world, int cx, int cz)
	{
		makeStructure(world, cx, cz);
		placeStructures(world, cx, cz);
	}
}
