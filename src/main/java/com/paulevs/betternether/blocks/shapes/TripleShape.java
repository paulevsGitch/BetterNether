package com.paulevs.betternether.blocks.shapes;

import net.minecraft.util.IStringSerializable;

public enum TripleShape implements IStringSerializable {
	TOP("top"), MIDDLE("middle"), BOTTOM("bottom");

	final String name;

	TripleShape(String name) {
		this.name = name;
	}

	@Override
	public String getString() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}