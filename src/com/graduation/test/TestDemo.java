package com.graduation.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.graduation.service.IUserService;

/**
 * Created with IntelliJ IDEA.
 * User: wenfe_000
 * Date: 5/15/14
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestDemo {
    ClassPathXmlApplicationContext ctx = null;
   // @Before
    public void config(){
       ctx =  new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml"});
    }

   // @Test
    public void test01(){
        IUserService userService = (IUserService)ctx.getBean("userService");
        userService.deleteUserById(50);
    }

    @Test
    public void testPatter(){
        //String str = "select * updatea from update,delete,drop,alter,insert....";
        String str = "select * from t_user where deletea dropa";
        str =  str.toUpperCase();
       // String re = "(update|delete|insert|drop|alter)";
       // String re = "(update\\b)|(delete\\b)|(insert)|(drop)|(alter)";
       // String re = "\\b[(update)|(delete)|(insert)|(drop)|(alter)\\b";
     //   System.out.println(str.indexOf("updatea"));
      //  System.out.println("update".matches(str));
     //  System.out.println(str.matches(re));
       /*  System.out.println(re.matches(str));
        String[] ss = str.split(re);
        System.out.println(ss.length);
        for(String s: ss){
            System.out.println(s);
        }*/

        String re ="(\\bupdate\\b)|(\\bdelete\\b)|(\\binsert\\b)|(\\bdrop\\b)|(\\balter\\b)";
        Pattern p = Pattern.compile(re,Pattern.CASE_INSENSITIVE );
       Matcher m =  p.matcher(str);
        System.out.println(m.find());

    }
}
