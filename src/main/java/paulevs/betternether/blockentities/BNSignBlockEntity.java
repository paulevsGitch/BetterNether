package paulevs.betternether.blockentities;

import java.util.function.UnaryOperator;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNSignBlockEntity extends BlockEntity
{
	private final Text[] text;
	private boolean editable;
	private PlayerEntity editor;
	private final StringRenderable[] textBeingEdited;
	private DyeColor textColor;

	public BNSignBlockEntity()
	{
		super(BlockEntitiesRegistry.SIGN);
		this.text = new Text[] { LiteralText.EMPTY, LiteralText.EMPTY, LiteralText.EMPTY, LiteralText.EMPTY };
		this.editable = true;
		this.textBeingEdited = new StringRenderable[4];
		this.textColor = DyeColor.BLACK;
	}

	public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);

		for (int i = 0; i < 4; ++i)
		{
			String string = Text.Serializer.toJson(this.text[i]);
			tag.putString("Text" + (i + 1), string);
		}

		tag.putString("Color", this.textColor.getName());
		return tag;
	}

	public void fromTag(BlockState state, CompoundTag tag)
	{
		this.editable = false;
		super.fromTag(state, tag);
		this.textColor = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);

		for (int i = 0; i < 4; ++i)
		{
			String string = tag.getString("Text" + (i + 1));
			Text text = Text.Serializer.fromJson(string.isEmpty() ? "\"\"" : string);
			if (this.world instanceof ServerWorld)
			{
				try
				{
					this.text[i] = Texts.parse(this.getCommandSource((ServerPlayerEntity) null), text, (Entity) null, 0);
				}
				catch (CommandSyntaxException var7)
				{
					this.text[i] = text;
				}
			}
			else
			{
				this.text[i] = text;
			}

			this.textBeingEdited[i] = null;
		}

	}

	public void setTextOnRow(int row, Text text)
	{
		this.text[row] = text;
		this.textBeingEdited[row] = null;
	}

	@Nullable
	@Environment(EnvType.CLIENT)
	public StringRenderable getTextBeingEditedOnRow(int row, UnaryOperator<StringRenderable> unaryOperator)
	{
		if (this.textBeingEdited[row] == null && this.text[row] != null)
		{
			this.textBeingEdited[row] = (StringRenderable) unaryOperator.apply(this.text[row]);
		}

		return this.textBeingEdited[row];
	}

	@Nullable
	public BlockEntityUpdateS2CPacket toUpdatePacket()
	{
		return new BlockEntityUpdateS2CPacket(this.pos, 9, this.toInitialChunkDataTag());
	}

	public CompoundTag toInitialChunkDataTag()
	{
		return this.toTag(new CompoundTag());
	}

	public boolean copyItemDataRequiresOperator()
	{
		return true;
	}

	public boolean isEditable()
	{
		return this.editable;
	}

	@Environment(EnvType.CLIENT)
	public void setEditable(boolean bl)
	{
		this.editable = bl;
		if (!bl)
		{
			this.editor = null;
		}

	}

	public void setEditor(PlayerEntity player)
	{
		this.editor = player;
	}

	public PlayerEntity getEditor()
	{
		return this.editor;
	}

	public boolean onActivate(PlayerEntity player)
	{
		Text[] var2 = this.text;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4)
		{
			Text text = var2[var4];
			Style style = text == null ? null : text.getStyle();
			if (style != null && style.getClickEvent() != null)
			{
				ClickEvent clickEvent = style.getClickEvent();
				if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND)
				{
					player.getServer().getCommandManager().execute(this.getCommandSource((ServerPlayerEntity) player), clickEvent.getValue());
				}
			}
		}

		return true;
	}

	public ServerCommandSource getCommandSource(@Nullable ServerPlayerEntity player)
	{
		String string = player == null ? "Sign" : player.getName().getString();
		Text text = player == null ? new LiteralText("Sign") : player.getDisplayName();
		return new ServerCommandSource(CommandOutput.DUMMY, Vec3d.ofCenter(this.pos), Vec2f.ZERO, (ServerWorld) this.world, 2, string, (Text) text, this.world.getServer(), player);
	}

	public DyeColor getTextColor()
	{
		return this.textColor;
	}

	public boolean setTextColor(DyeColor value)
	{
		if (value != this.getTextColor())
		{
			this.textColor = value;
			this.markDirty();
			this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
			return true;
		}
		else
		{
			return false;
		}
	}
}