package paulevs.betternether.config.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Config;
import paulevs.betternether.config.Configs;

public class ConfigScreen extends Screen {
	private Screen parrent;
	private Component header;

	public ConfigScreen(Screen parrent) {
		super(new TranslatableComponent("bn_config"));
		this.parrent = parrent;
	}

	@Override
	protected void init() {
		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE,
				new OnPress() {
					@Override
					public void onPress(Button button) {
						Config.save();
						ConfigScreen.this.minecraft.setScreen(parrent);
					}
				}));

		header = new TranslatableComponent("\u00A7b* ").append(new TranslatableComponent("config.betternether.mod_reload").getString());

		// Fog //
		final String varFog = "fog_density[vanilla: 1.0]";
		final float fogDefault = 0.75F;

		AbstractWidget fogButton = new ProgressOption("fog", 0.0, 1.0, 0.05F,
				(gameOptions) -> {
					return (double) Configs.MAIN.getFloat("improvement", varFog, fogDefault);
				},
				(gameOptions, value) -> {
					float val = value.floatValue();
					Configs.MAIN.setFloat("improvement", varFog, fogDefault, val);
					BetterNether.changeFogDensity(val);
				},
				(gameOptions, doubleOption) -> {
					double val = doubleOption.get(gameOptions);
					String color = Math.abs(val - fogDefault) < 0.001 ? "" : "\u00A7b";
					return new TranslatableComponent("config.betternether.fog").append(String.format(": %s%.2f", color, val));
				}).createButton(this.minecraft.options, this.width / 2 - 100, 27, 150);
		this.addRenderableWidget(fogButton);

		this.addRenderableWidget(new Button(this.width / 2 + 40 + 20, 27, 40, 20, new TranslatableComponent("config.betternether.reset"), new OnPress() {
			@Override
			public void onPress(Button button) {
				Configs.MAIN.setFloat("improvement", varFog, fogDefault, fogDefault);
				BetterNether.changeFogDensity(fogDefault);
				fogButton.onClick(fogButton.getWidth() * fogDefault + fogButton.x, fogButton.y);
			}
		}));

		// Thin Armor //
		final String varArmour = "smaller_armor_offset";
		boolean hasArmour = Configs.MAIN.getBoolean("improvement", varArmour, true);

		AbstractWidget armorButton = new Button(this.width / 2 - 100, 27 * 2, 150, 20, new TranslatableComponent("config.betternether.armour"), new OnPress() {
			@Override
			public void onPress(Button button) {
				boolean value = !Configs.MAIN.getBoolean("improvement", varArmour, true);
				Configs.MAIN.setBoolean("improvement", varArmour, true, value);
				String color = value ? ": \u00A7a" : ": \u00A7c";
				button.setMessage(new TranslatableComponent("config.betternether.armour")
						.append(color + CommonComponents.optionStatus(value).getString()));
			}
		});
		String color = hasArmour ? ": \u00A7a" : ": \u00A7c";
		armorButton.setMessage(new TranslatableComponent("config.betternether.armour").append(color + CommonComponents.optionStatus(hasArmour).getString()));
		this.addRenderableWidget(armorButton);

		this.addRenderableWidget(new Button(this.width / 2 + 40 + 20, 27 * 2, 40, 20, new TranslatableComponent("config.betternether.reset"), new OnPress() {
			@Override
			public void onPress(Button button) {
				Configs.MAIN.setBoolean("improvement", varArmour, true, true);
				BetterNether.setThinArmor(true);
				armorButton.setMessage(new TranslatableComponent("config.betternether.armour").append(": \u00A7a" + CommonComponents.optionStatus(true).getString()));
			}
		}));

		// Lavafalls //
		final String varLava = "lavafall_particles";
		boolean hasLava = Configs.MAIN.getBoolean("improvement", varLava, true);

		AbstractWidget lavaButton = new Button(this.width / 2 - 100, 27 * 3, 150, 20, new TranslatableComponent("config.betternether.armour"), new OnPress() {
			@Override
			public void onPress(Button button) {
				boolean value = !Configs.MAIN.getBoolean("improvement", varLava, true);
				Configs.MAIN.setBoolean("improvement", varLava, true, value);
				String color = value ? ": \u00A7a" : ": \u00A7c";
				button.setMessage(new TranslatableComponent("config.betternether.lavafalls")
						.append(color + CommonComponents.optionStatus(value).getString()));
			}
		});
		color = hasLava ? ": \u00A7a" : ": \u00A7c";
		lavaButton.setMessage(new TranslatableComponent("config.betternether.lavafalls").append(color + CommonComponents.optionStatus(hasLava).getString()));
		this.addRenderableWidget(lavaButton);

		this.addRenderableWidget(new Button(this.width / 2 + 40 + 20, 27 * 3, 40, 20, new TranslatableComponent("config.betternether.reset"), new OnPress() {
			@Override
			public void onPress(Button button) {
				Configs.MAIN.setBoolean("improvement", varLava, true, true);
				BetterNether.setThinArmor(true);
				lavaButton.setMessage(new TranslatableComponent("config.betternether.lavafalls").append(": \u00A7a" + CommonComponents.optionStatus(true).getString()));
			}
		}));
	}

	public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);

		GuiComponent.drawCenteredString(matrices, this.font, header, this.width / 2, 14, 16777215);
	}
}
