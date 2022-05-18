package org.betterx.betternether.integrations;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;

import net.fabricmc.loader.api.FabricLoader;

import org.apache.logging.log4j.LogManager;
import org.betterx.betternether.registry.NetherItems;

import java.lang.reflect.Constructor;

public class VanillaExcavatorsIntegration {
    private static boolean hasExcavators;
    private static Constructor<?> excavatorConstructor;

    public static Item makeExcavator(Tier material, int attackDamage, float attackSpeed) {
        if (!hasExcavators)
            return Items.AIR;
        try {
            return (Item) excavatorConstructor.newInstance(material,
                                                           attackDamage,
                                                           attackSpeed,
                                                           NetherItems.defaultSettings());
        } catch (Exception e) {
            e.printStackTrace();
            return Items.AIR;
        }
    }

    static {
        hasExcavators = FabricLoader.getInstance().isModLoaded("vanillaexcavators");
        try {
            if (hasExcavators) {
                LogManager.getLogger().info("[BetterNether] Enabled Vanilla Excavators Integration");
                Class<?> itemClass = Class.forName("draylar.magna.item.ExcavatorItem");
                if (itemClass != null)
                    for (Constructor<?> c : itemClass.getConstructors())
                        if (c.getParameterCount() == 4) {
                            excavatorConstructor = c;
                            break;
                        }
                hasExcavators = (excavatorConstructor != null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasExcavators() {
        return hasExcavators;
    }
}
