package lk.ijse.ceylontool.repo;

import lk.ijse.ceylontool.model.PlaceOrder;
import lk.ijse.ceylontool.db.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            if (isOrderSaved) {
                boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                if (isOrderDetailSaved) {
                    boolean isItemQtyUpdate = ItemRepo.updateQty(po.getOdList());
                    if (isItemQtyUpdate) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}