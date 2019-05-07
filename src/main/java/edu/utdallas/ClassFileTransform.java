package edu.utdallas;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


class ClassFileTransform implements ClassFileTransformer 
{
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException 
    {
        System.out.println(className);
        if (className.startsWith("org/apache/commons/dbutils") || className.startsWith("org/joda/time") || className.startsWith("com/theoryinpractise/clojure") || className.startsWith("org/apache/commons/lang3")
                 || className.startsWith("fjord") || className.startsWith("au/com/ds/ef") || className.startsWith("com/nilhcem/fakesmtp") || className.startsWith("com/github/vbauer/caesar") || className.startsWith("net/objecthunter/exp4")
                || className.startsWith("org/agilewiki/jactor") || className.startsWith("org/rauschig") || className.startsWith("org/jboss/modules") || className.startsWith("org/jinstagram") || className.startsWith("com/github/zafarkhaja/semver")
                || className.startsWith("org/jsoup") || className.startsWith("org/skyscreamer") || className.startsWith("ru/yandex/qatools/ashot") || className.startsWith("com/jadventure/game") || className.startsWith("com/groupon/jenkins") || className.equals("other") )
        {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassTransformVisitor ca = new ClassTransformVisitor(cw);
            cr.accept(ca, 0);
            return cw.toByteArray();
        }
        return classfileBuffer;
    }
}
