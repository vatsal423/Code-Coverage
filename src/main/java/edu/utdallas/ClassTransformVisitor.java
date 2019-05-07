package edu.utdallas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


class ClassTransformVisitor extends ClassVisitor implements Opcodes 
{
    String class_name;
    int counter;
    
    public ClassTransformVisitor(final ClassVisitor codevisitor) 
    {
        super(ASM5, codevisitor);
    }

    @Override
    public void visit(int version, int access, String class_name, String signature, String super_name, String[] interfaces) 
    {
        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ");
        super.visit(version, access, class_name, signature, super_name, interfaces);
        this.class_name=class_name;
    }
    
    
    
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) 
    {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
 
        String s = desc.substring(desc.indexOf("(") + 1, desc.indexOf(")"));
        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+s);
       
        Pattern pattern = Pattern.compile("\\[*L[^;]+;|\\[[ZBCSIFDJ]|[ZBCSIFDJ]"); //Regex for desc \[*L[^;]+;|\[[ZBCSIFDJ]|[ZBCSIFDJ]
        Matcher matcher = pattern.matcher(s);
        
        counter = 0;
        while(matcher.find()) 
        {
            //System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"+matcher.group());
            counter += 1;
        }
        
        matcher = pattern.matcher(s);
        
        String[] parameter_descriptors = new String[counter];
        int a = 0;
        while(matcher.find()) 
        {
            parameter_descriptors[a]=matcher.group();
            //System.out.println("aaaaaaaaaaaaaaaa"+listMatches[a]);
            a += 1;
        }
        return mv == null ? null : new MethodTransformVisitor(mv,class_name,name,parameter_descriptors);
    }
    
}
