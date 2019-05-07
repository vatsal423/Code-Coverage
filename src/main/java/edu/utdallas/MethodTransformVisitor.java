package edu.utdallas;

//import static javax.swing.text.html.HTML.Attribute.DATA;
//import static com.oracle.jrockit.jfr.DataType.STRING;

import java.util.Arrays;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

//import static org.objectweb.asm.Type.OBJECT;

class MethodTransformVisitor extends MethodVisitor implements Opcodes
{    
    protected int lastVisitedLine;
    protected String class_name;
    protected String lastVisitedVariable;
    protected String method_name;
    protected String[] listMatches;
   
   
    public MethodTransformVisitor(final MethodVisitor mv, String nameOfclass, String method_name,String[] listMatches) 
    {
        super(ASM5, mv);
        this.class_name=nameOfclass;
        this.method_name = method_name;
        this.listMatches = Arrays.copyOf(listMatches,listMatches.length);
        
    }
    
    /*@Override
    public void visitParameter(String name, int access) 
    {
        System.out.println("Hi visit Parameter");
        if(!name.equals(""))
        {
            mv.visitLdcInsn(class_name);
            mv.visitLdcInsn(new String(name));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CodeCoverageCollect", "addVariable", "(Ljava/lang/String;)V", false);
        }
        super.visitParameter(name,access);
    } */
   
    
    @Override 
    public void visitCode() 
    {
        if(!method_name.equals("<init>"))
        {
            
            //System.out.println("bbbbbbbbbbbbbbbbb");
            int length = listMatches.length;           
            System.out.println("\nParameter Descriptors of Method: "+method_name);
            mv.visitIntInsn(BIPUSH, length);
            mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");
            mv.visitVarInsn(ASTORE, length);
            for (int i=0;i<length;i++) 
            {
                System.out.println(listMatches[i]);
                mv.visitVarInsn(ALOAD, length);
                mv.visitIntInsn(BIPUSH, i);

                if (listMatches[i].equals("Z")) 
                {
                    mv.visitVarInsn(ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;",false);
                }
                else if (listMatches[i].equals("C")) 
                {
                    mv.visitVarInsn(ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;",false);
                }
                else if (listMatches[i].equals("I")) 
                {
                    mv.visitVarInsn(ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;",false);
                }
                else if (listMatches[i].equals("S")) 
                {
                    mv.visitVarInsn(ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;",false);
                }
                else if (listMatches[i].equals("J")) 
                {
                    mv.visitVarInsn(LLOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;",false);
                }
                else if (listMatches[i].equals("F")) 
                {
                    mv.visitVarInsn(FLOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;",false);
                }
                else if (listMatches[i].equals("D")) 
                {
                    mv.visitVarInsn(DLOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;",false);
                }
                else if (listMatches[i].equals("B")) 
                {
                    mv.visitVarInsn(ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;",false);
                }
                else if (listMatches[i].equals("Ljava/lang/String;"))
                {
                    mv.visitVarInsn(ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "Ljava/lang/String", "valueOf", "(Ljava/lang/String;)Ljava/lang/String;",false);
                }
                else
                {
                    mv.visitVarInsn(ALOAD, i);
                }
                mv.visitInsn(AASTORE);
                i++; 
            }
            System.out.println("\n");
                
                mv.visitLdcInsn(method_name);          
                mv.visitVarInsn(ALOAD,length);                
                //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
                mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CoverageCollection", "addVariableName", "(Ljava/lang/String;Ljava/lang/Object)V",false);
                //System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbb" );
        }
                super.visitCode();
    }
    
    
   /*
    @Override
    public void visitAttribute(Attribute att)
    {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb ");
        super.visitAttribute(att);
    }
*/
   
    @Override
    public void visitLineNumber(int line, Label start) 
    {
        //System.out.println("Hii visit Line Number");
        if (0 != line) 
        {
            lastVisitedLine = line;    	
	
            mv.visitLdcInsn(class_name);
            mv.visitLdcInsn(new Integer(line));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CoverageCollection", "addMethodLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
	}		
        super.visitLineNumber(line, start);
    }
    
    @Override
    public void visitLabel(Label label) 
    {
	if (0 != lastVisitedLine) 
        {
	    	mv.visitLdcInsn(class_name);
		mv.visitLdcInsn(new Integer(lastVisitedLine));
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
		mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CoverageCollection", "addMethodLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
        }
        super.visitLabel(label);
    }
   
    /*@Override
    public void visitLocalVariable(String name, String desc, String sign, Label start, Label end, int index)
    {
        if(!name.equals(""))
        {
            //System.out.print("Inside visitLocalVariable..");
            lastVisitedVariable = name;
            mv.visitLdcInsn(new String(lastVisitedVariable));
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CodeCoverageCollect", "addVariable", "(Ljava/lang/String;)V", false);
        }
        super.visitLocalVariable(name, desc, sign, end, end, index);
    }*/
    
   
    @Override
    public void visitEnd() 
    {  
        super.visitEnd();
    }
}
