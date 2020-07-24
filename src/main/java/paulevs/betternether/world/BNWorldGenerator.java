package paulevs.betternether.world;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.GenerationStep.Feature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import paulevs.betternether.BetterNether;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.blocks.BlockStalactite;
import paulevs.betternether.config.Config;
import paulevs.betternether.registry.BiomesRegistry;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.StructureCaves;
import paulevs.betternether.structures.StructurePath;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.world.structures.CityFeature;

public class BNWorldGenerator
{
	private static boolean hasCleaningPass;
	private static boolean hasFixPass;

	private static float cincinnasiteDensity;
	private static float rubyDensity;
	private static float lapisDensity;
	private static float structureDensity;
	private static float lavaStructureDensity;
	private static float globalDensity;
	
	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	
	private static Mutable popPos = new Mutable();
	
	private static final NetherBiome[][][] BIOMES = new NetherBiome[8][64][8];
	
	private static final List<BlockPos> LIST_FLOOR = new ArrayList<BlockPos>(4096);
	private static final List<BlockPos> LIST_WALL = new ArrayList<BlockPos>(4096);
	private static final List<BlockPos> LIST_CEIL = new ArrayList<BlockPos>(4096);
	private static final List<BlockPos> LIST_LAVA = new ArrayList<BlockPos>(1024);
	private static final HashSet<Biome> MC_BIOMES = new HashSet<Biome>();
	
	private static boolean hasCaves;
	private static boolean hasPaths;
	
	private static StructureCaves caves;
	private static StructurePath paths;
	private static NetherBiome biome;

	public static final StructureFeature<DefaultFeatureConfig> CITY = Registry.register(
			Registry.STRUCTURE_FEATURE,
			new Identifier(BetterNether.MOD_ID, "nether_city"),
			new CityFeature(DefaultFeatureConfig.CODEC)
			);
	
	@SuppressWarnings("unchecked")
	public static void onModInit()
	{
		hasCleaningPass = Config.getBoolean("generator.world.terrain", "terrain_cleaning_pass", true);
		hasFixPass = Config.getBoolean("generator.world.terrain", "world_fixing_pass", true);
		
		hasCaves = Config.getBoolean("generator.world.environment", "generate_caves", true);
		hasPaths = Config.getBoolean("generator.world.environment", "generate_paths", true);
		
		cincinnasiteDensity = Config.getFloat("generator.world.ores", "cincinnasite_ore_density", 1F / 1024F);
		rubyDensity = Config.getFloat("generator.world.ores", "ruby_ore_density", 1F / 4000F);
		lapisDensity = Config.getFloat("generator.world.ores", "lapis_ore_density", 1F / 4000F);
		structureDensity = Config.getFloat("generator.world", "structures_density", 1F / 32F) * 1.0001F;
		lavaStructureDensity = Config.getFloat("generator.world", "lava_structures_density", 1F / 128F) * 1.0001F;
		globalDensity = Config.getFloat("generator.world", "global_plant_and_structures_density", 1F) * 1.0001F;
		
		if (Config.getBoolean("generator.world.cities", "generate", true))
		{
			StructureFeature.STRUCTURES.put("nether_city", CITY);
			
			Map<StructureFeature<?>, GenerationStep.Feature> genStep = null;
			for (Field f: StructureFeature.class.getDeclaredFields())
			{
				if (f.getType().equals(Map.class))
				{
					f.setAccessible(true);
					try
					{
						genStep = (Map<StructureFeature<?>, Feature>) f.get(StructureFeature.class);
					}
					catch (IllegalArgumentException | IllegalAccessException e)
					{
						e.printStackTrace();
					}
					break;
				}
			}
			if (genStep != null)
				genStep.put(CITY, GenerationStep.Feature.RAW_GENERATION);
			else
				System.out.println("Cities faild adding to generation");
			
			ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> sFeature = CITY.configure(FeatureConfig.DEFAULT);
			
			for (Biome b: BiomesRegistry.getRegisteredBiomes())
			{
				b.addStructureFeature(sFeature);
			}
		}
	}

