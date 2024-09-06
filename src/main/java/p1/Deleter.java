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
 * Servlet implementation class Deleter
 */
@WebServlet("/Deleter")
public class Deleter extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/foodsite";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "tiger";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deleter() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve recipe ID parameter
        String recipeId = request.getParameter("recipeId");

        // Validate recipe ID
        if (recipeId != null && !recipeId.isEmpty()) {
            // Delete recipe from the database
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                // Establish connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Prepare SQL statement
                String sql = "DELETE FROM recipes WHERE rid = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, recipeId);

                // Execute the statement
                int rowsDeleted = stmt.executeUpdate();

                if (rowsDeleted > 0) {
                    // Recipe deleted successfully
                    response.sendRedirect("recipe_deleted.jsp");
                } else {
                    // Recipe with the specified ID not found
                    response.sendRedirect("recipe_not_found.jsp");
                }
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
            // Handle invalid recipe ID
            response.sendRedirect("invalid_recipe_id.jsp");
        }
    }
}
