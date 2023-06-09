package etu2029.framework.servlet;

import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import etu2029.framework.Mapping;
import java.net.URL;
import java.io.*;
import java.lang.reflect.*;
import etu2029.framework.annotation.*;


public class Frontservlet extends HttpServlet{
    
    HashMap<String,Mapping> MappingUrls;
    Map<String, Mapping> mappingUrls = new HashMap<>();
    public void init() throws ServletException{
        try {
            super.init();
            // String packages = String.valueOf(getInitParameter("packages"));
            String packages = "etu2029.model";
            this.MappingUrls=new HashMap<>();
            String path = packages.replaceAll("[.]","\\\\");
            URL packageUrl=Thread.currentThread().getContextClassLoader().getResource(path);
            File packDir =new File(packageUrl.toURI());
            File[] inside=packDir.listFiles(file->file.getName().endsWith(".class"));
            List<Class> lists = new ArrayList<>();
            for(File f : inside){
                    String c =packages+"."+f.getName().substring(0,f.getName().lastIndexOf("."));
                    lists.add(Class.forName(c));
            }
            for(Class c:lists){
                Method[] methods = c.getDeclaredMethods();
                for(Method m : methods){
                    if(m.isAnnotationPresent(Annotation.class)){
                        Annotation url = m.getAnnotation(Annotation.class);
                        if(!url.Url().isEmpty() && url.Url() !=null){
                            Mapping map = new Mapping(c.getName() , m.getName());
                            this.MappingUrls.put(url.Url(),map);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        out.println(request.getHttpServletMapping().getMatchValue());
        for (Map.Entry<String, Mapping> entry : MappingUrls.entrySet()) {
            out.println(entry.getKey() + " " + entry.getValue().getClassName() + " " + entry.getValue().getMethod());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        processRequest(request, response);
    }

}
