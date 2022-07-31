package org.betterx.betternether.items.complex;

import org.betterx.bclib.items.complex.EquipmentDescription;
import org.betterx.bclib.recipes.GridRecipe;

import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;

import java.util.function.Function;

public class DiamondDescriptor<I extends Item> extends EquipmentDescription<I> {
    Item base;

    public DiamondDescriptor(Item base, Function<Tier, I> creator) {
        super(creator);
        this.base = base;
    }

    @Override
    protected boolean buildRecipe(Item tool, ItemLike stick, GridRecipe builder) {
        builder.addMaterial('P', base);
        builder.addMaterial('#', Items.DIAMOND);
        if (tool instanceof PickaxeItem) {
            builder.setShape("#P#");
        } else if (tool instanceof AxeItem) {
            builder.setShape(" #", "#P");
        } else if (tool instanceof HoeItem) {
            builder.setShape(" #", "#P");
        } else if (tool instanceof ShovelItem) {
            builder.setShape("#", "P");
        } else if (tool instanceof SwordItem) {
            builder.setShape(" #", "#P");
        } else return true;

        return false;
    }
}
