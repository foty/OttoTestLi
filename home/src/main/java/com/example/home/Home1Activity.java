package com.example.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Create by lxx
 * Date : 2020/1/10 15:10
 * Use by
 */

@Route(path = "/home/home1")
public class Home1Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
    }

    public void homeClick(View view) {
//        ARouter.getInstance().build("/app/MainActivity").navigation();
//        ARouter.getInstance().build("/order/OrderActivity").navigation();
//        finish();

        Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
        Log.d("lxx", "home fragment=: " + fragment);
    }
}
