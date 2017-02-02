/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.servlet;

import epam.railway.commands.ICommand;
import epam.railway.manager.Config;
import epam.railway.manager.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author denis
 */
public class Controller extends HttpServlet {

    private static final Logger servletLogger = LogManager.getLogger(Controller.class);
    private final ControllerHelper controllerHelper = ControllerHelper.getInstance();
    private static final String ERROR = "error";

    public Controller() {
        super();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page;
        try {
            ICommand command = controllerHelper.getCommand(request);
            page = command.execute(request, response);
            
        } catch (ServletException e) {
            servletLogger.error(Message.getInstance().getProperty(Message.SERVLET_EXECPTION));
            request.setAttribute(ERROR, Message.getInstance().getProperty(Message.SERVLET_EXECPTION));
            page = Config.getInstance().getProperty(Config.ERROR);
        } catch (IOException e) {
            servletLogger.error(Message.getInstance().getProperty(Message.IO_EXCEPTION));
            request.setAttribute(ERROR, Message.getInstance().getProperty(Message.IO_EXCEPTION));
            page = Config.getInstance().getProperty(Config.ERROR);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

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
        return "Servlet";
    }

}
