
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConexionBD {

            String url = "jdbc:mysql://127.0.0.1:3306/Datos_clientes_suscripcion";
            String user = "root";
            String password = "elkin1013";
            
// public ResultSet getClientData() throws SQLException {
//     
//        Connection connection = DriverManager.getConnection(url, user, password);
//        Statement statement = connection.createStatement();
//        String query = "SELECT NameC, EmailC, ProduC FROM datos_cliente";
//        return statement.executeQuery(query);
//    }
public List<datos_cliente> getclientes () {
        List<datos_cliente> clientes  = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM datos_cliente");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NameC");
                String email = rs.getString("EmailC");
                String producto = rs.getString("ProduC");
                clientes .add(new datos_cliente(ID, NameC, EmailC, ProduC));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
}
