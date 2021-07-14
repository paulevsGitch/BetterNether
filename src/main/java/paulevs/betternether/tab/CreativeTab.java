package paulevs.betternether.tab;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class CreativeTab {
	public static final CreativeModeTab BN_TAB = FabricItemGroupBuilder.create(
			new ResourceLocation(BetterNether.MOD_ID, "items"))
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
