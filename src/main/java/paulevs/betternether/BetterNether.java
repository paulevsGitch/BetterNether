package paulevs.betternether;

import net.fabricmc.api.ModInitializer;
import paulevs.betternether.config.Config;
import paulevs.betternether.registers.BiomesRegister;
import paulevs.betternether.registers.BlockEntitiesRegister;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.registers.EntityRegister;
import paulevs.betternether.registers.ItemsRegister;
import paulevs.betternether.registers.SoundsRegister;
import paulevs.betternether.world.BNWorldGenerator;
import paulevs.betternether.world.structures.piece.StructureTypes;

public class BetterNether implements ModInitializer
{
	public static final String MOD_ID = "betternether";
	private static boolean thinArmor = true;
	private static float fogStart = 0.05F;
	private static float fogEnd = 0.5F;

	@Override
	public void onInitialize()
	{
		Config.load();
		initOptions();
		SoundsRegister.register();
		StructureTypes.init();
		BlocksRegister.register();
		BlockEntitiesRegister.register();
		ItemsRegister.register();
		BiomesRegister.register();
		BNWorldGenerator.onModInit();
		EntityRegister.register();
		Config.save();
	}
	
	private void initOptions()
	{
		thinArmor = Config.getBoolean("improvement", "smaller_armor_offset", true);
		float density = Config.getFloat("improvement", "fog_density[vanilla: 1.0]", 0.75F);
		makeStart(density);
		makeEnd(density);
	}
	
	public static boolean hasThinArmor()
	{
		return thinArmor;
	}
	
	private void makeStart(float density)
	{
		fogStart = -0.45F * density + 0.5F;
	}
	
	private void makeEnd(float density)
	{
		fogEnd = -0.5F * density + 1;
	}
	
	public static float getFogStart()
	{
		return fogStart;
	}
	
	public static float getFogEnd()
	{
		return fogEnd;
	}
}
