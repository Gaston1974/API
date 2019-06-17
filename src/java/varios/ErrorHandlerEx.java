/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package varios;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gaston
 */
public class ErrorHandlerEx extends Exception {

    private int codigo = 0;
    private String msg = "";
    
    
    public ErrorHandlerEx(HttpServletResponse res, PrintWriter salida, int codigo) {
        
        this.codigo = codigo;
        EnviarMsg(res, salida);
    }   
    
    
    public ErrorHandlerEx(HttpServletResponse res, PrintWriter salida) {
        
        salida.println("{\"codigo\":\"400\",\"respuesta\":\"algunos de los campos requeridos no tiene valor\"}");
        res.setStatus(400);
        msg = "falla debido a campos con valor nulo";
    }   
    
    public ErrorHandlerEx(HttpServletResponse res, PrintWriter salida, String valor) {
        
        if ( valor.equals("1")) {
        salida.println("{\"codigo\":\"400\",\"respuesta\":\"falla al paresear el JSON, Revice la estructura\"}");
        res.setStatus(400);
        msg = "falla al paresear el JSON";
                                }
        else {
        salida.println("{\"codigo\":\"503\",\"respuesta\":\"servicio no disponible\"}");
        res.setStatus(503);
        msg = "query devuelve valor nulo";    
        }
    } 
        
        
    public void EnviarMsg(HttpServletResponse resCode, PrintWriter salida) {
      
        switch (codigo) {
            
            case 200 :
                    msg = "{\"respuesta\":\"No se pudo crear el recurso, intente nuevamente mas tarde\"}";  
                    resCode.setStatus(200);
                    break;
            case 204 :
                    msg = "{\"respuesta\":\"\"}";  // sin contenido
                    resCode.setStatus(204);
                    break;
            case 400 :
                    msg = "{\"respuesta\":\"revice la sintaxis en la url\"}";
                    resCode.setStatus(400);
                    break;        
            case 503 :
                    msg = "{\"respuesta\":\"el servidor de APIsport no esta disponible\"}";
                    resCode.setStatus(503);
                    break;
            default :  
                    msg = "{\"respuesta\":\"servicio no disponible\"}";
                    resCode.setStatus(codigo);
        }
        
        salida.println(getMsg());
    }

    public String getMsg() {
        return msg;
    }
    
  
    
}
