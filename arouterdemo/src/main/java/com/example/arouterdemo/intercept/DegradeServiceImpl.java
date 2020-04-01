package com.example.arouterdemo.intercept;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;

// 实现DegradeService接口，并加上一个Path内容任意的注解即可
@Route(path = "/xxx/xxx")
public class DegradeServiceImpl implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        // do something.
        String path = postcard.getPath();
        Log.d("lxx", "DegradeServiceImpl: onLost() path= " + path);

        //重新跳转
        // String str = "";
        // ARouter.getInstance().build(str).navigation();
    }

    @Override
    public void init(Context context) {
        Log.d("lxx", "全局降级拦截");
    }
}