package com.example.ottotestli.mvvm;

import androidx.databinding.ObservableField;

/**
 * Create by lxx
 * Date : 2019/12/19 11:07
 * Use by
 */
public class ObservableLoginBean2 {

    /**
     * 继承于 Observable 类相对来说限制有点高，且也需要进行 notify 操作，因此为了简单起见可以选择使用 ObservableField。
     * ObservableField 可以理解为官方对 BaseObservable 中字段的注解和刷新等操作的封装，
     * 官方原生提供了对基本数据类型的封装，例如 ObservableBoolean、ObservableByte、ObservableChar、ObservableShort、
     * ObservableInt、ObservableLong、ObservableFloat、ObservableDouble 以及 ObservableParcelable ，
     * 也可通过 ObservableField 泛型来申明其他类型。
     */
    private ObservableField<String> hobby;
    private ObservableField<String> sex;

    private ObservableField<String> content;

    public ObservableField<String> getHobby() {
        return hobby;
    }

    public void setHobby(ObservableField<String> hobby) {
        this.hobby = hobby;
    }

    public ObservableField<String> getSex() {
        return sex;
    }

    public void setSex(ObservableField<String> sex) {
        this.sex = sex;
    }

    public ObservableField<String> getContent() {
        return content;
    }

    public void setContent(ObservableField<String> content) {
        this.content = content;
    }
}
