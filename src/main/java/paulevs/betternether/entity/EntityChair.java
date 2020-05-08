package paulevs.betternether.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
			Direction dir = Direction.fromRotation(this.yaw);
			BlockPos out = this.getBlockPos().offset(dir);
			List<Entity> list = this.getPassengerList();
			this.removeAllPassengers();
			this.remove();
			for (Entity e: list)
			{
				e.updatePosition(out.getX(), out.getY(), out.getZ());
				System.out.println(out);
			}
		}
	}

	@Override
	public void tickMovement()
	{
		super.tickMovement();
		this.setVelocity(Vec3d.ZERO);
	}
}
