#学习自定义Starter总结：
###如何使用：
1、在你的项目中引入如下依赖
```
    <groupId>com.wjl</groupId>
    <artifactId>log-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
```
2、在你的配置文件中添加
```
    log.type=aspect #代表使用aspect执行日志打印
    log.type=interceptor #代表使用interceptor执行日志打印
```
******
###自定义Starter注意事项
<pre>步骤：创建SpringBoot项目，删除启动类、test文件夹、Pom文件中的Parent标签、
在resources文件夹中创建META-INF文件夹，并创建spring.factories文件和spring-configuration-metadata.json文件</pre>
@Configuration注解与@Component注解的都可以将该Bean交给Spring管理，
只是@Configuration注解可以识别@Bean注解<p>

spring.factories里面可以写多个自动配置类，使用','隔开，该配置的作用是
可以让Spring容器读取该文件，如果不填写则需要在项目启动类上加上@ComponentScan注解，
扫描该配置类所在的包<p>

你的项目中如果需要让Spring容器装载jar包里的类，则jar里的类需要加上@Component注解，
或者让@Configuration注解配合@Bean注解一起使用<p>

###强烈建议项目中添加拦截器使用 实现WebMvcConfigure接口，而非 继承WebMvcConfigurationSupport类，
因为继承后者会导致springboot自动配置失效，更多区别请自行百度