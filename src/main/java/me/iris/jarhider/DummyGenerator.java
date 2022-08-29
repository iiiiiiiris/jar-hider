package me.iris.jarhider;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class DummyGenerator implements Opcodes {
    public static Manifest generateManifest() {
        final Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "Entry");
        return manifest;
    }

    public static ClassNode generateClass() {
        // Create class node & set needed vars
        final ClassNode node = new ClassNode();
        node.name = "Entry";
        node.access = ACC_PUBLIC;
        node.sourceFile = "/";
        node.version = V1_8;
        node.superName = "java/lang/Object";

        // Create method
        final MethodNode methodNode = new MethodNode(ACC_PUBLIC + ACC_STATIC,
                "main", "([Ljava/lang/String;)V", null, null);

        // Create label for printing string
        final Label l0 = new Label();
        methodNode.visitLabel(l0);
        methodNode.visitLineNumber(7, l0);

        // Get out stream & print string
        methodNode.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        methodNode.visitLdcInsn(":)");
        methodNode.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // Create another label for return instruction
        final Label l1 = new Label();
        methodNode.visitLabel(l1);
        methodNode.visitLineNumber(8, l1);

        // End of method
        methodNode.visitInsn(RETURN);

        // Add hello world method to class
        //noinspection unchecked
        node.methods.add(methodNode);

        // Return class node
        return node;
    }
}
