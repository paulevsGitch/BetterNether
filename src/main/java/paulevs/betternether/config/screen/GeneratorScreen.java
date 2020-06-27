package paulevs.betternether.config.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Config;

public class GeneratorScreen extends Screen
{
	private Screen parrent;
	private Text header;
	
	public GeneratorScreen(Screen parrent)
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
					GeneratorScreen.this.client.openScreen(parrent);
				}
			}
		));
		
		header = new TranslatableText("\u00A7b* ").append(new TranslatableText("config.betternether.mod_reload").getString());

		// Fog //
		final String varStructs = "global_plant_and_structures_density";
		final float strucDefault = 1.0F;
		
		AbstractButtonWidget structuresButton = new DoubleOption("structures", 0.0, 1.0, 0.01F,
			(gameOptions) -> {
				return (double) Config.getFloat("world", varStructs, strucDefault);
			},
			(gameOptions, value) -> {
				float val = value.floatValue();
				Config.setFloat("world", varStructs, strucDefault, val);
				BetterNether.changeFogDensity(val);
			},
			(gameOptions, doubleOption) -> {
				double val = doubleOption.get(gameOptions);
				String color = Math.abs(val - strucDefault) < 0.001 ? "" : "\u00A7b";
				return new TranslatableText("config.betternether.global_structures_density").append(String.format(": %s%.2f", color, val));
			}
		).createButton(this.client.options, this.width / 2 - 100, 27, 150);
		this.addButton(structuresButton);

		this.addButton(new ButtonWidget(this.width / 2 + 40 + 20, 27, 40, 20, new TranslatableText("config.betternether.reset"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				Config.setFloat("improvement", varStructs, strucDefault, strucDefault);
				BetterNether.changeFogDensity(strucDefault);
				structuresButton.onClick(structuresButton.getWidth() * strucDefault + structuresButton.x, structuresButton.y);
			}
		}));
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		this.renderBackground(matrices);
		for (AbstractButtonWidget button: this.buttons)
		{
			button.render(matrices, mouseX, mouseY, delta);
		}
		this.drawCenteredText(matrices, this.textRenderer, header, this.width / 2, 14, 16777215);
	}
}