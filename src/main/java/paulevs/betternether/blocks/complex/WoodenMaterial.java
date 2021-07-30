package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.BNBarStool;
import paulevs.betternether.blocks.BNNormalChair;
import paulevs.betternether.blocks.BNTaburet;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.BlockStalagnateBowl;
import paulevs.betternether.blocks.BlockStem;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.api.TagAPI;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.blocks.BaseCraftingTableBlock;
import ru.bclib.blocks.BaseSlabBlock;
import ru.bclib.blocks.BaseStairsBlock;
import ru.bclib.complexmaterials.ComplexMaterial;
import ru.bclib.complexmaterials.entry.BlockEntry;
import ru.bclib.complexmaterials.entry.RecipeEntry;
import ru.bclib.config.Configs;
import ru.bclib.config.PathConfig;
import ru.bclib.recipes.GridRecipe;
import ru.bclib.registry.BlockRegistry;
import ru.bclib.registry.ItemRegistry;

public class WoodenMaterial extends ru.bclib.complexmaterials.WoodenComplexMaterial {
    public final static String BLOCK_TRUNK = "trunk";
    public final static String BLOCK_STEM = "stem";
    public final static String BLOCK_ROOF = "roof";
    public final static String BLOCK_ROOF_STAIRS = "roof_stair";
    public final static String BLOCK_ROOF_SLAB = "roof_slab";
    public final static String BLOCK_TABURET = "taburet";
    public final static String BLOCK_CHAIR = "chair";
    public final static String BLOCK_BAR_STOOL = "bar_stool";
    public final static String BLOCK_BOWL = "bowl";

    public WoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
        super(BetterNether.MOD_ID, name, "nether", woodColor, planksColor);
    }
    
    @Override
    public ComplexMaterial init(BlockRegistry blocksRegistry, ItemRegistry itemsRegistry, PathConfig recipeConfig) {
        return super.init(blocksRegistry, itemsRegistry, recipeConfig);
    }
    
    public WoodenMaterial init() {
        return (WoodenMaterial) super.init(NetherBlocks.getBlockRegistry(), NetherItems.getItemRegistry(), Configs.RECIPE_CONFIG);
    }
    
    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initBase(blockSettings, itemSettings);
        super.initStorage(blockSettings, itemSettings);
        
    
        addBlockEntry(new BlockEntry(BLOCK_CRAFTING_TABLE, (complexMaterial, settings) -> {
            return new BaseCraftingTableBlock(getBlock(BLOCK_PLANKS));
        }).setBlockTags(TagAPI.BLOCK_WORKBENCHES).setItemTags(TagAPI.ITEM_WORKBENCHES));
        addBlockEntry(new BlockEntry(BLOCK_STEM, (complexMaterial, settings) -> {
            return new BlockStem(woodColor);
        }));
    
        addBlockEntry(new BlockEntry(BLOCK_ROOF, (complexMaterial, settings) -> {
            return new BaseBlock(FabricBlockSettings.copyOf(getBlock(BLOCK_PLANKS)));
        }));
        addBlockEntry(new BlockEntry(BLOCK_ROOF_STAIRS, (complexMaterial, settings) -> {
            return new BaseStairsBlock(getBlock(BLOCK_ROOF));
        }));
        addBlockEntry(new BlockEntry(BLOCK_ROOF_SLAB, (complexMaterial, settings) -> {
            return new BaseSlabBlock(getBlock(BLOCK_ROOF));
        }));
    
        addBlockEntry(new BlockEntry(BLOCK_TABURET, (complexMaterial, settings) -> {
            return new BNTaburet(getBlock(BLOCK_SLAB));
        }));
        addBlockEntry(new BlockEntry(BLOCK_CHAIR, (complexMaterial, settings) -> {
            return new BNNormalChair(getBlock(BLOCK_SLAB));
        }));
        addBlockEntry(new BlockEntry(BLOCK_BAR_STOOL, (complexMaterial, settings) -> {
            return new BNBarStool(getBlock(BLOCK_SLAB));
        }));
    
    
        addBlockEntry(new BlockEntry(BLOCK_TRUNK, false, (complexMaterial, settings) -> {
            return new BlockStalagnate();
        }));
        addBlockEntry(new BlockEntry(BLOCK_BOWL, false, (complexMaterial, settings) -> {
            return new BlockStalagnateBowl(getBlock(WoodenMaterial.BLOCK_TRUNK));
        }));
    }
    
    @Override
    public void initDefaultRecipes() {
        super.initDefaultRecipes();
    
        Block planks = getBlock(BLOCK_PLANKS);
        Block slab = getBlock(BLOCK_SLAB);
        addRecipeEntry(new RecipeEntry(BLOCK_ROOF_STAIRS, (material, config, id) -> {
            Block log_stripped = getBlock(BLOCK_STRIPPED_LOG);
            Block bark_stripped = getBlock(BLOCK_STRIPPED_BARK);
            Block log = getBlock(BLOCK_LOG);
            Block bark = getBlock(BLOCK_BARK);
            GridRecipe.make(id, planks)
                      .checkConfig(config)
                      .setOutputCount(4)
                      .setList("#")
                      .addMaterial('#', log, bark, log_stripped, bark_stripped)
                      .setGroup("end_planks")
                      .build();
        }));
    
        addRecipeEntry(new RecipeEntry(BLOCK_ROOF, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_ROOF))
                      .checkConfig(config)
                      .setOutputCount(4)
                      .setShape("# #", "###", " # ")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_planks_roof")
                      .build();
        }));
    
        addRecipeEntry(new RecipeEntry(BLOCK_ROOF_STAIRS, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_ROOF_STAIRS))
                      .checkConfig(config)
                      .setOutputCount(4)
                      .setShape("#  ", "## ", "###")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_planks_roof_stairs")
                      .build();
        }));
        
        addRecipeEntry(new RecipeEntry(BLOCK_ROOF_SLAB, (material, config, id) -> {
            GridRecipe.make(id, getBlock(BLOCK_ROOF_SLAB))
                      .checkConfig(config)
                      .setOutputCount(6)
                      .setShape("###")
                      .addMaterial('#', planks)
                      .setGroup(receipGroupPrefix + "_planks_roof_slabs")
                      .build();
        }));
    
        if (Registry.BLOCK.getKey(slab) != Registry.BLOCK.getDefaultKey()) {
            addRecipeEntry(new RecipeEntry(BLOCK_TABURET, (material, config, id) -> {
                GridRecipe.make(id, getBlock(BLOCK_TABURET))
                          .checkConfig(config)
                          .setShape("##", "II")
                          .addMaterial('#', planks)
                          .addMaterial('I', Items.STICK)
                          .setGroup(receipGroupPrefix + "_taburet")
                          .build();
            }));
            
            addRecipeEntry(new RecipeEntry(BLOCK_CHAIR, (material, config, id) -> {
                GridRecipe.make(id, getBlock(BLOCK_CHAIR))
                          .checkConfig(config)
                          .setShape("I ", "##", "II")
                          .addMaterial('#', planks)
                          .addMaterial('I', Items.STICK)
                          .setGroup(receipGroupPrefix + "_chair")
                          .build();
            }));
            
            addRecipeEntry(new RecipeEntry(BLOCK_BAR_STOOL, (material, config, id) -> {
                GridRecipe.make(id, getBlock(BLOCK_BAR_STOOL))
                          .checkConfig(config)
                          .setShape("##", "II", "II")
                          .addMaterial('#', planks)
                          .addMaterial('I', Items.STICK)
                          .setGroup(receipGroupPrefix + "_bar_stool")
                          .build();
            }));
        }
        
    }
}
