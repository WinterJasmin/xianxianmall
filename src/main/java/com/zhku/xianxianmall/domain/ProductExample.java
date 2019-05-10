package com.zhku.xianxianmall.domain;

import java.util.Arrays;
import java.util.Date;

/**
 * @author: qwe
 * @date: 2019/5/3 19:32
 * @description:Product的扩展类，为方便商品详情页product颜色、尺码信息的回显使用
 */
public class ProductExample extends Product {

    private String[] exampleSize;

    private String[] exampleColor;

    public String[] getExampleSize() {
        return exampleSize;
    }

    public void setExampleSize(String[] exampleSize) {
        this.exampleSize = exampleSize;
    }

    public String[] getExampleColor() {
        return exampleColor;
    }

    public void setExampleColor(String[] exampleColor) {
        this.exampleColor = exampleColor;
    }

    public ProductExample() {
    }

    public ProductExample(Integer pid, String pname, String pdescription, Double pprice, Integer pstate, Integer pclass, Date pdate, String size, String color, String picaddress, String[] exampleSize, String[] exampleColor) {
        super(pid, pname, pdescription, pprice, pstate, pclass, pdate, size, color, picaddress);
        this.exampleSize = exampleSize;
        this.exampleColor = exampleColor;
    }

    @Override
    public String toString() {
        return "ProductExample{" +
                "pid=" + super.getPid() +
                ", pname='" + super.getPname() + '\'' +
                ", pdescription='" + super.getPdescription() + '\'' +
                ", pprice=" + super.getPprice() +
                ", pstate=" + super.getPstate() +
                ", pclass=" + super.getPclass() +
                ", pdate=" + super.getPdate() +
                ", size='" + super.getSize() + '\'' +
                ", color='" + super.getColor() + '\'' +
                ", picaddress='" + super.getPicaddress() + '\'' +
                ",exampleSize=" + Arrays.toString(exampleSize) +
                ", exampleColor=" + Arrays.toString(exampleColor) +
                '}';
    }
}
