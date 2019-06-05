package paulevs.betternether.sounds;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import paulevs.betternether.entities.EntityFirefly;

public class SoundRegister
{
	public static final SoundEvent FLY_SOUND = new SoundEvent(new ResourceLocation("betternether", "mob.firefly.fly")).setRegistryName("bn_fly");
	
	public static void register()
	{
		ForgeRegistries.SOUND_EVENTS.register(FLY_SOUND);
	}
}
