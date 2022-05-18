package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.advancements.BNCriterion;

import java.util.Iterator;

public class BNObsidian extends BaseBlock {
    final Block transformsTo;

    public BNObsidian() {
        this(null);
    }

    public BNObsidian(Block transformsTo) {
        this(FabricBlockSettings.copyOf(Blocks.OBSIDIAN), transformsTo);
    }

    protected BNObsidian(Properties settings, Block transformsTo) {
        super(settings);
        this.transformsTo = transformsTo;

    }

    @Override
    public void neighborChanged(BlockState blockState,
                                Level level,
                                BlockPos blockPos,
                                Block block,
                                BlockPos rodPos,
                                boolean bl) {
        if (transformsTo != null) {
            final BlockState updaterState = level.getBlockState(rodPos);
            if (updaterState.is(Blocks.LIGHTNING_ROD)) {
                if (updaterState.getValue(LightningRodBlock.POWERED)) {
                    BNObsidian.onLightningUpdate(level, blockPos, transformsTo);
                }
            }
        }
    }

    public static void onLightningUpdate(Level level, BlockPos blockPos, Block transformsTo) {
        BlocksHelper.setWithoutUpdate(level, blockPos, transformsTo.defaultBlockState());

        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        Iterator nearbyPlayer = level.getEntitiesOfClass(ServerPlayer.class,
                                                         (new AABB(x, y, z, x, y - 4, z)).inflate(10.0D, 5.0D, 10.0D))
                                     .iterator();

        while (nearbyPlayer.hasNext()) {
            final ServerPlayer serverPlayer = (ServerPlayer) nearbyPlayer.next();
            BNCriterion.CONVERT_BY_LIGHTNING.trigger(serverPlayer, transformsTo.asItem());
        }
    }
}
