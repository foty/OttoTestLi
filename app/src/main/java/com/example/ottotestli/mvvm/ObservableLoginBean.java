package com.example.ottotestli.mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Create by lxx
 * Date : 2019/12/19 10:10
 * Use by
 */
public class ObservableLoginBean extends BaseObservable {

    //如果是 public 修饰符，则可以直接在成员变量上方加上 @Bindable 注解
    @Bindable
    public String phone;

    //如果是 private 修饰符，则在成员变量的 get 方法上添加 @Bindable 注解
    private String details;
    private String address;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        //只更新该字段
        notifyPropertyChanged(com.example.ottotestli.BR.phone);
    }

    @Bindable //如果是 private 修饰符，则在成员变量的 get 方法上添加 @Bindable 注解
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
        //更新所有字段
        notifyChange();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
