package paulevs.betternether.items.materials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class BNArmorMaterial implements ArmorMaterial
{
	private static final int[] DURABILITY = {13, 15, 16, 11};
	private final String name;
	private final int multiplier;
	private final int enchantLevel;
	private final SoundEvent sound;
	private final ItemConvertible repair;
	private final float toughness;
	private final int protection;
	
	public BNArmorMaterial(String name, int durabilityMultiplier, int enchantLevel, SoundEvent equipSound, ItemConvertible repairItem, float toughness, int protection)
	{
		this.name = name;
		this.multiplier = durabilityMultiplier;
		this.enchantLevel = enchantLevel;
		this.sound = equipSound;
		this.repair = repairItem;
		this.toughness = toughness;
		this.protection = protection;
	}
	
	@Override
	public int getDurability(EquipmentSlot slot)
	{
		return DURABILITY[slot.getEntitySlotId()] * multiplier;
	}

	@Override
	public int getProtectionAmount(EquipmentSlot slot)
	{
		return protection;
	}

	@Override
	public int getEnchantability()
	{
		return enchantLevel;
	}

	@Override
	public SoundEvent getEquipSound()
	{
		return sound;
	}

	@Override
	public Ingredient getRepairIngredient()
	{
		return Ingredient.ofItems(repair);
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public float getToughness()
	{
		return toughness;
	}

	@Override
	public float method_24355()
	{
		return 0;
	}
}
