package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.MushroomBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.sound.BlockSoundGroup;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

public class BlockGiantLucis extends MushroomBlock {
	public BlockGiantLucis() {
		super(FabricBlockSettings.of(Material.SOLID_ORGANIC)
				.materialColor(MaterialColor.YELLOW)
				.breakByTool(FabricToolTags.AXES)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(1F)
				.lightLevel(15)
				.nonOpaque());
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		ItemStack tool = builder.get(LootContextParameters.TOOL);
		if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0) return Lists.newArrayList(new ItemStack(this.asItem()));
		return Lists.newArrayList(new ItemStack(BlocksRegistry.LUCIS_SPORE, MHelper.randRange(0, 1, MHelper.RANDOM)),
				new ItemStack(ItemsRegistry.GLOWSTONE_PILE, MHelper.randRange(0, 2, MHelper.RANDOM)));
	}
}
