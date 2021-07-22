package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.BetterNether;
import paulevs.betternether.recipes.RecipesHelper;
import paulevs.betternether.registry.NetherBlocks;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.blocks.BaseSlabBlock;
import ru.bclib.blocks.BaseStairsBlock;
import ru.bclib.config.Configs;
import ru.bclib.recipes.GridRecipe;

public class WoodenMaterial extends ru.bclib.blocks.complex.WoodenMaterial {
    public final static String NAME_ROOF = "roof";
    public final static String NAME_ROOF_STAIRS = "roof_stair";
    public final static String NAME_ROOF_SLAB = "roof_slab";
    public final Block roof;
    public final Block root_stairs;
    public final Block roof_slab;

    public WoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
        super(BetterNether.MOD_ID, name, woodColor, planksColor, "nether", NetherBlocks::registerBlockBCLib);

        roof = NetherBlocks.registerBlockBCLib(name + "_" + NAME_ROOF, new BaseBlock(FabricBlockSettings.copyOf(planks)));
        root_stairs = NetherBlocks.registerBlockBCLib(name + "_" + NAME_ROOF_STAIRS, new BaseStairsBlock(roof));
        roof_slab = NetherBlocks.registerBlockBCLib(name + "_" + NAME_ROOF_SLAB, new BaseSlabBlock(roof));


        RecipesHelper.makeRoofRecipe(planks, roof);
        GridRecipe.make(modID, name + "_" + NAME_ROOF_STAIRS, stairs).checkConfig(Configs.RECIPE_CONFIG).setOutputCount(4).setShape("#  ", "## ", "###").addMaterial('#', planks).setGroup(receipGroupPrefix + "_planks_stairs").build();
        GridRecipe.make(modID, name + "_" + NAME_ROOF_SLAB, slab).checkConfig(Configs.RECIPE_CONFIG).setOutputCount(6).setShape("###").addMaterial('#', planks).setGroup(receipGroupPrefix + "_planks_slabs").build();

    }
}
