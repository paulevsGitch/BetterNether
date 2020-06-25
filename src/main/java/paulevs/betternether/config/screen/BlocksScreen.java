package paulevs.betternether.config.screen;

import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import paulevs.betternether.config.Config;

public class BlocksScreen extends Screen
{
	private Screen parrent;
	private ButtonListWidget list;
	private Text header;
	private ButtonWidget back;
	
	public BlocksScreen(Screen parrent)
	{
		super(parrent.getTitle());
		this.parrent = parrent;
	}
	
	@Override
	protected void init()
	{
		header = new TranslatableText("config.betternether.mod_reload");
		
		back = new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 24, ScreenTexts.DONE,
			new PressAction()
			{
				@Override
				public void onPress(ButtonWidget button)
				{
					Config.save();
					BlocksScreen.this.client.openScreen(parrent);
				}
			}
		);
		this.addButton(back);

		// Blocks //
		JsonObject group = Config.getGroup("blocks");
		List<Entry<String, JsonElement>> memebers = Config.getGroupMembers(group);
		
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		
		memebers.forEach((entry) -> {
			String name = entry.getKey();
			String blockName = name.substring(0, name.indexOf("["));
			
			this.list.addSingleOptionEntry(new BooleanOption(blockName, (gameOptions) -> {
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
					return new TranslatableText("block.betternether." + blockName).append(color + ScreenTexts.getToggleText(value).getString());
				}
			});
		});
		
		this.children.add(list);
	}
	
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		this.renderBackground(matrices);
		list.render(matrices, mouseX, mouseY, delta);
		back.render(matrices, mouseX, mouseY, delta);
		this.drawCenteredText(matrices, this.textRenderer, header, this.width / 2, 10, 16777215);
	}
}