package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BlockProperties.TripleShape;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.interfaces.SurvivesOnNetherGround;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockSmoker extends BlockBaseNotFull implements SurvivesOnNetherGround {
    private static final VoxelShape TOP_SHAPE = box(4, 0, 4, 12, 8, 12);
    private static final VoxelShape MIDDLE_SHAPE = box(4, 0, 4, 12, 16, 12);
    public static final EnumProperty<TripleShape> SHAPE = BNBlockProperties.TRIPLE_SHAPE;

    public BlockSmoker() {
        super(Materials.makeWood(MaterialColor.COLOR_BROWN));
        this.registerDefaultState(getStateDefinition().any().setValue(SHAPE, TripleShape.TOP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (world.isEmptyBlock(pos.above()))
            world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return state.getValue(SHAPE) == TripleShape.TOP ? TOP_SHAPE : MIDDLE_SHAPE;
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
        if (!canSurvive(state, world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        Block side = world.getBlockState(pos.above()).getBlock();
        if (side != this)
            return state.setValue(SHAPE, TripleShape.TOP);
        side = world.getBlockState(pos.below()).getBlock();
        if (side == this)
            return state.setValue(SHAPE, TripleShape.MIDDLE);
        else
            return state.setValue(SHAPE, TripleShape.BOTTOM);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.below());
        return down.getBlock() == this || isSurvivable(down);
    }
}
