package paulevs.betternether.items.materials;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import paulevs.betternether.registry.NetherItems;

public enum BNToolMaterial implements Tier {
	CINCINNASITE(512, 6F, 2, 14, 2.5F,NetherItems.CINCINNASITE_INGOT),
	CINCINNASITE_DIAMOND(1800, 8F, 3, 22, 3.5F, Items.DIAMOND),
	NETHER_RUBY(1300, 4F, 3, 9, 2.0F, NetherItems.NETHER_RUBY);
	
	private final int durability;
	private final float speed;
	private final int level;
	private final int enchantibility;
	private final float damage;
	private final ItemLike reapair;

	BNToolMaterial(int durability, float speed, int level, int enchantibility, float damage, ItemLike reapair) {
		this.durability = durability;
		this.speed = speed;
		this.level = level;
		this.enchantibility = enchantibility;
		this.damage = damage;
		this.reapair = reapair;
	}

	@Override
	public int getUses() {
		return durability;
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