	public static void init(long seed)
	{
		//if (caves == null)
		//{
			caves = new StructureCaves(seed);
			paths = new StructurePath(seed + 1);
		//}
	}
	
	private static NetherBiome getBiomeLocal(int x, int y, int z, Random random)
	{
		int px = (int) Math.round(x + random.nextGaussian() * 0.5) >> 1;
		int py = (int) Math.round(y + random.nextGaussian() * 0.5) >> 1;
		int pz = (int) Math.round(z + random.nextGaussian() * 0.5) >> 1;
		return BIOMES[clamp(px, 7)][clamp(py, 63)][clamp(pz, 7)];
	}
	
	private static int clamp(int x, int max)
	{
		return x < 0 ? 0 : x > max ? max : x;
	}

	public static void populate(ChunkRegion world, int sx, int sz, Random random)
	{
		//Structure Generator
		if (random.nextFloat() < structureDensity)
		{
			popPos.set(sx + random.nextInt(16), MHelper.randRange(33, 100, random), sz + random.nextInt(16));
			StructureType type = StructureType.FLOOR;
			boolean isAir =  world.getBlockState(popPos).isAir();
			boolean airUp = world.getBlockState(popPos.up()).isAir() && world.getBlockState(popPos.up(3)).isAir();
			boolean airDown = world.getBlockState(popPos.down()).isAir() && world.getBlockState(popPos.down(3)).isAir();
			NetherBiome biome = getBiomeLocal(popPos.getX() - sx, popPos.getY(), popPos.getZ() - sz, random);
			if (!isAir && !airUp && !airDown)
				type = StructureType.UNDER;
			else
			{
				if (!biome.hasCeilStructures() || random.nextBoolean()) // Floor
				{
					while (world.getBlockState(popPos.down()).isAir() && popPos.getY() > 1)
					{
						popPos.setY(popPos.getY() - 1);
					}
				}
				else // Ceil
				{
					while (!BlocksHelper.isNetherGroundMagma(world.getBlockState(popPos.up())) && popPos.getY() < 127)
					{
						popPos.setY(popPos.getY() + 1);
					}
					type = StructureType.CEIL;
				}
			}
			biome = getBiomeLocal(popPos.getX() - sx, popPos.getY(), popPos.getZ() - sz, random);
			if (world.isAir(popPos))
			{
				if (type == StructureType.FLOOR)
				{
					BlockState down = world.getBlockState(popPos.down());
					if (BlocksHelper.isNetherGroundMagma(down))
						biome.genFloorBuildings(world, popPos, random);
				}
				else if (type == StructureType.CEIL)
				{
					BlockState up = world.getBlockState(popPos.up());
					if (BlocksHelper.isNetherGroundMagma(up))
					{
						biome.genCeilBuildings(world, popPos, random);
					}
				}
			}
			else
				biome.genUnderBuildings(world, popPos, random);
		}
		
		if (random.nextFloat() < lavaStructureDensity)
		{
			popPos.set(sx + random.nextInt(16), 32, sz + random.nextInt(16));
			if (world.isAir(popPos) && BlocksHelper.isLava(world.getBlockState(popPos.down())))
			{
				biome = getBiomeLocal(popPos.getX() - sx, popPos.getY(), popPos.getZ() - sz, random);
				biome.genLavaBuildings(world, popPos, random);
			}
		}
		
		LIST_LAVA.clear();
		LIST_FLOOR.clear();
		LIST_WALL.clear();
		LIST_CEIL.clear();

		int ex = sx + 16;
		int ez = sz + 16;

		for (int x = 0; x < 16; x++)
		{
			int wx = sx + x;
			for (int z = 0; z < 16; z++)
			{
				int wz = sz + z;
				for (int y = 1; y < 126; y++)
				{
					if (caves.isInCave(x, y, z))
						continue;

					biome = getBiomeLocal(x, y, z, random);

					popPos.set(wx, y, wz);
					BlockState state = world.getBlockState(popPos);
					boolean lava = BlocksHelper.isLava(state);
					if (lava || BlocksHelper.isNetherGroundMagma(state) || state.getBlock() == Blocks.GRAVEL)
					{
						if (!lava && ((state = world.getBlockState(popPos.up())).isAir() || !state.getMaterial().blocksLight() || !state.getMaterial().blocksMovement()) && state.getFluidState().isEmpty())// world.isAir(popPos.up()))
							biome.genSurfColumn(world, popPos, random);

						if (((x + y + z) & 1) == 0 && random.nextFloat() < globalDensity && random.nextFloat() < biome.getPlantDensity())
						{
							// Ground Generation
							if (world.isAir(popPos.up()))
							{
								if (lava)
									LIST_LAVA.add(popPos.up());
								else
									LIST_FLOOR.add(new BlockPos(popPos.up()));
							}

							// Ceiling Generation
							else if (world.isAir(popPos.down()))
							{
								LIST_CEIL.add(new BlockPos(popPos.down()));
							}

							// Wall Generation
							else
							{
								boolean bNorth = world.isAir(popPos.north());
								boolean bSouth = world.isAir(popPos.south());
								boolean bEast = world.isAir(popPos.east());
								boolean bWest = world.isAir(popPos.west());
								if (bNorth || bSouth || bEast || bWest)
								{
									BlockPos objPos = null;
									if (bNorth)
										objPos = popPos.north();
									else if (bSouth)
										objPos = popPos.south();
									else if (bEast)
										objPos = popPos.east();
									else
										objPos = popPos.west();

									if ((popPos.getX() >= sx) && (popPos.getX() < ex) && (popPos.getZ() >= sz) && (popPos.getZ() < ez))
									{
										boolean bDown = world.isAir(objPos.down());
										boolean bUp = world.isAir(objPos.up());

										if (bDown && bUp)
										{
											LIST_WALL.add(new BlockPos(objPos));
										}
									}
								}
							}
						}
						if (random.nextFloat() < cincinnasiteDensity)
							spawnOre(BlocksRegistry.CINCINNASITE_ORE.getDefaultState(), world, popPos, random, 6, 14);
						if (random.nextFloat() < rubyDensity)
							spawnOre(BlocksRegistry.NETHER_RUBY_ORE.getDefaultState(), world, popPos, random, 1, 5);
						if (random.nextFloat() < lapisDensity)
							spawnOre(BlocksRegistry.NETHER_LAPIS_ORE.getDefaultState(), world, popPos, random, 1, 6);
					}
				}
			}
		}
		
		for (BlockPos pos: LIST_LAVA)
		{
			if (world.isAir(pos))
			{
				biome = getBiomeLocal(pos.getX() - sx, pos.getY(), pos.getZ() - sz, random);
				if (biome != null)
					biome.genLavaObjects(world, pos, random);
			}
		}
		
		for (BlockPos pos: LIST_FLOOR)
			if (world.isAir(pos))
			{
				biome = getBiomeLocal(pos.getX() - sx, pos.getY(), pos.getZ() - sz, random);
				if (biome != null)
					biome.genFloorObjects(world, pos, random);
			}
		
		for (BlockPos pos: LIST_WALL)
			if (world.isAir(pos))
			{
				biome = getBiomeLocal(pos.getX() - sx, pos.getY(), pos.getZ() - sz, random);
				if (biome != null)
					biome.genWallObjects(world, pos, random);
			}
		
		for (BlockPos pos: LIST_CEIL)
			if (world.isAir(pos))
			{
				biome = getBiomeLocal(pos.getX() - sx, pos.getY(), pos.getZ() - sz, random);
				if (biome != null)
					biome.genCeilObjects(world, pos, random);
			}
	}
	
