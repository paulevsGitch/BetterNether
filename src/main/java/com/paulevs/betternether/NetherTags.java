package com.paulevs.betternether;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class NetherTags {
    public static void init ()
    {
        Blocks.init();
        Items.init();
    }
	
	public static class Blocks {
		private static void init(){}
		
		public static final IOptionalNamedTag<Block> SOUL_GROUND = tag("soul_ground");
		public static final IOptionalNamedTag<Block> NETHERRACK = tag("netherrack");
		public static final IOptionalNamedTag<Block> MYCELIUM = tag("nether_mycelium");
		public static final IOptionalNamedTag<Block> NYLIUM = tag("nylium");

        private static IOptionalNamedTag<Block> tag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation(BetterNether.MOD_ID, name));
        }
	}
	
	public static class Items {
		private static void init(){}
		
		public static final IOptionalNamedTag<Item> SOUL_GROUND = tag("soul_ground");
		
        private static IOptionalNamedTag<Item> tag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation(BetterNether.MOD_ID, name));
        }
	}
}
