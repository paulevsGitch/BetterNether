package paulevs.betternether.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.config.Configs;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.structures.IStructure;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.StructureWorld;
import paulevs.betternether.structures.decorations.StructureStalactiteCeil;
import paulevs.betternether.structures.decorations.StructureStalactiteFloor;
import paulevs.betternether.structures.plants.StructureWartCap;

public class NetherBiome {
	private static final OpenSimplexNoise SCATTER = new OpenSimplexNoise(1337);
	private static int structureID = 0;

	private ArrayList<StructureInfo> generatorsFloor = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsWall = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsCeil = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsLava = new ArrayList<StructureInfo>();

	private ArrayList<StructureInfo> buildGeneratorsFloor = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsCeil = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsLava = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsUnder = new ArrayList<StructureInfo>();

	protected final ResourceLocation mcID;
	protected final Biome biome;
	
	protected float maxSubBiomeChance = 1;
	protected float plantDensity = 1.0001F;
	protected float noiseDensity = 0.3F;
	protected float genChance = 1;
	protected float edgeSize;
	
	protected List<Subbiome> subbiomes;
	protected NetherBiome biomeParent;
	protected NetherBiome edge;

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
			structureFormat("spawn_altar_ladder", -5, StructureType.FLOOR, 1),

			structureFormat("ghast_hive", -20, StructureType.CEIL, 1F),

