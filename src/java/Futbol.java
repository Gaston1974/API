/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import hibernate.HibernateUtil;
import entidades.Fequipo;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import varios.Leedor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map; 
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.json.simple.parser.*;
import org.hibernate.Session; 
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import entidades.Jugador;
import java.io.InputStream;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import varios.ErrorHandlerEx;



/**
 *
 * @author root
 */

@WebServlet(urlPatterns = {"/Futbol/equipo", "Futbol/equipo/*"})
public class Futbol extends HttpServlet {
    
    
        private final String USER_AGENT = "Mozilla/5.0";

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          response.setContentType("text/html;charset=UTF-8;application/json");
     
            try 
            (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            // declaracion de variables
            
       
        String opcion = "0";
        Fequipo eq = new Fequipo();
        Leedor leedor = new Leedor();
        List<String> valores = null ;
        int i = 0;
        int statusCode = 0;
        FileWriter fwtr1 = null;
        String rutaArchivo = "/home/gaston/javaAPI_REST/API_REST/web/WEB-INF/jugadores.json" ;
        
       
        String urlPattern = request.getPathInfo(); 
        String regex = ".*/jugadores.*";

        boolean matches = Pattern.matches(regex, urlPattern);
    
        String [] equipo = urlPattern.split("/");
        opcion = equipo [1]; 
        
        fwtr1 = new FileWriter(rutaArchivo);
              
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();      
                                                                                    
        session.beginTransaction();
        

                System.out.println("Testing 1 - Send Http GET request");
                   
                // param = busco id ( API FUTBOL ) del equipo en la base
                try {    
                int opc = Integer.parseInt(opcion);
                    
                                    
                eq = (Fequipo) session.getNamedQuery("Select_equipoId").setInteger(0, opc).uniqueResult();
                String param = eq.getApi_id(); 
                String key = "&APIkey=3181aba25e0ededb5fa60883bd351da54315e3395abfbee8ab8cf6f768c63751";
                
                // Invoco API REST de Cristian                
                statusCode = sendGet(param, key);
                
                } catch (NumberFormatException e ) {
                Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, e);
                throw new ErrorHandlerEx( out, 400 );
                                       }
                catch (Exception ex) {
                Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, ex);
                throw new ErrorHandlerEx( out, 204 );
                                       }
                             
                             
        session.getTransaction().commit();    
        
            // urlImagen = busco id ( API FUTBOL ) del equipo en la base         
            String urlImagen = eq.getLogo_url();
            
            fwtr1.write("{\n[ "); 
                                     

                // parseo JSON
                
                Object obj; 
                obj = new JSONParser().parse(new FileReader("/home/gaston/javaAPI_REST/API_REST/web/WEB-INF/file.json"));
                JSONObject jo = (JSONObject) obj;
                
                Iterator<Map.Entry> itr1 ;
                Iterator<Map.Entry>  itr3 ;

                
                JSONArray ja = (JSONArray) jo.get("result"); 
                Iterator itr2 = ja.iterator(); 
                
                if ( !itr2.hasNext() && statusCode == 200 ) 
                    fwtr1.write("{\n\"No hay jugadores para el equipo elegido\" }" ); 
                else if ( statusCode != 200 )
                    throw new ErrorHandlerEx(out, statusCode);
                else {
                
                while (itr2.hasNext())  { 
                itr1 = ((Map) itr2.next()).entrySet().iterator(); 
                       while (itr1.hasNext()) { 
                       Map.Entry element = (Map.Entry) itr1.next();
                       if (element.getKey().equals("players"))   {                          //PARSEO jugadores
                                        JSONArray value = (JSONArray) element.getValue();
                                        Iterator itr4 = value.iterator();
                                                while (itr4.hasNext())  {
                                                if ( i > 0)    
                                                fwtr1.write(",");    
                                                itr3 = ((Map) itr4.next()).entrySet().iterator();
                                                valores = new ArrayList();
                                                    while (itr3.hasNext())  { 
                                                    Map.Entry pair = itr3.next(); 
                                                    valores.add((String) pair.getValue().toString()) ;
                                                                            } 
                                                Jugador ju = new Jugador(valores);   
                                                ju.escribirJson(fwtr1);
                                                i++;
                                                                        } 
                                                                 }
                                              } 
                                         } 
                fwtr1.write("\n]\n}");
                fwtr1.close();    
		leedor.leer(out, "/home/gaston/javaAPI_REST/API_REST/web/WEB-INF/jugadores.json" );
            }  
        }   catch (ErrorHandlerEx e1) {
                Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, e1);
                System.out.println("Excepcion:" + e1.getMsg());
            }
            catch (Exception ex) {
                Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
    }
    

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
        
    
        body = getBody(request);
        System.out.println("body:" + body);
        Object obj; 
                        try {
                            obj = new JSONParser().parse(body);
                            JSONObject jo = (JSONObject) obj;
                            
                            paisId = (String) jo.get("pais");
                            equipo = (String) jo.get("equipo");
                            equipoId = (String) jo.get("IdProveedor");
                            
                            ValidaData(out, paisId, equipo, equipoId);
                            
                            } catch (org.json.simple.parser.ParseException ex) {
                            Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, ex);
                            throw new ErrorHandlerEx(out,"1");
                                                        }
                             catch (Exception ex)       {
                            Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, ex);
                            throw new ErrorHandlerEx(out,"1");    
                                         }  
                             
        try {
      
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();      
                                                                                    
        session.beginTransaction();                     
                               
                max =  (int) session.createQuery("SELECT MAX(equipoId) FROM Fequipo").uniqueResult();
                max++;
                
                Fequipo eq = new Fequipo(Integer.parseInt(paisId), max, equipo, equipoId);          
                             
                session.save(eq);
                  
        
        session.getTransaction().commit();  
        
            } catch (Exception ex)       {
              Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, ex);
              
                                         }  
        out.println("{\"codigo\":\"201\",\"respuesta\":\"El equipo fue dado de alta\"}");
       
        } catch (ErrorHandlerEx e1) {
          Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, e1);
          System.out.println("Error:" + e1.getMsg());
                                                        }
        
    }
    

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        try 
            (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            // declaracion de variables
            
        int aux = 0;
        String equipo = "";
        equipo = request.getParameter("equipo");
        //String body = "";
        ValidaData(out, equipo);     
              
        //body = getBody(request);
        /*
        Object obj; 
                        try {
                            obj = new JSONParser().parse(body);
                            JSONObject jo = (JSONObject) obj;
                            
                            equipo = (String) jo.get("equipo");
                            
                            ValidaData(out, equipo);
                            
                            } catch (ParseException ex) {
                            Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, null, ex);
                            throw new ErrorHandlerEx(out,"1");
                                                        }     */
          try {
                                     
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();      
                                                                                    
        session.beginTransaction();
           
                System.out.println("Testing 1 - Send Http DELETE request");
                   
                aux =  (int) session.createQuery("SELECT equipoId FROM Fequipo t WHERE t.equipo = ?").setString(0, equipo).uniqueResult();
        
                if ( aux != 0  )          {    
                    
                    Fequipo eq = (Fequipo) session.get(Fequipo.class, aux);
                    session.delete(eq);
                                          }
                    
        session.getTransaction().commit();  
        
               } catch (Exception ex) {
              Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, ex);     
              throw new ErrorHandlerEx(out,"2");
                                      }          
          
       out.println("{\"codigo\":\"201\",\"respuesta\":\"El equipo fue eliminado\"}");
      
        } catch (ErrorHandlerEx e1) {
          Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, e1);
          System.out.println("Error:" + e1.getMsg());
                                                        }
 
    }
   
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        try 
          //   (OutputStream output = response.getOutputStream()) {
            (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            // declaracion de variables
            
        int aux = 0;
        String body = "";
        String equipo = "";
        String apiId = "";
      
              
        body = getBody(request);
        
        Object obj; 
                        try {
                            obj = new JSONParser().parse(body);
                            JSONObject jo = (JSONObject) obj;
                            
                            equipo = (String) jo.get("equipo");
                            apiId = (String) jo.get("IdProveedor");
                            
                            ValidaData(out, equipo, apiId );
                                                                                                 
                            } catch (org.json.simple.parser.ParseException ex) {
                            Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, ex);
                            throw new ErrorHandlerEx(out,"1");
                                                        }
      
          try {
              
                         
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();      
                                                                                    
        session.beginTransaction();
           
                System.out.println("Testing 1 - Send Http PUT request");
                   
                aux =  (int) session.createQuery("SELECT equipoId FROM Fequipo t WHERE t.equipo = ?").setString(0, equipo).uniqueResult();
                
                
                if ( aux != 0 ) {
                    
                    Fequipo eq = (Fequipo) session.get(Fequipo.class, aux);
                    eq.setApi_id(apiId);
                    session.flush(); 
                    
                                }
                    
        session.getTransaction().commit();  
        
               } catch (Exception ex) {
              Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, ex);
              throw new ErrorHandlerEx(out,"2");
                                                        }          
          
        out.println("{\"codigo\":\"204\",\"respuesta\":\"El equipo fue actualizado\"}");

        } catch (ErrorHandlerEx e1) {
          Logger.getLogger(Futbol.class.getName()).log(Level.SEVERE, null, e1);
          System.out.println("Error:" + e1.getMsg());
                                                        }
 
    }    
        
    
     	// HTTP GET request
        public int sendGet(String p, String k) throws Exception {

		int status;
                String url = "https://allsportsapi.com/api/football/?&met=Teams&teamId=";
                FileWriter fwtr2 = new FileWriter("/home/gaston/javaAPI_REST/API_REST/web/WEB-INF/file.json");
                            
             
                String urlParam = url + p ;
                String urlComp = urlParam + k ;
                String line2="";
                
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(urlComp);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);
                request.addHeader("Accept","application/json");
                
                       
		HttpResponse response = client.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + urlComp);
		System.out.println("Response Code : " + 
                       response.getStatusLine().getStatusCode());                    
                status = response.getStatusLine().getStatusCode();

		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			 line2 =result.append(line).toString();
                         fwtr2.write(line2);
		}

                fwtr2.close();
                return status;
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
    
        // Implementacion validacion parametros URL
    
        public void ValidaData(PrintWriter salida, String pais, String equipo, String equipo_id)
            throws ErrorHandlerEx{
        
        if ( pais.equals("") || equipo.equals("") || equipo_id.equals("")) 
        throw new ErrorHandlerEx(salida);
        
    }
        
        public void ValidaData(PrintWriter salida, String equipo)
            throws ErrorHandlerEx{
        
        if (  equipo.equals("") )
        throw new ErrorHandlerEx(salida);
        
    }
                          
        public void ValidaData(PrintWriter salida, String equipo, String sport)
            throws ErrorHandlerEx{
        
        if (  equipo.equals("") || sport.equals("") )
        throw new ErrorHandlerEx(salida);
        
    }
}
