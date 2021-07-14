package paulevs.betternether.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import paulevs.betternether.registry.ItemsRegistry;

public class BNArmor extends ArmorItem {
	public BNArmor(ArmorMaterial material, EquipmentSlot slot) {
		super(material, slot, ItemsRegistry.defaultSettings());
	}
}
