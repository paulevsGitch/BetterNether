package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.mojang.math.Vector3f;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.registry.NetherBlocks;

public class BlockStatueRespawner extends BlockBaseNotFull {
    private static final VoxelShape SHAPE = box(1, 0, 1, 15, 16, 15);
    private static final DustParticleOptions EFFECT = new DustParticleOptions(Vector3f.XP, 1.0F);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    private final ItemStack requiredItem;

    public BlockStatueRespawner() {
        super(FabricBlockSettings.copyOf(NetherBlocks.CINCINNASITE_BLOCK).luminance(15).noOcclusion());
        this.setRenderLayer(BNRenderLayer.CUTOUT);
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(TOP, false));
        this.setDropItself(false);

        String itemName = Configs.MAIN.getString("respawn_statue",
                                                 "respawn_item",
                                                 Registry.ITEM.getKey(Items.GLOWSTONE).toString());
        Item item = Registry.ITEM.get(new ResourceLocation(itemName));
        if (item == Items.AIR)
            item = Items.GLOWSTONE;
        int count = Configs.MAIN.getInt("respawn_statue", "item_count", 4);
        requiredItem = new ItemStack(item, count);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING, TOP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
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
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() == requiredItem.getItem() && stack.getCount() >= requiredItem.getCount()) {
            float y = state.getValue(TOP) ? 0.4F : 1.4F;
            if (!player.isCreative()) {
                player.getMainHandItem().shrink(requiredItem.getCount());
            }
            for (int i = 0; i < 50; i++)
                world.addParticle(EFFECT,
                                  pos.getX() + world.random.nextFloat(),
                                  pos.getY() + y + world.random.nextFloat() * 0.2,
                                  pos.getZ() + world.random.nextFloat(), 0, 0, 0);
            player.displayClientMessage(Component.translatable("message.spawn_set", new Object[0]), true);
            if (!world.isClientSide) {
                ((ServerPlayer) player).setRespawnPosition(world.dimension(), pos, player.getYHeadRot(), false, true);
            }
            player.playSound(SoundEvents.TOTEM_USE, 0.7F, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            player.displayClientMessage(Component.translatable("message.spawn_help", requiredItem), true);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (state.getValue(TOP))
            return true;
        BlockState up = world.getBlockState(pos.above());
        return up.isAir() || (up.getBlock() == this && up.getValue(TOP));
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClientSide())
            BlocksHelper.setWithUpdate(world, pos.above(), state.setValue(TOP, true));
    }

    @Override
    public BlockState updateShape(BlockState state,
                                  Direction facing,
                                  BlockState neighborState,
                                  LevelAccessor world,
                                  BlockPos pos,
                                  BlockPos neighborPos) {
        if (state.getValue(TOP)) {
            return world.getBlockState(pos.below()).getBlock() == this ? state : Blocks.AIR.defaultBlockState();
        } else {
            return world.getBlockState(pos.above()).getBlock() == this ? state : Blocks.AIR.defaultBlockState();
        }
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
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (player.isCreative() && state.getValue(TOP) && world.getBlockState(pos.below()).getBlock() == this) {
            world.setBlockAndUpdate(pos.below(), Blocks.AIR.defaultBlockState());
        }
        super.playerWillDestroy(world, pos, state, player);
    }
}
