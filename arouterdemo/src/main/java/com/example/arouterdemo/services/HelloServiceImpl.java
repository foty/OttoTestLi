package com.example.arouterdemo.services;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/service/hello", name = "测试服务")
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String name) {
        Log.d("lxx", "name= " + name);
    }

    @Override
    public void init(Context context) {
        //同一个服务只会被初始化一次
        Log.d("lxx", "hello服务初始化");
    }
}