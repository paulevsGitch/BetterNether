package paulevs.betternether.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import paulevs.betternether.blocks.BNChair;

public class EntityChair extends Mob {
	public EntityChair(EntityType<? extends EntityChair> type, Level world) {
		super(type, world);
	}

	@Override
	public void tick() {
		if (!this.isVehicle())
			this.discard();
		else if (this.level.getBlockState(this.blockPosition()).getBlock() instanceof BNChair)
			super.tick();
		else {
			this.ejectPassengers();
			this.discard();
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.setDeltaMovement(Vec3.ZERO);
	}
	
	@Override
	public boolean isAlive() {
		return !this.isRemoved();
	}
	
	public static AttributeSupplier getAttributeContainer() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 0).build();
		
	}
}
