/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varios;

import java.io.PrintWriter;

/**
 *
 * @author root
 */
public class ImpresorHTML {

    public ImpresorHTML() {
    }
    
    
    public void imprimir(PrintWriter salida, String color, String desc, String titulo){
        
            salida.println("<!DOCTYPE html>");
            salida.println("<html>");
            salida.println("<head>");
            salida.println("<title>" + titulo + "</title>");            
            salida.println("</head>");
            salida.println("<body>");
            salida.println("<br><br><br><br><br><br><br><br><br><br><br><br>");
            salida.println("<h1><label><p align=center><font size=2 color=" + color + "face=\"helvetica\">" + desc + "</p></label></h1>");
            salida.println("</body>");
            salida.println("</html>");
    
    }    
    
    
}
