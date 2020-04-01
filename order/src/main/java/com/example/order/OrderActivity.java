package com.example.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Create by lxx
 * Date : 2020/1/13 9:31
 * Use by
 */

@Route(path = "/order/OrderActivity")
public class OrderActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    public void orderClick(View view) {
        ARouter.getInstance().build("/app/MainActivity").navigation();
        finish();
    }
}
