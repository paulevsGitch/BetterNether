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

public class ImprovementsScreen extends Screen
{
	private Screen parrent;
	private Text header;
	
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
		
		header = new TranslatableText("\u00A7b* ").append(new TranslatableText("config.betternether.mod_reload").getString());

		// Fog //
		final String varFog = "fog_density[vanilla: 1.0]";
		final float fogDefault = 0.75F;
		
		AbstractButtonWidget fogButton = new DoubleOption("fog", 0.0, 1.0, 0.05F,
			(gameOptions) -> {
				return (double) Config.getFloat("improvement", varFog, fogDefault);
			},
			(gameOptions, value) -> {
				float val = value.floatValue();
				Config.setFloat("improvement", varFog, fogDefault, val);
				BetterNether.changeFogDensity(val);
			},
			(gameOptions, doubleOption) -> {
				double val = doubleOption.get(gameOptions);
				String color = Math.abs(val - fogDefault) < 0.001 ? "" : "\u00A7b";
				return new TranslatableText("config.betternether.fog").append(String.format(": %s%.2f", color, val));
			}
		).createButton(this.client.options, this.width / 2 - 100, 27, 150);
		this.addButton(fogButton);

		this.addButton(new ButtonWidget(this.width / 2 + 40 + 20, 27, 40, 20, new TranslatableText("config.betternether.reset"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				Config.setFloat("improvement", varFog, fogDefault, fogDefault);
				BetterNether.changeFogDensity(fogDefault);
				fogButton.onClick(fogButton.getWidth() * fogDefault + fogButton.x, fogButton.y);
			}
		}));
		
		// Thin Armor //
		final String varArmour = "smaller_armor_offset";
		boolean hasArmour = Config.getBoolean("improvement", varArmour, true);
		
		AbstractButtonWidget armorButton = new ButtonWidget(this.width / 2 - 100, 27 * 2, 150, 20, new TranslatableText("config.betternether.armour"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				boolean value = !Config.getBoolean("improvement", varArmour, true);
				Config.setBoolean("improvement", varArmour, true, value);
				String color = value ? ": \u00A7a" : ": \u00A7c";
				button.setMessage(new TranslatableText("config.betternether.armour")
						.append(color + ScreenTexts.getToggleText(value).getString()));
			}
		});
		String color = hasArmour ? ": \u00A7a" : ": \u00A7c";
		armorButton.setMessage(new TranslatableText("config.betternether.armour").append(color + ScreenTexts.getToggleText(hasArmour).getString()));
		this.addButton(armorButton);
		
		this.addButton(new ButtonWidget(this.width / 2 + 40 + 20, 27 * 2, 40, 20, new TranslatableText("config.betternether.reset"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				Config.setBoolean("improvement", varArmour, true, true);
				BetterNether.setThinArmor(true);
				armorButton.setMessage(new TranslatableText("config.betternether.armour").append(": \u00A7a" + ScreenTexts.getToggleText(true).getString()));
			}
		}));
		
		// Lavafalls //
		final String varLava = "lavafall_particles";
		boolean hasLava = Config.getBoolean("improvement", varLava, true);
		
		AbstractButtonWidget lavaButton = new ButtonWidget(this.width / 2 - 100, 27 * 3, 150, 20, new TranslatableText("config.betternether.armour"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				boolean value = !Config.getBoolean("improvement", varLava, true);
				Config.setBoolean("improvement", varLava, true, value);
				String color = value ? ": \u00A7a" : ": \u00A7c";
				button.setMessage(new TranslatableText("config.betternether.lavafalls")
						.append(color + ScreenTexts.getToggleText(value).getString()));
			}
		});
		color = hasLava ? ": \u00A7a" : ": \u00A7c";
		lavaButton.setMessage(new TranslatableText("config.betternether.lavafalls").append(color + ScreenTexts.getToggleText(hasLava).getString()));
		this.addButton(lavaButton);
		
		this.addButton(new ButtonWidget(this.width / 2 + 40 + 20, 27 * 3, 40, 20, new TranslatableText("config.betternether.reset"), new PressAction()
		{
			@Override
			public void onPress(ButtonWidget button)
			{
				Config.setBoolean("improvement", varLava, true, true);
				BetterNether.setThinArmor(true);
				lavaButton.setMessage(new TranslatableText("config.betternether.lavafalls").append(": \u00A7a" + ScreenTexts.getToggleText(true).getString()));
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