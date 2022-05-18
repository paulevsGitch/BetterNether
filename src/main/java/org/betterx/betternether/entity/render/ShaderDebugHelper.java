package org.betterx.betternether.entity.render;

import net.minecraft.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

abstract class ShaderDebugHelper extends RenderStateShard {
    static int targetCacheID = 50;

    ShaderDebugHelper(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }

    protected static net.minecraft.client.renderer.ShaderInstance myDebugShader;
    protected static RenderStateShard.ShaderStateShard MY_DEBUG_SHADER;

    @Nullable
    static net.minecraft.client.renderer.ShaderInstance getRenderTypeMyShader() {
        return myDebugShader;
    }

    static void initDebugShader() {
        if (myDebugShader == null) {
            ResourceProvider factory = Minecraft.getInstance().getResourceManager();
            try {
                myDebugShader = new DebugShader(factory);
                MY_DEBUG_SHADER = new RenderStateShard.ShaderStateShard(ShaderDebugHelper::getRenderTypeMyShader);
            } catch (IOException e) {
                e.printStackTrace();
                MY_DEBUG_SHADER = RenderStateShard.RENDERTYPE_EYES_SHADER;
            }
        }
    }

    private static int debugCachingID = 0;
    private static RenderType reloadableDebugLayer = null;

    static RenderType getDebugLayer(ResourceLocation texture, Function<ResourceLocation, RenderType> setup) {

        if (debugCachingID < targetCacheID || reloadableDebugLayer == null) {
            myDebugShader = null;
            System.out.println("Reloading RenderLayer: " + targetCacheID);
            debugCachingID = targetCacheID;
            reloadableDebugLayer = setup.apply(texture);
        }
        return reloadableDebugLayer;
    }
}

class DebugShader extends net.minecraft.client.renderer.ShaderInstance {
    private static Program vertexShader;
    private static Program fragmentShader;

    private static String doInit(final ResourceProvider factory, final String baseName) {
        try {
            System.out.println("Reloading Shader");
            vertexShader = getOrCreate(factory, Program.Type.VERTEX, "bn_debug");
            fragmentShader = getOrCreate(factory, Program.Type.FRAGMENT, "bn_debug");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baseName;
    }

    public DebugShader(ResourceProvider factory) throws IOException {
        super(factory, doInit(factory, "rendertype_eyes"), DefaultVertexFormat.NEW_ENTITY);
    }

    public Program getFragmentProgram() {
        return DebugShader.fragmentShader;
    }

    public Program getVertexProgram() {
        return DebugShader.vertexShader;
    }

    public void attachToProgram() {
        this.getFragmentProgram().attachToShader(this);
        this.getVertexProgram().attachToShader(this);
    }

    private static Program getOrCreate(final ResourceProvider factory,
                                       Program.Type type,
                                       String name) throws IOException {
        Program program;
        System.out.println("Loading " + name + type.getExtension());


        String workingDirAbs = Paths.get("").toAbsolutePath().toString();
        final String shaderFile = workingDirAbs + "/" + name + type.getExtension();
        String dummyName = "shaders/core/" + name + type.getExtension();
        final String basePath = FileUtil.getFullResourcePath(dummyName);

        try {
            InputStream file = new FileInputStream(new File(shaderFile));
            program = Program.compileShader(type, name, file, "betternether", new GlslPreprocessor() {
                private final Set<String> visitedImports = Sets.newHashSet();

                public String applyImport(boolean inline, String name) {
                    String inludePath = inline ? basePath : "shaders/include/";
                    name = FileUtil.normalizeResourcePath(inludePath + name);
                    if (!this.visitedImports.add(name)) {
                        return null;
                    } else {
                        ResourceLocation identifier = new ResourceLocation(name);

                        try {
                            Resource resource = factory.getResource(identifier).orElse(null);

                            String content;
                            try (InputStream is = resource.open()) {
                                content = IOUtils.toString(is, StandardCharsets.UTF_8);
                            } catch (Throwable error) {
                                throw error;
                            }

                            return content;
                        } catch (IOException var9) {
                            System.err.println("Could not open GLSL import " + name + ": " + var9.getMessage());
                            return "#error " + var9.getMessage();
                        }
                    }
                }
            });
        } finally {

        }

        return program;
    }
}