package paulevs.betternether.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherFeatures;
import paulevs.betternether.registry.NetherStructures;
import paulevs.betternether.structures.IStructure;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.StructureWorld;
import paulevs.betternether.structures.decorations.StructureStalactiteCeil;
import paulevs.betternether.structures.decorations.StructureStalactiteFloor;
import paulevs.betternether.structures.plants.StructureWartCap;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.world.biomes.BCLBiome;

public abstract class NetherBiomeData {
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

	private ArrayList<StructureInfo> generatorsFloor = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsWall = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsCeil = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> generatorsLava = new ArrayList<StructureInfo>();

	private ArrayList<StructureInfo> buildGeneratorsFloor = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsCeil = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsLava = new ArrayList<StructureInfo>();
	private ArrayList<StructureInfo> buildGeneratorsUnder = new ArrayList<StructureInfo>();
	
	
	protected float plantDensity = 1.0001F;
	protected float noiseDensity = 0.3F;
	private final ResourceLocation id;
	
	private ArrayList<String> structures;
	private static final String DATA_RECORD = "NETHER_DATA";
	
	
	private static void addDefaultStructures(BCLBiomeBuilder builder) {
		//		IStructureFeatures sf = (IStructureFeatures)(Object)structureFeatures;
		//		addStructureFeature(sf.getRUINED_PORTAL_NETHER());
		//		addStructureFeature(sf.getNETHER_BRIDGE());
		//		addStructureFeature(sf.getBASTION_REMNANT());
		
		//TODO: 1.18 Missing Carvers
		//builder.carver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
		builder.feature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);
	}
	
	private static void addDefaultFeatures(BCLBiomeBuilder builder) {
		builder
			.defaultMushrooms()
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
			.feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED);
	}
	
	private static void addDefaultBNMobs(BCLBiomeBuilder builder){
		builder
			.spawn(EntityRegistry.FIREFLY, 5, 3, 6)
			.spawn(EntityRegistry.SKULL, 2, 2, 4)
			.spawn(EntityRegistry.NAGA, 8, 3, 5)
			.spawn(EntityRegistry.HYDROGEN_JELLYFISH, 5, 2, 5);
	}
	
	private static void addVanillaMobs(BCLBiomeBuilder builder) {
		builder
			.spawn(EntityType.GHAST, 50, 4, 4)
			.spawn(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4)
			.spawn(EntityType.MAGMA_CUBE, 2, 4, 4)
			.spawn(EntityType.ENDERMAN, 1, 4, 4)
			.spawn(EntityType.PIGLIN, 15, 4, 4)
			.spawn(EntityType.STRIDER, 60, 1, 2);
	}
	
	public static BCLBiome create(NetherBiomeData data){
		final ResourceLocation ID = data.id;
		Biome BASE_BIOME = NetherBiomes.netherWastes();
		
		BCLBiomeBuilder builder = BCLBiomeBuilder
			.start(ID)
			.category(BiomeCategory.NETHER)
			.temperature(BASE_BIOME.getBaseTemperature())
			.wetness(BASE_BIOME.getDownfall())
			.precipitation(Precipitation.NONE)
			.waterColor(BASE_BIOME.getWaterColor())
			.waterFogColor(BASE_BIOME.getWaterFogColor())
			.skyColor(BASE_BIOME.getSkyColor());
		
		if (data.hasVanillaStructures()) addDefaultStructures(builder);
		if (data.hasVanillaFeatures()) addDefaultFeatures(builder);
		if (data.hasVanillaOres()) builder.netherDefaultOres();
		
		if (data.spawnVanillaMobs()){
			addVanillaMobs(builder);
		}
		addDefaultBNMobs(builder);
		
		NetherFeatures.addDefaultFeatures(builder);
		data.addCustomBuildData(builder);
		BCLBiome biome = builder.build();
		data.build();
		biome.addCustomData(DATA_RECORD, data);
		
		NetherStructures.addDefaultFeatures(biome);
		return biome;
	}
	
	
	public static NetherBiomeData getCustomNetherData(BCLBiome biome){
		if (biome==null) return null;
		return biome.getCustomData(DATA_RECORD);
	}
	
	public NetherBiomeData(BiomeDefinition definition) {
		this("");
	}
	
	public NetherBiomeData(String name) {
		id = BetterNether.makeID(name.replace(' ', '_').toLowerCase());
		structures = new ArrayList<String>(DEF_STRUCTURES.length);
		
		addStructure("cap_gen", new StructureWartCap(), StructureType.WALL, 0.8F, true);
		
		if (hasBNStructures()) {
			for (String s : DEF_STRUCTURES)
				structures.add(s);
		}

		if (hasStalactites()) {
			addStructure("netherrack_stalactite", STALACTITE_NETHERRACK, StructureType.FLOOR, 0.05F, true);
			addStructure("glowstone_stalactite", STALACTITE_GLOWSTONE, StructureType.FLOOR, 0.01F, true);

			addStructure("netherrack_stalagmite", STALAGMITE_NETHERRACK, StructureType.CEIL, 0.01F, true);
			addStructure("glowstone_stalagmite", STALAGMITE_GLOWSTONE, StructureType.CEIL, 0.005F, true);
		}
	}
	
	public boolean spawnVanillaMobs(){
		return true;
	}
	
	public boolean hasVanillaFeatures(){
		return true;
	}
	
	public boolean hasVanillaOres(){
		return true;
	}
	
	public boolean hasVanillaStructures(){
		return true;
	}
	
	public boolean hasBNStructures(){
		return true;
	}
	
	public boolean hasStalactites(){
		return true;
	}
	
	public void addCustomBuildData(BCLBiomeBuilder builder){}

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
		return "generator.biome." + id.getNamespace() + "." + getRegistryName();
	}

	public void build() {
		final String group = getGroup();
		String[] structAll = Configs.BIOMES.getStringArray(group, "schematics", structures.toArray(new String[] {}));
		for (String struct : structAll) {
			structureFromString(struct);
		}
		setPlantDensity(Configs.BIOMES.getFloat(group, "plants_and_structures_density", 1));
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
		return id.getPath();
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
			case LAVA:
				generatorsLava.add(new StructureInfo(structure, density, useNoise));
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
	
	public boolean hasCeilStructures() {
		return !buildGeneratorsCeil.isEmpty();
	}
	
	public String getNamespace() {
		return id.getNamespace();
	}
	
}
