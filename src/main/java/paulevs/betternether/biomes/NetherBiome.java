package paulevs.betternether.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.decorator.ChanceRangeDecoratorConfig;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import paulevs.betternether.config.Config;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.structures.IStructure;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.StructureWorld;
import paulevs.betternether.structures.plants.StructureWartCap;

public class NetherBiome extends Biome
{
	private static final OpenSimplexNoise SCATTER = new OpenSimplexNoise(1337);
	private static int structureID = 0;
	
	private ArrayList<StructureInfo> generatorsFloor = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsWall = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsCeil = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsLava = new ArrayList<StructureInfo>();
	//private ArrayList<StructureInfo> generatorsUnder = new ArrayList<StructureInfo>();
	
	private ArrayList<StructureInfo> buildGeneratorsFloor = new ArrayList<StructureInfo>();
	//private ArrayList<StructureInfo> buildGeneratorsWall = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsCeil = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsLava = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsUnder = new ArrayList<StructureInfo>();
	
	protected String name;
	protected NetherBiome edge;
	protected int edgeSize;
	protected List<Subbiome> subbiomes;
	protected NetherBiome parrent;
	protected float maxSubBiomeChance = 1;
	protected float genChance = 1;
	protected float noiseDensity = 0.3F;
	
	private static final String[] DEF_STRUCTURES = new String[] {
			structureFormat("altar_01", -2, StructureType.FLOOR, 1),
			structureFormat("altar_02", -4, StructureType.FLOOR, 1),
			structureFormat("altar_03", -3, StructureType.FLOOR, 1),
			structureFormat("altar_04", -3, StructureType.FLOOR, 1),
			structureFormat("altar_05", -2, StructureType.FLOOR, 1),
			structureFormat("altar_06", -2, StructureType.FLOOR, 1),
			structureFormat("altar_07", -2, StructureType.FLOOR, 1),
			structureFormat("altar_08", -2, StructureType.FLOOR, 1),
			structureFormat("portal_01", -4, StructureType.FLOOR, 1),
			structureFormat("portal_02", -3, StructureType.FLOOR, 1),
			structureFormat("garden_01", -3, StructureType.FLOOR, 1),
			structureFormat("garden_02", -2, StructureType.FLOOR, 1),
			structureFormat("pillar_01", -1, StructureType.FLOOR, 1),
			structureFormat("pillar_02", -1, StructureType.FLOOR, 1),
			structureFormat("pillar_03", -1, StructureType.FLOOR, 1),
			structureFormat("pillar_04", -1, StructureType.FLOOR, 1),
			structureFormat("pillar_05", -1, StructureType.FLOOR, 1),
			structureFormat("pillar_06", -1, StructureType.FLOOR, 1),
			structureFormat("respawn_point_01", -3, StructureType.FLOOR, 1),
			structureFormat("respawn_point_02", -2, StructureType.FLOOR, 1),
			structureFormat("respawn_point_03", -3, StructureType.FLOOR, 1),
			structureFormat("respawn_point_04", -2, StructureType.FLOOR, 1),
			structureFormat("spawn_altar_ladder", -2, StructureType.FLOOR, 1)
	};
	
	private ArrayList<String> structures;
	
