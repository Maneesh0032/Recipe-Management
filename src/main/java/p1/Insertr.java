package p1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Insertr
 */
@WebServlet("/Insertr")
public class Insertr extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/foodsite";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "tiger";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Insertr() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String recipeName = request.getParameter("recipeName");
        String ingredients = request.getParameter("ingredients");
        String instructions = request.getParameter("instructions");

        // Validate form data
        if (recipeName != null && !recipeName.isEmpty() && ingredients != null && !ingredients.isEmpty() && instructions != null && !instructions.isEmpty()) {
            // Insert recipe into the database
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                // Establish connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Prepare SQL statement
                String sql = "INSERT INTO recipes (recipe_name, ingredients, instructions) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, recipeName);
                stmt.setString(2, ingredients);
                stmt.setString(3, instructions);

                // Execute the statement
                stmt.executeUpdate();

                // Redirect user to a success page
                response.sendRedirect("recipe_added.jsp");
            } catch (ClassNotFoundException | SQLException e) {
                // Handle database errors
                e.printStackTrace(); // For demonstration purposes only, handle the error appropriately in a real application
                response.sendRedirect("error.jsp");
            } finally {
                // Close resources
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); // Log or handle the error appropriately
                }
            }
        } else {
            // Handle invalid form data
            response.sendRedirect("error.jsp");
        }
    }
}
