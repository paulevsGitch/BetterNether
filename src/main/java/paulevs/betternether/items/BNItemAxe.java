package paulevs.betternether.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import paulevs.betternether.BetterNether;

public class BNItemAxe extends ItemAxe
{
	private float speed;
	
	public BNItemAxe(String name, ToolMaterial material, int maxDamage, float speed)
	{
		super(material);
		this.speed = speed;
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setMaxDamage(maxDamage);
	}
	
	public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return super.getDestroySpeed(stack, state) * speed;
    }
}
