package paulevs.betternether.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import paulevs.betternether.BetterNether;
import paulevs.betternether.mixin.common.LootTableBuilderAccessor;
import paulevs.betternether.registry.NetherBlocks;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class BNLoot {
	public static void register() {
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
			
			if (BuiltInLootTables.RUINED_PORTAL.equals(id) || BuiltInLootTables.NETHER_BRIDGE.equals(id)) {
				table.pool(FabricLootPoolBuilder.builder()
												.rolls(UniformGenerator.between(0, 4))
												.with(LootItem.lootTableItem(NetherBlocks.BLUE_OBSIDIAN.asItem())
															  .setWeight(1)
															  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
												.with(EmptyLootItem.emptyItem()
																   .setWeight(9)));
			}
			else if (BuiltInLootTables.BASTION_BRIDGE.equals(id) || BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(id) ||  BuiltInLootTables.BASTION_TREASURE.equals(id)) {
				table.pool(FabricLootPoolBuilder.builder()
												.rolls(UniformGenerator.between(1, 2))
												.with(LootItem.lootTableItem(NetherBlocks.BLUE_CRYING_OBSIDIAN.asItem())
															  .setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
												.with(LootItem.lootTableItem(NetherBlocks.BLUE_WEEPING_OBSIDIAN.asItem())
															  .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
												.with(LootItem.lootTableItem(NetherBlocks.WEEPING_OBSIDIAN.asItem())
															  .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
												.with(EmptyLootItem.emptyItem()
																   .setWeight(50)));
			}
			else if (BuiltInLootTables.BASTION_OTHER.equals(id)) {
				table.pool(FabricLootPoolBuilder.builder()
												.rolls(UniformGenerator.between(1, 2))
												.with(LootItem.lootTableItem(NetherBlocks.BLUE_OBSIDIAN.asItem())
															  .setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 6.0F))))
												.with(LootItem.lootTableItem(NetherBlocks.BLUE_CRYING_OBSIDIAN.asItem())
															  .setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
												.with(LootItem.lootTableItem(NetherBlocks.BLUE_WEEPING_OBSIDIAN.asItem())
															  .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
												.with(LootItem.lootTableItem(NetherBlocks.WEEPING_OBSIDIAN.asItem())
															  .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
												.with(EmptyLootItem.emptyItem()
																   .setWeight(50)));
			}
			else if (BuiltInLootTables.PIGLIN_BARTERING.equals(id)) {
				List<LootPool> pools = new ArrayList<>(0);
				try {
					for (Field f : table.getClass()
										.getDeclaredFields()) {
						if (LootTableLoadingCallback.class.isAssignableFrom(f.getType())) {
							f.setAccessible(true);
							LootTableBuilderAccessor hook = (LootTableBuilderAccessor) f.get(table);
							if (hook != null) {
								pools = hook.getPools();
							}
						}
					}
					
					if (pools != null && pools.size() > 0) {
						LootPool pool = pools.get(0);
						LootPool newPool = FabricLootPoolBuilder.builder()
																.copyFrom(pool, true)
																.with(LootItem.lootTableItem(NetherBlocks.BLUE_OBSIDIAN.asItem())
																			  .setWeight(40))
																.with(LootItem.lootTableItem(NetherBlocks.BLUE_CRYING_OBSIDIAN.asItem())
																			  .setWeight(40)
																			  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
																.with(LootItem.lootTableItem(NetherBlocks.BLUE_WEEPING_OBSIDIAN.asItem())
																			  .setWeight(20)
																			  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
																.with(LootItem.lootTableItem(NetherBlocks.WEEPING_OBSIDIAN.asItem())
																			  .setWeight(20)
																			  .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
																.build();
						pools.set(0, newPool);
						
					}
					//System.out.println(" + " + id);
				}
				catch (Throwable t) {
					BetterNether.LOGGER.error("ERROR building bartering table: " + t.getMessage());
				}
			}
			else {
				//System.out.println(" - " + id);
			}
		});
	}
}
