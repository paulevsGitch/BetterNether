package org.betterx.betternether.registry;

import org.betterx.bclib.api.v2.ComposterAPI;
import org.betterx.bclib.blocks.SimpleLeavesBlock;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.mixin.common.BlockBehaviourAccessor;
import org.betterx.betternether.mixin.common.BlockBehaviourPropertiesAccessor;
import org.betterx.worlds.together.tag.v3.MineableTags;
import org.betterx.worlds.together.tag.v3.TagManager;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class NetherTags {
//	public static final Tag<Block> SOUL_GROUND_BLOCK = TagAPI.makeCommonBlockTag( "soul_ground");
//	public static final Tag<Block> NETHERRACK = TagAPI.makeCommonBlockTag("netherrack");
//	public static final Tag<Block> MYCELIUM = TagAPI.makeCommonBlockTag("nether_mycelium");
    //public static final TagAPI.TagKey<Block> NYLIUM = TagAPI.BLOCK_NYLIUM;

    public static final TagKey<Biome> BETTER_NETHER_DECORATIONS = TagManager.BIOMES.makeStructureTag(
            BetterNether.MOD_ID,
            "nether_decorations"
    );

    public static final TagKey<Item> SOUL_GROUND_ITEM = TagManager.ITEMS.makeCommonTag("soul_ground");

    public static final TagKey<Block> NETHER_FARMLAND = TagManager.BLOCKS.makeCommonTag("nether_farmland");


    public static final TagKey<Block> NETHER_SAND = TagManager.BLOCKS.makeCommonTag("nether_sand");
    public static final TagKey<Biome> BETTER_NETHER = TagManager.BIOMES.makeTag(BetterNether.MOD_ID, "biome");

    public static void register() {
        TagManager.BLOCKS.add(NETHER_SAND, Blocks.SOUL_SAND);

        NetherBlocks.getModBlocks().forEach(block -> {
            BlockBehaviour.Properties properties = ((BlockBehaviourAccessor) block).getProperties();
            Material material = ((BlockBehaviourPropertiesAccessor) properties).getMaterial();
            Item item = block.asItem();

            if (material.equals(Material.STONE) || material.equals(Material.METAL)) {
                TagManager.BLOCKS.add(MineableTags.PICKAXE, block);
            } else if (material.equals(Material.WOOD) || material.equals(Material.NETHER_WOOD)) {
                TagManager.BLOCKS.add(MineableTags.AXE, block);
            } else if (material.equals(Material.LEAVES) || material.equals(Material.PLANT) || material.equals(Materials.NETHER_PLANT) || material.equals(
                    Material.WATER_PLANT)) {
                TagManager.BLOCKS.add(MineableTags.HOE, block);
                TagManager.BLOCKS.add(BlockTags.LEAVES, block);
                ComposterAPI.allowCompost(0.3f, item);
            } else if (material.equals(Material.SAND)) {
                TagManager.BLOCKS.add(MineableTags.SHOVEL, block);
            }

            if (block instanceof LeavesBlock || block instanceof SimpleLeavesBlock) {
                TagManager.BLOCKS.add(BlockTags.LEAVES, block);
                ComposterAPI.allowCompost(0.3f, item);
            }
			/*else if (block instanceof BlockCincinnasitePedestal) {
				TagHelper.addTag(PEDESTALS, block);
			}*/

            Material mat = block.defaultBlockState().getMaterial();
            if (mat.equals(Material.PLANT) || material.equals(Materials.NETHER_PLANT) || mat.equals(Material.REPLACEABLE_PLANT)) {
                ComposterAPI.allowCompost(0.1f, item);
            }
        });

        TagManager.ITEMS.add(ItemTags.CHEST_BOATS, NetherItems.WARPED_CHEST_BOAT, NetherItems.CRIMSON_CHEST_BOAT);
        TagManager.BLOCKS.add(BlockTags.BEACON_BASE_BLOCKS, NetherBlocks.NETHER_RUBY_BLOCK);
    }
}