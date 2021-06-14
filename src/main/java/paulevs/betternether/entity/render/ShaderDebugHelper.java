package paulevs.betternether.entity.render;

import com.google.common.collect.Sets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GLImportProcessor;
import net.minecraft.client.gl.Program;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.FileNameUtil;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.Function;

abstract class ShaderDebugHelper extends RenderPhase {
    static int targetCacheID = 50;

    ShaderDebugHelper(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }

    protected static net.minecraft.client.render.Shader myDebugShader;
    protected static RenderPhase.Shader MY_DEBUG_SHADER;
    @Nullable
    static net.minecraft.client.render.Shader getRenderTypeMyShader() {
        return myDebugShader;
    }

    static void initDebugShader(){
        if (myDebugShader ==null) {
            ResourceFactory factory = MinecraftClient.getInstance().getResourceManager();
            try {
                myDebugShader = new DebugShader(factory);
                MY_DEBUG_SHADER = new RenderPhase.Shader(RenderPhaseAccessor::getRenderTypeMyShader);
            } catch (IOException e) {
                e.printStackTrace();
                MY_DEBUG_SHADER = RenderPhase.EYES_SHADER;
            }
        }
    }

    private static int debugCachingID = 0;
    private static RenderLayer reloadableDebugLayer = null;
    static RenderLayer getDebugLayer(Identifier texture, Function<Identifier, RenderLayer> setup){

        if (debugCachingID<targetCacheID || reloadableDebugLayer == null){
            myDebugShader = null;
            System.out.println("Reloading RenderLayer: " + targetCacheID);
            debugCachingID = targetCacheID;
            reloadableDebugLayer = setup.apply(texture);
        }
        return reloadableDebugLayer;
    }
}

class DebugShader extends net.minecraft.client.render.Shader {
    private static Program vertexShader;
    private static Program fragmentShader;
    private static String doInit(final ResourceFactory factory, final String baseName){
        try {
            System.out.println("Reloading Shader");
            vertexShader = loadProgram(factory, Program.Type.VERTEX, "bn_debug");
            fragmentShader = loadProgram(factory, Program.Type.FRAGMENT, "bn_debug");
        } catch (IOException e){
            e.printStackTrace();
        }
        return baseName;
    }
    public DebugShader(ResourceFactory factory) throws IOException {
        super(factory, doInit(factory, "rendertype_eyes"), VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
    }

    public Program getFragmentShader() {
        return DebugShader.fragmentShader;
    }

    public Program getVertexShader() {
        return DebugShader.vertexShader;
    }

    public void attachReferencedShaders() {
        this.getFragmentShader().attachTo(this);
        this.getVertexShader().attachTo(this);
    }

    private static Program loadProgram(final ResourceFactory factory, Program.Type type, String name) throws IOException {
        Program program;
        System.out.println("Loading " + name + type.getFileExtension());


        final String shaderFile = "/Users/frank/Desktop/mc1.17/assets/minecraft/shaders/core/" + name + type.getFileExtension();
        String dummyName = "shaders/core/" + name + type.getFileExtension();
        final String basePath = FileNameUtil.getPosixFullPath(dummyName);

        try {
            InputStream file = new FileInputStream(new File(shaderFile));
            program = Program.createFromResource(type, name, file, "betternether", new GLImportProcessor() {
                private final Set<String> visitedImports = Sets.newHashSet();

                public String loadImport(boolean inline, String name) {
                    String inludePath = inline ? basePath : "shaders/include/";
                    name = FileNameUtil.normalizeToPosix(inludePath + name);
                    if (!this.visitedImports.add(name)) {
                        return null;
                    } else {
                        Identifier identifier = new Identifier(name);

                        try {
                            Resource resource = factory.getResource(identifier);

                            String content;
                            try {
                                content = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
                            } catch (Throwable error) {
                                if (resource != null) {
                                    try {
                                        resource.close();
                                    } catch (Throwable innerError) {
                                        error.addSuppressed(innerError);
                                    }
                                }

                                throw error;
                            }

                            if (resource != null) {
                                resource.close();
                            }

                            return content;
                        } catch (IOException var9) {
                            System.err.println((String)"Could not open GLSL import " + (Object)name + ": " +  (Object)var9.getMessage());
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