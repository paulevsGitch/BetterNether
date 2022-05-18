package org.betterx.betternether.entity.model;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;

import com.google.common.collect.ImmutableList;
import org.betterx.betternether.entity.EntityChair;

public class ModelEmpty extends AgeableListModel<EntityChair> {
    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of();
    }

    @Override
    public void setupAnim(EntityChair entity,
                          float limbAngle,
                          float limbDistance,
                          float customAngle,
                          float headYaw,
                          float headPitch) {

    }
}
