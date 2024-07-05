package lk.ijse.ceylontool.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Supply implements Serializable {

    private String t_id;
    private String code;
    private String s_id;
    private String sup_name;
    private String item_name;


    private int qty;
    private BigDecimal buyingPrice;

    public Supply() {
    }

    public Supply(String t_id,String code ,String s_id,String sup_name,String item_name, int qty,BigDecimal buyingPrice) {
         this.t_id = t_id;
       this.code = code;

    this.s_id = s_id;
    this.sup_name = sup_name;
    this.item_name =item_name;
    this.qty = qty;
    this.buyingPrice = buyingPrice;
    }

    public String getSId() {
        return s_id;
    }

    public void setSId(String s_id) {
        this.s_id = s_id;
    }

    public String getTId() {
        return t_id;
    }

    public void setTId(String t_id) {
        this.t_id = s_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSupName() {
        return sup_name;
    }

    public void setSupName(String sup_name) {
        this.sup_name = sup_name;
    }





    public String getItemName() {
        return item_name;
    }

    public void setItemName(String item_name) {
        this.item_name = item_name;
    }

    public BigDecimal getBuyingPricePrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }













}