			structureFormat("lava/pyramid_1", -1, StructureType.LAVA, 1F),
			structureFormat("lava/pyramid_2", -1, StructureType.LAVA, 1F),
			structureFormat("lava/pyramid_3", -1, StructureType.LAVA, 1F),
			structureFormat("lava/pyramid_4", -1, StructureType.LAVA, 1F)
	};

	private ArrayList<String> structures;
	private Biome actualBiome;

	protected static final StructureStalactiteFloor STALACTITE_NETHERRACK = new StructureStalactiteFloor(NetherBlocks.NETHERRACK_STALACTITE, null);
	protected static final StructureStalactiteFloor STALACTITE_GLOWSTONE = new StructureStalactiteFloor(NetherBlocks.GLOWSTONE_STALACTITE, Blocks.GLOWSTONE);
	protected static final StructureStalactiteFloor STALACTITE_BLACKSTONE = new StructureStalactiteFloor(NetherBlocks.BLACKSTONE_STALACTITE, Blocks.BLACKSTONE, Blocks.BLACKSTONE, Blocks.NETHERRACK);
	protected static final StructureStalactiteFloor STALACTITE_BASALT = new StructureStalactiteFloor(NetherBlocks.BASALT_STALACTITE, Blocks.BASALT, Blocks.BASALT, Blocks.NETHERRACK);

	protected static final StructureStalactiteCeil STALAGMITE_NETHERRACK = new StructureStalactiteCeil(NetherBlocks.NETHERRACK_STALACTITE, null);
	protected static final StructureStalactiteCeil STALAGMITE_GLOWSTONE = new StructureStalactiteCeil(NetherBlocks.GLOWSTONE_STALACTITE, Blocks.GLOWSTONE);
	protected static final StructureStalactiteCeil STALAGMITE_BLACKSTONE = new StructureStalactiteCeil(NetherBlocks.BLACKSTONE_STALACTITE, Blocks.BLACKSTONE, Blocks.BLACKSTONE, Blocks.NETHERRACK);
	protected static final StructureStalactiteCeil STALAGMITE_BASALT = new StructureStalactiteCeil(NetherBlocks.BASALT_STALACTITE, Blocks.BASALT, Blocks.BASALT, Blocks.NETHERRACK);

	public NetherBiome(BiomeDefinition definition) {
		definition.addMobSpawn(EntityRegistry.FIREFLY, 5, 2, 6);
		definition.addMobSpawn(EntityRegistry.SKULL, 2, 2, 4);
		definition.addMobSpawn(EntityRegistry.NAGA, 20, 2, 4);
		definition.addMobSpawn(EntityRegistry.HYDROGEN_JELLYFISH, 5, 2, 5);

		biome = definition.build();
		mcID = definition.getID();

		subbiomes = new ArrayList<Subbiome>();
		addStructure("cap_gen", new StructureWartCap(), StructureType.WALL, 0.8F, true);
		subbiomes.add(new Subbiome(this, 1));

		structures = new ArrayList<String>(DEF_STRUCTURES.length);
		if (definition.hasBNStructures()) {
			for (String s : DEF_STRUCTURES)
				structures.add(s);
		}

		if (definition.hasStalactites()) {
			addStructure("netherrack_stalactite", STALACTITE_NETHERRACK, StructureType.FLOOR, 0.05F, true);
			addStructure("glowstone_stalactite", STALACTITE_GLOWSTONE, StructureType.FLOOR, 0.01F, true);

			addStructure("netherrack_stalagmite", STALAGMITE_NETHERRACK, StructureType.CEIL, 0.01F, true);
			addStructure("glowstone_stalagmite", STALAGMITE_GLOWSTONE, StructureType.CEIL, 0.005F, true);
		}
	}

	public void setPlantDensity(float density) {
		this.plantDensity = density * 1.0001F;
	}

	public float getPlantDensity() {
		return plantDensity;
	}

	public void setNoiseDensity(float density) {
		this.noiseDensity = 1 - density * 2;
	}

	public float getNoiseDensity() {
		return (1F - this.noiseDensity) / 2F;
	}

	private String getGroup() {
		return "generator.biome." + mcID.getNamespace() + "." + getRegistryName();
	}

	public void build() {
		String group = getGroup();
		String[] structAll = Configs.BIOMES.getStringArray(group, "schematics", structures.toArray(new String[] {}));
		for (String struct : structAll) {
			structureFromString(struct);
		}
		setNoiseDensity(Configs.BIOMES.getFloat(group, "noise_density", getNoiseDensity()));
	}

	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {}

	public void genFloorObjects(ServerLevelAccessor world, BlockPos pos, Random random) {
		for (StructureInfo info : generatorsFloor)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}

	public void genWallObjects(ServerLevelAccessor world, BlockPos pos, Random random) {
		for (StructureInfo info : generatorsWall)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}

	public void genCeilObjects(ServerLevelAccessor world, BlockPos pos, Random random) {
		for (StructureInfo info : generatorsCeil)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}

	public void genLavaObjects(ServerLevelAccessor world, BlockPos pos, Random random) {
		for (StructureInfo info : generatorsLava)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random);
	}

	protected static double getFeatureNoise(BlockPos pos, int id) {
		return SCATTER.eval(pos.getX() * 0.1, pos.getY() * 0.1 + id * 10, pos.getZ() * 0.1);
	}

	public String getRegistryName() {
		return mcID.getPath();
	}

	public NetherBiome getEdge() {
		return edge;
	}

	public void setEdge(NetherBiome edge) {
		this.edge = edge;
		edge.biomeParent = this;
	}

	public float getEdgeSize() {
		return edgeSize;
	}

	public void setEdgeSize(float size) {
		edgeSize = size;
	}

	public void addSubBiome(NetherBiome biome, float chance) {
		maxSubBiomeChance += chance;
		biome.biomeParent = this;
		subbiomes.add(new Subbiome(biome, maxSubBiomeChance));
	}

	public NetherBiome getSubBiome(Random random) {
		float chance = random.nextFloat() * maxSubBiomeChance;
		for (Subbiome biome : subbiomes)
			if (biome.canGenerate(chance))
				return biome.biome;
		return this;
	}

	public NetherBiome getParentBiome() {
		return this.biomeParent;
	}

	public boolean hasEdge() {
		return edge != null;
	}

	public boolean hasParentBiome() {
		return biomeParent != null;
	}

	public boolean isSame(NetherBiome biome) {
		return biome == this || (biome.hasParentBiome() && biome.getParentBiome() == this);
	}

	protected void addStructure(String name, IStructure structure, StructureType type, float density, boolean useNoise) {
		String group = getGroup() + ".procedural." + type.getName() + "." + name;
		float dens = Configs.BIOMES.getFloat(group, "density", density);
		boolean limit = Configs.BIOMES.getBoolean(group, "limit", useNoise);
		this.addStructure(structure, type, dens, limit);
	}

	private void addStructure(IStructure structure, StructureType type, float density, boolean useNoise) {
		switch (type) {
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

	protected void addWorldStructures(String... structures) {
		for (String s : structures)
			this.structures.add(s);
	}

	protected class StructureInfo {
		final IStructure structure;
		final float density;
		final boolean useNoise;
		final int id;

		StructureInfo(IStructure structure, float density, boolean useNoise) {
			this.structure = structure;
			this.density = density;
			this.useNoise = useNoise;
			id = structureID++;
		}

		boolean canGenerate(Random random, BlockPos pos) {
			return (!useNoise || getFeatureNoise(pos, id) > noiseDensity) && random.nextFloat() < density;
		}
	}

	protected class Subbiome {
		NetherBiome biome;
		float chance;

		Subbiome(NetherBiome biome, float chance) {
			this.biome = biome;
			this.chance = chance;
		}

		public boolean canGenerate(float chance) {
			return chance < this.chance;
		}
	}

	public boolean canGenerate(float chance) {
		return chance <= this.genChance;
	}

	public void setGenChance(float chance) {
		this.genChance = chance;
	}

	protected static String structureFormat(String name, int offset, StructureType type, float chance) {
		return String.format(Locale.ROOT, "name: %s; offset: %d; type: %s; chance: %f", name, offset, type.getName(), chance);
	}

	public void genFloorBuildings(ServerLevelAccessor world, BlockPos pos, Random random) {
		chancedStructure(world, pos, random, buildGeneratorsFloor);
	}
	
	public void genCeilBuildings(ServerLevelAccessor world, BlockPos pos, Random random) {
		chancedStructure(world, pos, random, buildGeneratorsCeil);
	}

	public void genLavaBuildings(ServerLevelAccessor world, BlockPos pos, Random random) {
		chancedStructure(world, pos, random, buildGeneratorsLava);
	}

	public void genUnderBuildings(ServerLevelAccessor world, BlockPos pos, Random random) {
		chancedStructure(world, pos, random, buildGeneratorsUnder);
	}

	private void chancedStructure(ServerLevelAccessor world, BlockPos pos, Random random, List<StructureInfo> infoList) {
		float chance = getLastChance(infoList);
		if (chance > 0) {
			float rnd = random.nextFloat() * chance;
			for (StructureInfo info : infoList)
				if (rnd <= info.density) {
					info.structure.generate(world, pos, random);
					return;
				}
		}
	}

	private void structureFromString(String structureString) {
		String[] args = structureString.split(";");

		String name = "";
		int offset = 0;
		StructureType type = StructureType.FLOOR;
		float chance = 0;

		for (String a : args) {
			if (a.contains("name:")) {
				name = a.replace("name:", "").trim();
			}
			else if (a.contains("offset:")) {
				offset = Integer.parseInt(a.replace("offset:", "").trim());
			}
			else if (a.contains("type:")) {
				type = StructureType.fromString(a);
			}
			else if (a.contains("chance:")) {
				chance = Float.parseFloat(a.replace("chance:", "").trim());
			}
		}

		if (!name.isEmpty()) {
			StructureWorld structure = new StructureWorld(name, offset, type);
			if (structure.loaded()) {
				List<StructureInfo> infoList = null;
				switch (structure.getType()) {
					case CEIL:
						infoList = buildGeneratorsCeil;
						break;
					case FLOOR:
						infoList = buildGeneratorsFloor;
						break;
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

	private float getLastChance(List<StructureInfo> info) {
		int size = info.size();
		return size > 0 ? info.get(size - 1).density : 0;
	}

	public Biome getBiome() {
		return biome;
	}

	public boolean hasCeilStructures() {
		return !buildGeneratorsCeil.isEmpty();
	}

	public String getNamespace() {
		return mcID.getNamespace();
	}

	@Override
	public String toString() {
		return mcID.toString();
	}

	public ResourceLocation getID() {
		return mcID;
	}

	public Biome getActualBiome() {
		return actualBiome;
	}

	public void setActualBiome(Biome actualBiome) {
		this.actualBiome = actualBiome;
	}
}
