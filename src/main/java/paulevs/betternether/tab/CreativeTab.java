package paulevs.betternether.tab;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registers.BlocksRegister;

public class CreativeTab
{
	public static final ItemGroup BN_TAB = FabricItemGroupBuilder.create(
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
						boolean b1 = stack1.getItem() instanceof BlockItem;
						boolean b2 = stack2.getItem() instanceof BlockItem;
						if (b1 ^ b2)
							return b2 ? 1 : -1;
						
						String n1 = stack1.getItem().toString();
						String n2 = stack2.getItem().toString();
						
						return n1.compareTo(n2);
						
						//int compare = n1.compareTo(n2);
						
						/*if (b1)
						{
							b1 = ((BlockItem) stack1.getItem()).getBlock().getDefaultState().isOpaque();
							b2 = ((BlockItem) stack2.getItem()).getBlock().getDefaultState().isOpaque();
							if (b1 ^ b2)
								return b2 ? 1 : -1;
						}
						
						return compare;*///n1.compareTo(n2);
					}
				});
			})
			.build();
	
	//public static final ItemGroup BN_TAB = FabricItemGroupBuilder.create(new Identifier(BetterNether.MOD_ID, "items")).icon(() -> new ItemStack(BlocksRegister.NETHER_GRASS)).build();
}
