package paulevs.betternether.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Config;
import paulevs.betternether.registers.BlocksRegister;

public class BNWorldGenerator
{
	//public static IStructureWorld[] globalStructuresLand;
	//public static IStructureWorld[] globalStructuresLava;
	//public static IStructureWorld[] globalStructuresCave;
	
	public static boolean hasCleaningPass = Config.getBoolean("generator_world", "cleaning_pass", true);

	//private static float structueDensity = 1F / 64F;
	private static float oreDensity = Config.getFloat("generator_world", "cincinnasite_ore_density", 1F / 1024F);
	
	public static boolean enablePlayerDamage;
	public static boolean enableMobDamage;
	
	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	
	//private static CityStructureManager cityManager;
	//private static BlockPos pos;
	private static Mutable popPos = new Mutable();
	
	private static NetherBiome[][][] biomeArray = new NetherBiome[8][64][8];
	private static BiomeMap map;
	private static int cacheTimer = 0;

	public static void init(IWorld world)
	{
		long seed = world.getSeed();
		map = new BiomeMap(seed);
		
		/*if (ConfigLoader.hasCities())
		{
			cityManager = new CityStructureManager(seed);
			cityManager.load(world);
			cityManager.setDistance(ConfigLoader.getCityDistance());
		}*/
	}
	
	public static void clearCache()
	{
		if (cacheTimer > 512)
		{
			cacheTimer = 0;
			map.clearCache();
		}
	}
	
	/*public static void save(World world)
	{
		if (cityManager != null)
			cityManager.save(world);
	}*/
	
	private static void makeBiomeArray(int sx, int sz)
	{
		int wx, wy, wz;
		for (int x = 0; x < 8; x++)
		{
			wx = sx + (x << 1);
			for (int y = 0; y < 64; y++)
			{
				wy = (y << 1);
				for (int z = 0; z < 8; z++)
				{
					wz = sz + (z << 1);
					biomeArray[x][y][z] = map.getBiome(wx, wy, wz);
				}
			}
		}
		cacheTimer ++;
	}
	
	private static NetherBiome getBiomeLocal(int x, int y, int z, Random random)
	{
		x = (x + random.nextInt(4) - 2) >> 1;
		y = (y + random.nextInt(4) - 2) >> 1;
		z = (z + random.nextInt(4) - 2) >> 1;
		
		return biomeArray[clamp(x, 7)][clamp(y, 63)][clamp(z, 7)];
	}
	
	private static int clamp(int x, int max)
	{
		return x < 0 ? 0 : x > max ? max : x;
	}

