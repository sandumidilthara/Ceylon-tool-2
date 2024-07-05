package lk.ijse.ceylontool.repo;

import lk.ijse.ceylontool.db.DBConnection;
import lk.ijse.ceylontool.model.Item;
import lk.ijse.ceylontool.model.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static ResultSet generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        return connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");

    }


    public static boolean exitItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();

    }


    public static void saveItem(String code, String description, BigDecimal unitPrice, int qtyOnHand, BigDecimal sellingPrice, String weight) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand,sellingPrice,weight) VALUES (?,?,?,?,?,?)");
        pstm.setString(1, code);
        pstm.setString(2, description);
        pstm.setBigDecimal(3, unitPrice);
        pstm.setInt(4, qtyOnHand);

        pstm.setBigDecimal(5, sellingPrice);
        pstm.setString(6, weight);
        pstm.executeUpdate();
    }


    public static void updateItem(String code, String description, BigDecimal unitPrice, int qtyOnHand, BigDecimal sellingPrice, String weight) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? , sellingPrice =?,weight =? WHERE code=?");
        pstm.setString(1, description);
        pstm.setBigDecimal(2, unitPrice);
        pstm.setInt(3, qtyOnHand);
        pstm.setBigDecimal(4, sellingPrice);
        pstm.setString(5, weight);
        pstm.setString(6, code);
        pstm.executeUpdate();
    }


    public static void deleteItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
        pstm.setString(1, code);
        pstm.executeUpdate();
    }



    public static List<String> getCodes() throws SQLException, ClassNotFoundException {
        String sql = "SELECT code FROM Item";

        PreparedStatement pstm = DBConnection.getDbConnection().getConnection()
                .prepareStatement(sql);

        List<String> codeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }
    public static Item searchByCode(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE code = ?";

        PreparedStatement pstm = DBConnection.getDbConnection().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getInt(4),
                    resultSet.getBigDecimal(5),
                    resultSet.getString(6)

            );
















        }
        return null;
    }

    public static boolean updateQty(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderDetail od) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET qtyOnHand = qtyOnHand - ? WHERE code = ?";
        PreparedStatement pstm = DBConnection.getDbConnection().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, od.getQty());
        pstm.setString(2, od.getItemCode());

        return pstm.executeUpdate() > 0;
    }
}

