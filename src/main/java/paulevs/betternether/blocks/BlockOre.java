package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import paulevs.betternether.MHelper;

public class BlockOre extends OreBlock {
	private final Item dropItem;
	private final int minCount;
	private final int maxCount;

	public BlockOre(Item drop, int minCount, int maxCount) {
		super(FabricBlockSettings.of(Material.STONE)
				.requiresCorrectToolForDrops()
				.destroyTime(3F)
				.explosionResistance(5F)
				.requiresCorrectToolForDrops()
				.sound(SoundType.NETHERRACK));
		this.dropItem = drop;
		this.minCount = minCount;
		this.maxCount = maxCount;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.getParameter(LootContextParams.TOOL);
		if (tool.isCorrectToolForDrops(state)) {
			int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
			if (enchant > 0) {
				return Lists.newArrayList(new ItemStack(this));
			}
			enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
			int min = Mth.clamp(minCount + enchant, 0, maxCount);
			if (min == maxCount)
				return Lists.newArrayList(new ItemStack(dropItem, maxCount));
			int count = MHelper.randRange(min, maxCount, MHelper.RANDOM);
			return Lists.newArrayList(new ItemStack(dropItem, count));
		}
		return Lists.newArrayList();
	}
}
