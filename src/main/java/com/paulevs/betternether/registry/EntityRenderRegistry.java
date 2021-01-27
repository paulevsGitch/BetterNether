package com.paulevs.betternether.registry;

import com.paulevs.betternether.entity.render.RenderChair;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderRegistry {
    public static void register() {
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.CHAIR, RenderChair::new);
    }
}