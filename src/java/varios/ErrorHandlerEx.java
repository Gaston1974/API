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
    
    
    public void EnviarMsg(PrintWriter salida) {
      
        switch (codigo) {
            
            case 201 :
                    msg = "request OK, El equipo consultado no existe en la base de datos";
                    break;
            case 204 :
                    msg = "request OK, respuesta sin contenido";
                    break;
            case 503 :
                    msg = "request OK, el servidor de APIsport no esta disponible";
                    break;
            default :        
                    msg = "request OK, falla";
        }
        
        salida.println(getMsg());
    }

    public String getMsg() {
        return msg;
    }
    
  
    
}
