package com.gz.gzcar.Database;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.Date;

/**
 * 收费记录表
 * <p/>
 * Created by Endeavor on 2016/8/23.
 */
@Table(name = "charge_record")
public class FreeInfoTable {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "type")
    private String type;

    @Column(name = "in_time")
    private Date inTime;

    @Column(name = "out_time")
    private Date outTime;

    @Column(name = "park_time")
    private String parkTime;

    @Column(name = "money")
    private double money;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getParkTime() {
        return parkTime;
    }

    public void setParkTime(String parkTime) {
        this.parkTime = parkTime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "FreeInfoTable{" +
                "id=" + id +
                ", carNumber='" + carNumber + '\'' +
                ", type='" + type + '\'' +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", parkTime='" + parkTime + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
