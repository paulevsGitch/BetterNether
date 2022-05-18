package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import org.betterx.betternether.BlocksHelper;

public class BlockBNPot extends BlockBaseNotFull {
    private static final VoxelShape SHAPE = box(3, 0, 3, 13, 8, 13);

    public BlockBNPot(Block material) {
        super(FabricBlockSettings.copy(material).noOcclusion());
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
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
        BlockPos plantPos = pos.above();
        if (hit.getDirection() == Direction.UP && world.isEmptyBlock(plantPos)) {
            BlockState plant = BlockPottedPlant.getPlant(player.getMainHandItem().getItem());
            if (plant != null) {
                if (!world.isClientSide())
                    BlocksHelper.setWithUpdate(world, plantPos, plant);
                world.playLocalSound(
                        pos.getX() + 0.5,
                        pos.getY() + 1.5,
                        pos.getZ() + 0.5,
                        SoundEvents.CROP_PLANTED,
                        SoundSource.BLOCKS,
                        0.8F,
                        1.0F,
                        true);
                if (!player.isCreative())
                    player.getMainHandItem().shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
