package lk.ijse.ceylontool.repo;

import lk.ijse.ceylontool.db.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplyRepo {
    public static void deleteSupply(String code) throws SQLException, ClassNotFoundException {



        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item_Supply WHERE t_id=?");
        pstm.setString(1, code);
        pstm.executeUpdate();
    }

    public static boolean exitSupply(String tId) throws SQLException, ClassNotFoundException {




        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT t_id FROM Item_Supply WHERE t_id=?");
        pstm.setString(1, tId);
        return pstm.executeQuery().next();
    }

    public static void saveSupply(String tId, String code, String sId, String supName, String itemName, int qty, BigDecimal buyingPrice) throws SQLException, ClassNotFoundException {






        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item_Supply (t_id,code,s_id,sup_name,item_name,qty,buyingPrice) VALUES (?,?,?,?,?,?,?)");
        pstm.setString(1, tId);
        pstm.setString(2, code);
        pstm.setString(3, sId);
        pstm.setString(4, supName);
        pstm.setString(5,   itemName);
        pstm.setInt(6, qty);
        pstm.setBigDecimal(7, buyingPrice);

        pstm.executeUpdate();
    }

    public static void updateSupply(String tId, String code, String sId, String supName, String itemName, int qty, BigDecimal buyingPrice) throws SQLException, ClassNotFoundException {


        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Supplier SET code=?, s_id=?  sup_name=?,item_name =?,qty=?,buyingPrice =? WHERE t_id=?");
        pstm.setString(1, code);
        pstm.setString(2, sId);
        pstm.setString(3, supName);
        pstm.setString(4,   itemName);
        pstm.setInt(5, qty);
        pstm.setBigDecimal(6, buyingPrice);
        pstm.setString(7, tId);
        pstm.executeUpdate();







    }

    public static ResultSet generateNewId() throws SQLException, ClassNotFoundException {


        Connection connection = DBConnection.getDbConnection().getConnection();
        return  connection.createStatement().executeQuery("SELECT t_id FROM Item_Supply ORDER BY t_id DESC LIMIT 1;");

    }
    }

