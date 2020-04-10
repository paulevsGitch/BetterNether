package paulevs.betternether.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import paulevs.betternether.registry.ItemsRegistry;

public class BNItemAxe extends AxeItem
{
	protected float speed;
	
	public BNItemAxe(ToolMaterial material, int durability, float speed)
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
