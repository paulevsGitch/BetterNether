package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import ru.bclib.api.tag.CommonBlockTags;
import ru.bclib.blocks.BaseOreBlock;
import ru.bclib.interfaces.CustomItemProvider;
import ru.bclib.interfaces.TagProvider;

import java.util.List;

public class BlockOre extends BaseOreBlock implements TagProvider, CustomItemProvider
{
	public final boolean fireproof;
	public BlockOre(Item drop, int minCount, int maxCount, int experience, int miningLevel, boolean fireproof) {
		super(
			FabricBlockSettings.of(Material.STONE, MaterialColor.COLOR_RED)
							   .hardness(3F)
							   .resistance(5F)
							   .requiresTool()
							   .sounds(SoundType.NETHERRACK),
			()->drop,
			minCount,
			maxCount,
			experience,
			miningLevel
		);
		this.fireproof = fireproof;
	}
	
	@Override
	public void addTags(List<TagKey<Block>> blockTags, List<TagKey<Item>> itemTags) {
		blockTags.add(CommonBlockTags.NETHERRACK);
	}

	@Override
	public BlockItem getCustomItem(ResourceLocation blockID, FabricItemSettings settings) {
		if (fireproof) settings = settings.fireproof();
		return new BlockItem(this, settings);
	}
}
