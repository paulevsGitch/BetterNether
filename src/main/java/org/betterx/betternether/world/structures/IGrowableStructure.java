package org.betterx.betternether.world.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;

public interface IGrowableStructure {
    void grow(ServerLevelAccessor world, BlockPos pos, RandomSource random);
}
