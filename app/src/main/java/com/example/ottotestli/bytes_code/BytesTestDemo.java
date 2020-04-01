package com.example.ottotestli.bytes_code;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * Create by lxx
 * Date : 2020/1/3 13:46
 * Use by
 */
public class BytesTestDemo {

    public static void main(String[] args) {
        int i1 = 10;
        int i2 = 30;
        System.out.println(i1 + i2);

        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = null;
        try {

            ctClass = classPool.get("com.example.ottotestli.bytes_code.Student");
            CtMethod ctMethod = ctClass.getDeclaredMethod("say");
            ctMethod.setBody("System.out.println(\"this method is changed dynamically!\");");
            //会加载此类，如果此类在之前已经被加载过，则会报一个duplicate load的错误，表示不能重复加载一个类。
            // 所以，修改方法的实现必须在修改的类加载之前进行
            ctClass.toClass();

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        Student student = new Student();
        student.say();

    }
}
