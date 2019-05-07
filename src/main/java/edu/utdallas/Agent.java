package edu.utdallas;

import java.lang.instrument.Instrumentation;

public class Agent 
{
    //called before main method is called and after jvm is initialized
    public static void premain(String agentArgs, Instrumentation inst) 
    {
        //registers the transformer
        inst.addTransformer(new ClassFileTransform());
    }
}

