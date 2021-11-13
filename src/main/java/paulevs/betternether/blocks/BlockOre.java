package paulevs.betternether.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import paulevs.betternether.MHelper;
import ru.bclib.blocks.BaseOreBlock;

public class BlockOre extends BaseOreBlock {
	
	public BlockOre(Item drop, int minCount, int maxCount, int experience) {
		super(
			drop,
			minCount,
			maxCount,
			experience,
			
			FabricBlockSettings.of(Material.STONE, MaterialColor.COLOR_RED)
				.hardness(3F)
				.resistance(5F)
				.requiresTool()
				.sounds(SoundType.NETHERRACK));
	}

}
