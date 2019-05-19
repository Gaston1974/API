/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author root
 */
public class Leedor {

    public Leedor() {
    }
    
    
    public void leer (PrintWriter salida, String ruta ) {
        
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
                
        try {
            
            archivo = new File (ruta);
            fr = new FileReader (archivo);
            br = new BufferedReader (fr);
            
            String linea;
            linea = br.readLine();
                    
            while ( linea != null) {           
            salida.println(linea);
            linea = br.readLine();           
        }    
            
        }
        
        catch(Exception e){
          e.printStackTrace();
         }finally{
            
            try{
                if ( null != fr ){
                    fr.close();
                }
           }catch ( Exception e2){
                e2.printStackTrace();
           }
            
        } 
        
        
    }
    
}
