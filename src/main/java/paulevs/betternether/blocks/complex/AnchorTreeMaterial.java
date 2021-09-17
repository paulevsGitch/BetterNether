package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockAnchorTreeSapling;
import ru.bclib.complexmaterials.entry.BlockEntry;

public class AnchorTreeMaterial  extends NetherWoodenMaterial {
    public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;
    public AnchorTreeMaterial() {
        super("anchor_tree", MaterialColor.COLOR_BLUE, MaterialColor.COLOR_GREEN);
    }

    @Override
    public AnchorTreeMaterial init() {
        return (AnchorTreeMaterial)super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> {
            return new BlockAnchorTreeSapling();
        }));
    }

    public Block getSapling(){
        return getBlock(BLOCK_SAPLING);
    }

    public boolean isTreeLog(Block block) {
        return block == getLog() || block == getBark();
    }
}
