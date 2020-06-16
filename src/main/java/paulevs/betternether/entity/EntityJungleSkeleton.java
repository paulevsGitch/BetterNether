package paulevs.betternether.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class EntityJungleSkeleton extends SkeletonEntity
{
	public EntityJungleSkeleton(EntityType<? extends SkeletonEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Override
	public void tickMovement()
	{
		this.tickHandSwing();
		this.updateDespawnCounter();
		super.tickMovement();
	}
	
	@Override
	public EntityData initialize(WorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag)
	{
		entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);
		super.initEquipment(difficulty);

		this.equipStack(EquipmentSlot.MAINHAND, getHandItem());
		this.equipStack(EquipmentSlot.OFFHAND, getOffhandItem());

		this.updateEnchantments(difficulty);
		this.updateAttackType();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficulty.getClampedLocalDifficulty());
		if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty())
		{
			LocalDate localDate = LocalDate.now();
			int i = localDate.get(ChronoField.DAY_OF_MONTH);
			int j = localDate.get(ChronoField.MONTH_OF_YEAR);
			if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
				this.equipStack(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
				this.armorDropChances[EquipmentSlot.HEAD.getEntitySlotId()] = 0.0F;
			}
		}

		return entityData;
	}
	
	private ItemStack getHandItem()
	{
		int n = this.random.nextInt(3);
		switch(n)
		{
		case 0:
		default:
			return new ItemStack(this.random.nextBoolean() ? Items.WOODEN_SWORD : Items.STONE_SWORD);
		case 1:
			return new ItemStack(Items.BOW);
		case 2:
			return new ItemStack(Items.AIR);
		}
	}
	
	private ItemStack getOffhandItem()
	{
		return this.random.nextInt(8) == 0 ? new ItemStack(Items.SHIELD) : new ItemStack(Items.AIR);
	}
	
	public static boolean canSpawn(EntityType<? extends EntityJungleSkeleton> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random)
	{
		//System.out.println(world.getBlockState(pos.down()).getBlock());
		return world.getDifficulty() != Difficulty.PEACEFUL;// &&
		//		world.getBlockState(pos.down()).getBlock() == BlocksRegistry.JUNGLE_GRASS;
	}
}
