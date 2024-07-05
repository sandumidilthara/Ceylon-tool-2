package lk.ijse.ceylontool.repo;

import lk.ijse.ceylontool.model.OrderDetail;
import lk.ijse.ceylontool.db.DBConnection;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {
    public static boolean save(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(OrderDetail od) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO order_detail VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DBConnection.getDbConnection().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getItemCode());
        pstm.setInt(3, od.getQty());
        pstm.setDouble(4, od.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }
}
