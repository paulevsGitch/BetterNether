package org.betterx.betternether.blocks;

import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.interfaces.SurvivesOnNetherrack;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

public class BlockHookMushroom extends BaseBlockMold implements SurvivesOnNetherrack {
    public BlockHookMushroom() {
        super(Materials.makeGrass(MaterialColor.COLOR_PINK)
                       .luminance(13)
                       .sounds(SoundType.CROP)
                       .noOcclusion()
                       .noCollission()
                       .instabreak()
                       .randomTicks()
        );
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSurviveOnBottom(state, world, pos);
    }
}
