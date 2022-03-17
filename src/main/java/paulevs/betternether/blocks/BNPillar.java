package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import paulevs.betternether.blocks.materials.Materials;

import java.util.Collections;
import java.util.List;

public class BNPillar extends RotatedPillarBlock {
	public BNPillar(Properties settings) {
		super(settings);
	}

	public BNPillar(Block block) {
		super(FabricBlockSettings.copyOf(block));
	}

	public BNPillar(MaterialColor color) {
		super(Materials.makeWood(color));
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}
}
