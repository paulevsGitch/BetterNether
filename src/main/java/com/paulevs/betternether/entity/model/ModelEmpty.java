package com.paulevs.betternether.entity.model;

import com.google.common.collect.ImmutableList;

import com.paulevs.betternether.entity.EntityChair;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelEmpty extends AgeableModel<EntityChair> {
	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of();
	}

	@Override
	public void setRotationAngles(EntityChair entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {

	}
}