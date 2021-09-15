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
		super(BetterNether.MOD_ID, "5.3.2");
	}
	
	@Override
	public Map<String, String> getIDReplacements() {
		return Map.ofEntries(
				Map.entry("betternether:chest", "bclib:chest"),
				Map.entry("betternether:barrel", "bclib:barrel"),
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
				Map.entry("betternether:mushroom_fir", "betternether:mushroom_fir_trunk")
		);
	}
}
