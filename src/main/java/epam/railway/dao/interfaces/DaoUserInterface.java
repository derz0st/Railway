/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epam.railway.dao.interfaces;

import epam.railway.entities.User;

import java.util.List;

/**
 *
 * @author denis
 */
public interface DaoUserInterface {

    /**
     * Finds user in database with specified email and password
     * @param email user email
     * @param password user password
     * @return user with email and password, return null if in db there isn't user with email and password
     */
    User findByEmailAndPassword(String email, String password);

    /**
     * Finds user in database with specified id
     * @param id id of user
     * @return user with specified id
     */
    User findById(Integer id);

    /**
     * Finds user in database with specified email
     * @param email email of user
     * @return true if user exists in database
     */
    boolean findByEmail(String email);

    /**
     * Insert in database record about new user
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email
     * @param password user's password
     */
    void addUser(String firstName, String lastName, String email, String password);

    /**
     * Finds in database all users, which have admin id = 0
     * @return all list of not admin users
     */
    List<User> findAllNotAdmin();

    /**
     * Block user with specified id
     * @param id id of user
     */
    void blockById(Integer id);

    /**
     * Unlock user with specified id
     * @param id id of user
     */
    void unblockById(Integer id);

    /**
     * Update in database information about user
     * @param user user which is updated
     */
    void update(User user);
}
