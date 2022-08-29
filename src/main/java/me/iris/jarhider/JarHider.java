package me.iris.jarhider;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.nio.file.Files;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class JarHider {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("No jar file provided.");
            return;
        }

        // Read input jar
        final File inJar = new File(args[0]);
        final byte[] inBytes = Files.readAllBytes(inJar.toPath());

        // Create jar buffer for the fake jar
        final ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
        try (JarOutputStream stream = new JarOutputStream(bufferStream, DummyGenerator.generateManifest())) {
            stream.putNextEntry(new JarEntry("Entry.class"));

            final ClassNode fakeEntry = DummyGenerator.generateClass();
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            fakeEntry.accept(writer);
            stream.write(writer.toByteArray());
        }

        // Write jars to a single file
        FileOutputStream out = new FileOutputStream("hidden.jar");
        out.write(bufferStream.toByteArray());
        out.write(inBytes);
        out.flush();
        out.close();
    }
}
