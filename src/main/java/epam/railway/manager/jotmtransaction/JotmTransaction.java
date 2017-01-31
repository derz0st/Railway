package epam.railway.manager.jotmtransaction;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

/**
 * Created by denis on 25.01.17.
 */
public class JotmTransaction {
    public static UserTransaction getUserTransaction() throws NamingException {
        UserTransaction ut = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        return ut;
    }
}
