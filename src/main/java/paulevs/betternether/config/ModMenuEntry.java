package paulevs.betternether.config;

import java.util.function.Function;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.screen.ConfigScreen;

public class ModMenuEntry implements ModMenuApi
{
	@Override
	public String getModId()
	{
		return BetterNether.MOD_ID;
	}
	
	@Override
	public Function<Screen, ? extends Screen> getConfigScreenFactory()
	{
		return ConfigScreen::new;
	}
}