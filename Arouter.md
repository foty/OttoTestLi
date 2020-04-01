话不多说，干就wans了   
添加依赖是第一步，在build.gradle文件中:
```
android {
    ...
    defaultConfig {
    ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
    }
}

//添加Arouter需要的依赖
dependencies {
    ...
    implementation 'com.alibaba:arouter-api:1.5.0'
    annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'
}    
```
##### 一、跳转:
###### 1.1基本跳转(模块内):
实现基本的跳转功能只需要两个步骤：
* 配置跳转path (地址最少为两级目录，格式:/xxx/xxx)
* 调用api跳转

```java
//配置此activity的路由地址，最少两级目录，否则会出现问题。格式为: /xxx/xxx
@Route(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        //使用
        ARouter.getInstance().build("/test/test1").navigation();
        finish();
    }
}
```
###### 1.2 带参跳转
使用ARouter跳转有时候会需要传递一些参数，ARouter同样提供了API来方便使用,在build()后面可以调用with系列API来设置传递参数:
* 字符串型: withString("", "")
* 布尔型参数: withBoolean("", true/false)
* 整型参数: withInt("", 1)  
* 对象型参数: withObject("", Object)(包括容器(map、list))
* 传递Bundle: withBundle("",bundle)
* 等等...

以上几种参数类型已经满足大部分需求，更多的api在以后的需要中在说明。
```
Bundle bundle = new Bundle();
bundle.putString("bunldstr","哈哈哈哈");

ArrayList list = new ArrayList();
list.add("1");
list.add("2");
list.add("3");
ARouter.getInstance().build("/test/TestActivity2")
       //基本数据类型参数
       .withBoolean("is_true", false)
       .withInt("age", 100)         
       .withString("name", "lisa")
       //容器类型参数
       .withObject("list", list)
       //对象类型参数,也可以传递List与Map
       .withObject("student", new Students("lili", "12312", "1"))
       //直接传递一个Bundle
       .withBundle("bundle",bundle)
       .navigation();
```
在要跳转的目标类接收参数:
```java
@Route(path = "/test/TestActivity2")
public class TestActivity2 extends AppCompatActivity {

    @Autowired(name = "is_true")
    public boolean isTrue;
    @Autowired/*(name = "age")*/ //这个后面的name不是必须的，如果这个类型是唯一的可以考虑省略。一般还是先加上去，避免无法找到。
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
        //必须要注入(接收参数时如果没有先注入的话会产生异常)
        ARouter.getInstance().inject(this);
    }
}
```
在实现这功能的时候遇到了一些问题，问题具体原因以及处理方案在文末最后介绍，赶时间的朋友可以直接拉到最后面。

###### 1.3 有回调跳转
当需要目标activity携带内容返回到原activity时候，即使用startActivityForResult时，使用这个API:
```
/**
  * Navigation to the route with path in postcard.
  *
  * @param mContext    Activity and so on.
  * @param requestCode startActivityForResult's param
  */
  public void navigation(Activity mContext, int requestCode) {
        navigation(mContext, requestCode, null);
  }
```
代码示例:
在TestActivity1类中
```
ARouter.getInstance().build("/test/TestActivity2")
                .navigation(this, 100);
```
在TestActivity2类适当位置
```
//设置result回调
 Intent intent = new Intent();
 intent.putExtra("data", "我是回调结果");
 setResult(RESULT_OK, intent);
 finish();
```
###### 1.4 fragment使用。
主要是获取fragment的实例，与activity的使用相同。
```
Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
```

