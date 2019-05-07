package edu.utdallas;
import java.io.*;
import java.util.Arrays;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;


import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.HashMap;
import java.util.ArrayList;


public class JUnitListener extends RunListener 
{
    // Called before all Test cases starts executing
    public void testRunStarted(Description description) throws Exception 
    {
	if (null == CoverageCollection.alltest_data)
	{
            CoverageCollection.alltest_data = new HashMap<String, HashMap<String, IntSet>>();
            CoverageCollection.allvariable = new ArrayList<String>();
	}		
        //System.out.println("\nTesting has started!!!");
    }
	
    //Called before each @Test is exectued
    public void testStarted(Description description) 
    {	
    	CoverageCollection.testcase_classname = "[TEST] " + description.getClassName() + ":" + description.getMethodName();
    	CoverageCollection.eachtest_data = new HashMap<String, IntSet>();
        CoverageCollection.eachvariable = new ArrayList<String>();
    }
    
    //Called after each @Test is Finished
    public void testFinished(Description description) 
    {
    	CoverageCollection.alltest_data.put(CoverageCollection.testcase_classname, CoverageCollection.eachtest_data);
        
        for(int i=0;i<CoverageCollection.eachvariable.size();i++)
        {
            CoverageCollection.allvariable.add(CoverageCollection.eachvariable.get(i));
        }
    }
    
    //Called after all tests cases are finished
    public void testRunFinished(Result result) throws IOException 
    {
        //System.out.println("Testing has finished!!!\n\n");
        
        // Write to .txt file
        File fout = new File("statement_coverage.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        StringBuilder builder = new StringBuilder();
        for (String testCaseName : CoverageCollection.alltest_data.keySet()) 
        {
            builder.append(testCaseName + "\n");
            HashMap<String, IntSet> caseCoverage =  CoverageCollection.alltest_data.get(testCaseName);
        	
            for (String className : caseCoverage.keySet()) 
            {
            	int[] lines = caseCoverage.get(className).toIntArray();
            	Arrays.sort(lines);
                
            	for (int i = 0; i < lines.length; i++) 
                {
                    builder.append(className + ": " + lines[i] + "\n");
                }
            }
        }
        bw.write(builder.toString());
        bw.close();
        
        File fout1 = new File("variable.txt");
        FileOutputStream fos1 = new FileOutputStream(fout1);
        BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(fos1));

        StringBuilder builder1 = new StringBuilder();
        for(int i=0;i<CoverageCollection.allvariable.size();i++)
        {
            builder1.append(CoverageCollection.allvariable.get(i));
        }
        bw1.write(builder1.toString());
        bw1.close();
    }
}
