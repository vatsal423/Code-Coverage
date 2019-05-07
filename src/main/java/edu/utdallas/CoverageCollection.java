package edu.utdallas;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.HashMap;
import java.util.ArrayList;


public class CoverageCollection 
{	
    public static HashMap<String, HashMap<String, IntSet>> alltest_data;
    public static HashMap<String, IntSet> eachtest_data;
    public static String testcase_classname;
    public static ArrayList<String> eachvariable;
    public static ArrayList<String> allvariable;
    
    
    
    // Called whenever executing a line
    public static void addMethodLine(String className, Integer line)
    {
        //System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    	if (eachtest_data == null) 
        {
            return;
    	}
    	
    	IntSet lines = eachtest_data.get(className);
        if (lines != null) 
        {
            lines.add(line);
        }
        else 
        {
            lines = new IntOpenHashSet(new int[]{line});
            eachtest_data.put(className, lines);
        }
    }
    
    public static void addVariableName(String method_name,Object[] o) 
    {
        System.out.println("ddddddddddddddddddddd" + o[1]);
    	/*if(method_name != null)
        {
            eachvariable.add(method_name);
        }*/
    }
}
