package com.example.arouterdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;


@Route(path = "/test/TestActivity2")
public class TestActivity2 extends AppCompatActivity {

    @Autowired(name = "is_true")
    public boolean isTrue;
    @Autowired/*(name = "age")*/
    int age;
    @Autowired(name = "name")
    String name;

    @Autowired(name = "list")
    public List list;
    @Autowired(name = "student")
    public Students student;

    @Autowired(name = "bundle")
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        //依赖注入(接收参数时如果没有先注入的话会产生异常)
        ARouter.getInstance().inject(this);

        Log.d("lxx", "接收到传递的参数: is_true= " + isTrue + "  age= " + age + "  name= " + name
                + "  list= " + list + "  student= " + student/*.name*/);

//        Log.d("lxx", "bundle: " + bundle.get("bunldstr"));
    }

    public void test2(View view) {
//        ARouter.getInstance().build("/app/MainActivity").navigation();

        //TestActivity3 添加了fragment
        ARouter.getInstance().build("/test/TestActivity3").navigation();

        //设置result回调
//        Intent intent = new Intent();
//        intent.putExtra("data", "我是回调结果");
//        setResult(RESULT_OK, intent);

        finish();
    }
}
