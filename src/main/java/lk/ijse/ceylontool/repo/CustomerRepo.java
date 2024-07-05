package lk.ijse.ceylontool.repo;

import lk.ijse.ceylontool.db.DBConnection;
import lk.ijse.ceylontool.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {

    public   static void
    saveCustomer(String id,String name,String address) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer (id,name, address) VALUES (?,?,?)");
        pstm.setString(1, id);
        pstm.setString(2, name);
        pstm.setString(3, address);
        pstm.executeUpdate();
    }

    public static void updateCustomer(String name,String address,String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=?");
        pstm.setString(1, name);
        pstm.setString(2, address);
        pstm.setString(3, id);
        pstm.executeUpdate();

    }

    public static boolean exitCustomer(String  id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT id FROM customer WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

public  static void deleteCustomer(String id) throws SQLException, ClassNotFoundException {

    Connection connection = DBConnection.getDbConnection().getConnection();
    PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
    pstm.setString(1, id);
    pstm.executeUpdate();
}
public  static ResultSet generateNewId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
            return  connection.createStatement().executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");

}

    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id FROM Customer";

        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }



    public static Customer searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE id = ?";
        PreparedStatement pstm = DBConnection.getDbConnection().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Customer customer = null;

        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);


            customer = new Customer(cus_id, name, address);
        }
        return customer;
    }

}