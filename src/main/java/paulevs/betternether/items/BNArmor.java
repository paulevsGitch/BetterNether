package paulevs.betternether.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import paulevs.betternether.registers.ItemsRegister;

public class BNArmor extends ArmorItem
{
	public BNArmor(ArmorMaterial material, EquipmentSlot slot)
	{
		super(material, slot, ItemsRegister.defaultSettings());
	}
}
