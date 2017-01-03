/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denis
 */
public class CommandTestNeo implements ICommand{
    
     @Override
    public String execute(HttpServletRequest request, HttpServletResponse responce) {
        String station = null;
        String page = "gdgdgdg";
        Connection con = null;
         try {
             Class.forName("org.neo4j.jdbc.Driver").newInstance();
             System.out.println("OKAY ????????");
         } catch (ClassNotFoundException ex) {
             System.out.println("adsfasdfasdfaf==============1");
             Logger.getLogger(CommandTestNeo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
             System.out.println("adsfasdfasdfaf==============2");
             Logger.getLogger(CommandTestNeo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
             System.out.println("adsfasdfasdfaf==============3");
             Logger.getLogger(CommandTestNeo.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
             con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost", "neo4j", "ycthssw5sn");
         } catch (SQLException ex) {
             Logger.getLogger(CommandTestNeo.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         // Querying
         String query = "MATCH (s:Station) RETURN s.name";
         try {
             PreparedStatement stmt = con.prepareStatement(query);
             //stmt.setString(1,"John");

             ResultSet rs = stmt.executeQuery();

             while (rs.next()) {
                 System.out.println(
                         "Friend: "+rs.getString("s.name"));
                 station = rs.getString("s.name");
             }
         } catch (Exception e) { System.out.println("============================"); e.printStackTrace(); }
         try {
             con.close();
         } catch (SQLException ex) {
             Logger.getLogger(CommandTestNeo.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         
         request.setAttribute("station", station);
        page = "/index.jsp";
       
        return page;
    }
}
