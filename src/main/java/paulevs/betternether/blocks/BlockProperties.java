package paulevs.betternether.blocks;

import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockProperties {
	public static final EnumProperty<CincinnasitPillarShape> PILLAR_SHAPE = EnumProperty.create("shape", CincinnasitPillarShape.class);
	public static final EnumProperty<BrownMushroomShape> BROWN_MUSHROOM_SHAPE = EnumProperty.create("shape", BrownMushroomShape.class);
	public static final EnumProperty<WillowBranchShape> WILLOW_SHAPE = EnumProperty.create("shape", WillowBranchShape.class);
	public static final EnumProperty<JellyShape> JELLY_MUSHROOM_VISUAL = EnumProperty.create("visual", JellyShape.class);
	public static final EnumProperty<EnumLucisShape> LUCIS_SHAPE = EnumProperty.create("shape", EnumLucisShape.class);
	public static final EnumProperty<PottedPlantShape> PLANT = EnumProperty.create("plant", PottedPlantShape.class);
	public static final EnumProperty<TripleShape> TRIPLE_SHAPE = EnumProperty.create("shape", TripleShape.class);
	public static final EnumProperty<FoodShape> FOOD = EnumProperty.create("food", FoodShape.class);
	
	public static final BooleanProperty DESTRUCTED = BooleanProperty.create("destructed");
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	public static final BooleanProperty FLOOR = BooleanProperty.create("floor");
	public static final BooleanProperty OPEN = BooleanProperty.create("open");
	public static final BooleanProperty FIRE = BooleanProperty.create("fire");
	
	public static final IntegerProperty AGE_THREE = IntegerProperty.create("age", 0, 2);
	public static final IntegerProperty AGE_FOUR = IntegerProperty.create("age", 0, 3);
	
	public static enum CincinnasitPillarShape implements StringRepresentable {
		SMALL("small"),
		TOP("top"),
		MIDDLE("middle"),
		BOTTOM("bottom");

		final String name;

		CincinnasitPillarShape(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum BrownMushroomShape implements StringRepresentable {
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
		public String getSerializedName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum EnumLucisShape implements StringRepresentable {
		CORNER("corner"),
		SIDE("side"),
		CENTER("center");

		final String name;

		EnumLucisShape(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum JellyShape implements StringRepresentable {
		NORMAL("normal"), SEPIA("sepia"), POOR("poor");

		final String name;

		JellyShape(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum TripleShape implements StringRepresentable {
		TOP("top"), MIDDLE("middle"), BOTTOM("bottom");

		final String name;

		TripleShape(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum FoodShape implements StringRepresentable {
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
		public String getSerializedName() {
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
	
	public static enum PottedPlantShape implements StringRepresentable {
		AGAVE("agave", () -> BlocksRegistry.AGAVE),
		BARREL_CACTUS("barrel_cactus", () -> BlocksRegistry.BARREL_CACTUS),
		BLACK_APPLE("black_apple", () -> BlocksRegistry.BLACK_APPLE_SEED),
		BLACK_BUSH("black_bush", () -> BlocksRegistry.BLACK_BUSH),
		EGG_PLANT("egg_plant", () -> BlocksRegistry.EGG_PLANT),
		INK_BUSH("ink_bush", () -> BlocksRegistry.INK_BUSH_SEED),
		REEDS("reeds", () -> BlocksRegistry.NETHER_REED),
		NETHER_CACTUS("nether_cactus", () -> BlocksRegistry.NETHER_CACTUS),
		NETHER_GRASS("nether_grass", () -> BlocksRegistry.NETHER_GRASS),
		ORANGE_MUSHROOM("orange_mushroom", () -> BlocksRegistry.ORANGE_MUSHROOM),
		RED_MOLD("red_mold", () -> BlocksRegistry.RED_MOLD),
		GRAY_MOLD("gray_mold", () -> BlocksRegistry.GRAY_MOLD),
		MAGMA_FLOWER("magma_flower", () -> BlocksRegistry.MAGMA_FLOWER),
		NETHER_WART("nether_wart", () -> BlocksRegistry.WART_SEED),
		WILLOW("willow", () -> BlocksRegistry.WILLOW_SAPLING),
		SMOKER("smoker", () -> BlocksRegistry.SMOKER),
		WART("wart", () -> Blocks.NETHER_WART),
		JUNGLE_PLANT("jungle_plant", () -> BlocksRegistry.JUNGLE_PLANT),
		JELLYFISH_MUSHROOM("jellyfish_mushroom", () -> BlocksRegistry.JELLYFISH_MUSHROOM_SAPLING),
		SWAMP_GRASS("swamp_grass", () -> BlocksRegistry.SWAMP_GRASS),
		SOUL_GRASS("soul_grass", () -> BlocksRegistry.SOUL_GRASS),
		BONE_GRASS("bone_grass", () -> BlocksRegistry.BONE_GRASS),
		BONE_MUSHROOM("bone_mushroom", () -> BlocksRegistry.BONE_MUSHROOM);

		private final Supplier<Block> block;
		private final String name;

		private PottedPlantShape(String name, Supplier<Block> block) {
			this.name = name;
			this.block = block;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

		public Item getItem() {
			return block.get().asItem();
		}

		public Block getBlock() {
			return block.get();
		}
	}
	
	public static enum WillowBranchShape implements StringRepresentable {
		END("end"),
		MIDDLE("middle");

		final String name;

		WillowBranchShape(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
