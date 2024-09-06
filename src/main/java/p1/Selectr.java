package p1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Selectr")
public class Selectr extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/foodsite";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "tiger";

    public Selectr() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve recipe ID and recipe name parameters
        String recipeId = request.getParameter("rid");
        String recipeName = request.getParameter("recipe_name");

        // Validate recipe ID and recipe name
        if (recipeId != null && !recipeId.isEmpty() && recipeName != null && !recipeName.isEmpty()) {
            // Select recipe from the database
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                // Establish connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Prepare SQL statement
                String sql = "SELECT * FROM recipes WHERE rid = ? AND recipe_name = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, recipeId);
                stmt.setString(2, recipeName);

                // Execute the statement
                rs = stmt.executeQuery();

                if (rs.next()) {
                    // Recipe found
                    // You can retrieve recipe details from ResultSet and forward to a JSP or display them in the servlet response
                    // For example:
                    String recipeIdFromDB = rs.getString("rid");
                    String recipeNameFromDB = rs.getString("recipe_name");
                    String recipeInstrucFromDB = rs.getString("instructions");
                    // Forward to a JSP page or display in the servlet response
                    response.getWriter().println("Recipe ID: " + recipeIdFromDB + " , Recipe Name: " + recipeNameFromDB +"  ,  Recipe INstructions :" + recipeInstrucFromDB );
                } else {
                    // Recipe with the specified ID and name not found
                    response.sendRedirect("recipe_not_found.jsp");
                }
            } catch (ClassNotFoundException | SQLException e) {
                // Handle database errors
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            } finally {
                // Close resources
                try {
                    if (rs != null) {
                        rs.close();
                    }
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
        } else {
            // Handle invalid recipe ID or recipe name
            response.sendRedirect("invalid_parameters.jsp");
        }
    }
}
