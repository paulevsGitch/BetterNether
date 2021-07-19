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
		return Map.ofEntries(Map.entry("betternether:chest", "bclib:chest"));
	}
}
