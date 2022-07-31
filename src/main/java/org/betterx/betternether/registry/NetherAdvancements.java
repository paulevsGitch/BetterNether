package org.betterx.betternether.registry;

import org.betterx.bclib.api.v2.advancement.AdvancementManager;
import org.betterx.bclib.items.complex.EquipmentSet;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.advancements.BNCriterion;

import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class NetherAdvancements {
    public static void register() {
        ResourceLocation root = AdvancementManager.Builder
                .create(BetterNether.makeID("root"))
                .startDisplay(NetherBlocks.NETHER_GRASS)
                .frame(FrameType.TASK)
                .hideToast()
                .hideFromChat()
                .background(new ResourceLocation("textures/gui/advancements/backgrounds/nether.png"))
                .endDisplay()
                .addCriterion(
                        "entered_nether",
                        ChangeDimensionTrigger
                                .TriggerInstance
                                .changedDimensionTo(Level.NETHER)
                )
                .requirements(RequirementsStrategy.OR)
                .buildAndRegister();

        ResourceLocation blueObsidian = AdvancementManager.Builder
                .create(BetterNether.makeID("blue_obsidian"))
                .parent(root)
                .startDisplay(NetherBlocks.BLUE_OBSIDIAN)
                .endDisplay()
                .addCriterion("brew_blue", BNCriterion.BREW_BLUE_TRIGGER)
                .requirements(RequirementsStrategy.OR)
                .buildAndRegister();

        ResourceLocation obsidianBlocks = AdvancementManager.Builder
                .create(BetterNether.makeID("obsidian_blocks"))
                .parent(blueObsidian)
                .startDisplay(NetherBlocks.BLUE_OBSIDIAN_BRICKS)
                .endDisplay()
                .addInventoryChangedCriterion("made_brick", NetherBlocks.OBSIDIAN_BRICKS)
                .addInventoryChangedCriterion("made_tile", NetherBlocks.OBSIDIAN_TILE)
                .addInventoryChangedCriterion("made_small_tile", NetherBlocks.OBSIDIAN_TILE_SMALL)
                .addInventoryChangedCriterion("made_rods", NetherBlocks.OBSIDIAN_ROD_TILES)
                .addInventoryChangedCriterion("made_blue_brick", NetherBlocks.BLUE_OBSIDIAN_BRICKS)
                .addInventoryChangedCriterion("made_blue_tile", NetherBlocks.BLUE_OBSIDIAN_TILE)
                .addInventoryChangedCriterion("made_small_blue_tile", NetherBlocks.BLUE_OBSIDIAN_TILE_SMALL)
                .addInventoryChangedCriterion("made_blue_rods", NetherBlocks.BLUE_OBSIDIAN_ROD_TILES)
                .requirements(RequirementsStrategy.AND)
                .buildAndRegister();

        ResourceLocation makeCrying = AdvancementManager.Builder
                .create(BetterNether.makeID("make_crying"))
                .parent(blueObsidian)
                .startDisplay(NetherBlocks.BLUE_WEEPING_OBSIDIAN)
                .frame(FrameType.CHALLENGE)
                .endDisplay()
                .addCriterion(
                        "made_blue_crying",
                        BNCriterion.CONVERT_BY_LIGHTNING.match(NetherBlocks.BLUE_CRYING_OBSIDIAN)
                )
                .addCriterion(
                        "made_crying",
                        BNCriterion.CONVERT_BY_LIGHTNING.match(NetherBlocks.BLUE_WEEPING_OBSIDIAN)
                )
                .addCriterion(
                        "made_blue_weeping",
                        BNCriterion.CONVERT_BY_LIGHTNING.match(Blocks.CRYING_OBSIDIAN)
                )
                .addCriterion(
                        "made_weeping",
                        BNCriterion.CONVERT_BY_LIGHTNING.match(NetherBlocks.WEEPING_OBSIDIAN)
                )
                .requirements(RequirementsStrategy.AND)
                .rewardXP(500)
                .buildAndRegister();

        ResourceLocation city = AdvancementManager.Builder
                .create(BetterNether.makeID("city"))
                .parent(root)
                .startDisplay(NetherBlocks.CINCINNASITE_CARVED)
                .endDisplay()
                .addAtStructureCriterion("ncity", NetherStructures.CITY_STRUCTURE)
                .requirements(RequirementsStrategy.OR)
                .buildAndRegister();


        ResourceLocation rubyOre = AdvancementManager.Builder
                .create(BetterNether.makeID("ruby_ore"))
                .parent(root)
                .startDisplay(NetherItems.NETHER_RUBY)
                .endDisplay()
                .addInventoryChangedCriterion("ruby_ore", NetherItems.NETHER_RUBY)
                .requirements(RequirementsStrategy.OR)
                .buildAndRegister();

        ResourceLocation rubyTools = AdvancementManager.Builder
                .create(BetterNether.makeID("ruby_tools"))
                .parent(rubyOre)
                .startDisplay(NetherItems.NETHER_RUBY_SET.getSlot(EquipmentSet.PICKAXE_SLOT))
                .endDisplay()
                .addToolSetCriterion(NetherItems.NETHER_RUBY_SET)
                .requirements(RequirementsStrategy.OR)
                .buildAndRegister();

        ResourceLocation rubyGear = AdvancementManager.Builder
                .create(BetterNether.makeID("ruby_gear"))
                .parent(rubyTools)
                .startDisplay(NetherItems.NETHER_RUBY_SET.getSlot(EquipmentSet.CHESTPLATE_SLOT))
                .endDisplay()
                .addArmorSetCriterion(NetherItems.NETHER_RUBY_SET)
                .requirements(RequirementsStrategy.AND)
                .buildAndRegister();

        ResourceLocation cincinnasiteOre = AdvancementManager.Builder
                .create(BetterNether.makeID("cincinnasite_ore"))
                .parent(root)
                .startDisplay(NetherItems.CINCINNASITE_INGOT)
                .endDisplay()
                .addInventoryChangedCriterion("cincinnasite_ore", NetherItems.CINCINNASITE_INGOT)
                .requirements(RequirementsStrategy.OR)
                .buildAndRegister();

        ResourceLocation cincinnasiteTools = AdvancementManager.Builder
                .create(BetterNether.makeID("cincinnasite_tools"))
                .parent(cincinnasiteOre)
                .startDisplay(NetherItems.CINCINNASITE_SET.getSlot(EquipmentSet.PICKAXE_SLOT))
                .endDisplay()
                .addToolSetCriterion(NetherItems.CINCINNASITE_SET)
                .requirements(RequirementsStrategy.OR)
                .buildAndRegister();

        ResourceLocation cincinnasiteGear = AdvancementManager.Builder
                .create(BetterNether.makeID("cincinnasite_gear"))
                .parent(cincinnasiteTools)
                .startDisplay(NetherItems.CINCINNASITE_SET.getSlot(EquipmentSet.CHESTPLATE_SLOT))
                .endDisplay()
                .addArmorSetCriterion(NetherItems.CINCINNASITE_SET)
                .requirements(RequirementsStrategy.AND)
                .buildAndRegister();

        ResourceLocation cincinnasiteDiamondTools = AdvancementManager.Builder
                .create(BetterNether.makeID("cincinnasite_diamond_tools"))
                .parent(cincinnasiteTools)
                .startDisplay(NetherItems.CINCINNASITE_DIAMOND_SET.getSlot(EquipmentSet.PICKAXE_SLOT))
                .frame(FrameType.GOAL)
                .endDisplay()
                .addToolSetCriterion(NetherItems.CINCINNASITE_DIAMOND_SET)
                .requirements(RequirementsStrategy.AND)
                .buildAndRegister();

        ResourceLocation forge = AdvancementManager.Builder
                .create(BetterNether.makeID("cincinnasite_forge"))
                .parent(cincinnasiteOre)
                .startDisplay(NetherBlocks.CINCINNASITE_FORGE)
                .frame(FrameType.GOAL)
                .endDisplay()
                .addCriterion("use_forge", BNCriterion.USED_FORGE_ANY_TRIGGER)
                .requirements(RequirementsStrategy.OR)
                .buildAndRegister();

        ResourceLocation netherWood = AdvancementManager.Builder
                .create(BetterNether.makeID("nether_wood"))
                .parent(root)
                .startDisplay(NetherBlocks.MAT_WILLOW.getLog())
                .endDisplay()
                .addWoodCriterion(NetherBlocks.MAT_WILLOW)
                .addWoodCriterion(NetherBlocks.MAT_STALAGNATE)
                .addWoodCriterion(NetherBlocks.MAT_RUBEUS)
                .addWoodCriterion(NetherBlocks.MAT_WART)
                .addWoodCriterion(NetherBlocks.MAT_MUSHROOM_FIR)
                .addWoodCriterion(NetherBlocks.MAT_ANCHOR_TREE)
                .addWoodCriterion(NetherBlocks.MAT_NETHER_SAKURA)
                .addInventoryChangedCriterion(
                        "nether_reed",
                        NetherBlocks.MAT_REED.getStem(),
                        NetherBlocks.MAT_REED.getPlanks()
                )
                .addInventoryChangedCriterion(
                        "nether_mushroom",
                        NetherBlocks.MAT_NETHER_MUSHROOM.getStem(),
                        NetherBlocks.MAT_NETHER_MUSHROOM.getPlanks()
                )
                .addInventoryChangedCriterion(
                        "crimson",
                        Blocks.CRIMSON_STEM,
                        Blocks.CRIMSON_HYPHAE,
                        Blocks.CRIMSON_PLANKS
                )
                .addInventoryChangedCriterion(
                        "warped",
                        Blocks.WARPED_STEM,
                        Blocks.WARPED_HYPHAE,
                        Blocks.WARPED_PLANKS
                )
                .requirements(RequirementsStrategy.AND)
                .buildAndRegister();


        ResourceLocation allTheBiomes = AdvancementManager.Builder
                .create(BetterNether.makeID("all_the_biomes"))
                .parent(city)
                .startDisplay(NetherItems.NETHER_RUBY_SET.getSlot(EquipmentSet.BOOTS_SLOT))
                .frame(FrameType.CHALLENGE)
                .endDisplay()
                .addVisitBiomesCriterion(NetherBiomes.ALL_BN_BIOMES.stream().map(b -> b.getBiomeKey()).toList())
                .requirements(RequirementsStrategy.AND)
                .rewardXP(1500)
                .buildAndRegister();
    }
}
