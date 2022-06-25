package org.betterx.betternether.blocks;

import org.betterx.bclib.interfaces.tools.AddMineableHoe;
import org.betterx.bclib.interfaces.tools.AddMineableShears;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.interfaces.SurvivesOnGravel;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import com.google.common.collect.Lists;

import java.util.List;

public class BlockAgave extends BlockCommonPlant implements AddMineableShears, AddMineableHoe, SurvivesOnGravel {
    private static final VoxelShape SHAPE = box(2, 0, 2, 14, 14, 14);
    private static final RandomSource RANDOM = new LegacyRandomSource(030620222201l);

    public BlockAgave() {
        super(FabricBlockSettings.of(Material.CACTUS)
                                 .mapColor(MaterialColor.TERRACOTTA_ORANGE)
                                 .requiresTool()
                                 .sounds(SoundType.WOOL)
                                 .noOcclusion()
                                 .noCollission()
                                 .destroyTime(0.4F)
                                 .randomTicks()
                                 .instabreak()
                                 .offsetType(Block.OffsetType.XZ)
        );
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        Vec3 vec3d = state.getOffset(view, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
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
        if (canSurvive(state, world, pos))
            return state;
        else
            return Blocks.AIR.defaultBlockState();
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (state.getValue(BlockCommonPlant.AGE) > 1) entity.hurt(DamageSource.CACTUS, 1.0F);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        if (state.getValue(BlockCommonPlant.AGE) == 3) {
            return Lists.newArrayList(new ItemStack(this, MHelper.randRange(1, 2, RANDOM)), new ItemStack(
                    NetherItems.AGAVE_LEAF, MHelper.randRange(2, 5, RANDOM)));
        }
        return Lists.newArrayList(new ItemStack(this));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSurviveOnTop(state, world, pos);
    }
}
