package paulevs.betternether.config.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class ConfigScreen extends Screen {
	private Screen parrent;

	public ConfigScreen(Screen parrent) {
		super(new TranslatableText("bn_config"));
		this.parrent = parrent;
	}

	@Override
	protected void init() {
		this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE,
				new PressAction() {
					@Override
					public void onPress(ButtonWidget button) {
						ConfigScreen.this.client.openScreen(parrent);
					}
				}));

		int i = 0;
		this.addButton(new ButtonWidget(this.width / 2 - 100, i += 20, 200, 20, new TranslatableText("config.betternether.rendering"),
				new PressAction() {
					@Override
					public void onPress(ButtonWidget button) {
						ConfigScreen.this.client.openScreen(new ImprovementsScreen(ConfigScreen.this));
					}
				}));
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		for (AbstractButtonWidget button : this.buttons)
			button.render(matrices, mouseX, mouseY, delta);
	}
}
