package paulevs.betternether.tab;

import java.util.Comparator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import paulevs.betternether.blocks.BlocksRegister;

public class CreativeTab extends CreativeTabs
{

	public CreativeTab()
	{
		super("better_nether");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(BlocksRegister.BLOCK_NETHER_GRASS);
	}
	
	@Override
    public void displayAllRelevantItems(NonNullList<ItemStack> itemList)
    {
		for (Item item : Item.REGISTRY)
        {
            item.getSubItems(this, itemList);
        }
		Comparator<? super ItemStack> comparator = new ItemComparator();
		itemList.sort(comparator);
    }
	
	class ItemComparator implements Comparator
	{
		@Override
		public int compare(Object arg0, Object arg1)
		{
			return compareStack((ItemStack) arg0, (ItemStack) arg1);
		}
		
		private int compareStack(ItemStack stack1, ItemStack stack2)
		{
			/*String name1 = stack1.getUnlocalizedName();
			String name2 = stack2.getUnlocalizedName();
			if (name1.contains("seed") || name1.contains("spore") && !(name2.contains("seed") || name2.contains("spore")))
				return 1;
			else if (name1.contains("stair") || name1.contains("slab") || name1.contains("fence") || name1.contains("gate"))
				return -1;
			else
				return 0;*/
				//return stack1.getUnlocalizedName().length() - stack2.getUnlocalizedName().length();
			String name1 = stack1.getUnlocalizedName();
			String name2 = stack2.getUnlocalizedName();
			return name1.compareTo(name2);
		}
	}

}
