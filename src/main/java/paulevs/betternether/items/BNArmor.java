package paulevs.betternether.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import paulevs.betternether.registry.ItemsRegistry;

public class BNArmor extends ArmorItem
{
	public BNArmor(ArmorMaterial material, EquipmentSlot slot)
	{
		super(material, slot, ItemsRegistry.defaultSettings());
	}
}
