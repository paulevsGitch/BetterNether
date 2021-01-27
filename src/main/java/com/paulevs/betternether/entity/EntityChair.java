package com.paulevs.betternether.entity;

import com.paulevs.betternether.blocks.BNChair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EntityChair extends MobEntity {
	public EntityChair(EntityType<? extends EntityChair> type, World world) {
		super(type, world);
	}

	@Override
	public void tick() {
		if (!this.isBeingRidden())
			this.remove();
		else if (this.getBlockState().getBlock() instanceof BNChair)
			super.tick();
		else {
			this.removePassengers();
			this.remove();
		}
	}

	@Override
	public void livingTick() {
		super.livingTick();
		this.setMotion(Vector3d.ZERO);
	}
	
	public static AttributeModifierMap getAttributeContainer() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 0).create();
		
	}
}
