package paulevs.betternether.config;


import com.terraformersmc.modmenu.api.ModMenuApi;
import paulevs.betternether.config.screen.ConfigScreen;
import ru.bclib.integration.ModMenuIntegration;

import java.lang.reflect.Proxy;

public class ModMenuEntry extends ModMenuIntegration {
	public static final ModMenuApi entrypointObject = createEntrypoint(new ModMenuEntry());

	public ModMenuEntry() {
		super(ConfigScreen::new);
	}
}