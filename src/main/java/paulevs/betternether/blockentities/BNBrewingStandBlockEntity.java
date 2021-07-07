package paulevs.betternether.blockentities;

import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.BlockEntitiesRegistry;
import paulevs.betternether.registry.BrewingRegistry;

public class BNBrewingStandBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
	private static final int[] TOP_SLOTS = new int[] { 3 };
	private static final int[] BOTTOM_SLOTS = new int[] { 0, 1, 2, 3 };
	private static final int[] SIDE_SLOTS = new int[] { 0, 1, 2, 4 };
	private NonNullList<ItemStack> inventory;
	private int brewTime;
	private boolean[] slotsEmptyLastTick;
	private Item itemBrewing;
	private int fuel;
	protected final ContainerData propertyDelegate;

	public BNBrewingStandBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntitiesRegistry.NETHER_BREWING_STAND, blockPos, blockState);
		this.inventory = NonNullList.withSize(5, ItemStack.EMPTY);
		this.propertyDelegate = new ContainerData() {
			public int get(int index) {
				switch (index) {
					case 0:
						return BNBrewingStandBlockEntity.this.brewTime;
					case 1:
						return BNBrewingStandBlockEntity.this.fuel;
					default:
						return 0;
				}
			}

			public void set(int index, int value) {
				switch (index) {
					case 0:
						BNBrewingStandBlockEntity.this.brewTime = value;
						break;
					case 1:
						BNBrewingStandBlockEntity.this.fuel = value;
				}

			}

			public int getCount() {
				return 2;
			}
		};
	}

	protected Component getDefaultName() {
		return new TranslatableComponent("container.brewing", new Object[0]);
	}

	public int getContainerSize() {
		return this.inventory.size();
	}

	public boolean isEmpty() {
		Iterator<ItemStack> var1 = this.inventory.iterator();

		ItemStack itemStack;
		do {
			if (!var1.hasNext()) {
				return true;
			}

			itemStack = (ItemStack) var1.next();
		}
		while (itemStack.isEmpty());

		return false;
	}

	public static void tick(Level world, BlockPos pos, BlockState state, BNBrewingStandBlockEntity blockEntity) {
		ItemStack itemStack = (ItemStack) blockEntity.inventory.get(4);
		if (blockEntity.fuel <= 0 && itemStack.getItem() == Items.BLAZE_POWDER) {
			blockEntity.fuel = 20;
			itemStack.shrink(1);
			blockEntity.setChanged(world, pos, state);
		}

		boolean bl = blockEntity.canCraft();
		boolean bl2 = blockEntity.brewTime > 0;
		ItemStack itemStack2 = (ItemStack) blockEntity.inventory.get(3);
		if (bl2) {
			--blockEntity.brewTime;
			boolean bl3 = blockEntity.brewTime == 0;
			if (bl3 && bl) {
				blockEntity.craft();
				blockEntity.setChanged(world, pos, state);
			}
			else if (!bl) {
				blockEntity.brewTime = 0;
				blockEntity.setChanged(world, pos, state);
			}
			else if (blockEntity.itemBrewing != itemStack2.getItem()) {
				blockEntity.brewTime = 0;
				blockEntity.setChanged(world, pos, state);
			}
		}
		else if (bl && blockEntity.fuel > 0) {
			--blockEntity.fuel;
			blockEntity.brewTime = 400;
			blockEntity.itemBrewing = itemStack2.getItem();
			blockEntity.setChanged(world, pos, state);
		}

		if (!blockEntity.level.isClientSide) {
			boolean[] bls = blockEntity.getSlotsEmpty();
			if (!Arrays.equals(bls, blockEntity.slotsEmptyLastTick)) {
				blockEntity.slotsEmptyLastTick = bls;
				//BlockState blockState = blockEntity.world.getBlockState(blockEntity.getPos());
				BlockState blockState = state;
				if (!(blockState.getBlock() instanceof BrewingStandBlock)) {
					return;
				}

				for (int i = 0; i < BrewingStandBlock.HAS_BOTTLE.length; ++i) {
					blockState = (BlockState) blockState.setValue(BrewingStandBlock.HAS_BOTTLE[i], bls[i]);
				}

				blockEntity.level.setBlock(pos, blockState, Block.UPDATE_CLIENTS);
			}
		}

	}

	public boolean[] getSlotsEmpty() {
		boolean[] bls = new boolean[3];

		for (int i = 0; i < 3; ++i) {
			if (!((ItemStack) this.inventory.get(i)).isEmpty()) {
				bls[i] = true;
			}
		}

		return bls;
	}

	private boolean canCraft() {
		ItemStack source = this.inventory.get(3);
		if (source.isEmpty()) {
			return false;
		}
		else if (!PotionBrewing.isIngredient(source)) {
			return false;
		}
		else {
			for (int i = 0; i < 3; ++i) {
				ItemStack bottle = this.inventory.get(i);
				if (!bottle.isEmpty()) {
					if (PotionBrewing.hasMix(bottle, source))
						return true;
					else if (BrewingRegistry.getResult(source, bottle) != null)
						return true;
				}
			}

			return false;
		}
	}

	private void craft(){
		craft(this.level, this.worldPosition, this.getBlockState());
	}

	private void craft(Level world, BlockPos blockPos, BlockState state) {
		ItemStack source = (ItemStack) this.inventory.get(3);

		for (int i = 0; i < 3; ++i) {
			ItemStack bottle = this.inventory.get(i);
			if (!bottle.isEmpty()) {
				ItemStack result = BrewingRegistry.getResult(source, bottle);
				if (result != null)
					this.inventory.set(i, result.copy());
				else
					this.inventory.set(i, PotionBrewing.mix(source, this.inventory.get(i)));
			}
		}

		source.shrink(1);
		if (source.getItem().hasCraftingRemainingItem()) {
			ItemStack itemStack2 = new ItemStack(source.getItem().getCraftingRemainingItem());
			if (source.isEmpty()) {
				source = itemStack2;
			}
			else if (!world.isClientSide) {
				Containers.dropItemStack(world, (double) blockPos.getX(), (double) blockPos.getY(),
						(double) blockPos.getZ(), itemStack2);
			}
		}

		this.inventory.set(3, source);
		this.level.levelEvent(LevelEvent.SOUND_BREWING_STAND_BREW, blockPos, 0);
	}

	public void load(CompoundTag tag) {
		super.load(tag);
		this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.inventory);
		this.brewTime = tag.getShort("BrewTime");
		this.fuel = tag.getByte("Fuel");
	}

	public CompoundTag save(CompoundTag tag) {
		super.save(tag);
		tag.putShort("BrewTime", (short) this.brewTime);
		ContainerHelper.saveAllItems(tag, this.inventory);
		tag.putByte("Fuel", (byte) this.fuel);
		return tag;
	}

	public ItemStack getItem(int slot) {
		return slot >= 0 && slot < this.inventory.size() ? (ItemStack) this.inventory.get(slot) : ItemStack.EMPTY;
	}

	public ItemStack removeItem(int slot, int amount) {
		return ContainerHelper.removeItem(this.inventory, slot, amount);
	}

	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(this.inventory, slot);
	}

	public void setItem(int slot, ItemStack stack) {
		if (slot >= 0 && slot < this.inventory.size()) {
			this.inventory.set(slot, stack);
		}

	}

	public boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		}
		else {
			return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D,
					(double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	public boolean canPlaceItem(int slot, ItemStack stack) {
		if (slot == 3) {
			return PotionBrewing.isIngredient(stack);
		}
		else {
			Item item = stack.getItem();
			if (slot == 4) {
				if (item == Items.BLAZE_POWDER) {
					return true;
				}
				ResourceLocation id = Registry.ITEM.getKey(item);
				return id.getNamespace().equals("biomemakeover") && id.getPath().equals("soul_embers");
			}
			else {
				return (item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE) && this.getItem(slot).isEmpty();
			}
		}
	}

	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.UP) {
			return TOP_SLOTS;
		}
		else {
			return side == Direction.DOWN ? BOTTOM_SLOTS : SIDE_SLOTS;
		}
	}

	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
		return this.canPlaceItem(slot, stack);
	}

	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
		if (slot == 3) {
			return stack.getItem() == Items.GLASS_BOTTLE;
		}
		else {
			return true;
		}
	}

	public void clearContent() {
		this.inventory.clear();
	}

	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return new BrewingStandMenu(syncId, playerInventory, this, this.propertyDelegate);
	}
}