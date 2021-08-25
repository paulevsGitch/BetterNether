package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.BlockStalagnateBowl;
import paulevs.betternether.blocks.BlockStalagnateSeed;
import paulevs.betternether.blocks.BlockStem;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.blocks.BaseSlabBlock;
import ru.bclib.blocks.BaseStairsBlock;
import ru.bclib.complexmaterials.entry.BlockEntry;
import ru.bclib.complexmaterials.entry.RecipeEntry;
import ru.bclib.recipes.GridRecipe;

public class RoofMaterial extends NetherWoodenMaterial {
	protected final static String BLOCK_ROOF = BLOCK_OPTIONAL_ROOF;
	protected final static String BLOCK_ROOF_STAIRS = BLOCK_OPTIONAL_ROOF_STAIRS;
	protected final static String BLOCK_ROOF_SLAB = BLOCK_OPTIONAL_ROOF_SLAB;
	public RoofMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
		super(name, woodColor, planksColor);
	}
	
	@Override
	public RoofMaterial init() {
		return (RoofMaterial)super.init();
	}
	
	@Override
	protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
		super.initDefault(blockSettings, itemSettings);
		
		addBlockEntry(new BlockEntry(BLOCK_ROOF, (complexMaterial, settings) -> {
			return new BaseBlock(FabricBlockSettings.copyOf(getBlock(BLOCK_PLANKS)));
		}));
		addBlockEntry(new BlockEntry(BLOCK_ROOF_STAIRS, (complexMaterial, settings) -> {
			return new BaseStairsBlock(getBlock(BLOCK_ROOF));
		}));
		addBlockEntry(new BlockEntry(BLOCK_ROOF_SLAB, (complexMaterial, settings) -> {
			return new BaseSlabBlock(getBlock(BLOCK_ROOF));
		}));
	}
	
	@Override
	public void initDefaultRecipes() {
		super.initDefaultRecipes();
		final Block planks = getBlock(BLOCK_PLANKS);
		final Block slab = getBlock(BLOCK_SLAB);
		
		if (Registry.BLOCK.getKey(slab) != Registry.BLOCK.getDefaultKey()) {
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
		}
	}
}
