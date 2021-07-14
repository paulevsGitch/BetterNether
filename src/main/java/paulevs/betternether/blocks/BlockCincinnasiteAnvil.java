package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockCincinnasiteAnvil extends AnvilBlock {
	public BlockCincinnasiteAnvil() {
		super(FabricBlockSettings.copy(BlocksRegistry.CINCINNASITE_BLOCK).noOcclusion());
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.getParameter(LootContextParams.TOOL);
		if (tool != null && tool.isCorrectToolForDrops(state)) {
			return Lists.newArrayList(new ItemStack(this));
		}
		else {
			return Lists.newArrayList();
		}
	}
}
