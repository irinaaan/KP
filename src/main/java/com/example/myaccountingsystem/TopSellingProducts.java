package com.example.myaccountingsystem;

public class TopSellingProducts {
        private String productName;
        private int orderCount;

        public TopSellingProducts(String productName, int orderCount) {
            this.productName = productName;
            this.orderCount = orderCount;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName= productName;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }
}