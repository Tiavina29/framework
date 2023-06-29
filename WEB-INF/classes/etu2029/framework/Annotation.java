package etu2029.framework;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author aris
 */

public class Annotation
{
    
    public static List<Class<?>> getClassesWithAnnotation(Class<? extends java.lang.annotation.Annotation> annotation) {
        ArrayList<String> list = new ArrayList<>();
        list.add("controller");
        
        List<Class<?>> classes = new ArrayList<>();
        try {
            for (String pack : list) {
                for (Class<?> cls : getClassesInPackage(pack)) {
                    if (cls.isAnnotationPresent(annotation)) {
                        classes.add(cls);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
    
    private static List<Class<?>> getClassesInPackage(String packageName) throws ClassNotFoundException, URISyntaxException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
//        URL packageUrl = classLoader.getResource(path);
//        File a = new File(packageUrl.toURI());
//        File[] i = a.listFiles(file->file.getName().endsWith(".class"));
//        for( File f : i ){
//               String c = packageName+"."+f.getName().substring(0,f.getName().lastIndexOf("."));
//               classes.add( Class.forName(c) );
//        }
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                classes.addAll(getClassesInDirectory(new File(resource.toURI()), packageName));
            }
        }
        return classes;
    }

    private static List<Class<?>> getClassesInDirectory(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    classes.addAll(getClassesInDirectory(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(className));
                }
            }
        }
        return classes;
    }
}