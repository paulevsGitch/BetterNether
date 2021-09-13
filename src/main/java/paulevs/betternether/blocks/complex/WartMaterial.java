package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockWartRoots;
import paulevs.betternether.blocks.BlockWartSeed;
import ru.bclib.complexmaterials.entry.BlockEntry;

public class WartMaterial extends RoofMaterial{
    public final static String BLOCK_SEED = BLOCK_OPTIONAL_SEED;
    public final static String BLOCK_ROOTS = "roots";

    public WartMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) {
        super(name, woodColor, planksColor);
    }

    @Override
    public WartMaterial init() {
        return (WartMaterial)super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);
        addBlockEntry(new BlockEntry(BLOCK_SEED, (complexMaterial, settings) -> {
            return new BlockWartSeed();
        }));

        addBlockEntry(new BlockEntry(BLOCK_ROOTS, false, (complexMaterial, settings) -> {
            return new BlockWartRoots();
        }));
    }

    public Block getRoot(){
        return getBlock(BLOCK_ROOTS);
    }

    public Block getSeed(){
        return getBlock(BLOCK_SEED);
    }
}
