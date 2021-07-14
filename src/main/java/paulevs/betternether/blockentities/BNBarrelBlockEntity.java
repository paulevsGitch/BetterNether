package paulevs.betternether.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.blocks.BNBarrel;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BNBarrelBlockEntity extends RandomizableContainerBlockEntity {
	private NonNullList<ItemStack> inventory;
	private int viewerCount;

	private BNBarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.inventory = NonNullList.withSize(27, ItemStack.EMPTY);
	}

	public BNBarrelBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntitiesRegistry.BARREL, pos, state);
	}

	public CompoundTag save(CompoundTag tag) {
		super.save(tag);
		if (!this.trySaveLootTable(tag)) {
			ContainerHelper.saveAllItems(tag, this.inventory);
		}

		return tag;
	}

	public void load(CompoundTag tag) {
		super.load(tag);
		this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) {
			ContainerHelper.loadAllItems(tag, this.inventory);
		}
	}

	public int getContainerSize() {
		return 27;
	}

	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	protected void setItems(NonNullList<ItemStack> list) {
		this.inventory = list;
	}

	protected Component getDefaultName() {
		return new TranslatableComponent("container.barrel");
	}

	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return ChestMenu.threeRows(syncId, playerInventory, this);
	}

	public void startOpen(Player player) {
		if (!player.isSpectator()) {
			if (this.viewerCount < 0) {
				this.viewerCount = 0;
			}

			++this.viewerCount;
			BlockState blockState = this.getBlockState();
			boolean bl = (Boolean) blockState.getValue(BarrelBlock.OPEN);
			if (!bl) {
				this.playSound(blockState, SoundEvents.BARREL_OPEN);
				this.setOpen(blockState, true);
			}

			this.scheduleUpdate();
		}
	}

	private void scheduleUpdate() {
		this.level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
	}

	public void tick() {
		int i = this.worldPosition.getX();
		int j = this.worldPosition.getY();
		int k = this.worldPosition.getZ();
		//this.viewerCount = ChestBlockEntity.countViewers(this.world, this, i, j, k);
		this.viewerCount = ChestBlockEntity.getOpenCount(this.level, this.worldPosition);
		if (this.viewerCount > 0) {
			this.scheduleUpdate();
		}
		else {
			BlockState blockState = this.getBlockState();
			if (!(blockState.getBlock() instanceof BNBarrel)) {
				this.setRemoved();
				return;
			}

			boolean bl = (Boolean) blockState.getValue(BarrelBlock.OPEN);
			if (bl) {
				this.playSound(blockState, SoundEvents.BARREL_CLOSE);
				this.setOpen(blockState, false);
			}
		}
	}

	public void stopOpen(Player player) {
		if (!player.isSpectator()) {
			--this.viewerCount;
		}
	}

	private void setOpen(BlockState state, boolean open) {
		this.level.setBlock(this.getBlockPos(), (BlockState) state.setValue(BarrelBlock.OPEN, open), 3);
	}

	private void playSound(BlockState blockState, SoundEvent soundEvent) {
		Vec3i vec3i = ((Direction) blockState.getValue(BarrelBlock.FACING)).getNormal();
		double d = (double) this.worldPosition.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
		double e = (double) this.worldPosition.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
		double f = (double) this.worldPosition.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
		this.level.playSound((Player) null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
	}
}