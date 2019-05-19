/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
import varios.ImpresorHTML;
//import java.sql.Timestamp; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.json.simple.JSONArray;
import varios.ErrorHandlerEx;

//import javax.servlet.*;


/**
 *
 * @author root
 */
@WebServlet(urlPatterns = {"/Query"})
public class Query extends HttpServlet {
    
    
        private final String USER_AGENT = "Mozilla/5.0";

    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          response.setContentType("text/html;charset=UTF-8;application/json");
     
            try 
            (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            // declaracion de variables
            
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String opcion = "0";
        Fequipo eq = new Fequipo();
        Leedor leedor = new Leedor();
        List<String> valores = null ;
        int i = 0;
        int statusCode = 0;
        FileWriter fwtr1 = null;
        String rutaArchivo = "/home/gaston/javaAPI_REST/API_REST/web/WEB-INF/jugadores.json" ;
        
        opcion = request.getParameter("teams");
        fwtr1 = new FileWriter(rutaArchivo);
              
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();      
                                                                                    
        session.beginTransaction();
        

                System.out.println("Testing 1 - Send Http GET request");
                   
                // param = busco id ( API FUTBOL ) del equipo en la base
                int opc = Integer.parseInt(opcion);
                
                try {
                    
                eq = (Fequipo) session.getNamedQuery("Select_equipoId").setInteger(0, opc).uniqueResult();
                String param = eq.getApi_id(); 
                String key = "&APIkey=3181aba25e0ededb5fa60883bd351da54315e3395abfbee8ab8cf6f768c63751";
                                
                statusCode = sendGet(param, key);
                
                } catch (Exception ex) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
                throw new ErrorHandlerEx( out, 201 );
                                       }
                             
                             
        session.getTransaction().commit();    
        
            // urlImagen = busco id ( API FUTBOL ) del equipo en la base         
            String urlImagen = eq.getLogo_url();
            
            fwtr1.write("{\n" +
                        "StatusCode:" + statusCode + ", \n" +
                        "jugador:[ " ); 
                               

                // parseo JSON
                
                Object obj; 
                obj = new JSONParser().parse(new FileReader("/home/gaston/javaAPI_REST/API_REST/web/WEB-INF/file.json"));
                JSONObject jo = (JSONObject) obj;
                
                Iterator<Map.Entry> itr1 ;
                Iterator<Map.Entry>  itr3 ;

                
                JSONArray ja = (JSONArray) jo.get("result"); 
                Iterator itr2 = ja.iterator(); 
                
                if ( !itr2.hasNext() && statusCode == 200 ) 
                    fwtr1.write("{\n no hay jugadores para el equipo elegido }" ); 
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
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, e1);
                System.out.println("Excepcion:" + e1.getMsg());
            }
            catch (Exception ex) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
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
}
