package paulevs.betternether.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBlackApple extends ItemFoodStandart
{
	public ItemBlackApple()
	{
		super("black_apple", 6, 0.5F);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 40, 3));
    }
}
