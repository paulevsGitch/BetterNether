package com.paulevs.betternether.registry;

import com.paulevs.betternether.blockentities.render.BNChestTileEntityRenderer;
import com.paulevs.betternether.blockentities.render.BNSignTileEntityRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;


public class TileEntityRenderRegistry {
	public static void register() {
		ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.CHEST, BNChestTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.SIGN, BNSignTileEntityRenderer::new);
	}
}
