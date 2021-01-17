package com.paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;

public interface IStructure {
	public void generate(IServerWorld world, BlockPos pos, Random random);
}