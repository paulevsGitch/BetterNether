package paulevs.betternether.entity;

import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityNagaProjectile extends Entity
{
	private LivingEntity owner;

	public EntityNagaProjectile(EntityType<? extends EntityNagaProjectile> type, World world)
	{
		super(type, world);
	}

	public void setParams(LivingEntity owner, LivingEntity target)
	{
		this.owner = owner;
		this.setPositionAndAngles(owner.getX(), owner.getEyeY(), owner.getZ(), owner.yaw, owner.pitch);
		this.updatePosition();
		Vec3d dir = target.getPos().add(0, target.getHeightOffset() * 0.5, 0).subtract(getPos()).normalize();
		this.setVelocity(dir);
		this.prevX = getX() - dir.x;
		this.prevY = getY() - dir.y;
		this.prevZ = getZ() - dir.z;
	}

	@Override
	public boolean hasNoGravity()
	{
		return true;
	}

	@Override
	protected void initDataTracker() {}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {}

	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {}

	@Override
	public Packet<?> createSpawnPacket()
	{
		int i = this.owner == null ? 0 : this.owner.getEntityId();
		return new EntitySpawnS2CPacket(this.getEntityId(), this.getUuid(), this.getX(), this.getY(), this.getZ(), this.pitch, this.yaw, this.getType(), i, this.getVelocity());
	}

	@Override
	public void tick()
	{
		super.tick();
		//prevX = getX();
		//prevX = getY();
		//prevZ = getZ();
		//setPosition(getX() + getVelocity().x, getY() + getVelocity().y, getZ() + getVelocity().z);
		//for (int i = 0; i < 10; i++)
		//	world.addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0, 0, 0);
		if (getX() == prevX && getY() == prevY && getZ() == prevZ)
		{
			world.addParticle(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 0, 0, 0);
			this.kill();
		}
		//System.out.println(this.isAlive());
	}
}