package com.example.arouterdemo.services;

import com.alibaba.android.arouter.facade.template.IProvider;

// 声明接口,其他组件通过接口来调用服务
public interface HelloService extends IProvider {
   void sayHello(String name);
}