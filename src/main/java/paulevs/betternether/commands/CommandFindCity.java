package paulevs.betternether.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import paulevs.betternether.world.BNWorldGenerator;

public class CommandFindCity implements ICommand
{
	@Override
	public int compareTo(ICommand o)
	{
		return 0;
	}

	@Override
	public String getName()
	{
		return "findNetherCity";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/findNetherCity or /findNetherCity x y z";
	}

	@Override
	public List<String> getAliases()
	{
		List<String> aliases = new ArrayList<String>();
		aliases.add("fns");
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		BlockPos pos = null;
		if (args.length == 0)
		{
			if (sender instanceof EntityPlayer)
			{
				pos = BNWorldGenerator.getNearestCity(sender.getEntityWorld(), sender.getPosition().getX() >> 4, sender.getPosition().getZ() >> 4);
			}
			else
			{
				sendMessage("commands.findnethercity.usage.console", sender);
				return;
			}
		}
		else if (args.length == 3)
		{
				try
				{
					double posX = Double.parseDouble(args[0]);
					double posZ = Double.parseDouble(args[2]);
					pos = BNWorldGenerator.getNearestCity(sender.getEntityWorld(), (int) posX >> 4, (int) posZ >> 4);
				}
				catch (NumberFormatException e)
				{
					sendMessage("commands.findnethercity.usage.coordinates", sender);
					return;
				}
		}
		else
		{
			sendMessage("commands.findnethercity.usage", sender);
			return;
		}
		sendResult(String.format("%d %d %d", pos.getX() << 4, 40, pos.getZ() << 4), sender);
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		if (sender instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) sender;
			boolean op = server.getPlayerList().getOppedPlayers().getEntry(((EntityPlayer) sender).getGameProfile()) != null;
			return op || player.isSpectator() || player.isCreative();
		}
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos)
	{
		List<String> tabs = new ArrayList<String>();
		if (sender instanceof EntityPlayer)
		{
			if (args.length == 1)
				tabs.add(Double.toString(sender.getPosition().getX()));
			else if (args.length == 2)
				tabs.add(Double.toString(sender.getPosition().getY()));
			else if (args.length == 3)
				tabs.add(Double.toString(sender.getPosition().getZ()));
		}
		return tabs;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}
	
	private void sendMessage(String message, ICommandSender sender)
	{
		TextComponentTranslation text = new TextComponentTranslation(message);
        text.getStyle().setColor(TextFormatting.RED);
        sender.sendMessage(text);
	}
	
	private void sendResult(String message, ICommandSender sender)
	{
        sender.sendMessage(new TextComponentString(message));
	}
}
