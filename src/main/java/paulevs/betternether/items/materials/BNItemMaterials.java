package paulevs.betternether.items.materials;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import paulevs.betternether.registry.ItemsRegistry;

public class BNItemMaterials
{
	public static final ArmorMaterial CINCINNASITE_ARMOR = new BNArmorMaterial("cincinnasite", 46, 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ItemsRegistry.CINCINNASITE_INGOT, 1F, 6);
	
	public static final ToolMaterial CINCINNASITE_TOOLS = new BNToolMaterial(512, 6F, 2, 14, ItemsRegistry.CINCINNASITE_INGOT);
	public static final ToolMaterial CINCINNASITE_DIAMOND_TOOLS = new BNToolMaterial(2048, 12F, 3, 22, Items.DIAMOND);
}
