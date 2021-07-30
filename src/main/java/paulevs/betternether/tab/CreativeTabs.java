package paulevs.betternether.tab;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import paulevs.betternether.BetterNether;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.NetherItems;

import java.util.stream.Collectors;

public class CreativeTabs {
	public static final CreativeModeTab BN_TAB;
	public static final CreativeModeTab TAB_ITEMS;

	static {
		BN_TAB = FabricItemGroupBuilder.create(BetterNether.makeID("nether_blocks")).icon(() -> new ItemStack(NetherBlocks.NETHER_GRASS))
				.appendItems(stacks -> stacks.addAll(NetherBlocks.getModBlocks().stream().map(ItemStack::new).collect(Collectors.toList())))
				.build();
		TAB_ITEMS = FabricItemGroupBuilder.create(BetterNether.makeID("nether_items")).icon(() -> new ItemStack(NetherItems.BLACK_APPLE))
				.appendItems(stacks -> stacks.addAll(NetherItems.getModItems().stream().map(ItemStack::new).collect(Collectors.toList())))
				.build();
	}
}
