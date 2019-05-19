/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hibernate.HibernateUtil;
import varios.Leedor;
import varios.ImpresorHTML;
import org.hibernate.Session;

/**
 *
 * @author root
 */
@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
              
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        Leedor leedor = new Leedor();
        ImpresorHTML impresor = new ImpresorHTML();
        
    // Instancio las clases de HIBERNATE    
       
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();      
                                                                                      
        session.beginTransaction();                                                  
        
        String result = (String) session.createQuery("SELECT t.pasword FROM TbUsuario t WHERE t.nombre = ? " ).setString(0, usuario).uniqueResult();
  
        session.getTransaction().commit();

        
    // Validacion de resultados    
                
    if ( !result.equals("") && password.equals(result) ) 
        
        leedor.leer(out, "/home/gaston/Consultorio/Consultorio/Consultorio/web/WEB-INF/opciones.html" );
           

    else {
        
        impresor.imprimir(out, "red", "el nombre de usuario y/o el password son incorrectos", "Login");
                  
        
        }  
    }
}        

    @Override
    public boolean equals(Object obj) {
        
        
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

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
        processRequest(request, response);
    }

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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
