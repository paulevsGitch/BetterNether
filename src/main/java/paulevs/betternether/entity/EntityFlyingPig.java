package paulevs.betternether.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import paulevs.betternether.MHelper;

public class EntityFlyingPig extends HostileEntity
{
	private static final TrackedData<Byte> FLAGS;
	private static final int BIT_ROOSTING = 0;

	public EntityFlyingPig(EntityType<? extends EntityFlyingPig> type, World world)
	{
		super(type, world);
	}

	public static DefaultAttributeContainer getAttributeContainer()
	{
		return MobEntity
				.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
				.add(EntityAttributes.GENERIC_ARMOR, 1.0)
				.build();
	}

	@Override
	protected void initDataTracker()
	{
		super.initDataTracker();
		this.dataTracker.startTracking(FLAGS, (byte) 0);
	}

	public boolean isRoosting()
	{
		byte b = this.dataTracker.get(FLAGS);
		return MHelper.getBit(b, BIT_ROOSTING);
	}

	public void setRoosting(boolean roosting)
	{
		byte b = this.dataTracker.get(FLAGS);
		this.dataTracker.set(FLAGS, MHelper.setBit(b, BIT_ROOSTING, roosting));
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.3F;
	}

	@Override
	protected float getSoundPitch()
	{
		return 0.2F;
	}

	static
	{
		FLAGS = DataTracker.registerData(EntityFlyingPig.class, TrackedDataHandlerRegistry.BYTE);
	}
}