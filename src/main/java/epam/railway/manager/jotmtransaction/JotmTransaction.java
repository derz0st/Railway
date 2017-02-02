package epam.railway.manager.jotmtransaction;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

/**
 * Provides container's UserTransaction
 */
public class JotmTransaction {
    public static UserTransaction getUserTransaction() throws NamingException {
        return (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
    }
}
