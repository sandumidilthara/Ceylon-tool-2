package lk.ijse.ceylontool.repo;

import lk.ijse.ceylontool.db.DBConnection;
import lk.ijse.ceylontool.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {
    public static String currentId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT order_id FROM orders ORDER BY order_id desc LIMIT 1";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?)";
        PreparedStatement pstm = DBConnection.getDbConnection().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getCustomerId());
        pstm.setDate(3, order.getDate());

        return pstm.executeUpdate() > 0;
    }
}
