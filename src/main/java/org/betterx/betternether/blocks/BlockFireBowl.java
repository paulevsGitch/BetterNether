package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import java.util.function.ToIntFunction;

public class BlockFireBowl extends BlockBaseNotFull {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 12, 16);
    public static final BooleanProperty FIRE = BNBlockProperties.FIRE;

    public BlockFireBowl(Block source) {
        super(FabricBlockSettings.copyOf(source).noOcclusion().lightLevel(getLuminance()));
        this.registerDefaultState(getStateDefinition().any().setValue(FIRE, false));
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    protected static ToIntFunction<BlockState> getLuminance() {
        return (state) -> {
            return state.getValue(FIRE) ? 15 : 0;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(FIRE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state,
                                 Level world,
                                 BlockPos pos,
                                 Player player,
                                 InteractionHand hand,
                                 BlockHitResult hit) {
        if (hit.getDirection() == Direction.UP) {
            if (player.getMainHandItem().getItem() == Items.FLINT_AND_STEEL && !state.getValue(FIRE)) {
                world.setBlockAndUpdate(pos, state.setValue(FIRE, true));
                if (!player.isCreative() && !world.isClientSide)
                    player.getMainHandItem().hurt(1, world.random, (ServerPlayer) player);
                world.playSound(player,
                        pos,
                        SoundEvents.FLINTANDSTEEL_USE,
                        SoundSource.BLOCKS,
                        1.0F,
                        world.random.nextFloat() * 0.4F + 0.8F);
                return InteractionResult.SUCCESS;
            } else if (player.getMainHandItem().isEmpty() && state.getValue(FIRE)) {
                world.setBlockAndUpdate(pos, state.setValue(FIRE, false));
                world.playSound(player,
                        pos,
                        SoundEvents.FIRE_EXTINGUISH,
                        SoundSource.BLOCKS,
                        1.0F,
                        world.random.nextFloat() * 0.4F + 0.8F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.fireImmune() && entity instanceof LivingEntity && world.getBlockState(pos).getValue(FIRE)) {
            entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
        }
    }

    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(FIRE)) {
            if (random.nextInt(24) == 0)
                world.playLocalSound(pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        SoundEvents.FIRE_AMBIENT,
                        SoundSource.BLOCKS,
                        1.0F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false);
            if (random.nextInt(4) == 0)
                world.addParticle(ParticleTypes.LARGE_SMOKE,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.75,
                        pos.getZ() + random.nextDouble(),
                        0.0D,
                        0.0D,
                        0.0D);
        }
    }
}