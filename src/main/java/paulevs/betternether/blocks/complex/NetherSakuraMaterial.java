package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockNetherSakuraSapling;
import ru.bclib.complexmaterials.entry.BlockEntry;

public class NetherSakuraMaterial extends NetherWoodenMaterial {
    public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;
    public NetherSakuraMaterial() {
        super("nether_sakura", MaterialColor.COLOR_PINK, MaterialColor.COLOR_BROWN);
    }

    @Override
    public NetherSakuraMaterial init() {
        return (NetherSakuraMaterial)super.init();
    }

    @Override
    protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
        super.initDefault(blockSettings, itemSettings);

        addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> {
            return new BlockNetherSakuraSapling();
        }));
    }

    public Block getSapling(){
        return getBlock(BLOCK_SAPLING);
    }

    public boolean isTreeLog(Block block) {
        return block == getLog() || block == getBark();
    }
}
