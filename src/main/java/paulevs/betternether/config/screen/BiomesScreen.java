package paulevs.betternether.config.screen;

import java.util.List;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Config;
import paulevs.betternether.registry.BiomesRegistry;

public class BiomesScreen extends Screen
{
	private Screen parrent;
	private ButtonListWidget list;
	private Text header;
	
	public BiomesScreen(Screen parrent)
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
					client.openScreen(parrent);
					Config.save();
				}
			}
		));
		
		header = new TranslatableText("config.betternether.mod_reload");

		// Biomes //
		final List<NetherBiome> biomes = BiomesRegistry.getAllBiomes();
		
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		
		biomes.forEach((biome) -> {
			final String key = "biome." + biome.getNamespace() + "." + biome.getRegistryName();
			System.out.println(key);
			if (!biome.hasParentBiome())
			{
				final float chance = BiomesRegistry.getDefaultMainChance(biome);
				final String path = "biomes." + biome.getNamespace() + ".variation";
				final String name = biome.getRegistryName() + "_size";
				
				this.list.addSingleOptionEntry(new DoubleOption(biome.getRegistryName(), 0, 10, 0.1F, (gameOptions) -> {
					return (double) Config.getFloat(path, name, chance);
				}, (gameOptions, value) -> {
					float val = value.floatValue();
					Config.setFloat(path, name, chance, val);
				}, (gameOptions, doubleOption) -> {
					double val = doubleOption.get(gameOptions);
					return new TranslatableText("[" + biome.getNamespace() + "] ")
							.append(new TranslatableText(key).getString() + String.format(": %.2f", val));
				}));
			}
		});
		
		this.children.add(list);
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		this.renderBackground(matrices);
		list.render(matrices, mouseX, mouseY, delta);
		for (AbstractButtonWidget button: this.buttons)
			button.render(matrices, mouseX, mouseY, delta);
		this.drawCenteredText(matrices, this.textRenderer, header, this.width / 2, 14, 16777215);
	}
}