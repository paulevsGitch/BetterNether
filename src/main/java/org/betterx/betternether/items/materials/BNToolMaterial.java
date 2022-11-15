package org.betterx.betternether.items.materials;

import org.betterx.bclib.items.complex.EquipmentSet;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum BNToolMaterial implements Tier {
    CINCINNASITE(2, 512, 6.2F, 2.5F, 16, NetherItems.CINCINNASITE_INGOT,
            EquipmentSet.AttackDamage.IRON_LEVEL,
            EquipmentSet.SetValues.copy(EquipmentSet.AttackSpeed.IRON_LEVEL, 0.2f)
    ),
    CINCINNASITE_DIAMOND(3, 2061, 8.2F, 3.7F, 14, Items.DIAMOND,
            EquipmentSet.AttackDamage.DIAMOND_LEVEL,
            EquipmentSet.SetValues.copy(EquipmentSet.AttackSpeed.DIAMOND_LEVEL, 0.3f)
    ),

    NETHER_RUBY(4, 2561, 9.2F, 5.0F, 22, NetherItems.NETHER_RUBY,
            EquipmentSet.AttackDamage.NETHERITE_LEVEL,
            EquipmentSet.SetValues.copy(EquipmentSet.AttackSpeed.NETHERITE_LEVEL, 0.4f)
    );

    /* Vanilla Settings
    WOOD(0, 59, 2.0f, 0.0f, 15, () -> Ingredient.of(ItemTags.PLANKS)),
    STONE(1, 131, 4.0f, 1.0f, 5, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    IRON(2, 250, 6.0f, 2.0f, 14, () -> Ingredient.of(Items.IRON_INGOT)),
    DIAMOND(3, 1561, 8.0f, 3.0f, 10, () -> Ingredient.of(Items.DIAMOND)),
    GOLD(0, 32, 12.0f, 0.0f, 22, () -> Ingredient.of(Items.GOLD_INGOT)),
    NETHERITE(4, 2031, 9.0f, 4.0f, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));
     */
    private final int uses;
    private final float speed;
    private final int level;
    private final int enchantibility;
    private final float damage;
    private final ItemLike reapair;

    public final EquipmentSet.SetValues attackDamages;
    public final EquipmentSet.SetValues attackSpeeds;

    BNToolMaterial(
            int level,
            int uses,
            float speed,
            float damage,
            int enchantibility,
            ItemLike reapair,
            EquipmentSet.SetValues attackDamages,
            EquipmentSet.SetValues attackSpeeds
    ) {
        this.uses = uses;
        this.speed = speed;
        this.level = level;
        this.enchantibility = enchantibility;
        this.damage = damage;
        this.reapair = reapair;
        this.attackDamages = attackDamages;
        this.attackSpeeds = attackSpeeds;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantibility;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(reapair);
    }
}
