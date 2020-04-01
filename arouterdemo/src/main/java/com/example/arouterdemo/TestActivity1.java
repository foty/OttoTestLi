package com.example.arouterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.arouterdemo.services.HelloService;

import java.util.ArrayList;

@Route(path = "/test/test1")
public class TestActivity1 extends AppCompatActivity {

    @Autowired
    HelloService helloService;

    @Autowired(name = "/service/hello")
    HelloService helloService2;

    HelloService helloService3;

    HelloService helloService4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        //注入服务(使用依赖注入的方式发现服务时必须先注入)
        ARouter.getInstance().inject(this);

    }

    public void test1(View view) {
        jump();
//        jumpWithForResult();
//        findService();
    }

    private void findService() {
        //发现服务的四种形式:

        // 1. (推荐)使用依赖注入的方式发现服务,通过注解标注字段,即可使用，无需主动获取
        // Autowired注解中标注name之后，将会使用byName的方式注入对应的字段，
        // 不设置name属性，会默认使用byType的方式发现服务(当同一接口有多个实现的时候，必须使用byName的方式发现服务)
        //1.
        helloService.sayHello("你好");
        //2.
        helloService2.sayHello("你好2");
        //3.通过类名主动发现服务:
        helloService3 = ARouter.getInstance().navigation(HelloService.class);
        helloService3.sayHello("你好3");
        //4.通过路由主动发现服务
        helloService4 = (HelloService) ARouter.getInstance().build("/service/hello")
                .navigation();
        helloService4.sayHello("你好4");
    }

    /**
     * 跳转
     */
    private void jump() {

        Bundle bundle = new Bundle();
        bundle.putString("bunldstr", "哈哈哈哈");

        ArrayList list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");

        ARouter.getInstance().build("/test/TestActivity2")
                //基本数据类型参数
                .withBoolean("is_true", true)
                .withInt("age", 100)
                .withString("name", "lisa")
                //容器类型参数
                .withObject("list", list)
                //对象类型参数,也可以传递List与Map
                .withObject("student", new Students("lili", "12312", "1"))
                //直接传递Bundle
                .withBundle("bundle", bundle)
                .navigation();


        //跳转到另一个模块的类中
//        ARouter.getInstance().build("/home/home1").navigation();

        //跳转到fragment
//        Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
//        Log.d("lxx", "fragment=: " + fragment);

        finish();
    }

    /**
     * 带回调跳转
     */
    private void jumpWithForResult() {
        ARouter.getInstance().build("/test/TestActivity2")
                .navigation(this, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            Log.d("lxx", "dada=: " + data.getStringExtra("data"));
        }
    }
}
