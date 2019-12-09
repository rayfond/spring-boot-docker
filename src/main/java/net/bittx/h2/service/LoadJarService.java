package net.bittx.h2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class LoadJarService {
    @Autowired
    private AnnotationConfigServletWebServerApplicationContext context;

    public void register(File file) {
        try {
            file = new File("D:\\test1.1.0.jar");
            //查找依赖的jar包，同级目录下的lib/
            List<URL> dependencyJar = findDependencyJar(file);


            URL[] urls = dependencyJar.toArray(new URL[dependencyJar.size()]);
            //新建classloader 核心
            URLClassLoader urlClassLoader = new URLClassLoader(urls, context.getClassLoader());

            //获取导入的jar的controller  service  dao 等类，并且创建BeanDefinition
            Set<BeanDefinition> beanDefinitions = getBeanDefinitions(urlClassLoader);

            beanDefinitions.forEach(item -> {
                //根据beanDefinition通过BeanFactory注册bean
                context.getDefaultListableBeanFactory().registerBeanDefinition(item.getBeanClassName(), item);
            });

            //修改BeanFactory的ClassLoader
            context.getDefaultListableBeanFactory().setBeanClassLoader(urlClassLoader);
            //获取requestMappingHandlerMapping，用来注册HandlerMapping
            RequestMappingHandlerMapping requestMappingHandlerMapping=context.getBean(RequestMappingHandlerMapping.class);
            beanDefinitions.forEach(item -> {

                String classname = item.getBeanClassName();
                try {
                    Class<?> c = Class.forName(classname, false, urlClassLoader);
                    Controller annotation = c.getAnnotation(Controller.class);
                    //获取该bean 真正的创建
                    Object proxy = context.getBean(item.getBeanClassName());
                    //如果此bean是Controller，则注册到RequestMappingHandlerMapping里面
                    if (annotation != null) {

                        Method getMappingForMethod = ReflectionUtils.findMethod(RequestMappingHandlerMapping.class, "getMappingForMethod", Method.class, Class.class);
                        getMappingForMethod.setAccessible(true);
                        try {
                            Method[] method_arr = c.getMethods();
                            for (Method m_d : method_arr) {
                                if (m_d.getAnnotation(RequestMapping.class) != null) {
                                    //创建RequestMappingInfo
                                    RequestMappingInfo mapping_info = (RequestMappingInfo) getMappingForMethod.invoke(requestMappingHandlerMapping, m_d, c);
                                    //注册
                                    requestMappingHandlerMapping.registerMapping(mapping_info, proxy, m_d);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static List<URL> findDependencyJar(File file) throws MalformedURLException {
        List<URL> list = new ArrayList<>();
        File parentFile = file.getParentFile();
        File libFile = new File(file.getParent() + File.separator + "lib");
        if (libFile.exists() && parentFile.isDirectory()) {
            for (File jar : libFile.listFiles()) {
                if (jar.isFile()
                        && jar.getName().toLowerCase().endsWith(".jar")
                ) {
                    list.add(jar.toURI().toURL());
                }
            }
        }
        list.add(file.toURI().toURL());
        return list;

    }


    public  Set<BeanDefinition> getBeanDefinitions(ClassLoader classLoader) throws Exception {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(classLoader);
        Resource[] resources = resourcePatternResolver.getResources("classpath*:com/xxx/**/*.**");

        MetadataReaderFactory metadata=new SimpleMetadataReaderFactory();
        for(Resource resource:resources) {
            MetadataReader metadataReader=metadata.getMetadataReader(resource);
            ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
            sbd.setResource(resource);
            sbd.setSource(resource);
            candidates.add(sbd);
        }
        for(BeanDefinition beanDefinition : candidates) {
            String classname=beanDefinition.getBeanClassName();
            Controller c=Class.forName(classname,false,classLoader).getAnnotation(Controller.class);
            Service s=Class.forName(classname,false,classLoader).getAnnotation(Service.class);
            Component component=Class.forName(classname,false,classLoader).getAnnotation(Component.class);
            if(c!=null ||s!=null ||component!=null)
                System.out.println(classname);
        }
        return candidates;
    }
}
