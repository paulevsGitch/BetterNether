package paulevs.betternether.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;
import ru.bclib.config.Configs;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.StructureWorld;
import paulevs.betternether.world.structures.decorations.StructureStalactiteCeil;
import paulevs.betternether.world.structures.decorations.StructureStalactiteFloor;
import paulevs.betternether.world.structures.plants.StructureWartCap;
import ru.bclib.world.biomes.BCLBiome;

public abstract class NetherBiome extends BCLBiome{
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
	
	protected static final StructureStalactiteFloor STALACTITE_NETHERRACK = new StructureStalactiteFloor(NetherBlocks.NETHERRACK_STALACTITE, null);
	protected static final StructureStalactiteFloor STALACTITE_GLOWSTONE = new StructureStalactiteFloor(NetherBlocks.GLOWSTONE_STALACTITE, Blocks.GLOWSTONE);
	protected static final StructureStalactiteFloor STALACTITE_BLACKSTONE = new StructureStalactiteFloor(NetherBlocks.BLACKSTONE_STALACTITE, Blocks.BLACKSTONE, Blocks.BLACKSTONE, Blocks.NETHERRACK);
	protected static final StructureStalactiteFloor STALACTITE_BASALT = new StructureStalactiteFloor(NetherBlocks.BASALT_STALACTITE, Blocks.BASALT, Blocks.BASALT, Blocks.NETHERRACK);
	
	protected static final StructureStalactiteCeil STALAGMITE_NETHERRACK = new StructureStalactiteCeil(NetherBlocks.NETHERRACK_STALACTITE, null);
	protected static final StructureStalactiteCeil STALAGMITE_GLOWSTONE = new StructureStalactiteCeil(NetherBlocks.GLOWSTONE_STALACTITE, Blocks.GLOWSTONE);
	protected static final StructureStalactiteCeil STALAGMITE_BLACKSTONE = new StructureStalactiteCeil(NetherBlocks.BLACKSTONE_STALACTITE, Blocks.BLACKSTONE, Blocks.BLACKSTONE, Blocks.NETHERRACK);
	protected static final StructureStalactiteCeil STALAGMITE_BASALT = new StructureStalactiteCeil(NetherBlocks.BASALT_STALACTITE, Blocks.BASALT, Blocks.BASALT, Blocks.NETHERRACK);
	
	
	private static final OpenSimplexNoise SCATTER = new OpenSimplexNoise(1337);
	private static int structureID = 0;

	private final ArrayList<StructureInfo> generatorsFloor = new ArrayList<>();
	private final ArrayList<StructureInfo> generatorsWall = new ArrayList<>();
	private final ArrayList<StructureInfo> generatorsCeil = new ArrayList<>();
	private final ArrayList<StructureInfo> generatorsLava = new ArrayList<>();

	private final ArrayList<StructureInfo> buildGeneratorsFloor = new ArrayList<>();
	private final ArrayList<StructureInfo> buildGeneratorsCeil = new ArrayList<>();
	private final ArrayList<StructureInfo> buildGeneratorsLava = new ArrayList<>();
	private final ArrayList<StructureInfo> buildGeneratorsUnder = new ArrayList<>();
	
	
	protected float plantDensity = 1.0001F;
	protected float noiseDensity = 0.3F;
	
	private final ArrayList<String> structures;
	
	protected NetherBiome(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		structures = new ArrayList<>(DEF_STRUCTURES.length);
		
		addStructure("cap_gen", new StructureWartCap(), StructureType.WALL, 0.8F, true);
		
		if (hasBNStructures()) {
			Collections.addAll(structures, DEF_STRUCTURES);
		}

		if (hasStalactites()) {
			addStructure("netherrack_stalactite", STALACTITE_NETHERRACK, StructureType.FLOOR, 0.05F, true);
			addStructure("glowstone_stalactite", STALACTITE_GLOWSTONE, StructureType.FLOOR, 0.01F, true);

			addStructure("netherrack_stalagmite", STALAGMITE_NETHERRACK, StructureType.CEIL, 0.01F, true);
			addStructure("glowstone_stalagmite", STALAGMITE_GLOWSTONE, StructureType.CEIL, 0.005F, true);
		}
		
		onInit();
		setupFromConfig();
		
		final String structureGroup = configGroup() + ".structures" ;
		List<String> structAll = Configs.BIOMES_CONFIG.getStringArray(structureGroup, "schematics", structures);
		for (String struct : structAll) {
			structureFromString(struct);
		}
		
		final String group = configGroup();
		setPlantDensity(Configs.BIOMES_CONFIG.getFloat(group, "plants_and_structures_density", getPlantDensity()));
		setNoiseDensity(Configs.BIOMES_CONFIG.getFloat(group, "noise_density", getNoiseDensity()));
	}
	
	protected abstract void onInit();
	
	public boolean hasBNStructures(){
		return true;
	}
	
	public boolean hasStalactites(){
		return true;
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

	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {}

	public void genFloorObjects(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		for (StructureInfo info : generatorsFloor)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random, MAX_HEIGHT, context);
	}

	public void genWallObjects(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		for (StructureInfo info : generatorsWall)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random, MAX_HEIGHT, context);
	}

	public void genCeilObjects(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		for (StructureInfo info : generatorsCeil)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random, MAX_HEIGHT, context);
	}

	public void genLavaObjects(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		for (StructureInfo info : generatorsLava)
			if (info.canGenerate(random, pos))
				info.structure.generate(world, pos, random, MAX_HEIGHT, context);
	}

	protected static double getFeatureNoise(BlockPos pos, int id) {
		return SCATTER.eval(pos.getX() * 0.1, pos.getY() * 0.1 + id * 10, pos.getZ() * 0.1);
	}
	
	
	protected void addStructure(String name, IStructure structure, StructureType type, float density, boolean useNoise) {
		String group = configGroup() + ".structures." + type.getName() + "." + name;
		float dens = Configs.BIOMES_CONFIG.getFloat(group, "density", density);
		boolean limit = Configs.BIOMES_CONFIG.getBoolean(group, "limit", useNoise);
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
			case LAVA:
				generatorsLava.add(new StructureInfo(structure, density, useNoise));
				break;
			default:
				break;
		}
	}

	protected void addStructures(String... structures) {
		this.structures.addAll(Arrays.asList(structures));
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

	protected static String structureFormat(String name, int offset, StructureType type, float chance) {
		return String.format(Locale.ROOT, "name: %s; offset: %d; type: %s; chance: %f", name, offset, type.getName(), chance);
	}

	public void genFloorBuildings(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsFloor);
	}
	
	public void genCeilBuildings(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsCeil);
	}

	public void genLavaBuildings(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsLava);
	}

	public void genUnderBuildings(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		chancedStructure(world, pos, random, MAX_HEIGHT, context, buildGeneratorsUnder);
	}

	private void chancedStructure(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context, List<StructureInfo> infoList) {
		float chance = getLastChance(infoList);
		if (chance > 0) {
			float rnd = random.nextFloat() * chance;
			for (StructureInfo info : infoList)
				if (rnd <= info.density) {
					info.structure.generate(world, pos, random, MAX_HEIGHT, context);
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
	
	public boolean hasCeilStructures() {
		return !buildGeneratorsCeil.isEmpty();
	}
	
}
