package org.betterx.betternether.blocks;

import org.betterx.bclib.BCLib;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.entity.EntityChair;
import org.betterx.betternether.registry.NetherEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public class BNChair extends BlockBaseNotFull {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final float height;

    public BNChair(Block block, int height) {
        super(FabricBlockSettings.copyOf(block).noOcclusion());
        this.height = (height - 3F) / 16F;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(
            BlockState state,
            Level world,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (world.isClientSide) {
            return InteractionResult.FAIL;
        } else {
            if (player.isPassenger() || player.isSpectator())
                return InteractionResult.FAIL;


            Optional<EntityChair> active = getEntity(world, pos);
            EntityChair entity;

            if (active.isEmpty()) {
                entity = createEntity(state, world, pos);
            } else {
                entity = active.get();
                if (entity.isVehicle())
                    return InteractionResult.FAIL;
            }

            if (entity != null) {
                float yaw = state.getValue(FACING).getOpposite().toYRot();
                player.startRiding(entity, true);
                player.setYBodyRot(yaw);
                player.setYHeadRot(yaw);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;
        }
    }

    @Nullable
    private EntityChair createEntity(BlockState state, Level world, BlockPos pos) {
        BCLib.LOGGER.info("Creating Chair at " + pos + ", " + state);
        EntityChair entity;
        double px = pos.getX() + 0.5;
        double py = pos.getY() + height;
        double pz = pos.getZ() + 0.5;
        float yaw = state.getValue(FACING).getOpposite().toYRot();

        entity = NetherEntities.CHAIR.create(world);
        entity.moveTo(px, py, pz, yaw, 0);
        entity.setNoGravity(true);
        entity.setSilent(true);
        entity.setInvisible(true);
        entity.setYHeadRot(yaw);
        entity.setYBodyRot(yaw);
        if (!world.addFreshEntity(entity)) {
            entity = null;
        }
        return entity;
    }

    private Optional<EntityChair> getEntity(Level level, BlockPos pos) {
        List<EntityChair> list = level.getEntitiesOfClass(
                EntityChair.class,
                new AABB(pos),
                entity -> true
        );
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.get(0));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return BlocksHelper.rotateHorizontal(state, rotation, FACING);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return BlocksHelper.mirrorHorizontal(state, mirror, FACING);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        BCLib.LOGGER.info("Created at " + blockPos + ", " + blockState + ", " + blockState2);
        if (blockState.hasProperty(BNNormalChair.TOP)) {
            if (blockState.getValue(BNNormalChair.TOP))
                return;
        }
        createEntity(blockState, level, blockPos);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);
//        Optional<EntityChair> e = getEntity(level, blockPos);
//
//        if (e.isPresent()) {
//            BCLib.LOGGER.info("Discarding Chair at " + blockPos);
//            e.get().discard();
//        }
    }
}
