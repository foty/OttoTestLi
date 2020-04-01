package com.example.ottotestli.knowledge;

/**
 * Create by lxx
 * Date : 2019/12/12 11:34
 * Use by 关于风味纬度配置，针对同一份代码不同渠道包的版本差异化而配置的.
 * <p>
 * flavorDimensions 就是配置风味纬度的key，后面的是可使用value
 *
 * 有时候我们同一份代码需要打出不同的渠道包,而且每个渠道包都会有一点点的不一样,比如app名称,logo等等.这时候通常的做法
 * 是设置 productFlavors来实现,如:
 *       productFlavors {
 *
 *         //A公司
 *         companyA {
 * //            dimension "color"
 *         }
 *         //B公司
 *         companyB {
 * //            dimension "color"
 *         }
 *         ......
 *     }
 * 此时在一些高版本的AS下就会抛出错误:
 * <p>
 * ERROR: All flavors must now belong to a named flavor dimension.
 * Learn more at https://d.android.com/r/tools/flavorDimensions-missing-error-message.html
 * <p>
 * 这是说我们的不同渠道包要属于同一个命名纬度。
 * 解决方案就是配置flavorDimensions.
 * <p>
 *     defaultConfig {
 *         ......
 *         flavorDimensions  "default"
 *     }
 * 可能不同的解决方案设置flavorDimensions 的值不同,有的是default,有的是versionCode或者其他.但是都可以达到目的.
 * 风味纬度可以设置多个,值后面使用,分隔开如:  flavorDimensions  "default","default2",... 但是通常情况下应用app
 * 都使用一个纬度足够.当编译时,它会根据所配置的纬度去寻找,编译对应渠道的配置.
 * 比如这样:
 * <P>
 *      flavorDimensions  "default","default2"
 *
 *      productFlavors {
 *
 *           companyA {
 *               dimension "default"
 *               ...
 *           }
 *
 *           companyB {
 *               dimension "default"
 *               ...
 *           }
 *
 *           companyC {
 *               dimension "default2"
 *               ...
 *           }
 *
 *           companyD {
 *               dimension "default2"
 *               ...
 *           }
 *           ......
 *       }
 * <P>
 * 就会先去找属于default这个纬度的渠道,读取里面的配置,然后在寻找属于default2这个纬度的渠道读取相应的配置.如上就会产生AC,AD,BC,BD
 * 四种渠道包.
 * 如果是要打差异性渠道包(只有一点点属性不一样,如app名称，logo等)就可以在清单文件中对应的属性使用占位符,在渠道配置中替换掉.
 *  example
 * <p>
 *      <application
 *         android:allowBackup="true"
 *         android:icon="${LOGO}"
 *         android:label="${NAME}"
 *         android:supportsRtl="true"
 *         android:theme="@style/AppTheme">
 *         ......
 *     </application>
 * <p>
 * in the build.gradle
 * <p>
 *     productFlavors {
 *         companyA {
 *             dimension "company"
 *             manifestPlaceholders = [NAME: "你的app名称",
 *                                     LOGO: "你的logo资源文件"]
 *             buildConfigField "String", "COMPANY", "\"companyA\""
 *         }
 *         ...
 *      }
 * <p>
 */
public class FlavorsDimension {
}
