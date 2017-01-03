/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.interfaces;

import java.util.List;
import epam.railway.entities.User;

/**
 *
 * @author denis
 */
public interface DaoUserInterface {
    User findByEmailAndPassword(String email, String password);
    User findById(Integer id);
    void addUser(String firstname, String lastname, String email, String password);
    List<User> findAllNotAdmin();
    void deleteById(Integer id);
    void update(User user);
}
