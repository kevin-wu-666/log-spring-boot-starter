####自定义Starter注意事项
<pre>步骤：创建SpringBoot项目，删除启动类、test文件夹、Pom文件中的Parent标签、
在resources文件夹中创建META-INF文件夹，并创建spring.factories文件和spring-configuration-metadata.json文件</pre>
@Configuration注解与@Component注解的都可以将该Bean交给Spring管理，
只是@Configuration注解可以识别@Bean注解<p>

spring.factories里面可以写多个自动配置类，使用','隔开，该配置的作用是
可以让Spring容器读取该文件，如果不填写则需要在项目启动类上加上@ComponentScan注解，
扫描该配置类所在的包<p>

项目中如果需要让Spring容器装载jar包里的类，则jar里的类需要加上@Component注解，
或者让@Configuration注解配合@Bean注解一起使用