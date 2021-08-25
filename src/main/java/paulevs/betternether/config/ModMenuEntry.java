package paulevs.betternether.config;


import com.terraformersmc.modmenu.util.ModMenuApiMarker;
import paulevs.betternether.config.screen.ConfigScreen;
import ru.bclib.integration.ModMenuIntegration;

public class ModMenuEntry extends ModMenuIntegration {
	public static final ModMenuApiMarker entrypointObject = createEntrypoint(new ModMenuEntry());

	public ModMenuEntry() {
		super(ConfigScreen::new);
	}
}