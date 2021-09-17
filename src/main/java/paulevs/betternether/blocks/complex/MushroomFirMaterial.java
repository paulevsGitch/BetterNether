package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockMushroomFir;
import paulevs.betternether.blocks.BlockMushroomFirSapling;
import paulevs.betternether.blocks.BlockStem;
import ru.bclib.complexmaterials.entry.BlockEntry;

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