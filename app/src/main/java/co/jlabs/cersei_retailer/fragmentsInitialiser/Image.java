package co.jlabs.cersei_retailer.fragmentsInitialiser;

import java.io.Serializable;

/**
 * Created by Lincoln on 04/04/16.
 */
public class Image implements Serializable{

    private String pincode,phone, address, area,a;
    private Boolean bool;
    public Image() {
    }
    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
    public Boolean getSuccess() {
        return bool;
    }

    public void setSuccess(Boolean a) {
        this.bool = a;
    }




}
