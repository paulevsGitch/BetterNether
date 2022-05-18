package org.betterx.betternether.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.betterx.betternether.blocks.BNChair;

public class EntityChair extends Entity {
    public EntityChair(EntityType<? extends EntityChair> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {

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
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public boolean isAlive() {
        return !this.isRemoved();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return this.getPassengers().size() == 0;
    }


    @Override
    public void push(Entity entity) {
        //Do nothing. Should not be pushable
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand interactionHand) {
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }

        if (!this.level.isClientSide) {
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    public static AttributeSupplier getAttributeContainer() {
        return AttributeSupplier.builder().build();
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }
}
