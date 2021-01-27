package com.paulevs.betternether;


import com.paulevs.betternether.blocks.BNRenderLayer;
import com.paulevs.betternether.client.IRenderTypeable;
import com.paulevs.betternether.entity.EntityChair;
import com.paulevs.betternether.entity.render.RenderChair;
import com.paulevs.betternether.registry.EntityRenderRegistry;
import com.paulevs.betternether.registry.RegistryHandler;
import com.paulevs.betternether.registry.TileEntitiesRegistry;
import com.paulevs.betternether.registry.TileEntityRenderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.paulevs.betternether.registry.RegistryHandler.CHAIR;

@Mod("betternether")
public class BetterNether
{
    public static final Logger LOGGER = LogManager.getLogger() ;
    public static final String MOD_ID = "betternether";

    public BetterNether() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        modEventBus.addGenericListener(Block.class, RegistryHandler::registerAllBlocks);
        modEventBus.addGenericListener(Item.class, RegistryHandler::registerAllItems);
        modEventBus.addGenericListener(TileEntityType.class, TileEntitiesRegistry::registerAll);
        modEventBus.addGenericListener(EntityType.class, RegistryHandler::registerAll);


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        modEventBus.addListener(this::clientSetup);


        MinecraftForge.EVENT_BUS.register(this);
        NetherTags.init();

    }

    private void clientSetup(FMLClientSetupEvent e) {
        registerRenderLayers();
        TileEntityRenderRegistry.register();
        EntityRenderRegistry.register();

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        GlobalEntityTypeAttributes.put(CHAIR, EntityChair.getAttributeContainer());

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void registerRenderLayers() {
        RenderType cutout = RenderType.getCutout();
        RenderType translucent = RenderType.getTranslucent();
        ForgeRegistries.BLOCKS.forEach(block -> {
            if (block instanceof IRenderTypeable) {
                BNRenderLayer layer = ((IRenderTypeable) block).getRenderLayer();
                if (layer == BNRenderLayer.CUTOUT)
                    RenderTypeLookup.setRenderLayer(block, cutout);
                else if (layer == BNRenderLayer.TRANSLUCENT)
                    RenderTypeLookup.setRenderLayer(block, translucent);
            }
        });
    }




    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    public static final ItemGroup BN_TAB = new ItemGroup("betternether") {

        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.NETHER_GRASS.getBlock());
        }
    };




}
