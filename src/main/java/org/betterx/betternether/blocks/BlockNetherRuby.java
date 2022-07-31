package org.betterx.betternether.blocks;

import org.betterx.bclib.interfaces.CustomItemProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BlockNetherRuby extends BlockBase implements CustomItemProvider {
    public BlockNetherRuby() {
        super(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK));
    }

    @Override
    public BlockItem getCustomItem(ResourceLocation blockID, Item.Properties settings) {
        return new BlockItem(this, settings.fireResistant());
    }
}
