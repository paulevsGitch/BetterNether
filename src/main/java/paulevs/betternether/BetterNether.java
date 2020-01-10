package paulevs.betternether;

import net.fabricmc.api.ModInitializer;
import paulevs.betternether.config.Config;
import paulevs.betternether.registers.BiomeRegister;
import paulevs.betternether.registers.BlockEntitiesRegister;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.registers.ItemsRegister;

public class BetterNether implements ModInitializer
{
	public static final String MOD_ID = "betternether";

	@Override
	public void onInitialize()
	{
		Config.load();
		BlocksRegister.register();
		BlockEntitiesRegister.register();
		ItemsRegister.register();
		BiomeRegister.register();
		Config.save();
	}
}
