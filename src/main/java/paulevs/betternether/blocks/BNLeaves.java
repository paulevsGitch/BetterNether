package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import ru.bclib.blocks.BaseLeavesBlock;
import ru.bclib.client.render.BCLRenderLayer;
import ru.bclib.interfaces.BlockModelProvider;
import ru.bclib.interfaces.RenderLayerProvider;
import ru.bclib.util.MHelper;

public class BNLeaves extends BaseLeavesBlock {
	public BNLeaves(Block sapling, MaterialColor color) {
		super(sapling, color);
	}
	
	public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
		serverLevel.setBlock(blockPos, updateDistance(blockState, serverLevel, blockPos), 3);
	}
	
	private static BlockState updateDistance(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
		int i = LeavesBlock.DECAY_DISTANCE;
		MutableBlockPos mutableBlockPos = new MutableBlockPos();
		
		for (int x=-1; x<=1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					if (x == 0 && y == 0 && z == 0) continue;
					final int dist = Math.abs(x) + Math.abs(y) + Math.abs(z);
					mutableBlockPos.setWithOffset(blockPos, x, y, z);
					i = Math.min(i, getDistanceAt(levelAccessor.getBlockState(mutableBlockPos)) + dist);
					if (i == 1) {
						break;
					}
				}
			}
		}
		
		return (BlockState)blockState.setValue(DISTANCE, i);
	}
	
	private static int getDistanceAt(BlockState blockState) {
		if (blockState.is(BlockTags.LOGS)) {
			return 0;
		} else {
			return blockState.getBlock() instanceof LeavesBlock ? (Integer)blockState.getValue(DISTANCE) : LeavesBlock.DECAY_DISTANCE;
		}
	}
}
