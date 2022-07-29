package org.betterx.betternether.blocks.complex;

import org.betterx.bclib.blocks.BaseRotatedPillarBlock;
import org.betterx.bclib.complexmaterials.ComplexMaterial;
import org.betterx.bclib.complexmaterials.WoodenComplexMaterial;
import org.betterx.bclib.complexmaterials.entry.BlockEntry;
import org.betterx.bclib.complexmaterials.entry.ItemEntry;
import org.betterx.bclib.complexmaterials.entry.RecipeEntry;
import org.betterx.bclib.config.Configs;
import org.betterx.bclib.config.PathConfig;
import org.betterx.bclib.items.boat.BoatTypeOverride;
import org.betterx.bclib.recipes.GridRecipe;
import org.betterx.bclib.registry.BlockRegistry;
import org.betterx.bclib.registry.ItemRegistry;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BNBarStool;
import org.betterx.betternether.blocks.BNNormalChair;
import org.betterx.betternether.blocks.BNTaburet;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public class NetherWoodenMaterial extends WoodenComplexMaterial {
    protected final static String BLOCK_OPTIONAL_TRUNK = "trunk";
    protected final static String BLOCK_OPTIONAL_BRANCH = "branch";
    protected final static String BLOCK_OPTIONAL_ROOT = "roots";
    protected final static String BLOCK_OPTIONAL_SAPLING = "sapling";
    protected final static String BLOCK_OPTIONAL_SEED = "seed";
    protected final static String BLOCK_OPTIONAL_STEM = "stem";
    protected final static String BLOCK_OPTIONAL_ROOF = "roof";
    protected final static String BLOCK_OPTIONAL_ROOF_STAIRS = "roof_stairs";
    protected final static String BLOCK_OPTIONAL_ROOF_SLAB = "roof_slab";

    public final static String BLOCK_TABURET = "taburet";
    public final static String BLOCK_CHAIR = "chair";
    public final static String BLOCK_BAR_STOOL = "bar_stool";
    public final static String ITEM_BOAT = "boat";
    public final static String ITEM_CHEST_BOAT = "chest_boat";

    private BoatTypeOverride BOAT_TYPE;

    public NetherWoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
        super(BetterNether.MOD_ID, name, "nether", woodColor, planksColor);
    }

    @Override
    public ComplexMaterial init(BlockRegistry blocksRegistry, ItemRegistry itemsRegistry, PathConfig recipeConfig) {
        return super.init(blocksRegistry, itemsRegistry, recipeConfig);
    }

    public NetherWoodenMaterial init() {
        return (NetherWoodenMaterial) super.init(
                NetherBlocks.getBlockRegistry(),
                NetherItems.getItemRegistry(),
                Configs.RECIPE_CONFIG
        );
    }

    @Override
    protected FabricBlockSettings getBlockSettings() {
        return FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS)
                                  .mapColor(planksColor);
    }

    protected void _initBase(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initBase(blockSettings, itemSettings);
        final TagKey<Block> tagBlockLog = getBlockTag(TAG_LOGS);
        final TagKey<Item> tagItemLog = getItemTag(TAG_LOGS);

        replaceOrAddBlockEntry(
                new BlockEntry(BLOCK_STRIPPED_LOG, (complexMaterial, settings) -> new BaseRotatedPillarBlock(settings))
                        .setBlockTags(BlockTags.LOGS, tagBlockLog)
                        .setItemTags(ItemTags.LOGS, tagItemLog)
        );
        replaceOrAddBlockEntry(
                new BlockEntry(
                        BLOCK_STRIPPED_BARK,
                        (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseBarkBlock(settings)
                )
                        .setBlockTags(BlockTags.LOGS, tagBlockLog)
                        .setItemTags(ItemTags.LOGS, tagItemLog)
        );

        replaceOrAddBlockEntry(
                new BlockEntry(
                        BLOCK_LOG,
                        (complexMaterial, settings) -> new org.betterx.bclib.blocks.BaseStripableLogBlock(
                                woodColor,
                                getBlock(BLOCK_STRIPPED_LOG)
                        )
                )
                        .setBlockTags(BlockTags.LOGS, tagBlockLog)
                        .setItemTags(ItemTags.LOGS, tagItemLog)
        );
        replaceOrAddBlockEntry(
                new BlockEntry(
                        BLOCK_BARK,
                        (complexMaterial, settings) -> new org.betterx.bclib.blocks.StripableBarkBlock(
                                woodColor,
                                getBlock(
                                        BLOCK_STRIPPED_BARK)
                        )
                )
                        .setBlockTags(BlockTags.LOGS, tagBlockLog)
                        .setItemTags(ItemTags.LOGS, tagItemLog)
        );
    }

    protected void initBoats(FabricItemSettings itemSettings) {
        BOAT_TYPE = BoatTypeOverride.create(BetterNether.MOD_ID, baseName, this.getPlanks());

        addItemEntry(new ItemEntry(ITEM_BOAT, (cmx, settings) -> BOAT_TYPE.createItem(false)));
        addItemEntry(new ItemEntry(ITEM_CHEST_BOAT, (cmx, settings) -> BOAT_TYPE.createItem(true)));
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        _initBase(blockSettings, itemSettings);
        super.initStorage(blockSettings, itemSettings);
        initDecorations(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(
                BLOCK_TABURET,
                (complexMaterial, settings) -> new BNTaburet(getBlock(BLOCK_SLAB))
        ));
        addBlockEntry(new BlockEntry(
                BLOCK_CHAIR,
                (complexMaterial, settings) -> new BNNormalChair(getBlock(BLOCK_SLAB))
        ));
        addBlockEntry(new BlockEntry(
                BLOCK_BAR_STOOL,
                (complexMaterial, settings) -> new BNBarStool(getBlock(BLOCK_SLAB))
        ));

        initBoats(itemSettings);
    }

    public static void makeTaburetRecipe(PathConfig config, ResourceLocation id, Block taburet, Block planks) {
        GridRecipe.make(id, taburet)
                  .checkConfig(config)
                  .setShape("##", "II")
                  .addMaterial('#', planks)
                  .addMaterial('I', Items.STICK)
                  .setGroup("nether" + "_taburet")
                  .build();
    }

    public static void makeChairRecipe(PathConfig config, ResourceLocation id, Block chair, Block planks) {
        GridRecipe.make(id, chair)
                  .checkConfig(config)
                  .setShape("I ", "##", "II")
                  .addMaterial('#', planks)
                  .addMaterial('I', Items.STICK)
                  .setGroup("nether" + "_chair")
                  .build();
    }

    public static void makeBarStoolRecipe(PathConfig config, ResourceLocation id, Block barStool, Block planks) {
        GridRecipe.make(id, barStool)
                  .checkConfig(config)
                  .setShape("##", "II", "II")
                  .addMaterial('#', planks)
                  .addMaterial('I', Items.STICK)
                  .setGroup("nether" + "_bar_stool")
                  .build();
    }

    protected void initDefaultFurniture() {
        final Block slab = getSlab();

        if (Registry.BLOCK.getKey(slab) != Registry.BLOCK.getDefaultKey()) {
            addRecipeEntry(new RecipeEntry(BLOCK_TABURET, (material, config, id) -> {
                makeTaburetRecipe(config, id, getBlock(BLOCK_TABURET), slab);
            }));

            addRecipeEntry(new RecipeEntry(BLOCK_CHAIR, (material, config, id) -> {
                makeChairRecipe(config, id, getBlock(BLOCK_CHAIR), slab);
            }));

            addRecipeEntry(new RecipeEntry(BLOCK_BAR_STOOL, (material, config, id) -> {
                makeBarStoolRecipe(config, id, getBlock(BLOCK_BAR_STOOL), slab);
            }));
        }
    }

    @Override
    public void initDefaultRecipes() {
        super.initDefaultRecipes();
        initDefaultFurniture();
    }

    @Override
    protected void initFlammable(FlammableBlockRegistry registry) {
        //Nothing burns in the nether
    }

    public Block getPlanks() {
        return getBlock(BLOCK_PLANKS);
    }

    public Block getSlab() {
        return getBlock(BLOCK_SLAB);
    }

    public Block getLog() {
        return getBlock(BLOCK_LOG);
    }

    public Block getBark() {
        return getBlock(BLOCK_BARK);
    }

    public Block getStrippedLog() {
        return getBlock(BLOCK_STRIPPED_LOG);
    }

    public Block getStrippedBark() {
        return getBlock(BLOCK_STRIPPED_BARK);
    }
}
