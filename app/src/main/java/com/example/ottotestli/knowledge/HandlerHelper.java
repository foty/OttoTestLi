package com.example.ottotestli.knowledge;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Create by lxx
 * Date : 2019/12/11 11:38l
 * Use by 优雅的使用 handler
 * <p>
 * 使用handler会导致内存泄漏.这是一个比较优雅的使用handler的的方法,还能避免内存泄漏.
 * <p>
 * 内存泄漏发生的主要原因: 当前对象需要被销毁时,由其他对象(包含外部对象或者非静态内部类)持有当前对象的引用,
 * 导致当前对象释放不了.也可以抽象的说成是生命周期长的对象持有生命周期短的对象的引用。
 * <p>
 * 解决办法:
 * <p>
 * 定义为静态内部类,对外部 activity 的引用使用弱引用的方式;
 * 在activity里的onDestroy 回调方法中,调用 handler的removeCallbacksAndMessages（null）方法,清除消息队列中message;
 * 单独定义成一个类，不作为非静态内部类存在;
 */
public class HandlerHelper extends Handler {
    private WeakReference<Activity> mWr;

    public HandlerHelper(WeakReference<Activity> mWr) {
        this.mWr = mWr;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);

        //取出弱引用中的对象
        Activity activity = mWr.get();
    }
}
