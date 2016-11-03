package com.example.wangmiao.teamdemo01.domain;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by kzj on 2016/11/1.
 */
public class Home_Clothes {


    private List<RowsEntity> rows;

    public static Home_Clothes objectFromData(String str) {

        return new Gson().fromJson(str, Home_Clothes.class);
    }

    public void setRows(List<RowsEntity> rows) {
        this.rows = rows;
    }

    public List<RowsEntity> getRows() {
        return rows;
    }

    public static class RowsEntity {
        /**
         * Discount : 3.288591
         * Id : 1414595
         * IsBaoYou : 1
         * IsPromotion : 0
         * Name : 秋冬装新款拼接卫衣女韩版宽松套头假两件上衣显瘦学生条纹衬衣潮
         * NewPrice : 49
         * OldPrice : 149
         * PFrom : 2
         * ProductImg : http://img1.tbcdn.cn/tfscom/i3/875806793/TB2uJ4FXA6z11Bjy0FoXXbvkpXa_!!875806793.jpg_430x430q90.jpg
         * ProductImg1 : http://img1.tbcdn.cn/tfscom/i3/875806793/TB2uJ4FXA6z11Bjy0FoXXbvkpXa_!!875806793.jpg_430x430q90.jpg
         * ProductImgWX : http://img.1zhebaoyou.com/upload/product/2016-11/3/100201611031006545179.jpg
         * ProductUrl : https://item.taobao.com/item.htm?id=538494975608
         * SaleTotal : 6593
         * SpreadUrl : http://s.click.taobao.com/t?e=m%3D2%26s%3DU%2Bbm4a3udmkcQipKwQzePOeEDrYVVa64Qih%2F7PxfOKS5VBFTL4hn2VDr7OASNDu70e71iVTN2RxDNjtY1PGMgTn640bzR4Te6D8fFF8ikCp3etMdIZmyEqpCoirhh9LZ5PpBJ2eMlXNZpJGe%2BswIJyGFCzYOOqAQ
         * picHeight : 880
         * picWidth : 800
         */

        private double Discount;
        private int IsBaoYou;
        private String Name;
        private double NewPrice;
        private double OldPrice;
        private String ProductImg;
        private String ProductUrl;
        private int SaleTotal;

        public static RowsEntity objectFromData(String str) {

            return new Gson().fromJson(str, RowsEntity.class);
        }

        public void setDiscount(double Discount) {
            this.Discount = Discount;
        }

        public void setIsBaoYou(int IsBaoYou) {
            this.IsBaoYou = IsBaoYou;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setNewPrice(int NewPrice) {
            this.NewPrice = NewPrice;
        }

        public void setOldPrice(int OldPrice) {
            this.OldPrice = OldPrice;
        }

        public void setProductImg(String ProductImg) {
            this.ProductImg = ProductImg;
        }

        public void setProductUrl(String ProductUrl) {
            this.ProductUrl = ProductUrl;
        }

        public void setSaleTotal(int SaleTotal) {
            this.SaleTotal = SaleTotal;
        }

        public double getDiscount() {
            return Discount;
        }

        public int getIsBaoYou() {
            return IsBaoYou;
        }

        public String getName() {
            return Name;
        }

        public double getNewPrice() {
            return NewPrice;
        }

        public double getOldPrice() {
            return OldPrice;
        }

        public String getProductImg() {
            return ProductImg;
        }

        public String getProductUrl() {
            return ProductUrl;
        }

        public int getSaleTotal() {
            return SaleTotal;
        }
    }
}