###### 1.5 跨模块跳转
(跨模块跳转一般在项目较庞大或业务较复杂的地方，按功能或者业务区分模块进行开发，项目结构会更加清晰。实现跨模块跳转与实现同模块跳转所需要的
步骤内容是一样的，区别就在于一个是模块A的activity跳转到模块B的activity，另一个是同一个模块下的activity相互跳转。具体步骤参考上面的同模块跳转。

(例子gif图)

##### 二、拦截器:
拦截器使用:
1.只需要自己定义一个类实现IInterceptor接口放在项目中即可，不需要添加任何调用、初始化、配置操作跟传递自定义实体类参数时定义一个类实现SerializationService接口放在项目中就可以。
2.在使用.navigation API的时候传入NavigationCallback对象，最终的处理结果将会回调到这里。经典运用: 是在跳转过程中处理登陆事件(比如token检查)，这样就不需要在目标页重复做登陆检查
```java
@Interceptor(priority = 8, name = "跳转拦截器")
public class JumpInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (false) {
            // 处理完成，交还控制权
            Log.d("tag", "没问题，不需要拦截");
            callback.onContinue(postcard);
        } else {
            // 觉得有问题，中断路由流程
//            callback.onInterrupt(new RuntimeException("我觉得有点异常"));
            Log.d("tag", "感觉不对，拦下来再说");
            callback.onInterrupt(null);
        }
        // 以上两种至少需要调用其中一种，否则不会继续路由(跳转)
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        Log.d("tag", "JumpInterceptor: 拦截器初始化了");
    }
}
```
拦截器使用，示例代码：
```
ARouter.getInstance()
       .build("/test/test1")
       .navigation(this, new NavigationCallback() {
             @Override
             public void onFound(Postcard postcard) {
                   Log.d("tag", "onFound: 发现了路由");
             }

             @Override
             public void onLost(Postcard postcard) {
                   Log.d("tag", "onLost: 没有找到路由");
             }

             @Override
             public void onArrival(Postcard postcard) {
                   Log.d("tag", "onArrival: 成功跳转");
             }

             @Override
             public void onInterrupt(Postcard postcard) {
                   Log.d("tag", "onArrival: 拦截成功");
                      // 处理其他逻辑
                   }
             });
```
以上代码中已经添加了必要的解释说明，这里不另开篇幅再次介绍。进入下一个环节。     

自定义全局降级策略:     
使用方式与拦截器一样，定义一个类实现DegradeService 接口即可。记得配置路由地址，地址没有要求限制。代码示例
```java
// 实现DegradeService接口，并加上一个Path内容任意的注解即可
@Route(path = "/app/degrade")
public class DegradeServiceImpl implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        // do something.
        String path = postcard.getPath();
        Log.d("tag", "DegradeServiceImpl: onLost() path= "+path);
        
        //重新跳转
        // String str = "";
        // ARouter.getInstance().build(str).navigation();
    }

    @Override
    public void init(Context context) {
        Log.d("tag", "全局降级拦截");
    }
}
```
自定义全局降级策略跟拦截器有相同之处，但貌似不能自己通过逻辑判断是否拦截，较为被动。当发现使用了错误的跳转路由路径时，会触发全局降级策略，回调onLost()方法。通过这一点，可以针对跳转path拥有更多的操作空间。比如为path添加一些代表特殊含义的标识符
，此时ARouter定会找不到这个path而触发降级策略，而在这里就可以统一处理这些标识符,然后重新跳转到目标页面中。

##### 三、服务的运用
服务的运用看起来非常像MVP的P层与V层的关系。分为暴露(创建)服务、发现(应用)服务。
暴露服务:
```java
// 声明接口,其他组件通过接口来调用服务
public interface HelloService extends IProvider {
    void sayHello(String name);
}

// 实现接口
@Route(path = "/service/hello", name = "测试服务")
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String name) {
         Log.d("tag", "服务初始化");
    }

    @Override
    public void init(Context context) { 
        //同一个服务只会被初始化一次
        Log.d("tag", "服务初始化");
    }
}
```
发现服务:   
发现服务有4种方式，总的说可以分两种一是依赖注入，而是依赖查找。
* 依赖注入，使用@Autowired，不添加具体的服务路由地址；但是当一个接口有多个实现时就必须添加地址区分；
* 依赖注入，后面添加路由地址；
* 依赖查找，通过服务类名主动发现服务；
* 依赖查找，通过地址主动发现服务；
代码示例：
```java
@Route(path = "/test/test1")
public class TestActivity1 extends AppCompatActivity {
    
    @Autowired
    HelloService helloService;
    @Autowired(name = "/service/hello")
    HelloService helloService2;
    HelloService helloService3;
    HelloService helloService4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        //注入服务(使用依赖注入方式时必须要先注入)
        ARouter.getInstance().inject(this);
    }

    public void test1(View view) {
        //发现服务的四种形式:

        //(推荐)使用依赖注入的方式发现服务,通过注解标注字段,即可使用.无需主动获取 Autowired
        // 注解中标注name之后，将会使用byName的方式注入对应的字段，
        // 不设置name属性，会默认使用byType的方式发现服务(当同一接口有多个实现的时候，必须使用byName的方式发现服务)
        //1.
        helloService.sayHello("你好");
        //2.
         helloService2.sayHello("你好2");
        //3.通过类名主动发现服务(byName):
        helloService3 = ARouter.getInstance().navigation(HelloService.class);
        helloService3.sayHello("你好3");
        //4.通过路由主动发现服务(byType)
        helloService4 = (HelloService) ARouter.getInstance().build("/service/hello")
                .navigation();
        helloService4.sayHello("你好4");
    }
}
```
##### 实践过程碰到的问题:
1.编译异常 Compiler >>> No module name   
关键异常信息:
``Compiler >>> No module name``   
处理方案：网上寻找解决办法 <https://blog.csdn.net/a34927341/article/details/87690451> 。产生原因是第一种。于是更新为使用最新的版本号，成功运行。

2.传递参数问题(ervice.SerializationService.object2Json):
关键异常信息:
>Caused by: java.lang.reflect.InvocationTargetException
        at java.lang.reflect.Method.invoke(Native Method)
        at androidx.appcompat.app.AppCompatViewInflater$DeclaredOnClickListener.onClick(AppCompatViewInflater.java:397)    
        at java.lang.reflect.Method.invoke(Native Method) 
        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:789) 
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:679) 
     Caused by: java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.String com.alibaba.android.arouter.facade.service.SerializationService.object2Json(java.lang.Object)' on a null object reference
        at com.alibaba.android.arouter.facade.Postcard.withObject(Postcard.java:230)
        at com.example.arouterdemo.TestActivity1.test1(TestActivity1.java:43)

