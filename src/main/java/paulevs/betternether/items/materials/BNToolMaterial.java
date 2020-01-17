package paulevs.betternether.items.materials;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class BNToolMaterial implements ToolMaterial
{
	private final int durability;
	private final float speed;
	private final int level;
	private final int enchantibility;
	private final ItemConvertible reapair;
	
	public BNToolMaterial(int durability, float speed, int level, int enchantibility, ItemConvertible reapair)
	{
		this.durability = durability;
		this.speed = speed;
		this.level = level;
		this.enchantibility = enchantibility;
		this.reapair = reapair;
	}

	@Override
	public int getDurability()
	{
		return durability;
	}

	@Override
	public float getMiningSpeed()
	{
		return speed;
	}

	@Override
	public float getAttackDamage()
	{
		return 5;
	}

	@Override
	public int getMiningLevel()
	{
		return level;
	}

	@Override
	public int getEnchantability()
	{
		return enchantibility;
	}

	@Override
	public Ingredient getRepairIngredient()
	{
		return Ingredient.ofItems(reapair);
	}
}
