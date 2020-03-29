package paulevs.betternether.blockentities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.GenericContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockChestOfDrawers;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BlockEntityChestOfDrawers extends LootableContainerBlockEntity
{
	private DefaultedList<ItemStack> inventory;
	private int watchers = 0;
	
	public BlockEntityChestOfDrawers()
	{
		super(BlockEntitiesRegistry.CHEST_OF_DRAWERS);
		this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
	}

	@Override
	public int getInvSize()
	{
		return 27;
	}

	@Override
	protected DefaultedList<ItemStack> getInvStackList()
	{
		return this.inventory;
	}

	@Override
	protected void setInvStackList(DefaultedList<ItemStack> list)
	{
		this.inventory = list;
	}

	@Override
	protected Text getContainerName()
	{
		return new TranslatableText("container.chest_of_drawers", new Object[0]);
	}

	@Override
	protected Container createContainer(int i, PlayerInventory playerInventory)
	{
		return GenericContainer.createGeneric9x3(i, playerInventory, this);
	}

	public void fromTag(CompoundTag tag)
	{
		super.fromTag(tag);
		this.inventory = DefaultedList.ofSize(this.getInvSize(), ItemStack.EMPTY);
		if (!this.deserializeLootTable(tag))
		{
			Inventories.fromTag(tag, this.inventory);
		}
	}

	public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);
		if (!this.serializeLootTable(tag))
		{
			Inventories.toTag(tag, this.inventory);
		}
		return tag;
	}

	public void onInvOpen(PlayerEntity player)
	{
		if (!player.isSpectator())
		{
			if (this.watchers < 0)
			{
				this.watchers = 0;
			}

			++this.watchers;
			this.onInvOpenOrClose();
		}
	}

	@Override
	public void onInvClose(PlayerEntity player)
	{
		if (!player.isSpectator())
		{
			--this.watchers;
			this.onInvOpenOrClose();
		}
	}

	protected void onInvOpenOrClose()
	{
		BlockState state = this.getCachedState();
		Block block = state.getBlock();
		if (block instanceof BlockChestOfDrawers && !world.isClient)
		{
			if (watchers > 0 && !state.get(BlockChestOfDrawers.OPEN))
			{
				BlocksHelper.setWithoutUpdate((ServerWorld) world, pos, state.with(BlockChestOfDrawers.OPEN, true));
			}
			else if (watchers == 0 && state.get(BlockChestOfDrawers.OPEN))
			{
				BlocksHelper.setWithoutUpdate((ServerWorld) world, pos, state.with(BlockChestOfDrawers.OPEN, false));
			}
		}

	}
	
	public void addItemsToList(List<ItemStack> items)
	{
		for (ItemStack item: inventory)
			if (item != null)
				items.add(item);
	}
}
