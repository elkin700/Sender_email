package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    private Connection cn;

    public ConexionBD(String url, String user, String password) throws SQLException {
        cn = DriverManager.getConnection(url, user, password);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = cn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    public void close() throws SQLException {
        cn.close();
    }

}
