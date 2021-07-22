package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registry.NetherBlocks;

import java.util.function.BiFunction;

public class WoodenMaterial extends ru.bclib.blocks.complex.WoodenMaterial {
    @Override
    protected Block newComposter(FabricBlockSettings materialPlanks, MaterialColor woodColor) {
        return null;
    }

    @Override
    protected Block newShelf(FabricBlockSettings materialPlanks, MaterialColor woodColor) {
        return null;
    }


    public WoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
        super(BetterNether.MOD_ID, name, woodColor, planksColor, "nether", NetherBlocks::registerBlockBCLib);
    }
}
