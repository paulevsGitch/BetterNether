package paulevs.betternether;

import net.fabricmc.api.ModInitializer;
import paulevs.betternether.config.Config;
import paulevs.betternether.registers.BiomesRegister;
import paulevs.betternether.registers.BlockEntitiesRegister;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.registers.ItemsRegister;
import paulevs.betternether.world.BNWorldGenerator;

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
		BiomesRegister.register();
		BNWorldGenerator.loadConfig();
		Config.save();
	}
}
