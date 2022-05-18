package org.betterx.betternether.items.materials;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import org.betterx.betternether.registry.NetherItems;

public enum BNArmorMaterial implements ArmorMaterial {
    CINCINNASITE("cincinnasite",
                 12,
                 new int[]{3, 5, 6, 3},
                 20,
                 SoundEvents.ARMOR_EQUIP_GOLD,
                 1.0F,
                 0.05f,
                 NetherItems.CINCINNASITE_INGOT),
    NETHER_RUBY("nether_ruby",
                30,
                new int[]{3, 6, 8, 3},
                25,
                SoundEvents.ARMOR_EQUIP_NETHERITE,
                2.4F,
                0.2f,
                NetherItems.NETHER_RUBY);
	
	/* Vanilla Settings
	LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, () -> Ingredient.of(Items.LEATHER)),
    CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0f, 0.0f, () -> Ingredient.of(Items.IRON_INGOT)),
    IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f, () -> Ingredient.of(Items.IRON_INGOT)),
    GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ARMOR_EQUIP_GOLD, 0.0f, 0.0f, () -> Ingredient.of(Items.GOLD_INGOT)),
    DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0f, 0.0f, () -> Ingredient.of(Items.DIAMOND)),
    TURTLE("turtle", 25, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_TURTLE, 0.0f, 0.0f, () -> Ingredient.of(Items.SCUTE)),
    NETHERITE("netherite", 37, new int[]{3, 6, 8, 3}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0f, 0.1f, () -> Ingredient.of(Items.NETHERITE_INGOT));
	 */

    private static final int[] DURABILITY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int multiplier;
    private final int enchantLevel;
    private final SoundEvent sound;
    private final ItemLike repair;
    private final float toughness;
    private final float knockbackResistance;
    private final int[] protection;

    BNArmorMaterial(String name,
                    int durabilityMultiplier,
                    int[] protection,
                    int enchantLevel,
                    SoundEvent equipSound,
                    float toughness,
                    float knockback,
                    ItemLike repairItem) {
        this.name = name;
        this.multiplier = durabilityMultiplier;
        this.enchantLevel = enchantLevel;
        this.sound = equipSound;
        this.repair = repairItem;
        this.toughness = toughness;
        this.protection = protection;
        this.knockbackResistance = knockback;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return DURABILITY[slot.getIndex()] * multiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return protection[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantLevel;
    }

    @Override
    public SoundEvent getEquipSound() {
        return sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(repair);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
