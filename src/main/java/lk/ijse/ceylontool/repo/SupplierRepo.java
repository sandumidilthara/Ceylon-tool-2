package lk.ijse.ceylontool.repo;

import lk.ijse.ceylontool.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierRepo {
    public static void saveSupplier(String id, String name, String address, int tp) throws SQLException, ClassNotFoundException {



        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Supplier (id,name, address,tp) VALUES (?,?,?,?)");
        pstm.setString(1, id);
        pstm.setString(2, name);
        pstm.setString(3, address);
        pstm.setInt(4, tp);
        pstm.executeUpdate();
    }

    public static ResultSet generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        return  connection.createStatement().executeQuery("SELECT id FROM Supplier ORDER BY id DESC LIMIT 1;");

    }

    public static void updateSupplier(String name, String address,int tp,String id) throws SQLException, ClassNotFoundException {



        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Supplier SET name=?, address=?  tp=? WHERE id=?");
        pstm.setString(1, name);
        pstm.setString(2, address);
        pstm.setInt(3, tp);
        pstm.setString(4, id);
        pstm.executeUpdate();
    }

    public static boolean exitSupplier(String id) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Supplier WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

    public static void deleteSupplier(String id) throws SQLException, ClassNotFoundException {


        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setString(1, id);
        pstm.executeUpdate();
    }
}