解决方案: 声明一个类实现SerializationService接口放置到项目中。

3.传递参数抛出空指针异常 Caused by: java.lang.NullPointerException。
处理方案: 在接收参数的activity初始化的时候添加以下代码:
```html
ARouter.getInstance().inject(this);
```
4.接收参数限定符使用的问题，此异常会导致项目无法成功通过编译。
关键异常信息：
>注: ARouter::Compiler >>> Found autowired field, start... <<<
错误: ARouter::Compiler An exception is encountered, [The inject fields CAN NOT BE 'private'!!! please check field [name] in class [com.example.arouterdemo.TestActivity2]]
      at com.alibaba.android.arouter.compiler.processor.AutowiredProcessor.categories(AutowiredProcessor.java:276)
      at com.alibaba.android.arouter.compiler.processor.AutowiredProcessor.process(AutowiredProcessor.java:95)

处理方案: 接收参数不使用private修饰，要么使用public要么不使用限定符。

5.使用List，Map等接收参数导致空指针异常。
处理犯案：接收时候不用实现类(ArrayList,HashMap)，而是用超类(List，Map)。如:
```
@Autowired(name = "list")
List list;
```

6.自定义实体类并且声明了新的构造方法导致空指针。
处理方案：自定义实体类在声明新的构造方法同时保留默认的构造方法。