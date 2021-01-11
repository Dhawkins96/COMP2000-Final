package com.Model;

public class Stock {

    public String itemName;
    private int itemCode;


    public float itemPrice;

    public int itemQuantity;



    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public void setItemCode(int itemCode){
        this.itemCode = itemCode;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemName(){
       return itemName;
    }

    public int getItemCode() {
        return itemCode;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public Stock(String stockName, int stockCode, float stockPrice, int stockQuantity){
        itemName = stockName;
        itemCode = stockCode;
        itemPrice = stockPrice;
        itemQuantity = stockQuantity;

    }
    public Stock(){

    }
}
