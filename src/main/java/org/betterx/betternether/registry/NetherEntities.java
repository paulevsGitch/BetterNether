package org.betterx.betternether.registry;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI;
import org.betterx.bclib.api.v2.spawning.SpawnRuleBuilder;
import org.betterx.bclib.entity.BCLEntityWrapper;
import org.betterx.bclib.interfaces.SpawnRule;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.entity.*;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.ui.ColorUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class NetherEntities {
    public enum KnownSpawnTypes {
        GHAST(50, 4, 4, EntityType.GHAST),
        ZOMBIFIED_PIGLIN(100, 4, 4, EntityType.ZOMBIFIED_PIGLIN),
        MAGMA_CUBE(2, 4, 4, EntityType.MAGMA_CUBE),
        SKULL(2, 2, 4, NetherEntities.SKULL),
        ENDERMAN(1, 4, 4, EntityType.ENDERMAN),
        PIGLIN(15, 4, 4, EntityType.PIGLIN),
        STRIDER(60, 1, 2, EntityType.STRIDER),
        HOGLIN(9, 1, 2, EntityType.HOGLIN),
        FIREFLY(5, 1, 3, NetherEntities.FIREFLY),
        HYDROGEN_JELLYFISH(5, 2, 6, NetherEntities.HYDROGEN_JELLYFISH),
        NAGA(8, 3, 5, NetherEntities.NAGA),
        FLYING_PIG(20, 2, 4, NetherEntities.FLYING_PIG),
        JUNGLE_SKELETON(40, 2, 4, NetherEntities.JUNGLE_SKELETON),
        PIGLIN_BRUTE(0, 1, 1, EntityType.PIGLIN_BRUTE);

        public final int weight;
        public final int minGroupSize;
        public final int maxGroupSize;
        public final EntityType type;
        public final BCLEntityWrapper wrapper;

        public boolean isVanilla() {
            return wrapper == null;
        }

        public void addSpawn(BCLBiomeBuilder builder, NetherBiomeConfig data) {
            final String category = data.configGroup() + ".spawn." + this.type.getCategory()
                                                                              .getName() + "." + this.type
                    .getDescriptionId()
                    .replace(
                            "entity.",
                            ""
                    );
            int weight = Configs.BIOMES.getInt(category, "weight", data.spawnWeight(this));
            int min = Configs.BIOMES.getInt(category, "minGroupSize", minGroupSize);
            int max = Configs.BIOMES.getInt(category, "maxGroupSize", maxGroupSize);

            if (wrapper == null) {
                builder.spawn(this.type, weight, min, max);
            } else {
                builder.spawn(this.wrapper, weight, min, max);
            }
        }

        public void addSpawn(ResourceLocation ID, Holder<Biome> biome, float multiplier) {
            final String category = ID.getNamespace() + "." + ID.getPath() + ".spawn." + this.type.getCategory()
                                                                                                  .getName() + "." + this.type
                    .getDescriptionId()
                    .replace(
                            "entity.",
                            ""
                    );
            int dweight = Configs.BIOMES.getInt(category, "weight", (int) (weight * multiplier));
            int min = Configs.BIOMES.getInt(category, "minGroupSize", minGroupSize);
            int max = Configs.BIOMES.getInt(category, "maxGroupSize", maxGroupSize);

            BiomeAPI.addBiomeMobSpawn(biome, this.type, dweight, min, max);
        }

        public void addSpawn(ResourceLocation ID, Holder<Biome> biome) {
            addSpawn(ID, biome, 1);
        }

        KnownSpawnTypes(int w, int min, int max, EntityType type) {
            weight = w;
            minGroupSize = min;
            maxGroupSize = max;
            this.type = type;
            this.wrapper = null;
        }

        <M extends Mob> KnownSpawnTypes(int w, int min, int max, BCLEntityWrapper type) {
            weight = w;
            minGroupSize = min;
            maxGroupSize = max;
            this.type = type.type();
            this.wrapper = type;
        }
    }

    public static final Map<EntityType<? extends Entity>, AttributeSupplier> ATTRIBUTES = Maps.newHashMap();
    private static final List<BCLEntityWrapper<?>> NETHER_ENTITIES = Lists.newArrayList();

    public static final EntityType<EntityChair> CHAIR = FabricEntityTypeBuilder.create(
                                                                                       MobCategory.MISC,
                                                                                       EntityChair::new
                                                                               )
                                                                               .dimensions(EntityDimensions.fixed(
                                                                                       0.5F,
                                                                                       0.8F
                                                                               ))
                                                                               .fireImmune()
                                                                               .disableSummon()
                                                                               .build();
    public static final EntityType<EntityNagaProjectile> NAGA_PROJECTILE = FabricEntityTypeBuilder
            .create(
                    MobCategory.MISC,
                    EntityNagaProjectile::new
            )
            .dimensions(
                    EntityDimensions.fixed(
                            1F,
                            1F
                    ))
            .disableSummon()
            .build();

    public static final BCLEntityWrapper<EntityFirefly> FIREFLY =
            register(
                    "firefly",
                    MobCategory.AMBIENT,
                    0.5f,
                    0.5f,
                    EntityFirefly::new,
                    EntityFirefly.createMobAttributes(),
                    true,
                    ColorUtil.color(255, 223, 168),
                    ColorUtil.color(233, 182, 95)
            );

    public static final BCLEntityWrapper<EntityHydrogenJellyfish> HYDROGEN_JELLYFISH =
            register(
                    "hydrogen_jellyfish",
                    MobCategory.AMBIENT,
                    2.0f,
                    5.0f,
                    EntityHydrogenJellyfish::new,
                    EntityHydrogenJellyfish.createMobAttributes(),
                    false,
                    ColorUtil.color(253, 164, 24),
                    ColorUtil.color(88, 21, 4)
            );

    public static final BCLEntityWrapper<EntityNaga> NAGA =
            register(
                    "naga",
                    MobCategory.MONSTER,
                    0.625f,
                    2.75f,
                    EntityNaga::new,
                    EntityNaga.createMobAttributes(),
                    true,
                    ColorUtil.color(12, 12, 12),
                    ColorUtil.color(210, 90, 26)
            );

    public static final BCLEntityWrapper<EntityFlyingPig> FLYING_PIG =
            register(
                    "flying_pig",
                    MobCategory.AMBIENT,
                    1.0f,
                    1.25f,
                    EntityFlyingPig::new,
                    EntityFlyingPig.createMobAttributes(),
                    true,
                    ColorUtil.color(241, 140, 93),
                    ColorUtil.color(176, 58, 47)
            );

    public static final BCLEntityWrapper<EntityJungleSkeleton> JUNGLE_SKELETON =
            register(
                    "jungle_skeleton",
                    MobCategory.MONSTER,
                    0.6F,
                    1.99F,
                    EntityJungleSkeleton::new,
                    EntityJungleSkeleton.createMonsterAttributes(),
                    true,
                    ColorUtil.color(134, 162, 149),
                    ColorUtil.color(6, 111, 79)
            );

    public static final BCLEntityWrapper<EntitySkull> SKULL =
            register(
                    "skull",
                    MobCategory.MONSTER,
                    0.625F,
                    0.625F,
                    EntitySkull::new,
                    EntitySkull.createMobAttributes(),
                    true,
                    ColorUtil.color(24, 19, 19),
                    ColorUtil.color(255, 28, 18)
            );


    private static <T extends Mob> BCLEntityWrapper<T> register(
            String name,
            MobCategory group,
            float width,
            float height,
            EntityFactory<T> entity,
            Builder attributes,
            boolean fixedSize,
            int eggColor,
            int dotsColor
    ) {
        ResourceLocation id = BetterNether.makeID(name);
        EntityType<T> type = FabricEntityTypeBuilder.create(group, entity)
                                                    .dimensions(fixedSize
                                                            ? EntityDimensions.fixed(width, height)
                                                            : EntityDimensions.scalable(width, height))
                                                    .fireImmune() //Nether Entities are by default immune to fire
                                                    .build();

        type = Registry.register(Registry.ENTITY_TYPE, id, type);
        FabricDefaultAttributeRegistry.register(type, attributes);
        NetherItems.makeEgg("spawn_egg_" + name, type, eggColor, dotsColor);

        if (Configs.MOBS.getBooleanRoot(id.getPath(), true)) {
            return new BCLEntityWrapper<>(type, true);
        }

        var wrapper = new BCLEntityWrapper<>(type, false);
        NETHER_ENTITIES.add(wrapper);
        return wrapper;
    }

    private static boolean testSpawnAboveLava(LevelAccessor world, BlockPos pos, boolean allow) {
        int h = org.betterx.bclib.util.BlocksHelper.downRay(world, pos, MAX_FLOAT_HEIGHT + 2);
        if (h > MAX_FLOAT_HEIGHT) return false;

        for (int i = 1; i <= h + 1; i++)
            if (org.betterx.bclib.util.BlocksHelper.isLava(world.getBlockState(pos.below(i))))
                return allow;

        return !allow;
    }

    public static final int MAX_FLOAT_HEIGHT = 7;
    public static final SpawnRule RULE_FLOAT_NOT_ABOVE_LAVA = (type, world, spawnReason, pos, random) -> testSpawnAboveLava(
            world,
            pos,
            false
    );
    public static final SpawnRule RULE_FLOAT_ABOVE_LAVA = (type, world, spawnReason, pos, random) -> testSpawnAboveLava(
            world,
            pos,
            true
    );


    public static void register() {
        registerEntity("chair", CHAIR, EntityChair.getAttributeContainer());
        registerEntity("naga_projectile", NAGA_PROJECTILE);

        SpawnRuleBuilder
                .start(FIREFLY)
                .belowMaxHeight()
                .customRule(RULE_FLOAT_NOT_ABOVE_LAVA)
                .maxNearby(32, 64)
                .buildNoRestrictions(Types.MOTION_BLOCKING_NO_LEAVES);

        SpawnRuleBuilder
                .start(HYDROGEN_JELLYFISH)
                .belowMaxHeight()
                .maxNearby(24, 64)
                .buildNoRestrictions(Types.MOTION_BLOCKING);

        SpawnRuleBuilder
                .start(NAGA)
                .hostile(8)
                .maxNearby(32, 64)
                .buildOnGround(Types.MOTION_BLOCKING_NO_LEAVES);

        SpawnRuleBuilder
                .start(FLYING_PIG)
                .belowMaxHeight()
                .customRule(RULE_FLOAT_NOT_ABOVE_LAVA)
                .maxNearby(16, 64)
                .buildNoRestrictions(Types.MOTION_BLOCKING);

        SpawnRuleBuilder
                .start(JUNGLE_SKELETON)
                .notPeaceful()
                .maxNearby(16, 64)
                .buildOnGround(Types.MOTION_BLOCKING_NO_LEAVES);

        SpawnRuleBuilder
                .start(SKULL)
                .belowMaxHeight()
                .vanillaHostile()
                .maxNearby(16, 64)
                .buildNoRestrictions(Types.MOTION_BLOCKING);
    }

    public static void registerEntity(String name, EntityType<? extends LivingEntity> entity) {
        registerEntity(name, entity, Mob.createMobAttributes().build());
    }

    public static void registerEntity(String name, EntityType<? extends Entity> entity, AttributeSupplier container) {
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(BetterNether.MOD_ID, name), entity);
        ATTRIBUTES.put(entity, container);
    }

    public static boolean isNetherEntity(Entity entity) {
        return NETHER_ENTITIES.contains(entity.getType());
    }

    static void modifyNonBNBiome(ResourceLocation biomeID, Holder<Biome> biome) {
        final boolean isCrimson = biomeID.equals(Biomes.CRIMSON_FOREST.location());

        KnownSpawnTypes.FIREFLY.addSpawn(biomeID, biome, isCrimson ? 3 : 1);
        KnownSpawnTypes.HYDROGEN_JELLYFISH.addSpawn(biomeID, biome);
        KnownSpawnTypes.NAGA.addSpawn(biomeID, biome, isCrimson ? 0 : 1);

        synchronized (Configs.BIOMES) {
            Configs.BIOMES.saveChanges();
        }
    }
}
