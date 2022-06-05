package org.betterx.betternether;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

import java.util.concurrent.ThreadLocalRandom;

public class MHelper {
    static class ThreadLocalRandomSource implements RandomSource {
        @Override
        public RandomSource fork() {
            return this;
        }

        @Override
        public PositionalRandomFactory forkPositional() {
            return null;
        }

        @Override
        public void setSeed(long l) {
            ThreadLocalRandom.current().setSeed(l);
        }

        @Override
        public int nextInt() {
            return ThreadLocalRandom.current().nextInt();
        }

        @Override
        public int nextInt(int i) {
            return ThreadLocalRandom.current().nextInt(i);
        }

        @Override
        public long nextLong() {
            return ThreadLocalRandom.current().nextLong();
        }

        @Override
        public boolean nextBoolean() {
            return ThreadLocalRandom.current().nextBoolean();
        }

        @Override
        public float nextFloat() {
            return ThreadLocalRandom.current().nextFloat();
        }

        @Override
        public double nextDouble() {
            return ThreadLocalRandom.current().nextDouble();
        }

        @Override
        public double nextGaussian() {
            return ThreadLocalRandom.current().nextGaussian();
        }
    }

    public static final float PI2 = (float) (Math.PI * 2);
    private static final int ALPHA = 255 << 24;
    public static final RandomSource RANDOM = new ThreadLocalRandomSource();

    public static int color(int r, int g, int b) {
        return ALPHA | (r << 16) | (g << 8) | b;
    }


    public static int randRange(int min, int max, RandomSource random) {
        return min + random.nextInt(max - min + 1);
    }

    public static float randRange(float min, float max, RandomSource random) {
        return min + random.nextFloat() * (max - min);
    }

    public static byte setBit(byte source, int pos, boolean value) {
        return value ? setBitTrue(source, pos) : setBitFalse(source, pos);
    }

    public static byte setBitTrue(byte source, int pos) {
        source |= 1 << pos;
        return source;
    }

    public static byte setBitFalse(byte source, int pos) {
        source &= ~(1 << pos);
        return source;
    }

    public static boolean getBit(byte source, int pos) {
        return ((source >> pos) & 1) == 1;
    }

    public static int floor(float x) {
        return x < 0 ? (int) (x - 1) : (int) x;
    }

    public static float wrap(float x, float side) {
        return x - floor(x / side) * side;
    }

    public static int floor(double x) {
        return x < 0 ? (int) (x - 1) : (int) x;
    }

    public static float nextFloat(RandomSource random, float d) {
        return random.nextFloat() * d;
    }

    public static double nextDouble(RandomSource random, double d) {
        return random.nextDouble() * d;
    }
}
