package paulevs.betternether.config.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Config;

public class ImprovementsScreen extends Screen
{
	private Screen parrent;
	
	public ImprovementsScreen(Screen parrent)
	{
		super(parrent.getTitle());
		this.parrent = parrent;
	}
	
	@Override
	protected void init()
	{
		this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE,
			new PressAction()
			{
				@Override
				public void onPress(ButtonWidget button)
				{
					Config.save();
					ImprovementsScreen.this.client.openScreen(parrent);
				}
			}
		));

		// Fog //
		final String varName = "fog_density[vanilla: 1.0]";
		final float fogDefault = 0.75F;
		AbstractButtonWidget fogButton = new DoubleOption("bn_fog_density", 0.0, 1.0, 0.05F,
			(gameOptions) -> {
				return (double) Config.getFloat("improvement", varName, fogDefault);
			},
			(gameOptions, value) -> {
				float val = value.floatValue();
				Config.setFloat("improvement", varName, fogDefault, val);
				BetterNether.changeFogDensity(val);
			},
			(gameOptions, doubleOption) -> {
				double val = doubleOption.get(gameOptions);
				String color = Math.abs(val - fogDefault) < 0.001 ? "" : "\u00A7b";
				return new TranslatableText("config.betternether.fog").append(String.format(": %s%.2f", color, val));
			}
		).createButton(this.client.options, this.width / 2 - 120 + 20, 27, 150);
		this.addButton(fogButton);

		this.addButton(new ButtonWidget(this.width / 2 + 40 + 20, 27, 40, 20, new TranslatableText("config.betternether.reset"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				Config.setFloat("improvement", varName, fogDefault, fogDefault);
				BetterNether.changeFogDensity(fogDefault);
				fogButton.onRelease(0, 0);
			}
		}));
		
		for (AbstractButtonWidget button: this.buttons)
			this.children.add(button);
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		this.renderBackground(matrices);
		for (AbstractButtonWidget button: this.buttons)
		{
			button.render(matrices, mouseX, mouseY, delta);
		}
	}
}