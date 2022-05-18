package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import org.betterx.betternether.blockentities.BlockEntityForge;
import org.betterx.betternether.registry.BlockEntitiesRegistry;
import org.betterx.betternether.registry.NetherBlocks;

import java.util.function.ToIntFunction;

public class BlockCincinnasiteForge extends AbstractFurnaceBlock {
    public BlockCincinnasiteForge() {
        super(FabricBlockSettings.copy(NetherBlocks.CINCINNASITE_BLOCK)
                                 .requiresCorrectToolForDrops()
                                 .lightLevel(getLuminance()));
    }

    private static ToIntFunction<BlockState> getLuminance() {
        return (blockState) -> {
            return (Boolean) blockState.getValue(BlockStateProperties.LIT) ? 13 : 0;
        };
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityForge(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world,
                                                                  BlockState state,
                                                                  BlockEntityType<T> type) {
        return createFurnaceTicker(world, type, BlockEntitiesRegistry.CINCINNASITE_FORGE);
    }

    @Override
    protected void openContainer(Level world, BlockPos pos, Player player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BlockEntityForge) {
            player.openMenu((MenuProvider) blockEntity);
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            double d = (double) pos.getX() + 0.5D;
            double e = pos.getY();
            double f = (double) pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.1D) {
                world.playLocalSound(d, e, f, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double h = random.nextDouble() * 0.6D - 0.3D;
            double i = axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : h;
            double j = random.nextDouble() * 6.0D / 16.0D;
            double k = axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
        }
    }
}
