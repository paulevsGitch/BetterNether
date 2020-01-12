package paulevs.betternether.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.config.Config;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.structures.IStructure;
import paulevs.betternether.structures.plants.StructureWartCap;

public class NetherBiome
{
	private static final OpenSimplexNoise SCATTER = new OpenSimplexNoise(1337);
	private static int structureID = 0;
	
	private ArrayList<StructureInfo> generatorsFloor = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsWall = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsCeil = new ArrayList<StructureInfo>();
	
	public Block testBlock = Blocks.AIR;
	
	protected String name;
	protected NetherBiome edge;
	protected int edgeSize;
	protected List<Subbiome> subbiomes;
	protected NetherBiome parrent;
	protected float maxSubBiomeChance = 1;
	
	public NetherBiome(String name)
	{
		this.name = name;
		edge = this;
		edgeSize = 0;
		subbiomes = new ArrayList<Subbiome>();
		addStructure("cap_gen", new StructureWartCap(), StructureType.WALL, 0.08F, true);
		subbiomes.add(new Subbiome(this, 1));
	}
	
	public void genSurfColumn(IWorld world, BlockPos pos, Random random) {}
	
	public void genFloorObjects(IWorld world, BlockPos pos, Random random)
	{
		for (StructureInfo info: generatorsFloor)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}
	
	public void genWallObjects(IWorld world, BlockPos pos, Random random)
	{
		for (StructureInfo info: generatorsWall)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}
	
	public void genCeilObjects(IWorld world, BlockPos pos, Random random)
	{
		for (StructureInfo info: generatorsCeil)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}
	
	protected double getFeatureNoise(BlockPos pos, int id)
	{
		return SCATTER.eval(pos.getX() * 0.1, pos.getY() * 0.1 + id * 10, pos.getZ() * 0.1);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getRegistryName()
	{
		return name.toLowerCase().replace(' ', '_');
	}

	public NetherBiome getEdge()
	{
		return edge;
	}
	
	public void setEdge(NetherBiome edge)
	{
		this.edge = edge;
		edge.parrent = this;
	}

	public int getEdgeSize()
	{
		return edgeSize;
	}
	
	public void setEdgeSize(int size)
	{
		edgeSize = size;
	}
	
	public void addSubBiome(NetherBiome biome, float chance)
	{
		int index = subbiomes.size() - 1;
		maxSubBiomeChance += subbiomes.get(index).chance;
		biome.parrent = this;
		subbiomes.add(new Subbiome(biome, maxSubBiomeChance));
	}
	
	public NetherBiome getSubBiome(Random random)
	{
		float chance = random.nextFloat() * maxSubBiomeChance;
		for (Subbiome biome: subbiomes)
			if (biome.canGenerate(chance))
				return biome.biome;
		return this;
	}
	
	public NetherBiome getParrentBiome()
	{
		return this.parrent;
	}
	
	public boolean hasEdge()
	{
		return edge != null;
	}
	
	public boolean hasParrent()
	{
		return parrent != null;
	}
	
	public boolean isSame(NetherBiome biome)
	{
		return biome == this ||
				(biome.hasParrent() && biome.getParrentBiome() == this);// ||
				//(this.hasParrent() && this.getParrentBiome() == biome);
	}
	
	protected void addStructure(String name, IStructure structure, StructureType type, float density, boolean useNoise)
	{
		String group = "generator_" + getRegistryName();
		float dens = Config.getFloat(group, name + "_density", density);
		boolean limit = Config.getBoolean(group, name + "_limit", useNoise);
		this.addStructure(structure, type, dens, limit);
	}
	
	private void addStructure(IStructure structure, StructureType type, float density, boolean useNoise)
	{
		switch(type)
		{
		case CEIL:
			generatorsCeil.add(new StructureInfo(structure, density, useNoise));
			break;
		case FLOOR:
			generatorsFloor.add(new StructureInfo(structure, density, useNoise));
			break;
		case WALL:
			generatorsWall.add(new StructureInfo(structure, density, useNoise));
			break;
		default:
			break;
		}
		
	}
	
	protected enum StructureType
	{
		FLOOR,
		WALL,
		CEIL;
	}
	
	protected class StructureInfo
	{
		final IStructure structure;
		final float density;
		final boolean useNoise;
		final int id;
		
		StructureInfo(IStructure structure, float density, boolean useNoise)
		{
			this.structure = structure;
			this.density = density;
			this.useNoise = useNoise;
			id = structureID ++;
		}
		
		boolean canGenerate(Random random, BlockPos pos)
		{
			return (!useNoise || getFeatureNoise(pos, id) > 0.5) && random.nextFloat() < density;
		}
	}
	
	protected class Subbiome
	{
		NetherBiome biome;
		float chance;
		
		Subbiome(NetherBiome biome, float chance)
		{
			this.biome = biome;
			this.chance = chance;
		}
		
		public boolean canGenerate(float chance)
		{
			return chance < this.chance;
		}
	}
}
