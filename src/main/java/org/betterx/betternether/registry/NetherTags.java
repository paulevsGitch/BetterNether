package org.betterx.betternether.registry;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import org.betterx.bclib.api.BonemealAPI;
import org.betterx.bclib.api.ComposterAPI;
import org.betterx.bclib.api.tag.CommonBlockTags;
import org.betterx.bclib.api.tag.NamedBlockTags;
import org.betterx.bclib.api.tag.NamedMineableTags;
import org.betterx.bclib.api.tag.TagAPI;
import org.betterx.bclib.blocks.SimpleLeavesBlock;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BlockTerrain;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.mixin.common.BlockBehaviourAccessor;
import org.betterx.betternether.mixin.common.BlockBehaviourPropertiesAccessor;

public class NetherTags {
//	public static final Tag<Block> SOUL_GROUND_BLOCK = TagAPI.makeCommonBlockTag( "soul_ground");
//	public static final Tag<Block> NETHERRACK = TagAPI.makeCommonBlockTag("netherrack");
//	public static final Tag<Block> MYCELIUM = TagAPI.makeCommonBlockTag("nether_mycelium");
    //public static final TagAPI.TagKey<Block> NYLIUM = TagAPI.BLOCK_NYLIUM;

    public static final TagKey<Item> SOUL_GROUND_ITEM = TagAPI.makeCommonItemTag("soul_ground");

    public static final TagKey<Block> NETHER_FARMLAND = TagAPI.makeCommonBlockTag("nether_farmland");
    public static final TagKey<Block> NETHER_FARMLAND_LOCATION = TagAPI.makeCommonBlockTag("nether_farmland");


    public static final TagKey<Block> NETHER_SAND = TagAPI.makeCommonBlockTag("nether_sand");
    public static final TagKey<Block> NETHER_SAND_LOCATION = TagAPI.makeCommonBlockTag("nether_sand");
    public static final TagKey<Biome> BETTER_NETHER = TagAPI.makeBiomeTag(BetterNether.MOD_ID, "biome");

    public static void register() {
        TagAPI.addBlockTag(NETHER_SAND_LOCATION, Blocks.SOUL_SAND);
        TagAPI.addBlockTag(CommonBlockTags.IS_OBSIDIAN, Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN);

        NetherBlocks.getModBlocks().forEach(block -> {
            BlockBehaviour.Properties properties = ((BlockBehaviourAccessor) block).getProperties();
            Material material = ((BlockBehaviourPropertiesAccessor) properties).getMaterial();
            Item item = block.asItem();

            if (material.equals(Material.STONE) || material.equals(Material.METAL)) {
                TagAPI.addBlockTag(NamedMineableTags.PICKAXE, block);
            } else if (material.equals(Material.WOOD) || material.equals(Material.NETHER_WOOD)) {
                TagAPI.addBlockTag(NamedMineableTags.AXE, block);
            } else if (material.equals(Material.LEAVES) || material.equals(Material.PLANT) || material.equals(Materials.NETHER_PLANT) || material.equals(
                    Material.WATER_PLANT)) {
                TagAPI.addBlockTag(NamedMineableTags.HOE, block);
                TagAPI.addBlockTag(NamedBlockTags.LEAVES, block);
                ComposterAPI.allowCompost(0.3f, item);
            } else if (material.equals(Material.SAND)) {
                TagAPI.addBlockTag(NamedMineableTags.SHOVEL, block);
            }

            if (block instanceof BlockTerrain) {
                BonemealAPI.addSpreadableBlock(block, Blocks.NETHERRACK);
            } else if (block instanceof LeavesBlock || block instanceof SimpleLeavesBlock) {
                TagAPI.addBlockTag(NamedBlockTags.LEAVES, block);
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
    }
}