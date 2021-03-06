/**
 *
 * Copyright (c) 2015, Emil Forslund. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.pyknic.ck4j.metrics;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.SyntheticRepository;
import org.pyknic.ck4j.visitors.ClassVisitor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 * 
 */
public class Main {
     static BufferedWriter writer = null;
    public static void main(String[] args) {

        System.out.println("Starting CK4J.");
File fout = new File("out.txt");
	

        String jarPath = "C:\\Users\\ialsmadi\\Desktop\\SQA_UNH\\Jars\\acme.jar";
       
        try{
            writer=new BufferedWriter(new FileWriter("C:\\output.csv"));
        }
        catch(Exception ex){
            
        }
        final CKMetricsBuilderMgr mgr = new CKMetricsBuilderMgr();

        System.out.println("Attempting to load '" + jarPath + "'.");
        try {
            final JarFile jar = new JarFile(new File(jarPath));
            mgr.visitAll(
                jar.stream()
                    .filter(e -> e.getName().endsWith(".class"))
                    //.filter(e -> !e.getName().contains("Enhancer"))
                    .map(e -> {
                    try {
                        final ClassParser cp = new ClassParser(jar.getInputStream(e), e.getName());
                        final JavaClass clazz = cp.parse();
                        SyntheticRepository.getInstance().storeClass(clazz);
                        return new ClassVisitor(clazz, mgr);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, 
                            "Failed to parse class: '" + e.getName() + "'.", ex
                        );
                        return null;
                    }
                })
            );
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, 
                "Failed to load .jar file: '" + jarPath + "'.", ex
            );
        }

        mgr.stream().forEach(e -> 
    //        System.out.println("    " + e.getKey() + " : " + e.getValue());
     //   });
                test(e.getKey(),e.getValue()));
        try{
        writer.close();
        }
        catch(Exception ex){
            
        }
        System.out.println("Closing down.");
         
                }
    
                static void test(String t1, CKMetrics t2){
                    try{
           System.out.println("    " + t1 + " : " + t2);
           writer.newLine();
           writer.write(t1 + "," + t2);
            }
            catch(Exception ex){
                
            }
                }
    static void test1(CKMetrics e)
        {
          //  System.out.println("    " + e.getKey() + " : " + e.getValue());
            try{
           writer.write(e.getCbo()+ "," + e.getDit()+","+e.getLcom()+","+e.getNoc()+","+
                   e.getRfc()+","+ e.getWmc());
            }
            catch(Exception ex){
                
            }
           
        
    }
}