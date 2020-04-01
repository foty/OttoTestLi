package com.example.ottotestli;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hook();
        Toast.makeText(this, "哈哈哈哈", Toast.LENGTH_SHORT).show();

    }

    private void hook() {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = null;
        try {
            ctClass = classPool.get("android.widget.Toast");
            CtMethod ctMethod = ctClass.getDeclaredMethod("makeText");

            ctMethod.setBody(" return makeText(context, null, \"这个内容被修改了\", duration);");

            ctClass.toClass();

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
