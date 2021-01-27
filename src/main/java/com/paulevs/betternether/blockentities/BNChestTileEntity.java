package com.paulevs.betternether.blockentities;

import com.paulevs.betternether.registry.TileEntitiesRegistry;
import net.minecraft.tileentity.ChestTileEntity;

public class BNChestTileEntity extends ChestTileEntity {
	public BNChestTileEntity() {
		super(TileEntitiesRegistry.CHEST);
	}
}
