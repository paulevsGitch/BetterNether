package paulevs.betternether.blockentities;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class NetherSignBlockEntity extends BlockEntity// implements NameableContainerFactory
{
	public final Text[] text = new Text[] {
			new LiteralText(""),
			new LiteralText(""),
			new LiteralText(""),
			new LiteralText("")
	};
	private boolean editable = true;
	private PlayerEntity editor;
	private final String[] textBeingEdited = new String[4];
	private DyeColor textColor;

	public NetherSignBlockEntity()
	{
		//super(BlockEntitiesRegistry.NETHER_SIGN);
		super(null);
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

	public void fromTag(CompoundTag tag)
	{
		this.editable = false;
		super.fromTag(tag);
		this.textColor = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);

		for (int i = 0; i < 4; ++i)
		{
			String string = tag.getString("Text" + (i + 1));
			Text text = Text.Serializer.fromJson(string.isEmpty() ? "\"\"" : string);
			if (this.world instanceof ServerWorld)
			{
				try
				{
					this.text[i] = Texts.parse(this.getCommandSource((ServerPlayerEntity) null), text, (Entity) null,
							0);
				}
				catch (CommandSyntaxException var6)
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

	@Environment(EnvType.CLIENT)
	public Text getTextOnRow(int row)
	{
		return this.text[row];
	}

	public void setTextOnRow(int row, Text text)
	{
		this.text[row] = text;
		this.textBeingEdited[row] = null;
	}

	@Nullable
	@Environment(EnvType.CLIENT)
	public String getTextBeingEditedOnRow(int row, Function<Text, String> function)
	{
		if (this.textBeingEdited[row] == null && this.text[row] != null)
		{
			this.textBeingEdited[row] = (String) function.apply(this.text[row]);
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

	public boolean shouldNotCopyTagFromItem()
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

	public void setEditor(PlayerEntity playerEntity)
	{
		this.editor = playerEntity;
	}

	public PlayerEntity getEditor()
	{
		return this.editor;
	}

	public boolean onActivate(PlayerEntity playerEntity)
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
					playerEntity.getServer().getCommandManager()
							.execute(this.getCommandSource((ServerPlayerEntity) playerEntity), clickEvent.getValue());
				}
			}
		}

		return true;
	}

	public ServerCommandSource getCommandSource(@Nullable ServerPlayerEntity player)
	{
		String string = player == null ? "Sign" : player.getName().getString();
		Text text = player == null ? new LiteralText("Sign") : player.getDisplayName();
		return new ServerCommandSource(CommandOutput.DUMMY,
				new Vec3d((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
						(double) this.pos.getZ() + 0.5D),
				Vec2f.ZERO, (ServerWorld) this.world, 2, string, (Text) text, this.world.getServer(), player);
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

	/*@Override
	public Container createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new SignContainer(syncId);
	}*/

	/*@Override
	public Text getDisplayName()
	{
		return new TranslatableText("sign", new Object[0]);
	}*/
}