package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.BNBarStool;
import paulevs.betternether.blocks.BNNormalChair;
import paulevs.betternether.blocks.BNTaburet;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.api.TagAPI;
import ru.bclib.blocks.BaseBarkBlock;
import ru.bclib.blocks.BaseCraftingTableBlock;
import ru.bclib.blocks.BaseRotatedPillarBlock;
import ru.bclib.blocks.BaseStripableLogBlock;
import ru.bclib.blocks.StripableBarkBlock;
import ru.bclib.complexmaterials.ComplexMaterial;
import ru.bclib.complexmaterials.WoodenComplexMaterial;
import ru.bclib.complexmaterials.entry.BlockEntry;
import ru.bclib.complexmaterials.entry.RecipeEntry;
import ru.bclib.config.Configs;
import ru.bclib.config.PathConfig;
import ru.bclib.recipes.GridRecipe;
import ru.bclib.registry.BlockRegistry;
import ru.bclib.registry.ItemRegistry;

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
	
	public NetherWoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
		super(BetterNether.MOD_ID, name, "nether", woodColor, planksColor);
	}
	
	@Override
	public ComplexMaterial init(BlockRegistry blocksRegistry, ItemRegistry itemsRegistry, PathConfig recipeConfig) {
		return super.init(blocksRegistry, itemsRegistry, recipeConfig);
	}
	
	public NetherWoodenMaterial init() {
		return (NetherWoodenMaterial) super.init(NetherBlocks.getBlockRegistry(), NetherItems.getItemRegistry(), Configs.RECIPE_CONFIG);
	}
	
	@Override
	protected FabricBlockSettings getBlockSettings() {
		return FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS)
								  .materialColor(planksColor);
	}

	protected void _initBase(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
		super.initBase(blockSettings, itemSettings);
		final Tag.Named<Block> tagBlockLog = getBlockTag(TAG_LOGS);
		final Tag.Named<Item> tagItemLog = getItemTag(TAG_LOGS);
		
		replaceOrAddBlockEntry(
			new BlockEntry(BLOCK_STRIPPED_LOG, (complexMaterial, settings) -> new BaseRotatedPillarBlock(settings))
				.setBlockTags(BlockTags.LOGS, tagBlockLog)
				.setItemTags(ItemTags.LOGS, tagItemLog)
		);
		replaceOrAddBlockEntry(
			new BlockEntry(BLOCK_STRIPPED_BARK, (complexMaterial, settings) -> new BaseBarkBlock(settings))
				.setBlockTags(BlockTags.LOGS, tagBlockLog)
				.setItemTags(ItemTags.LOGS, tagItemLog)
		);
		
		replaceOrAddBlockEntry(
			new BlockEntry(BLOCK_LOG, (complexMaterial, settings) -> new BaseStripableLogBlock(woodColor, getBlock(BLOCK_STRIPPED_LOG)))
				.setBlockTags(BlockTags.LOGS, tagBlockLog)
				.setItemTags(ItemTags.LOGS, tagItemLog)
		);
		replaceOrAddBlockEntry(
			new BlockEntry(BLOCK_BARK, (complexMaterial, settings) -> new StripableBarkBlock(woodColor, getBlock(BLOCK_STRIPPED_BARK)))
				.setBlockTags(BlockTags.LOGS, tagBlockLog)
				.setItemTags(ItemTags.LOGS, tagItemLog)
		);
	}
	
	@Override
	protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
		_initBase(blockSettings, itemSettings);
		super.initStorage(blockSettings, itemSettings);

		addBlockEntry(new BlockEntry(BLOCK_CRAFTING_TABLE, (complexMaterial, settings) -> {
			return new BaseCraftingTableBlock(getBlock(BLOCK_PLANKS));
		}).setBlockTags(TagAPI.BLOCK_WORKBENCHES)
		  .setItemTags(TagAPI.ITEM_WORKBENCHES));
		
		addBlockEntry(new BlockEntry(BLOCK_TABURET, (complexMaterial, settings) -> {
			return new BNTaburet(getBlock(BLOCK_SLAB));
		}));
		addBlockEntry(new BlockEntry(BLOCK_CHAIR, (complexMaterial, settings) -> {
			return new BNNormalChair(getBlock(BLOCK_SLAB));
		}));
		addBlockEntry(new BlockEntry(BLOCK_BAR_STOOL, (complexMaterial, settings) -> {
			return new BNBarStool(getBlock(BLOCK_SLAB));
		}));
	}
	
	@Override
	public void initDefaultRecipes() {
		super.initDefaultRecipes();
		
		final Block planks = getPlanks();
		final Block slab = getSlab();
		
		
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
