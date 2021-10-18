package paulevs.betternether;

import java.util.Map;

import ru.bclib.api.datafixer.DataFixerAPI;
import ru.bclib.api.datafixer.Patch;

public class Patcher {
	public static void register() {
		DataFixerAPI.registerPatch(Patcher_001::new);
	}
}

//--- Level 01
class Patcher_001 extends Patch {
	public Patcher_001() {
		super(BetterNether.MOD_ID, "5.3.4");
	}
	
	@Override
	public Map<String, String> getIDReplacements() {
		return Map.ofEntries(
				Map.entry("betternether:chest", "bclib:chest"),
				Map.entry("betternether:barrel", "bclib:barrel"),
				Map.entry("betternether:sign", "bclib:sign"),
				// Stalagnate //
				Map.entry("betternether:striped_log_stalagnate", "betternether:stalagnate_stripped_log"),
				Map.entry("betternether:striped_bark_stalagnate", "betternether:stalagnate_stripped_bark"),
				Map.entry("betternether:stalagnate_planks_stairs", "betternether:stalagnate_stairs"),
				Map.entry("betternether:stalagnate_planks_slab", "betternether:stalagnate_slab"),
				Map.entry("betternether:stalagnate_planks_fence", "betternether:stalagnate_fence"),
				Map.entry("betternether:stalagnate_planks_gate", "betternether:stalagnate_gate"),
				Map.entry("betternether:stalagnate_planks_button", "betternether:stalagnate_button"),
				Map.entry("betternether:stalagnate_planks_plate", "betternether:stalagnate_plate"),
				Map.entry("betternether:stalagnate_planks_trapdoor", "betternether:stalagnate_trapdoor"),
				Map.entry("betternether:stalagnate_planks_door", "betternether:stalagnate_door"),
				Map.entry("betternether:crafting_table_stalagnate", "betternether:stalagnate_crafting_table"),
				Map.entry("betternether:sign_stalagnate", "betternether:stalagnate_sign"),
				Map.entry("betternether:chest_stalagnate", "betternether:stalagnate_chest"),
				Map.entry("betternether:barrel_stalagnate", "betternether:stalagnate_barrel"),
				Map.entry("betternether:roof_tile_stalagnate", "betternether:stalagnate_roof"),
				Map.entry("betternether:roof_tile_stalagnate_stairs", "betternether:stalagnate_roof_stairs"),
				Map.entry("betternether:roof_tile_stalagnate_slab", "betternether:stalagnate_roof_slab"),
				Map.entry("betternether:taburet_stalagnate", "betternether:stalagnate_taburet"),
				Map.entry("betternether:chair_stalagnate", "betternether:stalagnate_chair"),
				Map.entry("betternether:bar_stool_stalagnate", "betternether:stalagnate_bar_stool"),
				Map.entry("betternether:stalagnate", "betternether:stalagnate_trunk"),
				// Willow //
				Map.entry("betternether:striped_log_willow", "betternether:willow_stripped_log"),
				Map.entry("betternether:striped_bark_willow", "betternether:willow_stripped_bark"),
				Map.entry("betternether:crafting_table_willow", "betternether:willow_crafting_table"),
				Map.entry("betternether:sign_willow", "betternether:willow_sign"),
				Map.entry("betternether:chest_willow", "betternether:willow_chest"),
				Map.entry("betternether:barrel_willow", "betternether:willow_barrel"),
				Map.entry("betternether:roof_tile_willow", "betternether:willow_roof"),
				Map.entry("betternether:roof_tile_willow_stairs", "betternether:willow_roof_stairs"),
				Map.entry("betternether:roof_tile_willow_slab", "betternether:willow_roof_slab"),
				Map.entry("betternether:taburet_willow", "betternether:willow_taburet"),
				Map.entry("betternether:chair_willow", "betternether:willow_chair"),
				Map.entry("betternether:bar_stool_willow", "betternether:willow_bar_stool"),
				// Rubeus //
				Map.entry("betternether:striped_log_rubeus", "betternether:rubeus_stripped_log"),
				Map.entry("betternether:striped_bark_rubeus", "betternether:rubeus_stripped_bark"),
				Map.entry("betternether:crafting_table_rubeus", "betternether:rubeus_crafting_table"),
				Map.entry("betternether:sign_rubeus", "betternether:rubeus_sign"),
				Map.entry("betternether:chest_rubeus", "betternether:rubeus_chest"),
				Map.entry("betternether:barrel_rubeus", "betternether:rubeus_barrel"),
				Map.entry("betternether:taburet_rubeus", "betternether:rubeus_taburet"),
				Map.entry("betternether:chair_rubeus", "betternether:rubeus_chair"),
				Map.entry("betternether:bar_stool_rubeus", "betternether:rubeus_bar_stool"),
				// Wart //
				Map.entry("betternether:striped_log_wart", "betternether:wart_stripped_log"),
				Map.entry("betternether:striped_bark_wart", "betternether:wart_stripped_bark"),
				Map.entry("betternether:crafting_table_wart", "betternether:wart_crafting_table"),
				Map.entry("betternether:sign_wart", "betternether:wart_sign"),
				Map.entry("betternether:chest_wart", "betternether:wart_chest"),
				Map.entry("betternether:barrel_wart", "betternether:wart_barrel"),
				Map.entry("betternether:roof_tile_wart", "betternether:wart_roof"),
				Map.entry("betternether:roof_tile_wart_stairs", "betternether:wart_roof_stairs"),
				Map.entry("betternether:roof_tile_wart_slab", "betternether:wart_roof_slab"),
				Map.entry("betternether:taburet_wart", "betternether:wart_taburet"),
				Map.entry("betternether:chair_wart", "betternether:wart_chair"),
				Map.entry("betternether:bar_stool_wart", "betternether:wart_bar_stool"),
				// Mushroom Fir //
				Map.entry("betternether:mushroom_fir_wood", "betternether:mushroom_fir_bark"),
				Map.entry("betternether:striped_log_mushroom_fir", "betternether:mushroom_fir_stripped_log"),
				Map.entry("betternether:striped_bark_mushroom_fir", "betternether:mushroom_fir_stripped_bark"),
				Map.entry("betternether:crafting_table_mushroom_fir", "betternether:mushroom_fir_crafting_table"),
				Map.entry("betternether:sign_mushroom_fir", "betternether:mushroom_fir_sign"),
				Map.entry("betternether:chest_mushroom_fir", "betternether:mushroom_fir_chest"),
				Map.entry("betternether:barrel_mushroom_fir", "betternether:mushroom_fir_barrel"),
				Map.entry("betternether:taburet_mushroom_fir", "betternether:mushroom_fir_taburet"),
				Map.entry("betternether:chair_mushroom_fir", "betternether:mushroom_fir_chair"),
				Map.entry("betternether:bar_stool_mushroom_fir", "betternether:mushroom_fir_bar_stool"),
				Map.entry("betternether:mushroom_fir", "betternether:mushroom_fir_trunk"),
				// Nether Mushroom //
				Map.entry("betternether:mushroom_stem","betternether:nether_mushroom_stem"),
				Map.entry("betternether:mushroom_planks","betternether:nether_mushroom_planks"),
				Map.entry("betternether:mushroom_stairs","betternether:nether_mushroom_stairs"),
				Map.entry("betternether:mushroom_slab","betternether:nether_mushroom_slab"),
				Map.entry("betternether:mushroom_fence","betternether:nether_mushroom_fence"),
				Map.entry("betternether:mushroom_gate","betternether:nether_mushroom_gate"),
				Map.entry("betternether:mushroom_button","betternether:nether_mushroom_button"),
				Map.entry("betternether:mushroom_plate","betternether:nether_mushroom_plate"),
				Map.entry("betternether:mushroom_trapdoor","betternether:nether_mushroom_trapdoor"),
				Map.entry("betternether:mushroom_door","betternether:nether_mushroom_door"),
				Map.entry("betternether:crafting_table_mushroom","betternether:nether_mushroom_crafting_table"),
				Map.entry("betternether:chest_mushroom","betternether:nether_mushroom_chest"),
				Map.entry("betternether:barrel_mushroom","betternether:nether_mushroom_barrel"),
				Map.entry("betternether:taburet_mushroom","betternether:nether_mushroom_taburet"),
				Map.entry("betternether:chair_mushroom","betternether:nether_mushroom_chair"),
				Map.entry("betternether:bar_stool_mushroom","betternether:nether_mushroom_bar_stool"),
				Map.entry("betternether:mushroom_ladder","betternether:nether_mushroom_ladder"),
				Map.entry("betternether:sign_mushroom","betternether:nether_mushroom_sign"),
				// Nether Reed //
				Map.entry("betternether:nether_reed", "betternether:nether_reed_stem"),
				Map.entry("betternether:reeds_block", "betternether:nether_reed_planks"),
				Map.entry("betternether:reeds_stairs", "betternether:nether_reed_stairs"),
				Map.entry("betternether:reeds_slab", "betternether:nether_reed_slab"),
				Map.entry("betternether:reeds_fence", "betternether:nether_reed_fence"),
				Map.entry("betternether:reeds_gate", "betternether:nether_reed_gate"),
				Map.entry("betternether:reeds_button", "betternether:nether_reed_button"),
				Map.entry("betternether:reeds_plate", "betternether:nether_reed_plate"),
				Map.entry("betternether:reeds_trapdoor", "betternether:nether_reed_trapdoor"),
				Map.entry("betternether:reeds_door", "betternether:nether_reed_door"),
				Map.entry("betternether:roof_tile_reeds", "betternether:nether_reed_roof"),
				Map.entry("betternether:roof_tile_reeds_stairs", "betternether:nether_reed_roof_stairs"),
				Map.entry("betternether:roof_tile_reeds_slab", "betternether:nether_reed_roof_slab"),
				Map.entry("betternether:crafting_table_reed", "betternether:nether_reed_crafting_table"),
				Map.entry("betternether:chest_reed", "betternether:nether_reed_chest"),
				Map.entry("betternether:barrel_reed", "betternether:nether_reed_barrel"),
				Map.entry("betternether:taburet_reeds", "betternether:nether_reed_taburet"),
				Map.entry("betternether:chair_reeds", "betternether:nether_reed_chair"),
				Map.entry("betternether:bar_stool_reeds", "betternether:nether_reed_bar_stool"),
				Map.entry("betternether:reeds_ladder", "betternether:nether_reed_ladder"),
				Map.entry("betternether:sign_reed", "betternether:nether_reed_sign"),
				// Anchor Tree //
				Map.entry("betternether:striped_log_anchor_tree", "betternether:anchor_tree_stripped_log"),
				Map.entry("betternether:striped_bark_anchor_tree", "betternether:anchor_tree_stripped_bark"),
				Map.entry("betternether:crafting_table_anchor_tree", "betternether:anchor_tree_crafting_table"),
				Map.entry("betternether:sign_anchor_tree", "betternether:anchor_tree_sign"),
				Map.entry("betternether:chest_anchor_tree", "betternether:anchor_tree_chest"),
				Map.entry("betternether:barrel_anchor_tree", "betternether:anchor_tree_barrel"),
				Map.entry("betternether:taburet_anchor_tree", "betternether:anchor_tree_taburet"),
				Map.entry("betternether:chair_anchor_tree", "betternether:anchor_tree_chair"),
				Map.entry("betternether:bar_stool_anchor_tree", "betternether:anchor_tree_bar_stool"),
				// Nether Sakura Tree //
				Map.entry("betternether:striped_log_nether_sakura", "betternether:nether_sakura_stripped_log"),
				Map.entry("betternether:striped_bark_nether_sakura", "betternether:nether_sakura_stripped_bark"),
				Map.entry("betternether:crafting_table_nether_sakura", "betternether:nether_sakura_crafting_table"),
				Map.entry("betternether:sign_nether_sakura", "betternether:nether_sakura_sign"),
				Map.entry("betternether:chest_nether_sakura", "betternether:nether_sakura_chest"),
				Map.entry("betternether:barrel_nether_sakura", "betternether:nether_sakura_barrel"),
				Map.entry("betternether:taburet_nether_sakura", "betternether:nether_sakura_taburet"),
				Map.entry("betternether:chair_nether_sakura", "betternether:nether_sakura_chair"),
				Map.entry("betternether:bar_stool_nether_sakura", "betternether:nether_sakura_bar_stool"),
				// Original Wood Stuff //
				Map.entry("betternether:chest_crimson", "betternether:crimson_chest"),
				Map.entry("betternether:barrel_crimson", "betternether:crimson_barrel"),
				Map.entry("betternether:chest_warped", "betternether:warped_chest"),
				Map.entry("betternether:barrel_warped", "betternether:warped_barrel"),
				Map.entry("betternether:taburet_oak", "betternether:oak_taburet"),
				Map.entry("betternether:taburet_spruce", "betternether:spruce_taburet"),
				Map.entry("betternether:taburet_birch", "betternether:birch_taburet"),
				Map.entry("betternether:taburet_jungle", "betternether:jungle_taburet"),
				Map.entry("betternether:taburet_acacia", "betternether:acacia_taburet"),
				Map.entry("betternether:taburet_dark_oak", "betternether:dark_oak_taburet"),
				Map.entry("betternether:taburet_crimson", "betternether:crimson_taburet"),
				Map.entry("betternether:taburet_warped", "betternether:warped_taburet"),
				Map.entry("betternether:chair_oak", "betternether:oak_chair"),
				Map.entry("betternether:chair_spruce", "betternether:spruce_chair"),
				Map.entry("betternether:chair_birch", "betternether:birch_chair"),
				Map.entry("betternether:chair_jungle", "betternether:jungle_chair"),
				Map.entry("betternether:chair_acacia", "betternether:acacia_chair"),
				Map.entry("betternether:chair_dark_oak", "betternether:dark_oak_chair"),
				Map.entry("betternether:chair_crimson", "betternether:crimson_chair"),
				Map.entry("betternether:chair_warped", "betternether:warped_chair"),
				Map.entry("betternether:bar_stool_oak", "betternether:oak_bar_stool"),
				Map.entry("betternether:bar_stool_spruce", "betternether:spruce_bar_stool"),
				Map.entry("betternether:bar_stool_birch", "betternether:birch_bar_stool"),
				Map.entry("betternether:bar_stool_jungle", "betternether:jungle_bar_stool"),
				Map.entry("betternether:bar_stool_acacia", "betternether:acacia_bar_stool"),
				Map.entry("betternether:bar_stool_dark_oak", "betternether:dark_oak_bar_stool"),
				Map.entry("betternether:bar_stool_crimson", "betternether:crimson_bar_stool"),
				Map.entry("betternether:bar_stool_warped", "betternether:warped_bar_stool")

				);
	}
}
