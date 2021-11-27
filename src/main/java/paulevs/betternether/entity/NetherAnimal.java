package paulevs.betternether.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public abstract class NetherAnimal extends Animal {
	protected NetherAnimal(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.hasCustomName();
	}
}
