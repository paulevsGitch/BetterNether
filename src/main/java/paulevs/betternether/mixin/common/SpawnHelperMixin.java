package paulevs.betternether.mixin.common;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin
{
	@Shadow
	private static BlockPos getEntitySpawnPos(WorldView world, EntityType<?> entityType, int x, int z) {
		return null;
	}
	
	@Shadow
	public static boolean canSpawn(SpawnRestriction.Location location, WorldView world, BlockPos pos, @Nullable EntityType<?> entityType) { return true; }
	
	/*@Inject(method = "populateEntities", at = @At("HEAD"))
	private static void onPopulate(WorldAccess world, Biome biome, int chunkX, int chunkZ, Random random, CallbackInfo info)
	{
		
		List<Biome.SpawnEntry> list = biome.getEntitySpawnList(SpawnGroup.CREATURE);
	      if (!list.isEmpty()) {
	         int i = chunkX << 4;
	         int j = chunkZ << 4;

	         while(random.nextFloat() < biome.getMaxSpawnChance()) {
	            Biome.SpawnEntry spawnEntry = (Biome.SpawnEntry)WeightedPicker.getRandom(random, list);
	            System.out.println("Selected " + spawnEntry);
	            int k = spawnEntry.minGroupSize + random.nextInt(1 + spawnEntry.maxGroupSize - spawnEntry.minGroupSize);
	            System.out.println("Count " + k);
	            EntityData entityData = null;
	            int l = i + random.nextInt(16);
	            int m = j + random.nextInt(16);
	            int n = l;
	            int o = m;

	            for(int p = 0; p < k; ++p) {
	               boolean bl = false;

	               for(int q = 0; !bl && q < 4; ++q) {
	                  BlockPos blockPos = getEntitySpawnPos(world, spawnEntry.type, l, m);
	                  System.out.println("Summonable? " + spawnEntry.type.isSummonable());
	                  System.out.println("Can Spawn? " + canSpawn(SpawnRestriction.getLocation(spawnEntry.type), world, blockPos, spawnEntry.type));
	                  if (spawnEntry.type.isSummonable() && canSpawn(SpawnRestriction.getLocation(spawnEntry.type), world, blockPos, spawnEntry.type)) {
	                     float f = spawnEntry.type.getWidth();
	                     double d = MathHelper.clamp((double)l, (double)i + (double)f, (double)i + 16.0D - (double)f);
	                     double e = MathHelper.clamp((double)m, (double)j + (double)f, (double)j + 16.0D - (double)f);
	                     if (!world.doesNotCollide(spawnEntry.type.createSimpleBoundingBox(d, (double)blockPos.getY(), e)) || !SpawnRestriction.canSpawn(spawnEntry.type, world, SpawnReason.CHUNK_GENERATION, new BlockPos(d, (double)blockPos.getY(), e), world.getRandom())) {
	                        continue;
	                     }

	                     Entity entity2;
	                     try {
	                        entity2 = spawnEntry.type.create(world.getWorld());
	                     } catch (Exception var26) {
	                        //LOGGER.warn("Failed to create mob", var26);
	                        continue;
	                     }

	                     entity2.refreshPositionAndAngles(d, (double)blockPos.getY(), e, random.nextFloat() * 360.0F, 0.0F);
	                     if (entity2 instanceof MobEntity) {
	                        MobEntity mobEntity = (MobEntity)entity2;
	                        if (mobEntity.canSpawn(world, SpawnReason.CHUNK_GENERATION) && mobEntity.canSpawn(world)) {
	                           entityData = mobEntity.initialize(world, world.getLocalDifficulty(mobEntity.getBlockPos()), SpawnReason.CHUNK_GENERATION, entityData, (CompoundTag)null);
	                           world.spawnEntity(mobEntity);
	                           bl = true;
	                        }
	                     }
	                  }

	                  l += random.nextInt(5) - random.nextInt(5);

	                  for(m += random.nextInt(5) - random.nextInt(5); l < i || l >= i + 16 || m < j || m >= j + 16; m = o + random.nextInt(5) - random.nextInt(5)) {
	                     l = n + random.nextInt(5) - random.nextInt(5);
	                  }
	               }
	            }
	         }

	      }
	}*/
}
