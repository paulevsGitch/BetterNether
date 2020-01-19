package paulevs.betternether.world.structures;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;

public class CityData
{
	public static void initialize()
	{
		List<Pair<StructurePoolElement,Integer>> elements = new ArrayList<Pair<StructurePoolElement,Integer>>();
		elements.add(new Pair<StructurePoolElement, Integer>(new SinglePoolElement("village/plains/town_centers/plains_meeting_point_2"), 50));
		StructurePoolBasedGenerator.REGISTRY.add(new StructurePool(new Identifier("village/plains/town_centers"), new Identifier("empty"), elements, StructurePool.Projection.RIGID));
	}
}
