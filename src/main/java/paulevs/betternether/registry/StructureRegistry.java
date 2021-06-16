package paulevs.betternether.registry;

import com.google.common.collect.Maps;
import paulevs.betternether.structures.IStructure;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.bones.StructureBoneReef;
import paulevs.betternether.structures.decorations.StructureCrystal;
import paulevs.betternether.structures.decorations.StructureGeyser;
import paulevs.betternether.structures.decorations.StructureWartDeadwood;
import paulevs.betternether.structures.plants.*;

import java.util.Map;

public class StructureRegistry {
	private static final Map<StructureType, Map<String, IStructure>> REGISTRY;

	public static void register(String name, IStructure structure, StructureType type) {
		REGISTRY.get(type).put(name, structure);
	}

	public static IStructure getStructure(String name, StructureType type) {
		return REGISTRY.get(type).get(name);
	}

	static {
		REGISTRY = Maps.newHashMap();
		for (StructureType type : StructureType.values()) {
			Map<String, IStructure> list = Maps.newHashMap();
			REGISTRY.put(type, list);
		}

		register("agave", new StructureAgave(), StructureType.FLOOR);
		register("barrel_cactus", new StructureBarrelCactus(), StructureType.FLOOR);
		register("big_warped_tree", new StructureBigWarpedTree(), StructureType.FLOOR);
		register("black_apple", new StructureBlackApple(), StructureType.FLOOR);
		register("black_bush", new StructureBlackBush(), StructureType.FLOOR);
		register("bone_reef", new StructureBoneReef(), StructureType.FLOOR);
		register("bush_rubeus", new StructureRubeusBush(), StructureType.FLOOR);
		register("crimson_fungus", new StructureCrimsonFungus(), StructureType.FLOOR);
		register("crimson_glowing_tree", new StructureCrimsonGlowingTree(), StructureType.FLOOR);
		register("crimson_pinewood", new StructureCrimsonPinewood(), StructureType.FLOOR);
		register("crimson_roots", new StructureCrimsonRoots(), StructureType.FLOOR);
		register("egg_plant", new StructureEggPlant(), StructureType.FLOOR);
		register("geyser", new StructureGeyser(), StructureType.FLOOR);
		register("giant_mold", new StructureGiantMold(), StructureType.FLOOR);
		register("gray_mold", new StructureGrayMold(), StructureType.FLOOR);
		register("ink_bush", new StructureInkBush(), StructureType.FLOOR);
		register("jungle_plant", new StructureJunglePlant(), StructureType.FLOOR);
		register("large_brown_mushroom", new StructureMedBrownMushroom(), StructureType.FLOOR);
		register("large_red_mushroom", new StructureMedRedMushroom(), StructureType.FLOOR);
		register("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR);
		register("mushroom_fir", new StructureMushroomFir(), StructureType.FLOOR);
		register("nether_cactus", new StructureNetherCactus(), StructureType.FLOOR);
		register("nether_grass", new StructureNetherGrass(), StructureType.FLOOR);
		register("swamp_grass", new StructureSwampGrass(), StructureType.FLOOR);
		register("nether_reed", new StructureReeds(), StructureType.FLOOR);
		register("nether_wart", new StructureNetherWart(), StructureType.FLOOR);
		register("obsidian_crystals", new StructureCrystal(), StructureType.FLOOR);
		register("old_brown_mushrooms", new StructureOldBrownMushrooms(), StructureType.FLOOR);
		register("old_red_mushrooms", new StructureOldRedMushrooms(), StructureType.FLOOR);
		register("orange_mushroom", new StructureOrangeMushroom(), StructureType.FLOOR);
		register("red_mold", new StructureRedMold(), StructureType.FLOOR);
		register("rubeus_tree", new StructureRubeus(), StructureType.FLOOR);
		register("smoker", new StructureSmoker(), StructureType.FLOOR);
		register("soul_grass", new StructureSoulGrass(), StructureType.FLOOR);
		register("soul_lily", new StructureSoulLily(), StructureType.FLOOR);
		register("soul_vein", new StructureSoulVein(), StructureType.FLOOR);
		register("stalagnate", new StructureStalagnate(), StructureType.FLOOR);
		register("twisted_vine", new StructureTwistedVines(), StructureType.FLOOR);
		register("vanilla_mushrooms", new StructureVanillaMushroom(), StructureType.FLOOR);
		register("warped_fungus", new StructureWarpedFungus(), StructureType.FLOOR);
		register("warped_roots", new StructureWarpedRoots(), StructureType.FLOOR);
		register("wart_bush", new StructureWartBush(), StructureType.FLOOR);
		register("wart_deadwood", new StructureWartDeadwood(), StructureType.FLOOR);
		register("wart_seed", new StructureWartSeed(), StructureType.FLOOR);
		register("wart_tree", new StructureWartTree(), StructureType.FLOOR);
		register("willow", new StructureWillow(), StructureType.FLOOR);

		register("black_vine", new StructureBlackVine(), StructureType.CEIL);
		register("eye", new StructureEye(), StructureType.CEIL);
		register("flowered_vine", new StructureBloomingVine(), StructureType.CEIL);
		register("golden_vine", new StructureGoldenVine(), StructureType.CEIL);

		register("lucis", new StructureLucis(), StructureType.WALL);
		register("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL);
		register("wall_moss", new StructureWallMoss(), StructureType.WALL);
		register("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL);
	}
}