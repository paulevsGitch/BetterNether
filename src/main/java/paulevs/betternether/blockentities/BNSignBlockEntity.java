package paulevs.betternether.blockentities;

import java.util.function.Function;
import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNSignBlockEntity extends BlockEntity {
	private static final String[] TEXT_KEYS = new String[]{"Text1", "Text2", "Text3", "Text4"};
	private static final String[] FILTERED_TEXT_KEYS = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
	private final Component[] text;
	private final Component[] filteredTexts;
	private boolean editable;
	private Player editor;
	@Nullable
	private FormattedCharSequence[] textBeingEdited;
	private DyeColor textColor;
	private boolean filterText;
	private boolean glowingText;

	public BNSignBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntitiesRegistry.SIGN, pos, state);
		this.text = new Component[] { TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY };
		this.filteredTexts = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
		this.editable = true;
		this.textBeingEdited = null;
		this.textColor = DyeColor.BLACK;
		this.glowingText = false;
	}

	public CompoundTag save(CompoundTag tag) {
		super.saveAdditional(tag);

		for (int i = 0; i < 4; ++i) {
			Component text = this.text[i];
			Component filteredText = this.filteredTexts[i];
			tag.putString(TEXT_KEYS[i], Component.Serializer.toJson(text));
			if (!filteredText.equals(text)) {
				tag.putString(FILTERED_TEXT_KEYS[i], Component.Serializer.toJson(filteredText));
			}
		}

		tag.putString("Color", this.textColor.getName());
		tag.putBoolean("GlowingText", this.glowingText);
		return tag;
	}

	public void load(CompoundTag tag) {
		this.editable = false;
		super.load(tag);
		this.textColor = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);
		this.glowingText = tag.getBoolean("GlowingText");

		for (int i = 0; i < 4; ++i) {
			String json = tag.getString(TEXT_KEYS[i]);
			Component text = parseTextFromJson(json);
			this.text[i] = text;

			String fkey = FILTERED_TEXT_KEYS[i];
			if (tag.contains(fkey, Tag.TAG_STRING)) {
				this.filteredTexts[i] = parseTextFromJson(tag.getString(fkey));
			} else {
				this.filteredTexts[i] = text;
			}
		}
		this.textBeingEdited = null;
	}

	private Component parseTextFromJson(String json) {
		Component text = this.unparsedTextFromJson(json);
		if (this.level instanceof ServerLevel) {
			try {
				return ComponentUtils.updateForEntity(this.getCommandSource((ServerPlayer)null), (Component)text, (Entity)null, 0);
			} catch (CommandSyntaxException e) {
			}
		}

		return text;
	}

	private Component unparsedTextFromJson(String json) {
		try {
			Component text = Component.Serializer.fromJson(json);
			if (text != null) {
				return text;
			}
		} catch (Exception e) {
		}

		return TextComponent.EMPTY;
	}


	public void setTextOnRow(int row, Component text) {
		this.text[row] = text;
		this.textBeingEdited[row] = null;
	}

	public FormattedCharSequence[] updateSign(boolean filterText, Function<Component, FormattedCharSequence> textOrderingFunction) {
		if (this.textBeingEdited == null || this.filterText != filterText) {
			this.filterText = filterText;
			this.textBeingEdited = new FormattedCharSequence[4];

			for(int i = 0; i < 4; ++i) {
				this.textBeingEdited[i] = (FormattedCharSequence)textOrderingFunction.apply(this.getTextOnRow(i, filterText));
			}
		}

		return this.textBeingEdited;
	}

	public Component getTextOnRow(int row, boolean filtered) {
		return this.getTexts(filtered)[row];
	}

	private Component[] getTexts(boolean filtered) {
		return filtered ? this.filteredTexts : this.text;
	}

	@Nullable
	@Environment(EnvType.CLIENT)
	public FormattedCharSequence getTextBeingEditedOnRow(int row, Function<Component, FormattedCharSequence> function) {
		if (this.textBeingEdited[row] == null && this.text[row] != null) {
			this.textBeingEdited[row] = (FormattedCharSequence) function.apply(this.text[row]);
		}

		return this.textBeingEdited[row];
	}

	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}

	public boolean onlyOpCanSetNbt() {
		return true;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public boolean isGlowingText() { return this.glowingText;}

	@Environment(EnvType.CLIENT)
	public void setEditable(boolean bl) {
		this.editable = bl;
		if (!bl) {
			this.editor = null;
		}

	}

	public void setEditor(Player player) {
		this.editor = player;
	}

	public Player getEditor() {
		return this.editor;
	}

	public boolean onActivate(Player player) {
		Component[] var2 = this.text;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			Component text = var2[var4];
			Style style = text == null ? null : text.getStyle();
			if (style != null && style.getClickEvent() != null) {
				ClickEvent clickEvent = style.getClickEvent();
				if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
					player.getServer().getCommands().performCommand(this.getCommandSource((ServerPlayer) player), clickEvent.getValue());
				}
			}
		}

		return true;
	}

	public CommandSourceStack getCommandSource(@Nullable ServerPlayer player) {
		String string = player == null ? "Sign" : player.getName().getString();
		Component text = player == null ? new TextComponent("Sign") : player.getDisplayName();
		return new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(this.worldPosition), Vec2.ZERO, (ServerLevel) this.level, 2, string, (Component) text, this.level.getServer(), player);
	}

	public DyeColor getTextColor() {
		return this.textColor;
	}

	public boolean setTextColor(DyeColor value) {
		if (value != this.getTextColor()) {
			this.textColor = value;
			this.setChanged();
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
			return true;
		}
		else {
			return false;
		}
	}
}