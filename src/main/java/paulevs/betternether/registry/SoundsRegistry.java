package paulevs.betternether.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;

public class SoundsRegistry
{
	public static final SoundEvent AMBIENT_MUSHROOM_FOREST = register("betternether.ambient.mushroom_forest");
	
	private static SoundEvent register(String id)
	{
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(BetterNether.MOD_ID, id)));
	}
	
	public static void register() {}
}
