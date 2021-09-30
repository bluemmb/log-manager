package org.me.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.me.api.Models.Alert;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api")
public class Api extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // PrintWriter out = response.getWriter();

        try {
            int limit = parseToInt(request.getParameter("limit"), 10);

            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * from alerts order by id desc limit ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();
            List<Alert> alerts = new ArrayList<>();
            while ( rs.next() )
            {
                Alert alert = new Alert(
                        rs.getInt("id"),
                        rs.getString("ruleName"),
                        rs.getString("component"),
                        rs.getString("description"),
                        rs.getDate("created_at")
                );
                alerts.add(alert);
            }

            ObjectMapper mapper = new ObjectMapper();
            String errorsJson = mapper.writeValueAsString(alerts);
            response.setContentType("application/json");
            response.getOutputStream().print(errorsJson);

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int parseToInt(String stringToParse, int defaultValue) {
        try {
            return Integer.parseInt(stringToParse);
        } catch(NumberFormatException ex) {
            return defaultValue;
        }
    }
}
