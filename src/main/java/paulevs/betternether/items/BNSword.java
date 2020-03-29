package paulevs.betternether.items;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import paulevs.betternether.registry.ItemsRegistry;

public class BNSword extends SwordItem
{
	public BNSword(ToolMaterial material, int durability, int attackDamage, float attackSpeed)
	{
		super(material, attackDamage, attackSpeed, ItemsRegistry.defaultSettings().fireproof());
	}
}
