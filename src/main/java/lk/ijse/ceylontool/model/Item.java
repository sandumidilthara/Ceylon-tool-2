package lk.ijse.ceylontool.model;

import java.io.Serializable;
import java.math.BigDecimal;



public class Item implements Serializable {
    private String code;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
    private BigDecimal sellingPrice;
    private String weight;
    public Item() {
    }

    public Item(String code, String description, BigDecimal unitPrice, int qtyOnHand,BigDecimal sellingPrice,String weight) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.sellingPrice = sellingPrice;
        this.weight = weight;    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }

    public BigDecimal getsellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
