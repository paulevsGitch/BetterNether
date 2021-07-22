package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.BNBarStool;
import paulevs.betternether.blocks.BNChair;
import paulevs.betternether.blocks.BNNormalChair;
import paulevs.betternether.blocks.BNTaburet;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.blocks.BlockStem;
import paulevs.betternether.recipes.RecipesHelper;
import paulevs.betternether.registry.NetherBlocks;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.blocks.BaseSlabBlock;
import ru.bclib.blocks.BaseStairsBlock;
import ru.bclib.config.Configs;
import ru.bclib.recipes.GridRecipe;

public class WoodenMaterial extends ru.bclib.blocks.complex.WoodenMaterial {
    public final static String NAME_STEM = "stem";
    public final static String NAME_ROOF = "roof";
    public final static String NAME_ROOF_STAIRS = "roof_stair";
    public final static String NAME_ROOF_SLAB = "roof_slab";
    public final static String NAME_TABURET = "taburet";
    public final static String NAME_CHAIR = "chair";
    public final static String NAME_BAR_STOOL = "bar_stool";

    public final Block main;
    public final Block stem;

    public final Block roof;
    public final Block root_stairs;
    public final Block roof_slab;

    public final Block taburet;
    public final Block chair;
    public final Block bar_stool;

    public WoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
        super(BetterNether.MOD_ID, name, woodColor, planksColor, "nether", NetherBlocks::registerBlockBCLib);


        main = NetherBlocks.registerBlockNI(name , new BlockStalagnate());
        stem = NetherBlocks.registerBlockBCLib(name + "_" + NAME_STEM, new BlockStem(woodColor));

        roof = NetherBlocks.registerBlockBCLib(name + "_" + NAME_ROOF, new BaseBlock(FabricBlockSettings.copyOf(planks)));
        root_stairs = NetherBlocks.registerBlockBCLib(name + "_" + NAME_ROOF_STAIRS, new BaseStairsBlock(roof));
        roof_slab = NetherBlocks.registerBlockBCLib(name + "_" + NAME_ROOF_SLAB, new BaseSlabBlock(roof));

        taburet = NetherBlocks.registerBlockBCLib(name + "_" + NAME_TABURET, new BNTaburet(slab));
        chair = NetherBlocks.registerBlockBCLib(name + "_" + NAME_CHAIR, new BNNormalChair(slab));
        bar_stool = NetherBlocks.registerBlockBCLib(name + "_" + NAME_BAR_STOOL, new BNBarStool(slab));


        GridRecipe.make(modID, name + "_" + NAME_ROOF_STAIRS, stairs).checkConfig(Configs.RECIPE_CONFIG).setOutputCount(4).setShape( "# #", "###", " # ").addMaterial('#', planks).setGroup(receipGroupPrefix + "_planks_roof").build();
        GridRecipe.make(modID, name + "_" + NAME_ROOF_STAIRS, stairs).checkConfig(Configs.RECIPE_CONFIG).setOutputCount(4).setShape("#  ", "## ", "###").addMaterial('#', planks).setGroup(receipGroupPrefix + "_planks_stairs").build();
        GridRecipe.make(modID, name + "_" + NAME_ROOF_SLAB, slab).checkConfig(Configs.RECIPE_CONFIG).setOutputCount(6).setShape("###").addMaterial('#', planks).setGroup(receipGroupPrefix + "_planks_slabs").build();

        RecipesHelper.makeTaburetRecipe(slab, taburet);
        RecipesHelper.makeChairRecipe(slab, chair);
        RecipesHelper.makeBarStoolRecipe(slab, bar_stool);
    }
}
