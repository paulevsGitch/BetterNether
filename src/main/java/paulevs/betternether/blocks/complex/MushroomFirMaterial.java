package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockMushroomFir;
import paulevs.betternether.blocks.BlockMushroomFirSapling;
import paulevs.betternether.blocks.BlockStem;
import ru.bclib.complexmaterials.entry.BlockEntry;
import ru.bclib.complexmaterials.entry.RecipeEntry;
import ru.bclib.recipes.GridRecipe;

public class MushroomFirMaterial extends NetherWoodenMaterial {
    public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;
    public final static String BLOCK_STEM = BLOCK_OPTIONAL_STEM;
    public final static String BLOCK_TRUNK = BLOCK_OPTIONAL_TRUNK;

    public MushroomFirMaterial() {
        super("mushroom_fir", MaterialColor.COLOR_BLUE, MaterialColor.COLOR_BLUE);
    }

    @Override
    public MushroomFirMaterial init() {
        return (MushroomFirMaterial)super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);
        addBlockEntry(new BlockEntry(BLOCK_TRUNK, false, (complexMaterial, settings) -> {
            return new BlockMushroomFir();
        }));

        addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> {
            return new BlockMushroomFirSapling();
        }));

        addBlockEntry(new BlockEntry(BLOCK_STEM, (complexMaterial, settings) -> {
            return new BlockStem(MaterialColor.TERRACOTTA_BLACK);
        }));
    }

    @Override
    public void initDefaultRecipes() {
        addRecipeEntry(new RecipeEntry(BLOCK_LOG, (material, config, id) -> {
            final Block log = getBlock(BLOCK_LOG);
            final Block stem = getBlock(BLOCK_STEM);

            GridRecipe.make(id, log)
                    .checkConfig(config)
                    .setOutputCount(1)
                    .setShape("##", "##")
                    .addMaterial('#', stem)
                    .setGroup(receipGroupPrefix +"_planks")
                    .build();
        }));
    }

    public Block getStem(){
        return getBlock(BLOCK_STEM);
    }

    public Block getSapling(){
        return getBlock(BLOCK_SAPLING);
    }

    public Block geTrunk(){
        return getBlock(BLOCK_TRUNK);
    }
}