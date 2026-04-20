package onix.dev.util.render.backends.gl;

import onix.dev.Onixvisual;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class ResourceUtils {
    private ResourceUtils() {}

    public static String readText(String path) {
        ClassLoader cl = ResourceUtils.class.getClassLoader();
        System.out.println("p: " + path);
        System.out.println(Onixvisual.class.getProtectionDomain().getCodeSource().getLocation().getPath() + path);
      //  try (InputStream in = cl.getResourceAsStream(path)) {
        File f = new File(Onixvisual.class.getProtectionDomain().getCodeSource().getLocation().getPath()+ "/" + path);
        System.out.println("f: " + f.exists() + " p: " + f.getAbsolutePath());
        try (InputStream in = Onixvisual.class.getResourceAsStream(path+ "/")) {
            if (in == null) throw new IllegalStateException("Resource not found: " + path);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                return sb.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + path, e);
        }
    }

    public static ByteBuffer readBinary(String path) {
        ClassLoader cl = ResourceUtils.class.getClassLoader();
        try (InputStream in = cl.getResourceAsStream(path)) {
            if (in == null) throw new IllegalStateException("Resource not found: " + path);
            byte[] bytes = in.readAllBytes();
            ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes).flip();
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource: " + path, e);
        }
    }
}


