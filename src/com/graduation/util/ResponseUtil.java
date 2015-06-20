package com.graduation.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: wenfe_000
 * Date: 5/12/14
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResponseUtil {
    public static void sendOperationResult(HttpServletResponse response, String result) {
        try {
            response.setContentType("html/text");
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException();
        }
    }
}
