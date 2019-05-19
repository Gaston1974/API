/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entidades.Fequipo;
import hibernate.HibernateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import varios.ImpresorHTML;


/**
 *
 * @author gaston
 */
@WebServlet(urlPatterns = {"/Query2"})
public class Query2 extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        
                    try 
            (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            // declaracion de variables
        System.out.println("Send Http POST request");   
            // declaracion de variables
        int max = 0;   
        String body = "";
        String paisId = "" ;
        String equipo = "";
        String equipoId = "";
        ImpresorHTML impresor = new ImpresorHTML();
        
    
        body = getBody(request);
        
        Object obj; 
                        try {
                            obj = new JSONParser().parse(body);
                            JSONObject jo = (JSONObject) obj;
                            
                            paisId = (String) jo.get("pais");
                            equipo = (String) jo.get("equipo");
                            equipoId = (String) jo.get("equipo_id");
                                                                                 
                            } catch (ParseException ex) {
                            Logger.getLogger(Query2.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
        try {
      
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();      
                                                                                    
        session.beginTransaction();                     
                               
                max =  (int) session.createQuery("SELECT MAX(equipoId) FROM Fequipo").uniqueResult();
                max++;
                
                Fequipo eq = new Fequipo(Integer.parseInt(paisId), max, equipo, equipoId);          
                             
                session.save(eq);
                  
        
        session.getTransaction().commit();  
        
            } catch (Exception ex) {
              Logger.getLogger(Query2.class.getName()).log(Level.SEVERE, null, ex);
              
                                                        }        
        impresor.imprimir(out, "green", "El equipo fue dado de alta", "API rest");
                
        }
        
    }

    
    // Implementacion metodo para leer body del POST request
    
    public static String getBody(HttpServletRequest request) throws IOException {

    String body = null;
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;

    try {
        InputStream inputStream = request.getInputStream();
        if (inputStream != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } else {
            stringBuilder.append("");
        }
    } catch (IOException ex) {
        throw ex;
    } finally {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                throw ex;
            }
        }
    }

    body = stringBuilder.toString();
    return body;
}
    
    
}
