package org.betterx.betternether.blocks;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.interfaces.SurvivesOnNetherMycelium;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BlockMold extends BaseBlockMold implements SurvivesOnNetherMycelium {
    public BlockMold(MaterialColor color) {
        super(color);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSurviveOnTop(world, pos);
    }
}

class BaseBlockMold extends BlockBaseNotFull {
    public BaseBlockMold(MaterialColor color) {
        this(color, p -> p);
    }

    public BaseBlockMold(MaterialColor color, Function<Properties, Properties> adaptProperties) {
        super(adaptProperties.apply(Materials.makeGrass(color)
                                             .sound(SoundType.CROP))
        );
        this.setRenderLayer(BNRenderLayer.CUTOUT);
        this.setDropItself(false);
    }

    public BaseBlockMold(Properties settings) {
        super(settings);
        this.setRenderLayer(BNRenderLayer.CUTOUT);
        this.setDropItself(false);
    }

    @Environment(EnvType.CLIENT)
    public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
        return 1.0F;
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
        if (!canSurvive(state, world, pos))
            return Blocks.AIR.defaultBlockState();
        else
            return state;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        if (random.nextInt(16) == 0) {
            int c = 0;
            c = world.getBlockState(pos.north()).getBlock() == this ? c++ : c;
            c = world.getBlockState(pos.south()).getBlock() == this ? c++ : c;
            c = world.getBlockState(pos.east()).getBlock() == this ? c++ : c;
            c = world.getBlockState(pos.west()).getBlock() == this ? c++ : c;
            if (c < 2) {
                BlockPos npos = new BlockPos(pos);
                switch (random.nextInt(4)) {
                    case 0:
                        npos = npos.offset(-1, 0, 0);
                        break;
                    case 1:
                        npos = npos.offset(1, 0, 0);
                        break;
                    case 2:
                        npos = npos.offset(0, 0, -1);
                        break;
                    default:
                        npos = npos.offset(0, 0, 1);
                        break;
                }
                if (world.isEmptyBlock(npos) && canSurvive(state, world, npos)) {
                    BlocksHelper.setWithoutUpdate(world, npos, defaultBlockState());
                }
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        if (builder.getParameter(LootContextParams.TOOL).getItem() instanceof ShearsItem)
            return Collections.singletonList(new ItemStack(this.asItem()));
        else
            return super.getDrops(state, builder);
    }
}
