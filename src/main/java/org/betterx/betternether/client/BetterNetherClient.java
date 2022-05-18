package org.betterx.betternether.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

import org.betterx.bclib.integration.modmenu.ModMenu;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BNRenderLayer;
import org.betterx.betternether.config.screen.ConfigScreen;
import org.betterx.betternether.registry.EntityRenderRegistry;
import org.betterx.betternether.registry.NetherParticles;

public class BetterNetherClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderLayers();
        EntityRenderRegistry.register();

        NetherParticles.register();
        ModMenu.addModMenuScreen(BetterNether.MOD_ID, ConfigScreen::new);
    }

    private void registerRenderLayers() {
        RenderType cutout = RenderType.cutout();
        RenderType translucent = RenderType.translucent();
        Registry.BLOCK.forEach(block -> {
            if (block instanceof IRenderTypeable) {
                BNRenderLayer layer = ((IRenderTypeable) block).getRenderLayer();
                if (layer == BNRenderLayer.CUTOUT)
                    BlockRenderLayerMap.INSTANCE.putBlock(block, cutout);
                else if (layer == BNRenderLayer.TRANSLUCENT)
                    BlockRenderLayerMap.INSTANCE.putBlock(block, translucent);
            }
        });
    }
}
