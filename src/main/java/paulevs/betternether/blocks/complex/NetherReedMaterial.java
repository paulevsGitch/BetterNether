package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockNetherReed;
import paulevs.betternether.blocks.BlockReedsBlock;
import ru.bclib.api.tag.NamedBlockTags;
import ru.bclib.api.tag.NamedItemTags;
import ru.bclib.blocks.BaseDoorBlock;
import ru.bclib.blocks.BaseFenceBlock;
import ru.bclib.blocks.BaseGateBlock;
import ru.bclib.blocks.BaseLadderBlock;
import ru.bclib.blocks.BaseSignBlock;
import ru.bclib.blocks.BaseSlabBlock;
import ru.bclib.blocks.BaseStairsBlock;
import ru.bclib.blocks.BaseTrapdoorBlock;
import ru.bclib.blocks.BaseWoodenButtonBlock;
import ru.bclib.blocks.WoodenPressurePlateBlock;
import ru.bclib.complexmaterials.entry.BlockEntry;
import ru.bclib.complexmaterials.entry.RecipeEntry;
import ru.bclib.recipes.GridRecipe;

public class NetherReedMaterial extends RoofMaterial {
    public final static String BLOCK_STEM = BLOCK_OPTIONAL_STEM;

    public NetherReedMaterial() {
        super("nether_reed",  MaterialColor.COLOR_CYAN, MaterialColor.COLOR_CYAN);
    }

