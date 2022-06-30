package org.betterx.betternether.blocks;

import org.betterx.betternether.config.Configs;
import org.betterx.betternether.interfaces.SurvivesOnNetherGround;
import org.betterx.betternether.registry.NetherEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collections;
import java.util.List;

public class BlockEggPlant extends BlockCommonPlant implements SurvivesOnNetherGround {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 8, 16);
    public static final BooleanProperty DESTRUCTED = BNBlockProperties.DESTRUCTED;

    private boolean enableModDamage = true;
    private boolean enablePlayerDamage = true;

    public BlockEggPlant() {
        super(MaterialColor.TERRACOTTA_WHITE);
        enableModDamage = Configs.MAIN.getBoolean("egg_plant", "mob_damage", true);
        enablePlayerDamage = Configs.MAIN.getBoolean("egg_plant", "player_damage", true);
        this.registerDefaultState(getStateDefinition().any().setValue(DESTRUCTED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        super.createBlockStateDefinition(stateManager);
        stateManager.add(DESTRUCTED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSurviveOnTop(world, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return SHAPE;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!state.getValue(DESTRUCTED))
            world.addParticle(
                    ParticleTypes.ENTITY_EFFECT,
                    pos.getX() + random.nextDouble(),
                    pos.getY() + 0.4,
                    pos.getZ() + random.nextDouble(),
                    0.46, 0.28, 0.55
            );
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!state.getValue(DESTRUCTED)) {
            if (enableModDamage && entity instanceof LivingEntity && !((LivingEntity) entity).hasEffect(MobEffects.POISON)) {
                if (!NetherEntities.isNetherEntity(entity))
                    ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, 100, 3));
            } else if (enablePlayerDamage && entity instanceof Player && !((Player) entity).hasEffect(MobEffects.POISON))
                ((Player) entity).addEffect(new MobEffectInstance(MobEffects.POISON, 100, 3));

            double px = pos.getX() + 0.5;
            double py = pos.getY() + 0.125;
            double pz = pos.getZ() + 0.5;
            if (world.isClientSide) {
                world.playLocalSound(px, py, pz, SoundType.WART_BLOCK.getBreakSound(), SoundSource.BLOCKS, 1, 1, false);
                BlockParticleOption effect = new BlockParticleOption(ParticleTypes.BLOCK, state);
                RandomSource random = world.random;
                for (int i = 0; i < 24; i++)
                    world.addParticle(
                            effect,
                            px + random.nextGaussian() * 0.2,
                            py + random.nextGaussian() * 0.2,
                            pz + random.nextGaussian() * 0.2,
                            random.nextGaussian(),
                            random.nextGaussian(),
                            random.nextGaussian()
                    );
            }

            world.setBlockAndUpdate(pos, state.setValue(DESTRUCTED, true));
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        if (builder.getParameter(LootContextParams.TOOL).getItem() instanceof ShearsItem)
            return Collections.singletonList(new ItemStack(this.asItem()));
        else
            return super.getDrops(state, builder);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        if (state.getValue(DESTRUCTED))
            world.setBlockAndUpdate(pos, this.defaultBlockState());
    }
}
