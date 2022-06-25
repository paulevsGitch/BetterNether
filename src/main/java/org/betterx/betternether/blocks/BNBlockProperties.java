package org.betterx.betternether.blocks;

import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.function.Supplier;

public class BNBlockProperties {
    public static final EnumProperty<CincinnasitPillarShape> PILLAR_SHAPE = EnumProperty.create(
            "shape",
            CincinnasitPillarShape.class
    );
    public static final EnumProperty<BrownMushroomShape> BROWN_MUSHROOM_SHAPE = EnumProperty.create(
            "shape",
            BrownMushroomShape.class
    );
    public static final EnumProperty<WillowBranchShape> WILLOW_SHAPE = EnumProperty.create(
            "shape",
            WillowBranchShape.class
    );
    public static final EnumProperty<JellyShape> JELLY_MUSHROOM_VISUAL = EnumProperty.create(
            "visual",
            JellyShape.class
    );
    public static final EnumProperty<EnumLucisShape> LUCIS_SHAPE = EnumProperty.create("shape", EnumLucisShape.class);
    public static final EnumProperty<PottedPlantShape> PLANT = EnumProperty.create("plant", PottedPlantShape.class);
    public static final EnumProperty<FoodShape> FOOD = EnumProperty.create("food", FoodShape.class);

    public static final BooleanProperty DESTRUCTED = BooleanProperty.create("destructed");
    public static final BooleanProperty FLOOR = BooleanProperty.create("floor");
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty FIRE = BooleanProperty.create("fire");

    public enum CincinnasitPillarShape implements StringRepresentable {
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

    public enum BrownMushroomShape implements StringRepresentable {
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

    public enum EnumLucisShape implements StringRepresentable {
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

    public enum JellyShape implements StringRepresentable {
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

    public enum FoodShape implements StringRepresentable {
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

    public enum PottedPlantShape implements StringRepresentable {
        AGAVE("agave", () -> NetherBlocks.AGAVE),
        BARREL_CACTUS("barrel_cactus", () -> NetherBlocks.BARREL_CACTUS),
        BLACK_APPLE("black_apple", () -> NetherBlocks.BLACK_APPLE_SEED),
        BLACK_BUSH("black_bush", () -> NetherBlocks.BLACK_BUSH),
        EGG_PLANT("egg_plant", () -> NetherBlocks.EGG_PLANT),
        INK_BUSH("ink_bush", () -> NetherBlocks.INK_BUSH_SEED),
        REEDS("reeds", () -> NetherBlocks.MAT_REED.getStem()),
        NETHER_CACTUS("nether_cactus", () -> NetherBlocks.NETHER_CACTUS),
        NETHER_GRASS("nether_grass", () -> NetherBlocks.NETHER_GRASS),
        ORANGE_MUSHROOM("orange_mushroom", () -> NetherBlocks.ORANGE_MUSHROOM),
        RED_MOLD("red_mold", () -> NetherBlocks.RED_MOLD),
        GRAY_MOLD("gray_mold", () -> NetherBlocks.GRAY_MOLD),
        MAGMA_FLOWER("magma_flower", () -> NetherBlocks.MAGMA_FLOWER),
        NETHER_WART("nether_wart", () -> NetherBlocks.MAT_WART.getSeed()),
        WILLOW("willow", () -> NetherBlocks.MAT_WILLOW.getSapling()),
        SMOKER("smoker", () -> NetherBlocks.SMOKER),
        WART("wart", () -> Blocks.NETHER_WART),
        JUNGLE_PLANT("jungle_plant", () -> NetherBlocks.JUNGLE_PLANT),
        JELLYFISH_MUSHROOM("jellyfish_mushroom", () -> NetherBlocks.JELLYFISH_MUSHROOM_SAPLING),
        SWAMP_GRASS("swamp_grass", () -> NetherBlocks.SWAMP_GRASS),
        SOUL_GRASS("soul_grass", () -> NetherBlocks.SOUL_GRASS),
        BONE_GRASS("bone_grass", () -> NetherBlocks.BONE_GRASS),
        BONE_MUSHROOM("bone_mushroom", () -> NetherBlocks.BONE_MUSHROOM);

        private final Supplier<Block> block;
        private final String name;

        PottedPlantShape(String name, Supplier<Block> block) {
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

    public enum WillowBranchShape implements StringRepresentable {
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
