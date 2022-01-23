package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.BlockStalagnateBowl;
import paulevs.betternether.blocks.BlockStalagnateSeed;
import paulevs.betternether.blocks.BlockStem;
import ru.bclib.api.tag.NamedBlockTags;
import ru.bclib.complexmaterials.entry.BlockEntry;
import ru.bclib.complexmaterials.entry.RecipeEntry;
import ru.bclib.recipes.GridRecipe;

public class StalagnateMaterial extends RoofMaterial{
	public final static String BLOCK_BOWL = "bowl";
	public final static String BLOCK_TRUNK = BLOCK_OPTIONAL_TRUNK;
	public final static String BLOCK_SEED = BLOCK_OPTIONAL_SEED;
	public final static String BLOCK_STEM = BLOCK_OPTIONAL_STEM;
	
	public StalagnateMaterial() {
		super("stalagnate", MaterialColor.TERRACOTTA_LIGHT_GREEN, MaterialColor.TERRACOTTA_LIGHT_GREEN);
	}
	
	@Override
	public StalagnateMaterial init() {
		return (StalagnateMaterial)super.init();
	}
	
	@Override
	protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
		super.initDefault(blockSettings, itemSettings);
		
		addBlockEntry(new BlockEntry(BLOCK_STEM, (complexMaterial, settings) ->  new BlockStem(woodColor)));
		addBlockEntry(new BlockEntry(BLOCK_TRUNK, false, (complexMaterial, settings) -> new BlockStalagnate()).setBlockTags(NamedBlockTags.CLIMBABLE));
		addBlockEntry(new BlockEntry(BLOCK_SEED, (complexMaterial, settings) -> new BlockStalagnateSeed()));
		addBlockEntry(new BlockEntry(BLOCK_BOWL, false, (complexMaterial, settings) -> new BlockStalagnateBowl(getBlock(BLOCK_OPTIONAL_TRUNK))));
	}
	
	@Override
	public void initDefaultRecipes() {
		super.initDefaultRecipes();
		
		addRecipeEntry(new RecipeEntry(BLOCK_LOG, (material, config, id) -> {
			Block stem = getStem();
			
			GridRecipe.make(id, getBlock(BLOCK_LOG))
					  .checkConfig(config)
					  .setOutputCount(1)
					  .setShape("##", "##")
					  .addMaterial('#', stem)
					  .setGroup(receipGroupPrefix + "_logs")
					  .build();
		}));
	}
	
	public Block getTrunk() {
		return getBlock(BLOCK_TRUNK);
	}
	
	public Block getStem() {
		return getBlock(BLOCK_STEM);
	}
	
	public Block getBowl() {
		return getBlock(BLOCK_BOWL);
	}
	
	public Block getSeed() {
		return getBlock(BLOCK_SEED);
	}
}
