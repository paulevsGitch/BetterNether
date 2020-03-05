package paulevs.betternether.mixin;

import java.util.HashMap;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.minecraft.container.ContainerListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import paulevs.betternether.IDimensionable;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerMixin extends PlayerEntity implements ContainerListener, IDimensionable
{
	private static final HashMap<UUID, DimensionType> SPAWN_DIM = new HashMap<UUID, DimensionType>();
	private static final HashMap<UUID, Boolean> USED_STATUE = new HashMap<UUID, Boolean>();
	
	public PlayerMixin(MinecraftServer server, ServerWorld world, GameProfile profile, ServerPlayerInteractionManager interactionManager)
	{
	      super(world, profile);
	}

	@Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
	private void readTag(CompoundTag tag, CallbackInfo info)
	{
		if (tag.contains("spawnDimension"))
		{
			int dimID = tag.getInt("spawnDimension");
			DimensionType spawnDimension = DimensionType.byRawId(dimID);
			SPAWN_DIM.put(getUuid(), spawnDimension);
		}
		if (tag.contains("statue"))
		{
			USED_STATUE.put(getUuid(), tag.getBoolean("statue"));
		}
	}

	@Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
	private void writeTag(CompoundTag tag, CallbackInfo info)
	{
		DimensionType spawnDimension = SPAWN_DIM.get(getUuid());
		if (spawnDimension != null)
			tag.putInt("spawnDimension", spawnDimension.getRawId());
	}
	
	public void setSpawnDimension(DimensionType type)
	{
		SPAWN_DIM.put(getUuid(), type);
	}
	
	public DimensionType getSpawnDimension()
	{
		return SPAWN_DIM.get(getUuid());
	}

	public void setUsedStatue(boolean used)
	{
		USED_STATUE.put(getUuid(), used);
	}

	public boolean usedStatue()
	{
		Boolean used = USED_STATUE.get(getUuid());
		return used == null ? false : used;
	}
}