	private static void makeLocalBiomes(ChunkRegion world, int sx, int sz)
	{
		MC_BIOMES.clear();
		for (int x = 0; x < 8; x++)
		{
			popPos.setX(sx + (x << 1) + 2);
			for (int y = 0; y < 64; y++)
			{
				popPos.setY((y << 1) + 2);
				for (int z = 0; z < 8; z++)
				{
					popPos.setZ(sz + (z << 1) + 2);
					Biome b = world.getBiome(popPos);
					BIOMES[x][y][z] = BiomesRegistry.getFromBiome(b);
					MC_BIOMES.add(b);
				}
			}
		}
	}

	public static void prePopulate(ChunkRegion world, int sx, int sz, Random random)
	{
		makeLocalBiomes(world, sx, sz);
		
		if (hasCaves)
		{
			popPos.set(sx, 0, sz);
			caves.generate(world, popPos, random);
		}
		
		if (hasCleaningPass)
		{
			List<BlockPos> pos = new ArrayList<BlockPos>();
			BlockPos up;
			BlockPos down;
			BlockPos north;
			BlockPos south;
			BlockPos east;
			BlockPos west;
			for (int y = 32; y < 110; y++)
			{
				popPos.setY(y);
				for (int x = 0; x < 16; x++)
				{
					popPos.setX(x | sx);
					for (int z = 0; z < 16; z++)
					{
						popPos.setZ(z | sz);
						if (canReplace(world, popPos))
						{
							up = popPos.up();
							down = popPos.down();
							north = popPos.north();
							south = popPos.south();
							east = popPos.east();
							west = popPos.west();
							if (world.isAir(north) && world.isAir(south))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(east) && world.isAir(west))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(up) && world.isAir(down))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(popPos.north().east().down()) && world.isAir(popPos.south().west().up()))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(popPos.south().east().down()) && world.isAir(popPos.north().west().up()))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(popPos.north().west().down()) && world.isAir(popPos.south().east().up()))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(popPos.south().west().down()) && world.isAir(popPos.north().east().up()))
								pos.add(new BlockPos(popPos));
						}
					}
				}
			}
			for (BlockPos p : pos)
			{
				BlocksHelper.setWithoutUpdate(world, p, AIR);
				up = p.up();
				BlockState state = world.getBlockState(up);
				if (!state.getBlock().canPlaceAt(state, world, up))
					BlocksHelper.setWithoutUpdate(world, up, AIR);
			}
		}
		
		if (hasPaths)
		{
			popPos.set(sx, 0, sz);
			paths.generate(world, popPos, random);
		}
	}
	
	private static boolean canReplace(WorldAccess world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		return BlocksHelper.isNetherGround(state) || state.getBlock() == Blocks.GRAVEL;
	}
	
	private static void spawnOre(BlockState state, WorldAccess world, BlockPos pos, Random random, int minSize, int maxSize)
	{
		int size = MHelper.randRange(minSize, maxSize, random);
		for (int i = 0; i < size; i++)
		{
			BlockPos local = pos.add(random.nextInt(3), random.nextInt(3), random.nextInt(3));
			if (BlocksHelper.isNetherrack(world.getBlockState(local)))
			{
				BlocksHelper.setWithoutUpdate(world, local, state);
			}
		}
	}
	
	public static void cleaningPass(WorldAccess world, int sx, int sz)
	{
		if (hasFixPass)
		{
			fixBlocks(world, sx, 30, sz, sx + 15, 110, sz + 15);
		}
	}
	
	private static void fixBlocks(WorldAccess world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		//List<BlockPos> lavafalls = Lists.newArrayList();
		//List<BlockPos> update = Lists.newArrayList();
		
		for (int y = y1; y <= y2; y++)
		{
			popPos.setY(y);
			for (int x = x1; x <= x2; x++)
			{
				popPos.setX(x);
				for (int z = z1; z <= z2; z++)
				{
					popPos.setZ(z);
					
					BlockState state = world.getBlockState(popPos);
					
					/*if (y > 32 && BlocksHelper.isLava(state) && !BlocksHelper.isLava(world.getBlockState(popPos.down())))
					{
						
						if (world.isAir(popPos.down()))
						{
							Mutable p = new Mutable().set(popPos.down());
							while(likeAir(world, p))
							{
								lavafalls.add(p.toImmutable());
								p.move(Direction.DOWN);
							}
							update.add(p.up());
						}
						else
						{
							for(Direction dir: BlocksHelper.HORIZONTAL)
							{
								BlockPos start = popPos.offset(dir);
								if (likeAir(world, start))
								{
									Mutable p = new Mutable().set(start);
									while(likeAir(world, p))
									{
										lavafalls.add(p.toImmutable());
										p.move(Direction.DOWN);
									}
									update.add(p.up());
								}
							}
						}
						
						continue;
					}*/
					
					if (!state.canPlaceAt(world, popPos))
					{
						BlocksHelper.setWithoutUpdate(world, popPos, AIR);
						continue;
					}
					
					if (!state.isOpaque() && world.getBlockState(popPos.up()).getBlock() == Blocks.NETHER_BRICKS)
					{
						BlocksHelper.setWithoutUpdate(world, popPos, Blocks.NETHER_BRICKS.getDefaultState());
						continue;
					}
					
					if (BlocksHelper.isLava(state) && world.isAir(popPos.up()) && world.isAir(popPos.down()))
					{
						BlocksHelper.setWithoutUpdate(world, popPos, AIR);
						continue;
					}
					
					if (state.getBlock() == Blocks.NETHER_WART_BLOCK || state.getBlock() == Blocks.WARPED_WART_BLOCK)
					{
						if (world.isAir(popPos.down()) && world.isAir(popPos.up()) && world.isAir(popPos.north()) && world.isAir(popPos.south()) && world.isAir(popPos.east()) && world.isAir(popPos.west()))
							BlocksHelper.setWithoutUpdate(world, popPos, AIR);
						continue;
					}
					
					if (state.getBlock() instanceof BlockStalactite && !(state = world.getBlockState(popPos.down())).isFullCube(world, popPos.down()) && !(state.getBlock() instanceof BlockStalactite))
					{
						Mutable sp = new Mutable().set(popPos);
						while (world.getBlockState(sp).getBlock() instanceof BlockStalactite)
						{
							BlocksHelper.setWithoutUpdate(world, sp, AIR);
							sp.offset(Direction.UP);
						}
						continue;
					}
				}
			}
		}
		
		/*for (BlockPos pos: lavafalls)
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, 8));
		
		for (BlockPos pos: update)
			world.getChunk(pos).markBlockForPostProcessing(popPos.set(pos.getX() & 15, pos.getY(), pos.getZ() & 15));*/
	}
	
	public static HashSet<Biome> getPopulateBiomes()
	{
		return MC_BIOMES;
	}
	
	/*private static List<BlockPos> blockStream(WorldAccess world, BlockPos pos)
	{
		List<BlockPos> path = new ArrayList<BlockPos>();
		
		Mutable mutable = new Mutable().set(pos);
		Mutable center = new Mutable();
		path.add(mutable.toImmutable());
		
		int d = 0;
		
		for (int i = 0; i < 256; i++)
		{
			if (d >= 3 || Math.abs(pos.getX() - mutable.getX()) > 8 || Math.abs(pos.getZ() - mutable.getZ()) > 8)
			{
				for (int x = -3; x <= 3; x++)
				{
					int x2 = x * x;
					for (int z = -3; z <= 3; z++)
					{
						int z2 = z * z;
						for (int y = -3; y <= 0; y++)
						{
							int y2 = y * y;
							if (x2 + z2 + y2 < 9)
							{
								path.add(center.add(x, y, z));
							}
						}
					}
				}
				break;
			}
			
			if (mutable.getY() < 33)
				break;
			
			if (likeAir(world, mutable.down()))
			{
				mutable.move(Direction.DOWN);
				path.add(mutable.toImmutable());
				center.set(mutable);
				d = 0;
				continue;
			}
			for(Direction dir: BlocksHelper.HORIZONTAL)
			{
				if (likeAir(world, mutable.offset(dir)))
				{
					mutable.offset(dir);
					path.add(mutable.toImmutable());
					d ++;
					break;
				}
			}
		}
		
		return path;
	}
	
	private static boolean likeAir(WorldAccess world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		return state.isAir() || !state.isFullCube(world, pos);
	}*/
}
