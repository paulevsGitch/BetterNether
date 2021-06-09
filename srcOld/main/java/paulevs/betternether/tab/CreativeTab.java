package paulevs.betternether.tab;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class CreativeTab {
	public static final ItemGroup BN_TAB = FabricItemGroupBuilder.create(
			new Identifier(BetterNether.MOD_ID, "items"))
			.icon(() -> new ItemStack(BlocksRegistry.NETHER_GRASS))
			.appendItems(stacks -> {
				for (Item i : ItemsRegistry.MOD_BLOCKS) {
					stacks.add(new ItemStack(i));
				}

				for (Item i : ItemsRegistry.MOD_ITEMS) {
					stacks.add(new ItemStack(i));
				}
			})
			.build();
}
