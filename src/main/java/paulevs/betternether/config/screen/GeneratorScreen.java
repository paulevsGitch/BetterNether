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

		// Global //
		final String varStructs = "global_plant_and_structures_density";
		final float strucDefault = 1.0F;
		
		int i = 27;
		AbstractButtonWidget structuresButton = new DoubleOption("global", 0.0, 1.0, 0.01F,
			(gameOptions) -> {
				return (double) Config.getFloat("world", varStructs, strucDefault);
			},
			(gameOptions, value) -> {
				float val = value.floatValue();
				Config.setFloat("world", varStructs, strucDefault, val);
			},
			(gameOptions, doubleOption) -> {
				double val = doubleOption.get(gameOptions);
				String color = Math.abs(val - strucDefault) < 0.001 ? "" : "\u00A7b";
				return new TranslatableText("config.betternether.global_structures_density").append(String.format(": %s%.2f", color, val));
			}
		).createButton(this.client.options, this.width / 2 - 145, i, 250);
		this.addButton(structuresButton);
		
		this.addButton(new ButtonWidget(this.width / 2 + 115, i, 40, 20, new TranslatableText("config.betternether.reset"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				Config.setFloat("world", varStructs, strucDefault, strucDefault);
				structuresButton.onClick(structuresButton.getWidth() * strucDefault + structuresButton.x, structuresButton.y);
			}
		}));
		i += 27;
		
		// Structures //
		
		final String varStructsOnly = "structures_density";
		final float strucDefaultOnly = 0.03125F;
		
		AbstractButtonWidget structuresOnlyButton = new DoubleOption("structures", 0.0, 0.1, 0.0001F,
			(gameOptions) -> {
				return (double) Config.getFloat("world", varStructsOnly, strucDefaultOnly);
			},
			(gameOptions, value) -> {
				float val = value.floatValue();
				Config.setFloat("world", varStructsOnly, strucDefaultOnly, val);
			},
			(gameOptions, doubleOption) -> {
				double val = doubleOption.get(gameOptions);
				String color = Math.abs(val - strucDefaultOnly) < 0.0001 ? "" : "\u00A7b";
				return new TranslatableText("config.betternether.structures_density").append(String.format(": %s%.4f", color, val));
			}
		).createButton(this.client.options, this.width / 2 - 145, i, 250);
		this.addButton(structuresOnlyButton);

		this.addButton(new ButtonWidget(this.width / 2 + 115, i, 40, 20, new TranslatableText("config.betternether.reset"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				Config.setFloat("world", varStructsOnly, strucDefaultOnly, strucDefaultOnly);
				structuresOnlyButton.onClick(structuresOnlyButton.getWidth() * 10 * strucDefaultOnly + structuresOnlyButton.x, structuresOnlyButton.y);
			}
		}));
		i += 27;
		
		// Cities //
		final int cDistDef = 64;
		
		AbstractButtonWidget citiesDistButton = new DoubleOption("cities_dist", 8, 256, 1F,
			(gameOptions) -> {
				return (double) Config.getInt("world.cities", "distance", cDistDef);
			},
			(gameOptions, value) -> {
				int val = Math.round(value.floatValue());
				Config.setInt("world.cities", "distance", cDistDef, val);
			},
			(gameOptions, doubleOption) -> {
				int val = (int) Math.round(doubleOption.get(gameOptions));
				String color = Math.abs(val - cDistDef) < 1 ? "" : "\u00A7b";
				return new TranslatableText("config.betternether.cities_dist").append(String.format(": %s%d", color, val));
			}
		).createButton(this.client.options, this.width / 2 - 145, i, 250);
		this.addButton(citiesDistButton);

		this.addButton(new ButtonWidget(this.width / 2 + 115, i, 40, 20, new TranslatableText("config.betternether.reset"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				Config.setInt("world.cities", "distance", cDistDef, cDistDef);
				citiesDistButton.onClick(Math.ceil((float) citiesDistButton.getWidth() * (cDistDef - 6) / (256 - 8)) + citiesDistButton.x, citiesDistButton.y);
			}
		}));
		i += 27;
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