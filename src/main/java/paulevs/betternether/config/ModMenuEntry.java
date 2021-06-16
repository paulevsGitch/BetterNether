package paulevs.betternether.config;

import java.util.function.Function;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.screen.ConfigScreen;

public class ModMenuEntry implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return screen -> new ConfigScreen(screen);
	}
}