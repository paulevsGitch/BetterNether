package org.betterx.betternether.blocks;

import org.betterx.bclib.interfaces.tools.AddMineableAxe;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.blocks.BNBlockProperties.EnumLucisShape;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.google.common.collect.Lists;

import java.util.List;

public class BlockLucisMushroom extends BlockBaseNotFull implements AddMineableAxe {
    private static final VoxelShape V_SHAPE = box(0, 0, 0, 16, 9, 16);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<EnumLucisShape> SHAPE = BNBlockProperties.LUCIS_SHAPE;

    public BlockLucisMushroom() {
        super(FabricBlockSettings.of(Materials.NETHER_GRASS)
                                 .mapColor(MaterialColor.COLOR_YELLOW)
                                 .luminance(15)
                                 .requiresTool()
                                 .sounds(SoundType.WOOD)
                                 .hardness(1F)
                                 .noOcclusion());
        this.registerDefaultState(getStateDefinition().any()
                                                      .setValue(FACING, Direction.NORTH)
                                                      .setValue(SHAPE, EnumLucisShape.CORNER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING, SHAPE);
    }

    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return V_SHAPE;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Lists.newArrayList(
                new ItemStack(NetherBlocks.LUCIS_SPORE),
                new ItemStack(NetherItems.GLOWSTONE_PILE, MHelper.randRange(2, 4, MHelper.RANDOM))
        );
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return BlocksHelper.rotateHorizontal(state, rotation, FACING);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.FRONT_BACK) {
            if (state.getValue(SHAPE) == EnumLucisShape.SIDE)
                state = state.setValue(FACING, state.getValue(FACING).getCounterClockWise());
            if (state.getValue(FACING) == Direction.NORTH) return state.setValue(FACING, Direction.WEST);
            if (state.getValue(FACING) == Direction.WEST) return state.setValue(FACING, Direction.NORTH);
            if (state.getValue(FACING) == Direction.SOUTH) return state.setValue(FACING, Direction.EAST);
            if (state.getValue(FACING) == Direction.EAST) return state.setValue(FACING, Direction.SOUTH);
        } else if (mirror == Mirror.LEFT_RIGHT) {
            if (state.getValue(SHAPE) == EnumLucisShape.SIDE)
                state = state.setValue(FACING, state.getValue(FACING).getCounterClockWise());
            if (state.getValue(FACING) == Direction.NORTH) return state.setValue(FACING, Direction.EAST);
            if (state.getValue(FACING) == Direction.EAST) return state.setValue(FACING, Direction.NORTH);
            if (state.getValue(FACING) == Direction.SOUTH) return state.setValue(FACING, Direction.WEST);
            if (state.getValue(FACING) == Direction.WEST) return state.setValue(FACING, Direction.SOUTH);
        }
        return state;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(NetherBlocks.LUCIS_SPORE);
    }
}