    @Override
    public NetherReedMaterial init() {
        return (NetherReedMaterial)super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(BLOCK_STEM, (complexMaterial, settings) -> {
            return new BlockNetherReed();
        }));
    }

    @Override
    protected void _initBase(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        addBlockEntry(new BlockEntry(BLOCK_PLANKS, (complexMaterial, settings) -> {
            return new BlockReedsBlock();
        }).setBlockTags(NamedBlockTags.PLANKS).setItemTags(NamedItemTags.PLANKS));

        addBlockEntry(new BlockEntry(BLOCK_STAIRS, (complexMaterial, settings) -> {
            return new BaseStairsBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.WOODEN_STAIRS, NamedBlockTags.STAIRS).setItemTags(NamedItemTags.WOODEN_STAIRS, NamedItemTags.STAIRS));
        addBlockEntry(new BlockEntry(BLOCK_SLAB, (complexMaterial, settings) -> {
            return new BaseSlabBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.WOODEN_SLABS, NamedBlockTags.SLABS).setItemTags(NamedItemTags.WOODEN_SLABS, NamedItemTags.SLABS));
        addBlockEntry(new BlockEntry(BLOCK_FENCE, (complexMaterial, settings) -> {
            return new BaseFenceBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.FENCES, NamedBlockTags.WOODEN_FENCES).setItemTags(NamedItemTags.FENCES, NamedItemTags.WOODEN_FENCES));
        addBlockEntry(new BlockEntry(BLOCK_GATE, (complexMaterial, settings) -> {
            return new BaseGateBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.FENCE_GATES));
        addBlockEntry(new BlockEntry(BLOCK_BUTTON, (complexMaterial, settings) -> {
            return new BaseWoodenButtonBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.BUTTONS, NamedBlockTags.WOODEN_BUTTONS).setItemTags(NamedItemTags.BUTTONS, NamedItemTags.WOODEN_BUTTONS));
        addBlockEntry(new BlockEntry(BLOCK_PRESSURE_PLATE, (complexMaterial, settings) -> {
            return new WoodenPressurePlateBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.PRESSURE_PLATES, NamedBlockTags.WOODEN_PRESSURE_PLATES).setItemTags(NamedItemTags.WOODEN_PRESSURE_PLATES));
        addBlockEntry(new BlockEntry(BLOCK_TRAPDOOR, (complexMaterial, settings) -> {
            return new BaseTrapdoorBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.TRAPDOORS, NamedBlockTags.WOODEN_TRAPDOORS).setItemTags(NamedItemTags.TRAPDOORS, NamedItemTags.WOODEN_TRAPDOORS));
        addBlockEntry(new BlockEntry(BLOCK_DOOR, (complexMaterial, settings) -> {
            return new BaseDoorBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.DOORS, NamedBlockTags.WOODEN_DOORS).setItemTags(NamedItemTags.DOORS, NamedItemTags.WOODEN_DOORS));


        addBlockEntry(new BlockEntry(BLOCK_LADDER, (complexMaterial, settings) -> {
            return new BaseLadderBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.CLIMBABLE));
        addBlockEntry(new BlockEntry(BLOCK_SIGN, (complexMaterial, settings) -> {
            return new BaseSignBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(NamedBlockTags.SIGNS).setItemTags(NamedItemTags.SIGNS));
    }

    @Override
    public void initDefaultRecipes() {
        Block planks = getBlock(BLOCK_PLANKS);
        addRecipeEntry(new RecipeEntry(BLOCK_PLANKS, (material, config, id) -> {
            Block stem = getStem();

            GridRecipe.make(id, planks)
                    .checkConfig(config)
                    .setOutputCount(1)
                    .setShape("##", "##")
                    .addMaterial('#', stem)
                    .setGroup(receipGroupPrefix +"_planks")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_STAIRS, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_STAIRS))
                    .checkConfig(config)
                    .setOutputCount(4)
                    .setShape("#  ", "## ", "###")
                    .addMaterial('#', planks)
                    .setGroup(receipGroupPrefix +"_planks_stairs")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_SLAB, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_SLAB))
                    .checkConfig(config)
                    .setOutputCount(6)
                    .setShape("###")
                    .addMaterial('#', planks)
                    .setGroup(receipGroupPrefix +"_planks_slabs")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_FENCE, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_FENCE))
                    .checkConfig(config)
                    .setOutputCount(3)
                    .setShape("#I#", "#I#")
                    .addMaterial('#', planks)
                    .addMaterial('I', Items.STICK)
                    .setGroup(receipGroupPrefix +"_planks_fences")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_GATE, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_GATE))
                    .checkConfig(config)
                    .setShape("I#I", "I#I")
                    .addMaterial('#', planks)
                    .addMaterial('I', Items.STICK)
                    .setGroup(receipGroupPrefix +"_planks_gates")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_BUTTON, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_BUTTON))
                    .checkConfig(config)
                    .setList("#")
                    .addMaterial('#', planks)
                    .setGroup(receipGroupPrefix +"_planks_buttons")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_PRESSURE_PLATE, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_PRESSURE_PLATE))
                    .checkConfig(config)
                    .setShape("##")
                    .addMaterial('#', planks)
                    .setGroup(receipGroupPrefix +"_planks_plates")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_TRAPDOOR, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_TRAPDOOR))
                    .checkConfig(config)
                    .setOutputCount(2)
                    .setShape("###", "###")
                    .addMaterial('#', planks)
                    .setGroup(receipGroupPrefix +"_trapdoors")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_DOOR, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_DOOR))
                    .checkConfig(config)
                    .setOutputCount(3)
                    .setShape("##", "##", "##")
                    .addMaterial('#', planks)
                    .setGroup(receipGroupPrefix +"_doors")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_CRAFTING_TABLE, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_CRAFTING_TABLE))
                    .checkConfig(config)
                    .setShape("##", "##")
                    .addMaterial('#', planks)
                    .setGroup(receipGroupPrefix +"_tables")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_LADDER, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_LADDER))
                    .checkConfig(config)
                    .setOutputCount(3)
                    .setShape("I I", "I#I", "I I")
                    .addMaterial('#', planks)
                    .addMaterial('I', Items.STICK)
                    .setGroup(receipGroupPrefix +"_ladders")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_SIGN, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_SIGN))
                    .checkConfig(config)
                    .setOutputCount(3)
                    .setShape("###", "###", " I ")
                    .addMaterial('#', planks)
                    .addMaterial('I', Items.STICK)
                    .setGroup(receipGroupPrefix +"_signs")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_CHEST, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_CHEST))
                    .checkConfig(config)
                    .setShape("###", "# #", "###")
                    .addMaterial('#', planks)
                    .setGroup(receipGroupPrefix +"_chests")
                    .build();
        }));
        addRecipeEntry(new RecipeEntry(BLOCK_BARREL, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_BARREL))
                    .checkConfig(config)
                    .setShape("#S#", "# #", "#S#")
                    .addMaterial('#', planks)
                    .addMaterial('S', getBlock(BLOCK_SLAB))
                    .setGroup(receipGroupPrefix +"_barrels")
                    .build();
        }));
    
        initDefaultFurniture();
        initRoofRecipes();
    }

    public Block getStem(){
        return getBlock(BLOCK_STEM);
    }
}
