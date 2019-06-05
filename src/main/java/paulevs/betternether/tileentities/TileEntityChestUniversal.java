package paulevs.betternether.tileentities;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlockCincinnasiteForge;
import paulevs.betternether.blocks.BlockInventoryUniversal;

public class TileEntityChestUniversal extends TileEntityLockableLoot implements ITickable
{
	private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
	/** The number of players currently using this chest */
	public int numPlayersUsing;
	private boolean open = false;

	public TileEntityChestUniversal()
	{

	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return 27;
	}

	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.chestContents)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.chest_of_drawers";
	}

	public static void registerFixesChest(DataFixer fixer)
	{
		fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityChest.class, new String[] {"Items"}));
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

		if (!this.checkLootAndRead(compound))
		{
			ItemStackHelper.loadAllItems(compound, this.chestContents);
		}

		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		if (!this.checkLootAndWrite(compound))
		{
			ItemStackHelper.saveAllItems(compound, this.chestContents);
		}

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public void openInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (this.numPlayersUsing < 0)
			{
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		}
		/*if (numPlayersUsing > 0 && !world.getBlockState(pos).getValue(BlockInventoryUniversal.OPEN))
		{
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockInventoryUniversal.OPEN, true), 3);
		}*/
	}

	public void closeInventory(EntityPlayer player)
	{
		if (!player.isSpectator() && this.getBlockType() instanceof BlockChest)
		{
			--this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		}
		/*if (numPlayersUsing < 1)
		{
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockInventoryUniversal.OPEN, false), 3);
		}*/
	}

	/**
	 * invalidates a tile entity
	 */
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}

	public String getGuiID()
	{
		return "minecraft:chest";
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		this.fillWithLoot(playerIn);
		return new ContainerChest(playerInventory, this, playerIn);
	}

	protected NonNullList<ItemStack> getItems()
	{
		return this.chestContents;
	}

	@Override
	public void update()
	{
		if (numPlayersUsing < 1 && open)
		{
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockInventoryUniversal.OPEN, false), 3);
			open = false;
		}
		else if (numPlayersUsing > 0 && !open)
		{
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockInventoryUniversal.OPEN, true), 3);
			open = true;
		}
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
	    return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
	    readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return (oldState.getBlock() != newState.getBlock());
	}
}
