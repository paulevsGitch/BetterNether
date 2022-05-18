package org.betterx.betternether.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;

import org.betterx.betternether.client.IRenderTypeable;

import java.util.Collections;
import java.util.List;

public class BlockBase extends Block implements IRenderTypeable {
    private boolean dropItself = true;
    private BNRenderLayer layer = BNRenderLayer.SOLID;

    public BlockBase(Properties settings) {
        super(settings);
    }

    public void setRenderLayer(BNRenderLayer layer) {
        this.layer = layer;
    }

    @Override
    public BNRenderLayer getRenderLayer() {
        return layer;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        if (dropItself)
            return Collections.singletonList(new ItemStack(this.asItem()));
        else
            return super.getDrops(state, builder);
    }

    public void setDropItself(boolean drop) {
        this.dropItself = drop;
    }

    /*
     * public int getLuminance(BlockState state) { return 0; }
     */
}
