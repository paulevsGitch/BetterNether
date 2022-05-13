package paulevs.betternether.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class EntityJungleSkeleton extends Skeleton {
	public EntityJungleSkeleton(EntityType<? extends Skeleton> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public void aiStep() {
		this.updateSwingTime();
		this.updateNoActionTime();
		super.aiStep();
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityTag) {
		entityData = super.finalizeSpawn(level, difficulty, spawnReason, entityData, entityTag);
		final RandomSource randomSource = level.getRandom();
		super.populateDefaultEquipmentSlots(randomSource, difficulty);

		this.setItemSlot(EquipmentSlot.MAINHAND, getHandItem());
		this.setItemSlot(EquipmentSlot.OFFHAND, getRandomOffhandItem());

		this.populateDefaultEquipmentEnchantments(randomSource, difficulty);
		this.reassessWeaponGoal();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficulty.getSpecialMultiplier());
		if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			LocalDate localDate = LocalDate.now();
			int i = localDate.get(ChronoField.DAY_OF_MONTH);
			int j = localDate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
				this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return entityData;
	}

	private ItemStack getHandItem() {
		int n = this.random.nextInt(3);
		switch (n) {
			case 0:
			default:
				return new ItemStack(this.random.nextBoolean() ? Items.WOODEN_SWORD : Items.STONE_SWORD);
			case 1:
				return new ItemStack(Items.BOW);
			case 2:
				return new ItemStack(Items.AIR);
		}
	}

	private ItemStack getRandomOffhandItem() {
		return this.random.nextInt(8) == 0 ? new ItemStack(Items.SHIELD) : new ItemStack(Items.AIR);
	}
}