	public NetherBiome(BiomeDefenition defenition)
	{
		super(defenition.buildBiomeSettings());
		
		this.addStructureFeature(Feature.NETHER_BRIDGE.configure(FeatureConfig.DEFAULT));
		this.addCarver(GenerationStep.Carver.AIR, configureCarver(Carver.NETHER_CAVE, new ProbabilityConfig(0.2F)));
		this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.SPRING_FEATURE.configure(DefaultBiomeFeatures.LAVA_SPRING_CONFIG).createDecoratedFeature(Decorator.COUNT_VERY_BIASED_RANGE.configure(new RangeDecoratorConfig(20, 8, 16, 256))));
		DefaultBiomeFeatures.addDefaultMushrooms(this);
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.NETHER_BRIDGE.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(DefaultBiomeFeatures.NETHER_SPRING_CONFIG).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(8, 4, 8, 128))));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.NETHER_FIRE_CONFIG).createDecoratedFeature(Decorator.FIRE.configure(new CountDecoratorConfig(10))));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.SOUL_FIRE_CONFIG).createDecoratedFeature(Decorator.FIRE.configure(new CountDecoratorConfig(10))));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.GLOWSTONE_BLOB.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.LIGHT_GEM_CHANCE.configure(new CountDecoratorConfig(10))));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.GLOWSTONE_BLOB.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(10, 0, 0, 128))));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).createDecoratedFeature(Decorator.CHANCE_RANGE.configure(new ChanceRangeDecoratorConfig(0.5F, 0, 0, 128))));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.configure(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).createDecoratedFeature(Decorator.CHANCE_RANGE.configure(new ChanceRangeDecoratorConfig(0.5F, 0, 0, 128))));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Target.NETHERRACK, Blocks.MAGMA_BLOCK.getDefaultState(), 33)).createDecoratedFeature(Decorator.MAGMA.configure(new CountDecoratorConfig(4))));
		this.addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.configure(DefaultBiomeFeatures.ENCLOSED_NETHER_SPRING_CONFIG).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(16, 10, 20, 128))));
		DefaultBiomeFeatures.addNetherOres(this);
		this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.GHAST, 50, 4, 4));
		this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4));
		this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.MAGMA_CUBE, 2, 4, 4));
		this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4));
		this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.PIGLIN, 15, 4, 4));
		
		this.name = defenition.getName();
		subbiomes = new ArrayList<Subbiome>();
		addStructure("cap_gen", new StructureWartCap(), StructureType.WALL, 0.8F, true);
		subbiomes.add(new Subbiome(this, 1));
		
		structures = new ArrayList<String>(DEF_STRUCTURES.length);
		for (String s: DEF_STRUCTURES)
			structures.add(s);
	}
	
	public void setNoiseDensity(float density)
	{
		this.noiseDensity = 1 - density * 2;
	}
	
	public float getNoiseDensity()
	{
		return (1F - this.noiseDensity) / 2F;
	}
	
	public void build()
	{
		String group = "generator_" + getRegistryName();
		String[] structAll = Config.getStringArray(group, "structures", structures.toArray(new String[] {}));
		for (String struct: structAll)
		{
			structureFromString(struct);
		}
		setNoiseDensity(Config.getFloat(group, "noise_density", getNoiseDensity()));
	}
	
	public void loadStructures()
	{
		
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
	
	public void genLavaObjects(IWorld world, BlockPos pos, Random random)
	{
		for (StructureInfo info: generatorsLava)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}
	
	/*public void genUnderObjects(IWorld world, BlockPos pos, Random random)
	{
		for (StructureInfo info: generatorsUnder)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}*/
	
	protected static double getFeatureNoise(BlockPos pos, int id)
	{
		return SCATTER.eval(pos.getX() * 0.1, pos.getY() * 0.1 + id * 10, pos.getZ() * 0.1);
	}
	
	public String getBiomeName()
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
		maxSubBiomeChance += chance;
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
	
	protected void addWorldStructures(String... structures)
	{
		for (String s: structures)
			this.structures.add(s);
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
			return (!useNoise || getFeatureNoise(pos, id) > noiseDensity) && random.nextFloat() < density;
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
	
	public boolean canGenerate(float chance)
	{
		return chance < this.genChance;
	}
	
	public void setGenChance(float chance)
	{
		this.genChance = chance;
	}
	
	protected static String structureFormat(String name, int offset, StructureType type, float chance)
	{
		return String.format(Locale.ROOT, "name: %s; offset: %d; type: %s; chance: %f", name, offset, type.getName(), chance);
	}

	public void genFloorBuildings(IWorld world, BlockPos pos, Random random)
	{
		chancedStructure(world, pos, random, buildGeneratorsFloor);
	}

	/*public void genWallBuildings(IWorld world, BlockPos pos, Random random)
	{
		chancedStructure(world, pos, random, worldGeneratorsWall);
	}*/
	
	public void genCeilBuildings(IWorld world, BlockPos pos, Random random)
	{
		chancedStructure(world, pos, random, buildGeneratorsCeil);
	}
	
	public void genLavaBuildings(IWorld world, BlockPos pos, Random random)
	{
		chancedStructure(world, pos, random, buildGeneratorsLava);
	}
	
	public void genUnderBuildings(IWorld world, BlockPos pos, Random random)
	{
		chancedStructure(world, pos, random, buildGeneratorsUnder);
	}
	
	private void chancedStructure(IWorld world, BlockPos pos, Random random, List<StructureInfo> infoList)
	{
		float chance = getLastChance(infoList);
		if (chance > 0)
		{
			float rnd = random.nextFloat() * chance;
			for (StructureInfo info: infoList)
				if (rnd <= info.density)
				{
					info.structure.generate(world, pos, random);
					return;
				}
		}
	}
	
	private void structureFromString(String structureString)
	{
		String[] args = structureString.split(";");
		
		String name = "";
		int offset = 0;
		StructureType type = StructureType.FLOOR;
		float chance = 0;
		
		for (String a: args)
		{
			if (a.contains("name:"))
			{
				name = a.replace("name:", "").trim();
			}
			else if (a.contains("offset:"))
			{
				offset = Integer.parseInt(a.replace("offset:", "").trim());
			}
			else if (a.contains("type:"))
			{
				type = StructureType.fromString(a);
			}
			else if (a.contains("chance:"))
			{
				chance = Float.parseFloat(a.replace("chance:", "").trim());
			}
		}
		
		if (!name.isEmpty())
		{
			StructureWorld structure = new StructureWorld(name, offset, type);
			if (structure.loaded())
			{
				List<StructureInfo> infoList = null;
				switch(structure.getType())
				{
				case CEIL:
					infoList = buildGeneratorsCeil;
					break;
				case FLOOR:
					infoList = buildGeneratorsFloor;
					break;
				//case WALL:
				//	infoList = buildGeneratorsWall;
				//	break;
				case LAVA:
					infoList = buildGeneratorsLava;
					break;
				case UNDER:
					infoList = buildGeneratorsUnder;
					break;
				default:
					break;
				}
				
				chance += getLastChance(infoList);
				StructureInfo info = new StructureInfo(structure, chance, false);
				infoList.add(info);
			}
		}
	}
	
	private float getLastChance(List<StructureInfo> info)
	{
		int size = info.size();
		return size > 0 ? info.get(size - 1).density : 0;
	}
	
	public Biome getBiome()
	{
		return this;
	}
}
