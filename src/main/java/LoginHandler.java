import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;

public class LoginHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String login = req.getParameter("username");
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/myDataSource");
            
            try (Connection conn = ds.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                String query = "SELECT * FROM users WHERE username = '" + login + "'";
                
                try (ResultSet rs = stmt.executeQuery(query)) {
                    if (rs.next()) {
                        resp.getWriter().println("Login successful");
                    } else {
                        resp.getWriter().println("Login failed");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
