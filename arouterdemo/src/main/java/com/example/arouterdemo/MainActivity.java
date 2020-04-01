package com.example.arouterdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view) {
        ARouter.getInstance()
                .build("/test/test1")
//                .navigation();
                .navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        Log.d("lxx", "onFound: ");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        Log.d("lxx", "onLost: ");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.d("lxx", "onArrival: ");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        String path = postcard.getPath();
                        Log.d("lxx", "onInterrupt: path= " + path);
                        Bundle bundle = postcard.getExtras();

                        ARouter.getInstance()
                                .build("/home/home1")
                                .greenChannel()
                                .navigation();
                        finish();
                    }
                });

        finish();
    }


}