	public static void generate(IWorld world, int cx, int cz, Random random)
	{
		NetherBiome biome;
		int sx = (cx << 4) | 8;
		int sz = (cz << 4) | 8;

		//Structure Generator
		/*if (coordinateRandom.nextFloat() < structueDensity)
		{
			pos = new BlockPos(sx + coordinateRandom.nextInt(8), 32 + coordinateRandom.nextInt(120 - 32), sz + coordinateRandom.nextInt(8));
			while (world.getBlockState(pos).getBlock() != Blocks.AIR && pos.getY() > 32)
			{
				pos = pos.down();
			}
			pos = downRay(world, pos);
			if (pos != null)
			{
				boolean terrain = true;
				for (int y = 1; y < 8; y++)
				{
					if (world.getBlockState(pos.up(y)).getBlock() != Blocks.AIR)
					{
						terrain = false;
						break;
					}
				}
				if (terrain)
				{
					if (globalStructuresLava.length > 0 && world.getBlockState(pos).getMaterial() == Material.LAVA)
						globalStructuresLava[coordinateRandom.nextInt(globalStructuresLava.length)].generateLava(world, pos.up(), coordinateRandom);
					else if (globalStructuresLand.length > 0)
						globalStructuresLand[coordinateRandom.nextInt(globalStructuresLand.length)].generateSurface(world, pos.up(), coordinateRandom);
				}
				else if (globalStructuresCave.length > 0)
				{
					globalStructuresCave[coordinateRandom.nextInt(globalStructuresCave.length)].generateSubterrain(world, pos, coordinateRandom);
				}
			}
		}*/

		makeBiomeArray(sx, sz);
		
		for (int x = 0; x < 16; x++)
		{
			int wx = sx + x;
			for (int z = 0; z < 16; z++)
			{
				int wz = sz + z;
				//biome = map.getBiome(wx, 10, wz);
				biome = getBiomeLocal(x, 10, z, random);
				popPos.set(wx, 128, wz);
				BlocksHelper.setWithoutUpdate(world, popPos, biome.testBlock.getDefaultState());
			}
		}

		// Total Populator
		for (int x = 0; x < 16; x++)
		{
			int wx = sx + x;
			for (int z = 0; z < 16; z++)
			{
				int wz = sz + z;
				for (int y = 5; y < 126; y++)
				{
					popPos.set(wx, y, wz);
					if (BlocksHelper.isNetherGround(world.getBlockState(popPos)))
					{
						biome = getBiomeLocal(x, y, z, random);
						
						if (world.isAir(popPos.up()))
							biome.genSurfColumn(world, popPos, random);

						if (((x + y + z) & 1) == 0)
						{
							// Ground Generation
							if (world.isAir(popPos.up()))
							{
								//biome.genSurfColumn(world, popPos, random);
								biome.genFloorObjects(world, popPos.up(), random);
							}

							// Ceiling Generation
							else if (world.isAir(popPos.down()))
							{
								biome.genCeilObjects(world, popPos.down(), random);
							}

							// Wall Generation
							else// if (((x + y + z) & 1) == 0)
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

									boolean bDown = world.isAir(objPos.down());
									boolean bUp = world.isAir(objPos.up());

									if (bDown && bUp)
									{
										biome.genWallObjects(world, objPos, random);
										//if (y < 37 && world.getBlockState(popPos).getBlock() instanceof BlockNetherBrick && random.nextInt(512) == 0)
										//	wartCapGen.generate(world, popPos, random);
									}
								}
							}
						}
						if (random.nextFloat() < oreDensity)
							spawnOre(BlocksRegister.BLOCK_CINCINNASITE_ORE.getDefaultState(), world, popPos, random);
					}
				}
			}
		}
	}
	
	public static void smoothChunk(IWorld world, int cx, int cz)
	{
		if (hasCleaningPass)
		{
			int wx = (cx << 4) | 8;
			int wz = (cz << 4) | 8;
			List<BlockPos> pos = new ArrayList<BlockPos>();
			BlockPos up;
			BlockPos down;
			BlockPos north;
			BlockPos south;
			BlockPos east;
			BlockPos west;
			for (int y = 32; y < 110; y++)
			{
				for (int x = 0; x < 16; x++)
					for (int z = 0; z < 16; z++)
					{
						popPos.set(x + wx, y, z + wz);
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
			for (BlockPos p : pos)
			{
				BlocksHelper.setWithoutUpdate(world, p, AIR);
			}
		}
		//if (cityManager != null)
		//	cityManager.generate(world, cx, cz);
	}
	
	private static boolean canReplace(IWorld world, BlockPos pos)
	{
		return BlocksHelper.isNetherGround(world.getBlockState(pos));
	}
	
	private static void spawnOre(BlockState state, IWorld world, BlockPos pos, Random random)
	{
		for (int i = 0; i < 6 + random.nextInt(11); i++)
		{
			BlockPos local = pos.add(random.nextInt(3), random.nextInt(3), random.nextInt(3));
			if (BlocksHelper.isNetherrack(world.getBlockState(local)))
			{
				BlocksHelper.setWithoutUpdate(world, local, state);
			}
		}
	}
	
	/*private static BlockPos downRay(World world, BlockPos start)
	{
		int dist = 0;
		Block b;
		BlockPos p;
		for (int j = start.getY(); j > 31; j--)
		{
			p = new BlockPos(start.getX(), j, start.getZ());
			b = world.getBlockState(p).getBlock();
			if (b != Blocks.AIR && (b instanceof BlockNetherrack || b instanceof BlockSoulSand || world.getBlockState(p).getMaterial() == Material.LAVA))
			{
				return new BlockPos(start.getX(), j, start.getZ());
			}
		}
		return null;
	}*/
	
	/*public static BlockPos getNearestCity(World world, int cx, int cz)
	{
		return cityManager.getNearestStructure(world, cx, cz);
	}*/
}
