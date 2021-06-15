package paulevs.betternether.blockentities;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNSignBlockEntity extends BlockEntity {
	private static final String[] TEXT_KEYS = new String[]{"Text1", "Text2", "Text3", "Text4"};
	private static final String[] FILTERED_TEXT_KEYS = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
	private final Text[] text;
	private final Text[] filteredTexts;
	private boolean editable;
	private PlayerEntity editor;
	@Nullable
	private OrderedText[] textBeingEdited;
	private DyeColor textColor;
	private boolean filterText;
	private boolean glowingText;

	public BNSignBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntitiesRegistry.SIGN, pos, state);
		this.text = new Text[] { LiteralText.EMPTY, LiteralText.EMPTY, LiteralText.EMPTY, LiteralText.EMPTY };
		this.filteredTexts = new Text[]{LiteralText.EMPTY, LiteralText.EMPTY, LiteralText.EMPTY, LiteralText.EMPTY};
		this.editable = true;
		this.textBeingEdited = null;
		this.textColor = DyeColor.BLACK;
		this.glowingText = false;
	}

	public NbtCompound writeNbt(NbtCompound tag) {
		super.writeNbt(tag);

		for (int i = 0; i < 4; ++i) {
			Text text = this.text[i];
			Text filteredText = this.filteredTexts[i];
			tag.putString(TEXT_KEYS[i], Text.Serializer.toJson(text));
			if (!filteredText.equals(text)) {
				tag.putString(FILTERED_TEXT_KEYS[i], Text.Serializer.toJson(filteredText));
			}
		}

		tag.putString("Color", this.textColor.getName());
		tag.putBoolean("GlowingText", this.glowingText);
		return tag;
	}

	public void readNbt(NbtCompound tag) {
		this.editable = false;
		super.readNbt(tag);
		this.textColor = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);
		this.glowingText = tag.getBoolean("GlowingText");

		for (int i = 0; i < 4; ++i) {
			String json = tag.getString(TEXT_KEYS[i]);
			Text text = parseTextFromJson(json);
			this.text[i] = text;

			String fkey = FILTERED_TEXT_KEYS[i];
			if (tag.contains(fkey, NbtElement.STRING_TYPE)) {
				this.filteredTexts[i] = parseTextFromJson(tag.getString(fkey));
			} else {
				this.filteredTexts[i] = text;
			}
		}
		this.textBeingEdited = null;
	}

	private Text parseTextFromJson(String json) {
		Text text = this.unparsedTextFromJson(json);
		if (this.world instanceof ServerWorld) {
			try {
				return Texts.parse(this.getCommandSource((ServerPlayerEntity)null), (Text)text, (Entity)null, 0);
			} catch (CommandSyntaxException e) {
			}
		}

		return text;
	}

	private Text unparsedTextFromJson(String json) {
		try {
			Text text = Text.Serializer.fromJson(json);
			if (text != null) {
				return text;
			}
		} catch (Exception e) {
		}

		return LiteralText.EMPTY;
	}


	public void setTextOnRow(int row, Text text) {
		this.text[row] = text;
		this.textBeingEdited[row] = null;
	}

	public OrderedText[] updateSign(boolean filterText, Function<Text, OrderedText> textOrderingFunction) {
		if (this.textBeingEdited == null || this.filterText != filterText) {
			this.filterText = filterText;
			this.textBeingEdited = new OrderedText[4];

			for(int i = 0; i < 4; ++i) {
				this.textBeingEdited[i] = (OrderedText)textOrderingFunction.apply(this.getTextOnRow(i, filterText));
			}
		}

		return this.textBeingEdited;
	}

	public Text getTextOnRow(int row, boolean filtered) {
		return this.getTexts(filtered)[row];
	}

	private Text[] getTexts(boolean filtered) {
		return filtered ? this.filteredTexts : this.text;
	}

	@Nullable
	@Environment(EnvType.CLIENT)
	public OrderedText getTextBeingEditedOnRow(int row, Function<Text, OrderedText> function) {
		if (this.textBeingEdited[row] == null && this.text[row] != null) {
			this.textBeingEdited[row] = (OrderedText) function.apply(this.text[row]);
		}

		return this.textBeingEdited[row];
	}

	@Nullable
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return new BlockEntityUpdateS2CPacket(this.pos, 9, this.toInitialChunkDataNbt());
	}

	public NbtCompound toInitialChunkDataNbt() {
		return this.writeNbt(new NbtCompound());
	}

	public boolean copyItemDataRequiresOperator() {
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

	public void setEditor(PlayerEntity player) {
		this.editor = player;
	}

	public PlayerEntity getEditor() {
		return this.editor;
	}

	public boolean onActivate(PlayerEntity player) {
		Text[] var2 = this.text;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			Text text = var2[var4];
			Style style = text == null ? null : text.getStyle();
			if (style != null && style.getClickEvent() != null) {
				ClickEvent clickEvent = style.getClickEvent();
				if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
					player.getServer().getCommandManager().execute(this.getCommandSource((ServerPlayerEntity) player), clickEvent.getValue());
				}
			}
		}

		return true;
	}

	public ServerCommandSource getCommandSource(@Nullable ServerPlayerEntity player) {
		String string = player == null ? "Sign" : player.getName().getString();
		Text text = player == null ? new LiteralText("Sign") : player.getDisplayName();
		return new ServerCommandSource(CommandOutput.DUMMY, Vec3d.ofCenter(this.pos), Vec2f.ZERO, (ServerWorld) this.world, 2, string, (Text) text, this.world.getServer(), player);
	}

	public DyeColor getTextColor() {
		return this.textColor;
	}

	public boolean setTextColor(DyeColor value) {
		if (value != this.getTextColor()) {
			this.textColor = value;
			this.markDirty();
			this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
			return true;
		}
		else {
			return false;
		}
	}
}