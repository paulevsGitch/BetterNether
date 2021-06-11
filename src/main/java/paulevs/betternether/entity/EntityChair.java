package paulevs.betternether.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BNChair;

public class EntityChair extends MobEntity {
	public EntityChair(EntityType<? extends EntityChair> type, World world) {
		super(type, world);
	}

	@Override
	public void tick() {
		if (!this.hasPassengers())
			this.discard();
		else if (this.world.getBlockState(this.getBlockPos()).getBlock() instanceof BNChair)
			super.tick();
		else {
			this.removeAllPassengers();
			this.discard();
		}
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		this.setVelocity(Vec3d.ZERO);
	}
	
	@Override
	public boolean isAlive() {
		return !this.isRemoved();
	}
	
	public static DefaultAttributeContainer getAttributeContainer() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 0).build();
		
	}
}
