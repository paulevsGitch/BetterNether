package paulevs.betternether.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import paulevs.betternether.registry.ItemsRegistry;

public class BNItemShovel extends ShovelItem
{
	protected float speed;
	
	public BNItemShovel(ToolMaterial material, int durability, float speed)
	{
		super(material, 1, -2.8F, ItemsRegistry.defaultSettings());//.maxDamageIfAbsent(durability));
		this.speed = speed;
	}
	
	@Override
	public float getMiningSpeed(ItemStack stack, BlockState state)
	{
		return super.getMiningSpeed(stack, state) * speed;
	}
}
