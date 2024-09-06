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

@WebServlet("/Updater")
public class Updater extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/foodsite";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "tiger";

    public Updater() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve recipe ID, new recipe name, new instructions, and update option parameters
        String recipeId = request.getParameter("rid");
        String newRecipeName = request.getParameter("recipe_name");
        String newInstructions = request.getParameter("instructions");
        String updateOption = request.getParameter("update_option");

        // Validate recipe ID
        if (recipeId == null || recipeId.isEmpty()) {
            response.sendRedirect("invalid_parameters.jsp");
            return;
        }

        // Update recipe in the database
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare SQL statement based on the provided update option
            String sql = "";
            if (updateOption.equals("both")) {
                sql = "UPDATE recipes SET recipe_name = ?, instructions = ? WHERE rid = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, newRecipeName);
                stmt.setString(2, newInstructions);
                stmt.setString(3, recipeId);
            } else if (updateOption.equals("recipe_name")) {
                sql = "UPDATE recipes SET recipe_name = ? WHERE rid = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, newRecipeName);
                stmt.setString(2, recipeId);
            } else if (updateOption.equals("instructions")) {
                sql = "UPDATE recipes SET instructions = ? WHERE rid = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, newInstructions);
                stmt.setString(2, recipeId);
            }

            // Execute the statement
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Recipe updated successfully
                response.sendRedirect("recipe_updated.jsp");
            } else {
                // Recipe with the specified ID not found
                response.sendRedirect("recipe_not_found.jsp");
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle database errors
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }
}
