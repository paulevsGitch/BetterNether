package paulevs.betternether.advancements;

import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.minecraft.advancements.critereon.LocationTrigger;
import paulevs.betternether.BetterNether;

public class BNCriterion {
	public static LocationTrigger BREW_BLUE;
	public static ConvertByLightningTrigger CONVERT_BY_LIGHTNING;
	
	public static void register(){
		BREW_BLUE = CriterionRegistry.register(new LocationTrigger(BetterNether.makeID("brew_blue")));
		CONVERT_BY_LIGHTNING = CriterionRegistry.register(new ConvertByLightningTrigger());
	}
}
