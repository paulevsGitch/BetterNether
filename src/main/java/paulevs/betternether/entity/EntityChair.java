package paulevs.betternether.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BNChair;

public class EntityChair extends MobEntity
{
	public EntityChair(EntityType<? extends EntityChair> type, World world)
	{
		super(type, world);
	}
	
	@Override
	public void tick()
	{
		if (!this.hasPassengers())
			this.remove();
		else if (this.getBlockState().getBlock() instanceof BNChair)
			super.tick();
		else
		{
			this.removeAllPassengers();
			this.remove();
		}
	}

	@Override
	public void tickMovement()
	{
		super.tickMovement();
		this.setVelocity(Vec3d.ZERO);
	}
}
