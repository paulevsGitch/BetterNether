package org.betterx.betternether.registry;

import com.google.common.collect.Maps;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.bones.StructureBoneReef;
import org.betterx.betternether.world.structures.decorations.StructureCrystal;
import org.betterx.betternether.world.structures.decorations.StructureGeyser;
import org.betterx.betternether.world.structures.decorations.StructureWartDeadwood;
import org.betterx.betternether.world.structures.plants.*;

import java.util.Map;

public class StructureRegistry {
    private static final Map<StructurePlacementType, Map<String, IStructure>> REGISTRY;

    public static void register(String name, IStructure structure, StructurePlacementType type) {
        REGISTRY.get(type).put(name, structure);
    }

    public static IStructure getStructure(String name, StructurePlacementType type) {
        return REGISTRY.get(type).get(name);
    }

    static {
        REGISTRY = Maps.newHashMap();
        for (StructurePlacementType type : StructurePlacementType.values()) {
            Map<String, IStructure> list = Maps.newHashMap();
            REGISTRY.put(type, list);
        }

        register("agave", new StructureAgave(), StructurePlacementType.FLOOR);
        register("barrel_cactus", new StructureBarrelCactus(), StructurePlacementType.FLOOR);
        register("big_warped_tree", new StructureBigWarpedTree(), StructurePlacementType.FLOOR);
        register("black_apple", new StructureBlackApple(), StructurePlacementType.FLOOR);
        register("black_bush", new StructureBlackBush(), StructurePlacementType.FLOOR);
        register("bone_reef", new StructureBoneReef(), StructurePlacementType.FLOOR);
        register("bush_rubeus", new StructureRubeusBush(), StructurePlacementType.FLOOR);
        register("crimson_fungus", new StructureCrimsonFungus(), StructurePlacementType.FLOOR);
        register("crimson_glowing_tree", new StructureCrimsonGlowingTree(), StructurePlacementType.FLOOR);
        register("crimson_pinewood", new StructureCrimsonPinewood(), StructurePlacementType.FLOOR);
        register("crimson_roots", new StructureCrimsonRoots(), StructurePlacementType.FLOOR);
        register("egg_plant", new StructureEggPlant(), StructurePlacementType.FLOOR);
        register("geyser", new StructureGeyser(), StructurePlacementType.FLOOR);
        register("giant_mold", new StructureGiantMold(), StructurePlacementType.FLOOR);
        register("gray_mold", new StructureGrayMold(), StructurePlacementType.FLOOR);
        register("ink_bush", new StructureInkBush(), StructurePlacementType.FLOOR);
        register("jungle_plant", new StructureJunglePlant(), StructurePlacementType.FLOOR);
        register("large_brown_mushroom", new StructureMedBrownMushroom(), StructurePlacementType.FLOOR);
        register("large_red_mushroom", new StructureMedRedMushroom(), StructurePlacementType.FLOOR);
        register("magma_flower", new StructureMagmaFlower(), StructurePlacementType.FLOOR);
        register("mushroom_fir", new StructureMushroomFir(), StructurePlacementType.FLOOR);
        register("nether_cactus", new StructureNetherCactus(), StructurePlacementType.FLOOR);
        register("nether_grass", new StructureNetherGrass(), StructurePlacementType.FLOOR);
        register("swamp_grass", new StructureSwampGrass(), StructurePlacementType.FLOOR);
        register("nether_reed", new StructureReeds(), StructurePlacementType.FLOOR);
        register("nether_wart", new StructureNetherWart(), StructurePlacementType.FLOOR);
        register("obsidian_crystals", new StructureCrystal(), StructurePlacementType.FLOOR);
        register("old_brown_mushrooms", new StructureOldBrownMushrooms(), StructurePlacementType.FLOOR);
        register("old_red_mushrooms", new StructureOldRedMushrooms(), StructurePlacementType.FLOOR);
        register("orange_mushroom", new StructureOrangeMushroom(), StructurePlacementType.FLOOR);
        register("red_mold", new StructureRedMold(), StructurePlacementType.FLOOR);
        register("rubeus_tree", new StructureRubeus(), StructurePlacementType.FLOOR);
        register("smoker", new StructureSmoker(), StructurePlacementType.FLOOR);
        register("soul_grass", new StructureSoulGrass(), StructurePlacementType.FLOOR);
        register("soul_lily", new StructureSoulLily(), StructurePlacementType.FLOOR);
        register("soul_vein", new StructureSoulVein(), StructurePlacementType.FLOOR);
        register("stalagnate", new StructureStalagnate(), StructurePlacementType.FLOOR);
        register("twisted_vine", new StructureTwistedVines(), StructurePlacementType.FLOOR);
        register("vanilla_mushrooms", new StructureVanillaMushroom(), StructurePlacementType.FLOOR);
        register("warped_fungus", new StructureWarpedFungus(), StructurePlacementType.FLOOR);
        register("warped_roots", new StructureWarpedRoots(), StructurePlacementType.FLOOR);
        register("wart_bush", new StructureWartBush(), StructurePlacementType.FLOOR);
        register("wart_deadwood", new StructureWartDeadwood(), StructurePlacementType.FLOOR);
        register("wart_seed", new StructureWartSeed(), StructurePlacementType.FLOOR);
        register("wart_tree", new StructureWartTree(), StructurePlacementType.FLOOR);
        register("willow", new StructureWillow(), StructurePlacementType.FLOOR);

        register("black_vine", new StructureBlackVine(), StructurePlacementType.CEIL);
        register("eye", new StructureEye(), StructurePlacementType.CEIL);
        register("flowered_vine", new StructureBloomingVine(), StructurePlacementType.CEIL);
        register("golden_vine", new StructureGoldenVine(), StructurePlacementType.CEIL);

        register("lucis", new StructureLucis(), StructurePlacementType.WALL);
        register("wall_brown_mushroom", new StructureWallBrownMushroom(), StructurePlacementType.WALL);
        register("wall_moss", new StructureWallMoss(), StructurePlacementType.WALL);
        register("wall_red_mushroom", new StructureWallRedMushroom(), StructurePlacementType.WALL);
    }
}