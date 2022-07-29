package org.betterx.betternether.integrations.emi;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blockentities.BlockEntityForge;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import dev.emi.emi.EmiPort;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;

import java.util.List;

public class EMIForgeRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final EmiIngredient input;
    private final EmiStack output;
    private final AbstractCookingRecipe recipe;
    private final int speedup;

    public EMIForgeRecipe(AbstractCookingRecipe recipe, int speedup) {
        this.id = new ResourceLocation(
                "emi",
                recipe.getId().getNamespace() + "/" + recipe.getId().getPath() + "/forge"
        );

        this.input = EmiIngredient.of(recipe.getIngredients().get(0));
        this.output = EmiStack.of(recipe.getResultItem());
        this.recipe = recipe;
        this.speedup = speedup;
    }


    static void addAllRecipes(EmiRegistry emiRegistry, RecipeManager manager) {
        org.betterx.bclib.integration.emi.EMIPlugin.addAllRecipes(
                emiRegistry, manager, BetterNether.LOGGER,
                RecipeType.SMELTING, (recipe) -> new EMIForgeRecipe(recipe, BlockEntityForge.SPEEDUP)
        );
    }

    public EmiRecipeCategory getCategory() {
        return EMIPlugin.FORGE;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public List<EmiIngredient> getInputs() {
        return List.of(this.input);
    }

    public List<EmiStack> getOutputs() {
        return List.of(this.output);
    }

    public int getDisplayWidth() {
        return 82;
    }

    public int getDisplayHeight() {
        return 38;
    }

    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(24, 5, 50 * this.recipe.getCookingTime())
               .tooltip((mx, my) -> List.of(
                               ClientTooltipComponent.create(
                                       EmiPort.ordered(
                                               EmiPort.translatable(
                                                       "emi.cooking.time",
                                                       (this.recipe.getCookingTime() / speedup) / 20.0F
                                               )
                                       )
                               )
                       )
               );

        widgets.addTexture(EmiTexture.EMPTY_FLAME, 1, 24);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 1, 24, 4000 / speedup, false, true, true);


        widgets.addText(
                EmiPort.ordered(EmiPort.translatable("emi.cooking.experience", this.recipe.getExperience())),
                26, 28, -1, true
        );
        widgets.addSlot(this.input, 0, 4);
        widgets.addSlot(this.output, 56, 0).output(true).recipeContext(this);
    }
}
