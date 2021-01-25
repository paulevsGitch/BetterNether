package com.paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;

import com.paulevs.betternether.BetterNether;
import com.paulevs.betternether.blockentities.BNBarrelTileEntity;
import com.paulevs.betternether.blockentities.BNChestTileEntity;
import com.paulevs.betternether.blockentities.BNSignTileEntity;
import com.paulevs.betternether.blocks.BNBarrel;
import com.paulevs.betternether.blocks.BNChest;
import com.paulevs.betternether.blocks.BNSign;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;



public class TileEntitiesRegistry {
	
	public static final DeferredRegister<TileEntityType<?>> DEFERRED = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BetterNether.MOD_ID);
	
	private static final List<Pair<String, TileEntityType<?>>> MODTILES = new ArrayList<>();
	
//	public static final TileEntityType<?> CINCINNASITE_FORGE = registerTileEntity("forge", TileEntityType.Builder.create(TileEntityForge::new, BlocksRegistry.CINCINNASITE_FORGE).build(null));
	//public static final TileEntityType<?> NETHERRACK_FURNACE = registerTileEntity("furnace",TileEntityType.Builder.create(TileEntityFurnace::new, getFurnaces()).build(null));
//public static final TileEntityType<?> CHEST_OF_DRAWERS = registerTileEntity("chest_of_drawers",TileEntityType.Builder.create(TileEntityChestOfDrawers::new, BlocksRegistry.CHEST_OF_DRAWERS).build(null));
 //	public static final TileEntityType<?> NETHER_BREWING_STAND = registerTileEntity("nether_brewing_stand",TileEntityType.Builder.create(BNBrewingStandTileEntity::new, BlocksRegistry.NETHER_BREWING_STAND).build(null));
	public static final TileEntityType<BNChestTileEntity> CHEST = registerTileEntity("chest",TileEntityType.Builder.create(BNChestTileEntity::new, getChests()).build(null));
	public static final TileEntityType<BNBarrelTileEntity> BARREL = registerTileEntity("barrel",TileEntityType.Builder.create(BNBarrelTileEntity::new, getBarrels()).build(null));
	public static final TileEntityType<BNSignTileEntity> SIGN = registerTileEntity("sign",TileEntityType.Builder.create(BNSignTileEntity::new, getSigns()).build(null));

	public static <T extends TileEntity> TileEntityType<T> registerTileEntity(String name, TileEntityType<T> type) {
		MODTILES.add(Pair.of(name, type));
		return type;
	}

	public static void registerAll(RegistryEvent.Register<TileEntityType<?>> e) {
		IForgeRegistry<TileEntityType<?>> r = e.getRegistry();
		
		for (Pair<String, TileEntityType<?>> tile : MODTILES) {
			r.register(tile.getRight().setRegistryName(new ResourceLocation(BetterNether.MOD_ID, tile.getLeft())));
		}
	}

	private static Block[] getChests() {
		List<Block> result = new ArrayList<Block>();
		RegistryHandler.getPossibleBlocks().forEach((block) -> {
			//Block block = ForgeRegistries.BLOCKS.getValue(source.getRegistryName());
			if (block instanceof BNChest)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}

	private static Block[] getBarrels() {
		List<Block> result = new ArrayList<Block>();
		RegistryHandler.getPossibleBlocks().forEach((block) -> {
			//Block block = ForgeRegistries.BLOCKS.getValue(source.getRegistryName());
			if (block instanceof BNBarrel)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}

	private static Block[] getSigns() {
		List<Block> result = new ArrayList<Block>();
		RegistryHandler.getPossibleBlocks().forEach((block) -> {
		//	Block block = ForgeRegistries.BLOCKS.getValue(source.getRegistryName());
			if (block instanceof BNSign)
				result.add(block);
		});
		return result.toArray(new Block[] {});
	}

	///private static Block[] getFurnaces() {
//		List<Block> result = new ArrayList<Block>();
	//	RegistryHandler.getPossibleBlocks().forEach((block) -> {
	//		//Block block = ForgeRegistries.BLOCKS.getValue(source.getRegistryName());
	//		if (block instanceof BlockNetherFurnace)
	//			result.add(block);
	//	});
	//	return result.toArray(new Block[] {});
//	}
}

