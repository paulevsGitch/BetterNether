package paulevs.betternether.config.screen;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import paulevs.betternether.config.Config;
import paulevs.betternether.registry.ItemsRegistry;

public class ItemsScreen extends Screen
{
	private Screen parrent;
	private ButtonListWidget list;
	private Text header;
	
	public ItemsScreen(Screen parrent)
	{
		super(parrent.getTitle());
		this.parrent = parrent;
	}
	
	@Override
	protected void init()
	{
		this.addButton(new ButtonWidget(this.width / 2 + 2, this.height - 27, 154, 20, ScreenTexts.DONE,
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

		// Items //
		final JsonObject group = Config.getGroup("items");
		final List<String> items = ItemsRegistry.getPossibleItems();
		
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		
		items.forEach((itemName) -> {
			String name = itemName + "[def: true]";
			
			this.list.addSingleOptionEntry(new BooleanOption(itemName, (gameOptions) -> {
				return group.get(name).getAsBoolean();
			}, (gameOptions, value) -> {
				group.addProperty(name, value);
				Config.markToSave();
			}) {
				@Override
				public Text getDisplayString(GameOptions options)
				{
					boolean value = group.get(name).getAsBoolean();
					String color = value ? ": \u00A7a" : ": \u00A7c";
					return new TranslatableText("item.betternether." + itemName).append(color + ScreenTexts.getToggleText(value).getString());
				}
			});
		});
		
		this.children.add(list);
		
		this.addButton(new ButtonWidget(this.width / 2 - 156, this.height - 27, 154, 20, new TranslatableText("config.betternether.reset"),
			new PressAction()
			{
				@Override
				public void onPress(ButtonWidget button)
				{
					items.forEach((itemName) -> {
						String name = itemName + "[def: true]";
						group.addProperty(name, true);
					});
					list.children().forEach((entry) -> {
						entry.children().forEach((element -> {
							if (element instanceof AbstractButtonWidget)
							{
								AbstractButtonWidget ab = (AbstractButtonWidget) element;
								String message = ab.getMessage().getString();
								message = message.substring(0, message.lastIndexOf(':'));
								ab.setMessage(new TranslatableText("").append(message).append(": \u00A7a" + ScreenTexts.getToggleText(true).getString()));
							}
							}));
						});
					Config.save();
				}
			}
		));
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