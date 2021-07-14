package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;

public class BNBoneBlock extends BlockBase {
	public BNBoneBlock() {
		super(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK));
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this.asItem()));
	}
}
