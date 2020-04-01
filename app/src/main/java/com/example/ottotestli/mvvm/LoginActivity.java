package com.example.ottotestli.mvvm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.example.ottotestli.R;
import com.example.ottotestli.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Create by lxx
 * Date : 2019/12/18 11:17
 * Use by
 */
public class LoginActivity extends Activity {

    private ActivityLoginBinding binding;
    private ObservableLoginBean oBean;
    private ObservableLoginBean2 loginBean2;
    private ObservableField<String> hobby;
    private ObservableField<String> sex;
    private ObservableField<String> content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginBean bean = new LoginBean();
        bean.setName("哈哈哈哈");
        bean.setAge("18");
        binding.setBean(bean);

        oBean = new ObservableLoginBean();
        oBean.setPhone("手机号码是: 10086");
        oBean.setDetails("只是详情");
        oBean.setAddress("这是地址内容");
        binding.setOBean(oBean);

        loginBean2 = new ObservableLoginBean2();

        hobby = new ObservableField<>();
        hobby.set("rap,打篮球");
        sex = new ObservableField<>();
        sex.set("男");
        content = new ObservableField<String>();
        content.set("");

        loginBean2.setHobby(hobby);
        loginBean2.setSex(sex);
        loginBean2.setContent(content);
        binding.setObean2(loginBean2);


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("嘤嘤嘤");
                emitter.onComplete();

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        List<String> list = new ArrayList<>();
        Observable.just("1","2","3")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("lxx", "s=: "+s);
                    }
                });


        Flowable.just(1,2,3,4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });

    }

    public void change(View view) {
        Toast.makeText(this, "绑定数据更新", Toast.LENGTH_SHORT).show();
        oBean.setPhone("手机号码:13800138000"); //如果没有设置详情，只设置手机号码，后面的地址不会更新到
        oBean.setDetails("这是详情二号"); //设置了详情，后面的地址也会更新到.
        oBean.setAddress("广东省天河区xxxxx");
    }

    public void change2(View view) {
        hobby.set("唱,跳,rap,打篮球");
        sex.set("女");
    }

    public void change3(View view) {
        Log.d("mvvm", "change3: 编辑输入的字符串是: " + loginBean2.getContent().get());
    }
}
