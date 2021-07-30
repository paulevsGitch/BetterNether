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
		super(BetterNether.MOD_ID, "5.2.0");
	}
	
	@Override
	public Map<String, String> getIDReplacements() {
		return Map.ofEntries(
				Map.entry("betternether:chest", "bclib:chest"),
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
				Map.entry("betternether:stalagnate", "betternether:stalagnate_main")
		);
	}
}
