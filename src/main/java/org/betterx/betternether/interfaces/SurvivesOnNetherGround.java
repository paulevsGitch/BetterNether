package org.betterx.betternether.interfaces;

import org.betterx.bclib.interfaces.SurvivesOnSpecialGround;
import org.betterx.betternether.BlocksHelper;

import net.minecraft.locale.Language;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public interface SurvivesOnNetherGround extends SurvivesOnSpecialGround {
    @Override
    default String getSurvivableBlocksString() {
        return Language.getInstance().getOrDefault("betternether.survive.nethergound");
    }

    @Override
    default boolean isSurvivable(BlockState state) {
        return BlocksHelper.isNetherGround(state) || state.is(Blocks.RED_SAND) || state.is(Blocks.SAND);
    }
}
