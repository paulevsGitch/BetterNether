package paulevs.betternether.items.materials;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class BNArmorMaterial implements ArmorMaterial {
	private static final int[] DURABILITY = new int[] { 3, 6, 8, 3 };
	private final String name;
	private final int multiplier;
	private final int enchantLevel;
	private final SoundEvent sound;
	private final ItemLike repair;
	private final float toughness;
	private final int[] protection;

	public BNArmorMaterial(String name, int durabilityMultiplier, int enchantLevel, SoundEvent equipSound, ItemLike repairItem, float toughness, int[] protection) {
		this.name = name;
		this.multiplier = durabilityMultiplier;
		this.enchantLevel = enchantLevel;
		this.sound = equipSound;
		this.repair = repairItem;
		this.toughness = toughness;
		this.protection = protection;
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
		return 0;
	}
}
