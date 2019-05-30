/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varios;

import java.io.PrintWriter;

/**
 *
 * @author gaston
 */
public class ErrorHandlerEx extends Exception {

    private int codigo = 0;
    private String msg = "";
    
    
    public ErrorHandlerEx(PrintWriter salida, int codigo) {
        
        this.codigo = codigo;
        EnviarMsg(salida);
    }   
    
    
    public ErrorHandlerEx(PrintWriter salida) {
        
        salida.println("{\"codigo\":\"400\",\"respuesta\":\"algunos de los campos requeridos no tiene valor\"}");
        msg = "falla debido a campos con valor nulo";
    }   
    
    public ErrorHandlerEx(PrintWriter salida, String valor) {
        
        if ( valor.equals("1")) {
        salida.println("{\"codigo\":\"400\",\"respuesta\":\"falla al paresear el JSON, Revice la estructura\"}");
        msg = "falla al paresear el JSON";
                                }
        else {
        salida.println("{\"codigo\":\"503\",\"respuesta\":\"servicio no disponible\"}");
        msg = "query devuelve valor nulo";    
        }
    } 
        
        
    public void EnviarMsg(PrintWriter salida) {
      
        switch (codigo) {
            
            case 204 :
                    msg = "{\"codigo\":\"204\",\"respuesta\":\"sin contenido\"}";
                    break;
            case 503 :
                    msg = "{\"codigo\":\"200\",\"respuesta\":\"el servidor de APIsport no esta disponible\"}";
                    break;
            default :        
                    msg = "{\"codigo\":\"503\",\"respuesta\":\"servicio no disponible\"}";
        }
        
        salida.println(getMsg());
    }

    public String getMsg() {
        return msg;
    }
    
  
    
}
