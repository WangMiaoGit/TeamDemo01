package com.example.wangmiao.teamdemo01.domain;

import java.util.List;

/**
 * Created by kzj on 2016/11/1.
 */
public class Home_Clothes {


    private List<RowsEntity> rows;

    public void setRows(List<RowsEntity> rows) {
        this.rows = rows;
    }

    public List<RowsEntity> getRows() {
        return rows;
    }

    public static class RowsEntity {
        /**
         * Discount : 2.2
         * Id : 1412230
         * IsBaoYou : 1
         * IsPromotion : 0
         * Name : 卫衣女韩版潮学生加绒衫加厚宽松套头bf秋冬大码女装胖mm卡通外套
         * NewPrice : 27.5
         * OldPrice : 125
         * PFrom : 1
         * ProductImg : http://img03.taobaocdn.com/bao/uploaded/i3/TB12nrhNpXXXXbuXpXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg
         * ProductImg1 : http://img03.taobaocdn.com/bao/uploaded/i3/TB12nrhNpXXXXbuXpXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg
         * ProductImgWX : http://img.1zhebaoyou.com/upload/product/2016-11/1/100201611011010431034.jpg
         * ProductUrl : https://detail.tmall.com/item.htm?id=539040648338
         * SaleTotal : 201
         * SpreadUrl : http://s.click.taobao.com/t?e=m%3D2%26s%3DIkzTgEGdPQscQipKwQzePOeEDrYVVa64pRe%2F8jaAHci5VBFTL4hn2TjaLYD4QoyEByy0g7RzMQdDNjtY1PGMgTn640bzR4Te6D8fFF8ikCrcwARGI3VHcT9CkxSUF2Dnd78QKDC%2FVUV2RhcrhVoMPnEqY%2Bakgpmw
         * picHeight : 880
         * picWidth : 800
         */

        private double Discount;
        private int IsBaoYou;
        private String Name;
        private double NewPrice;
        private int OldPrice;
        private String ProductImg;
        private int SaleTotal;

        public void setDiscount(double Discount) {
            this.Discount = Discount;
        }

        public void setIsBaoYou(int IsBaoYou) {
            this.IsBaoYou = IsBaoYou;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setNewPrice(double NewPrice) {
            this.NewPrice = NewPrice;
        }

        public void setOldPrice(int OldPrice) {
            this.OldPrice = OldPrice;
        }

        public void setProductImg(String ProductImg) {
            this.ProductImg = ProductImg;
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

        public int getOldPrice() {
            return OldPrice;
        }

        public String getProductImg() {
            return ProductImg;
        }

        public int getSaleTotal() {
            return SaleTotal;
        }
    }

    @Override
    public String toString() {
        return "Home_Clothes{" +
                "rows=" + rows +
                '}';
    }
}
