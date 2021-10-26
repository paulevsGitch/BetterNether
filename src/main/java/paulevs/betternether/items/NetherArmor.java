package paulevs.betternether.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.items.BaseArmorItem;

public class NetherArmor extends BaseArmorItem {
	public NetherArmor(ArmorMaterial material, EquipmentSlot slot) {
		super(material, slot, NetherItems.defaultSettings());
	}
}
