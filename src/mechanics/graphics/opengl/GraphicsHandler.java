package mechanics.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import files.FileManager;
import mechanics.graphics.opengl.Shader;
import mechanics.graphics.opengl.ShaderProgram;
import mechanics.graphics.opengl.VertexArrayObject;
import mechanics.graphics.opengl.VertexBufferObject;
import tensor.Matrix4;

public class GraphicsHandler {
	
	private static final CharSequence vertexSource, fragmentSource;
	
	static {
		String v, f;
		try {
			v = FileManager.readAll("/Users/claytonknittel/git/Physics/src/mechanics/graphics/opengl/vertexShader");
			f = FileManager.readAll("/Users/claytonknittel/git/Physics/src/mechanics/graphics/opengl/fragmentShader");
		} catch (IOException e) {
			v = "";
			f = "";
			e.printStackTrace();
		}
		vertexSource = v;
		fragmentSource = f;
	}
	
	private VertexArrayObject vao;
	private VertexBufferObject vbo;
	private Shader vertexShader;
	private Shader fragmentShader;
	private ShaderProgram program;
	
	private int uniModel, uniTex;
	
	private long time;
	
	public GraphicsHandler() {
		enter();
		time = 0;
	}
	
	
	public void input() {
		
	}
	
	public void update(long delta) {
		time += delta;
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		vao.bind();
		program.use();
		
		/*
		 * Set uniforms
		 */
        Matrix4 model = Matrix4.translate(0, time / 3000f, 0).multiply(Matrix4.rotate(time / 10f, 1f, 0f, 1f));
        program.setUniform(uniModel, model);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
	}

    public void enter() {
        /* Generate Vertex Array Object */
        vao = new VertexArrayObject();
        vao.bind();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Vertex data */
            FloatBuffer vertices = stack.mallocFloat(3 * 6);
            vertices.put(-0.6f).put(-0.4f).put(0f).put(1f).put(0f).put(0f);
            vertices.put(0.6f).put(-0.4f).put(0f).put(0f).put(1f).put(0f);
            vertices.put(0f).put(0.6f).put(0f).put(0f).put(0f).put(1f);
            vertices.flip();

            /* Generate Vertex Buffer Object */
            vbo = new VertexBufferObject();
            vbo.bind(GL_ARRAY_BUFFER);
            vbo.uploadData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        }

        /* Load shaders */
        vertexShader = Shader.createShader(GL_VERTEX_SHADER, vertexSource);
        fragmentShader = Shader.createShader(GL_FRAGMENT_SHADER, fragmentSource);

        /* Create shader program */
        program = new ShaderProgram();
        program.attachShader(vertexShader);
        program.attachShader(fragmentShader);
        program.bindFragmentDataLocation(0, "fragColor");
        program.link();
        program.use();

        specifyVertexAttributes();

        /* Get uniform location for the model matrix */
        uniModel = program.getUniformLocation("model");
        
        
        uniTex = program.getUniformLocation("texImage");
        // sets the value to the texture at location 0 which the first is by default)
        program.setUniform(uniTex, 0);

        /* Set view matrix to identity matrix */
        Matrix4 view = new Matrix4();
        int uniView = program.getUniformLocation("view");
        program.setUniform(uniView, view);

        /* Get width and height for calculating the ratio */
        float ratio;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            long window = GLFW.glfwGetCurrentContext();
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(window, width, height);
            ratio = width.get() / (float) height.get();
        }

        /* Set projection matrix to an orthographic projection */
        Matrix4 projection = Matrix4.orthographic(-ratio, ratio, -1f, 1f, -1f, 1f);
        int uniProjection = program.getUniformLocation("projection");
        program.setUniform(uniProjection, projection);
    }
	
	public void exit() {
        vao.delete();
        vbo.delete();
        vertexShader.delete();
        fragmentShader.delete();
        program.delete();
	}

    /**
     * Specifies the vertex attributes.
     */
    private void specifyVertexAttributes() {
        /* Specify Vertex Pointer */
        int posAttrib = program.getAttributeLocation("pos");
        program.enableVertexAttribute(posAttrib);
        program.pointVertexAttribute(posAttrib, 3, 6 * Float.BYTES, 0);

        /* Specify Color Pointer */
        int colAttrib = program.getAttributeLocation("color");
        program.enableVertexAttribute(colAttrib);
        program.pointVertexAttribute(colAttrib, 3, 6 * Float.BYTES, 3 * Float.BYTES);
        
//        int texAttrib = program.getAttributeLocation("texcoord");
//        program.enableVertexAttribute(texAttrib);
//        program.pointVertexAttribute(texAttrib, 2, 8 * Float.BYTES, 5 * Float.BYTES);
    }
	
}
