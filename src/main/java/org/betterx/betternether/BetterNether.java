package org.betterx.betternether;

import org.betterx.bclib.api.v2.dataexchange.DataExchangeAPI;
import org.betterx.bclib.networking.VersionCheckEntryPoint;
import org.betterx.betternether.advancements.BNCriterion;
import org.betterx.betternether.commands.CommandRegistry;
import org.betterx.betternether.config.Config;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.enchantments.ObsidianBreaker;
import org.betterx.betternether.loot.BNLoot;
import org.betterx.betternether.recipes.IntegrationRecipes;
import org.betterx.betternether.recipes.ItemRecipes;
import org.betterx.betternether.registry.*;
import org.betterx.betternether.world.BNWorldGenerator;
import org.betterx.worlds.together.util.Logger;
import org.betterx.worlds.together.world.WorldConfig;

import net.minecraft.resources.ResourceLocation;

import net.fabricmc.api.ModInitializer;

public class BetterNether implements ModInitializer, VersionCheckEntryPoint {
    public static final String MOD_ID = "betternether";
    public static final Logger LOGGER = new Logger(MOD_ID);
    private static boolean thinArmor = true;
    private static boolean lavafallParticles = true;
    private static float fogStart = 0.05F;
    private static float fogEnd = 0.5F;

    @Override
    public void onInitialize() {
        LOGGER.info("=^..^=    BetterNether for 1.19    =^..^=");
        //MigrationProfile.fixCustomFolder(new File("/Users/frank/Entwicklung/BetterNether/src/main/resources/data/betternether/structures/lava"));
        initOptions();
        SoundsRegistry.register();
        NetherBlocks.register();
        BlockEntitiesRegistry.register();
        NetherItems.register();
        NetherEntities.register();
        BNWorldGenerator.onModInit();
        NetherPoiTypes.register();
        NetherFeatures.register();
        NetherStructures.register();
        NetherBiomes.register();
        BrewingRegistry.register();
        CommandRegistry.register();
        FlatLevelPresetsRegistry.register();
        ObsidianBreaker.register();
        Config.save();

        IntegrationRecipes.register();
        NetherTags.register();
        ItemRecipes.register();
        BNLoot.register();
        BNCriterion.register();

        Configs.saveConfigs();
        WorldConfig.registerModCache(MOD_ID);
        DataExchangeAPI.registerMod(BetterNether.MOD_ID);
        Patcher.register();
    }

    private void initOptions() {
        thinArmor = Configs.MAIN.getBoolean("improvement", "smaller_armor_offset", true);
        lavafallParticles = Configs.MAIN.getBoolean("improvement", "lavafall_particles", true);
        float density = Configs.MAIN.getFloat("improvement", "fog_density[vanilla: 1.0]", 0.75F);
        changeFogDensity(density);
    }

    public static boolean hasThinArmor() {
        return thinArmor;
    }

    public static void setThinArmor(boolean value) {
        thinArmor = value;
    }

    public static boolean hasLavafallParticles() {
        return lavafallParticles;
    }

    public static void changeFogDensity(float density) {
        fogStart = -0.45F * density + 0.5F;
        fogEnd = -0.5F * density + 1;
    }

    public static float getFogStart() {
        return fogStart;
    }

    public static float getFogEnd() {
        return fogEnd;
    }

    public static ResourceLocation makeID(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Override
    public ResourceLocation updaterIcon(String modID) {
        if (modID.equals(MOD_ID))
            return makeID("icon_update.png");
        
        return null;
    }
}

