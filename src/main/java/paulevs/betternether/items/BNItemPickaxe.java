package paulevs.betternether.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import paulevs.betternether.registers.ItemsRegister;

public class BNItemPickaxe extends PickaxeItem
{
	protected float speed;

	public BNItemPickaxe(ToolMaterial material, int durability, float speed)
	{
		super(material, 1, -2.8F, ItemsRegister.defaultSettings());//.maxDamageIfAbsent(durability));
		this.speed = speed;
	}

	@Override
	public float getMiningSpeed(ItemStack stack, BlockState state)
	{
		return super.getMiningSpeed(stack, state) * speed;
	}
}
