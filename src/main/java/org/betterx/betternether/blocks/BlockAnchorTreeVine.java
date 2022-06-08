package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BlockProperties.TripleShape;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import java.util.function.ToIntFunction;

public class BlockAnchorTreeVine extends BlockBaseNotFull {
    protected static final VoxelShape SHAPE_SELECTION = Block.box(4, 0, 4, 12, 16, 12);
    public static final EnumProperty<TripleShape> SHAPE = BNBlockProperties.TRIPLE_SHAPE;

    public BlockAnchorTreeVine() {
        super(FabricBlockSettings.of(Materials.NETHER_PLANT)
                                 .mapColor(MaterialColor.COLOR_GREEN)
                                 .sounds(SoundType.CROP)
                                 .noCollission()
                                 .noLootTable()
                                 .instabreak()
                                 .noOcclusion()
                                 .lightLevel(getLuminance()));
        this.setRenderLayer(BNRenderLayer.CUTOUT);
        setDropItself(false);
    }

    protected static ToIntFunction<BlockState> getLuminance() {
        return (state) -> {
            return state.getValue(SHAPE) == TripleShape.BOTTOM ? 15 : 0;
        };
    }

    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        Vec3 vec3d = state.getOffset(view, pos);
        return SHAPE_SELECTION.move(vec3d.x, vec3d.y, vec3d.z);
    }

    @Environment(EnvType.CLIENT)
    public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction facing,
            BlockState neighborState,
            LevelAccessor world,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        Block up = world.getBlockState(pos.above()).getBlock();
        if (up != this && up != NetherBlocks.ANCHOR_TREE_LEAVES && up != Blocks.NETHERRACK)
            return Blocks.AIR.defaultBlockState();
        else
            return state;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(NetherBlocks.ANCHOR_TREE_LEAVES);
    }
}