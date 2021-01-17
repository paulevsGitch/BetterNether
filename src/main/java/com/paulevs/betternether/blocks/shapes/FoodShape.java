package com.paulevs.betternether.blocks.shapes;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;

public enum FoodShape implements IStringSerializable {
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
	public String getString() {
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