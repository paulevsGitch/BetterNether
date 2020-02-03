package paulevs.betternether.tab;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registers.BlocksRegister;

public class CreativeTab
{
	/*public static final ItemGroup BN_TAB = FabricItemGroupBuilder.create(
			new Identifier(BetterNether.MOD_ID, "items"))
			.icon(() -> new ItemStack(BlocksRegister.NETHER_GRASS))
			.appendItems(stacks ->
			{
				Iterator<Item> iterator = Registry.ITEM.iterator();
				while (iterator.hasNext())
				{
					Item item = iterator.next();
					if (item.getGroup() != ItemGroup.SEARCH && Registry.ITEM.getId(item).getNamespace().equals(BetterNether.MOD_ID))
						stacks.add(new ItemStack(item));
				}
				Collections.sort(stacks, new Comparator<ItemStack>()
				{
					@Override
					public int compare(ItemStack stack1, ItemStack stack2)
					{
						return stack1.getItem().toString().compareTo(stack2.getItem().toString());
					}
				});
			})
			.build();*/
	
	public static final ItemGroup BN_TAB = FabricItemGroupBuilder.create(new Identifier(BetterNether.MOD_ID, "items")).icon(() -> new ItemStack(BlocksRegister.NETHER_GRASS)).build();
}
