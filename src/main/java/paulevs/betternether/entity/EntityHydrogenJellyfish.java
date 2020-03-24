package paulevs.betternether.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

public class EntityHydrogenJellyfish extends FlyingEntity implements Flutterer
{
	private static final TrackedData<Float> SCALE = DataTracker.registerData(EntityHydrogenJellyfish.class, TrackedDataHandlerRegistry.FLOAT);

	public EntityHydrogenJellyfish(EntityType<? extends EntityHydrogenJellyfish> type, World world)
	{
		super(type, world);
	}

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(SCALE, 0.5F + random.nextFloat());
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag)
	{
		super.writeCustomDataToTag(tag);

		tag.putFloat("Scale", getScale());
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag)
	{
		super.readCustomDataFromTag(tag);

		if (tag.contains("Scale"))
		{
			this.dataTracker.set(SCALE, tag.getFloat("Scale"));
		}

		this.calculateDimensions();
	}

	public float getScale()
	{
		return this.dataTracker.get(SCALE);
	}

	public EntityDimensions getDimensions(EntityPose pose)
	{
		return super.getDimensions(pose).scaled(this.getScale());
	}
	
	@Override
	public void onPlayerCollision(PlayerEntity player)
	{
		this.dealDamage(this, player);
	}

	@Override
	public void calculateDimensions()
	{
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		super.calculateDimensions();
		this.setPosition(x, y, z);
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data)
	{
		if (SCALE.equals(data))
		{
			this.calculateDimensions();
		}
	}
}
