package org.betterx.betternether.integrations.emi;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.world.item.crafting.RecipeManager;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;

public class EMIPlugin implements EmiPlugin {
    public static final EmiIngredient BLACKSTONE_FURNACE_WORKSTATION = EmiStack.of(NetherBlocks.BLACKSTONE_FURNACE);
    public static final EmiIngredient NETHERRACK_FURNACE_WORKSTATION = EmiStack.of(NetherBlocks.NETHERRACK_FURNACE);
    public static final EmiIngredient BASALT_FURNACE_WORKSTATION = EmiStack.of(NetherBlocks.BASALT_FURNACE);
    public static final EmiIngredient CINCINNASITE_FORGE_WORKSTATION = EmiStack.of(NetherBlocks.CINCINNASITE_FORGE);

    public static final EmiRecipeCategory FORGE = new EmiRecipeCategory(
            BetterNether.makeID("forge"),
            CINCINNASITE_FORGE_WORKSTATION,
            org.betterx.bclib.integration.emi.EMIPlugin.getSprite(16, 16)
    );

    @Override
    public void register(EmiRegistry emiRegistry) {
        final RecipeManager manager = emiRegistry.getRecipeManager();

        emiRegistry.addCategory(FORGE);
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, BLACKSTONE_FURNACE_WORKSTATION);
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, NETHERRACK_FURNACE_WORKSTATION);
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, BASALT_FURNACE_WORKSTATION);
        emiRegistry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, CINCINNASITE_FORGE_WORKSTATION);

        EMIForgeRecipe.addAllRecipes(emiRegistry, manager);
    }

}
