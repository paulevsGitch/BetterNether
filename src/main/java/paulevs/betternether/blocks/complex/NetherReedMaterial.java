package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockNetherReed;
import paulevs.betternether.blocks.BlockReedsBlock;
import ru.bclib.api.TagAPI;
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
        }).setBlockTags(TagAPI.NAMED_BLOCK_PLANKS).setItemTags(TagAPI.NAMED_ITEM_PLANKS));

        addBlockEntry(new BlockEntry(BLOCK_STAIRS, (complexMaterial, settings) -> {
            return new BaseStairsBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_WOODEN_STAIRS, TagAPI.NAMED_BLOCK_STAIRS).setItemTags(TagAPI.NAMED_ITEM_WOODEN_STAIRS, TagAPI.NAMED_ITEM_STAIRS));
        addBlockEntry(new BlockEntry(BLOCK_SLAB, (complexMaterial, settings) -> {
            return new BaseSlabBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_WOODEN_SLABS, TagAPI.NAMED_BLOCK_SLABS).setItemTags(TagAPI.NAMED_ITEM_WOODEN_SLABS, TagAPI.NAMED_ITEM_SLABS));
        addBlockEntry(new BlockEntry(BLOCK_FENCE, (complexMaterial, settings) -> {
            return new BaseFenceBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_FENCES, TagAPI.NAMED_BLOCK_WOODEN_FENCES).setItemTags(TagAPI.NAMED_ITEM_FENCES, TagAPI.NAMED_ITEM_WOODEN_FENCES));
        addBlockEntry(new BlockEntry(BLOCK_GATE, (complexMaterial, settings) -> {
            return new BaseGateBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_FENCE_GATES));
        addBlockEntry(new BlockEntry(BLOCK_BUTTON, (complexMaterial, settings) -> {
            return new BaseWoodenButtonBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_BUTTONS, TagAPI.NAMED_BLOCK_WOODEN_BUTTONS).setItemTags(TagAPI.NAMED_ITEM_BUTTONS, TagAPI.NAMED_ITEM_WOODEN_BUTTONS));
        addBlockEntry(new BlockEntry(BLOCK_PRESSURE_PLATE, (complexMaterial, settings) -> {
            return new WoodenPressurePlateBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_PRESSURE_PLATES, TagAPI.NAMED_BLOCK_WOODEN_PRESSURE_PLATES).setItemTags(TagAPI.NAMED_ITEM_WOODEN_PRESSURE_PLATES));
        addBlockEntry(new BlockEntry(BLOCK_TRAPDOOR, (complexMaterial, settings) -> {
            return new BaseTrapdoorBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_TRAPDOORS, TagAPI.NAMED_BLOCK_WOODEN_TRAPDOORS).setItemTags(TagAPI.NAMED_ITEM_TRAPDOORS, TagAPI.NAMED_ITEM_WOODEN_TRAPDOORS));
        addBlockEntry(new BlockEntry(BLOCK_DOOR, (complexMaterial, settings) -> {
            return new BaseDoorBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_DOORS, TagAPI.NAMED_BLOCK_WOODEN_DOORS).setItemTags(TagAPI.NAMED_ITEM_DOORS, TagAPI.NAMED_ITEM_WOODEN_DOORS));


        addBlockEntry(new BlockEntry(BLOCK_LADDER, (complexMaterial, settings) -> {
            return new BaseLadderBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_CLIMBABLE));
        addBlockEntry(new BlockEntry(BLOCK_SIGN, (complexMaterial, settings) -> {
            return new BaseSignBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.NAMED_BLOCK_SIGNS).setItemTags(TagAPI.NAMED_ITEM_SIGNS));
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
