package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.StringIdentifiable;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockProperties {
	public static final EnumProperty<CincinnasitPillarShape> PILLAR_SHAPE = EnumProperty.of("shape", CincinnasitPillarShape.class);
	public static final EnumProperty<BrownMushroomShape> BROWN_MUSHROOM_SHAPE = EnumProperty.of("shape", BrownMushroomShape.class);
	public static final EnumProperty<WillowBranchShape> WILLOW_SHAPE = EnumProperty.of("shape", WillowBranchShape.class);
	public static final EnumProperty<JellyShape> JELLY_MUSHROOM_VISUAL = EnumProperty.of("visual", JellyShape.class);
	public static final EnumProperty<EnumLucisShape> LUCIS_SHAPE = EnumProperty.of("shape", EnumLucisShape.class);
	public static final EnumProperty<PottedPlantShape> PLANT = EnumProperty.of("plant", PottedPlantShape.class);
	public static final EnumProperty<TripleShape> TRIPLE_SHAPE = EnumProperty.of("shape", TripleShape.class);
	public static final EnumProperty<FoodShape> FOOD = EnumProperty.of("food", FoodShape.class);
	
	public static final BooleanProperty DESTRUCTED = BooleanProperty.of("destructed");
	public static final BooleanProperty BOTTOM = BooleanProperty.of("bottom");
	public static final BooleanProperty FLOOR = BooleanProperty.of("floor");
	public static final BooleanProperty OPEN = BooleanProperty.of("open");
	public static final BooleanProperty FIRE = BooleanProperty.of("fire");
	
	public static final IntProperty AGE_THREE = IntProperty.of("age", 0, 2);
	public static final IntProperty AGE_FOUR = IntProperty.of("age", 0, 3);
	
	public static enum CincinnasitPillarShape implements StringIdentifiable {
		SMALL("small"),
		TOP("top"),
		MIDDLE("middle"),
		BOTTOM("bottom");

		final String name;

		CincinnasitPillarShape(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum BrownMushroomShape implements StringIdentifiable {
		TOP("top"),
		SIDE_N("side_n"),
		SIDE_S("side_s"),
		SIDE_E("side_e"),
		SIDE_W("side_w"),
		CORNER_N("corner_n"),
		CORNER_S("corner_s"),
		CORNER_E("corner_e"),
		CORNER_W("corner_w"),
		MIDDLE("middle"),
		BOTTOM("bottom");

		final String name;

		BrownMushroomShape(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum EnumLucisShape implements StringIdentifiable {
		CORNER("corner"),
		SIDE("side"),
		CENTER("center");

		final String name;

		EnumLucisShape(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum JellyShape implements StringIdentifiable {
		NORMAL("normal"), SEPIA("sepia"), POOR("poor");

		final String name;

		JellyShape(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum TripleShape implements StringIdentifiable {
		TOP("top"), MIDDLE("middle"), BOTTOM("bottom");

		final String name;

		TripleShape(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum FoodShape implements StringIdentifiable {
		NONE("none"), WART("wart"), MUSHROOM("mushroom"), APPLE("apple");

		private final String name;
		private Item item;

		FoodShape(String name) {
			this.name = name;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

		public Item getItem() {
			return item;
		}
	}
	
	public static enum PottedPlantShape implements StringIdentifiable {
		AGAVE("agave", BlocksRegistry.AGAVE),
		BARREL_CACTUS("barrel_cactus", BlocksRegistry.BARREL_CACTUS),
		BLACK_APPLE("black_apple", BlocksRegistry.BLACK_APPLE_SEED),
		BLACK_BUSH("black_bush", BlocksRegistry.BLACK_BUSH),
		EGG_PLANT("egg_plant", BlocksRegistry.EGG_PLANT),
		INK_BUSH("ink_bush", BlocksRegistry.INK_BUSH_SEED),
		REEDS("reeds", BlocksRegistry.NETHER_REED),
		NETHER_CACTUS("nether_cactus", BlocksRegistry.NETHER_CACTUS),
		NETHER_GRASS("nether_grass", BlocksRegistry.NETHER_GRASS),
		ORANGE_MUSHROOM("orange_mushroom", BlocksRegistry.ORANGE_MUSHROOM),
		RED_MOLD("red_mold", BlocksRegistry.RED_MOLD),
		GRAY_MOLD("gray_mold", BlocksRegistry.GRAY_MOLD),
		MAGMA_FLOWER("magma_flower", BlocksRegistry.MAGMA_FLOWER),
		NETHER_WART("nether_wart", BlocksRegistry.WART_SEED),
		WILLOW("willow", BlocksRegistry.WILLOW_SAPLING),
		SMOKER("smoker", BlocksRegistry.SMOKER),
		WART("wart", Blocks.NETHER_WART),
		JUNGLE_PLANT("jungle_plant", BlocksRegistry.JUNGLE_PLANT),
		JELLYFISH_MUSHROOM("jellyfish_mushroom", BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING),
		SWAMP_GRASS("swamp_grass", BlocksRegistry.SWAMP_GRASS),
		SOUL_GRASS("soul_grass", BlocksRegistry.SOUL_GRASS),
		BONE_GRASS("bone_grass", BlocksRegistry.BONE_GRASS),
		BONE_MUSHROOM("bone_mushroom", BlocksRegistry.BONE_MUSHROOM);

		private final Block block;
		private final String name;

		private PottedPlantShape(String name, Block block) {
			this.name = name;
			this.block = block;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

		public Item getItem() {
			return block.asItem();
		}

		public Block getBlock() {
			return block;
		}
	}
	
	public static enum WillowBranchShape implements StringIdentifiable {
		END("end"),
		MIDDLE("middle");

		final String name;

		WillowBranchShape(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
