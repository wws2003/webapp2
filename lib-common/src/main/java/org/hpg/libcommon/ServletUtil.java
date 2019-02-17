/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.libcommon;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 * Util for servlet operations
 *
 * Created on : Feb 17, 2019, 12:57:32 AM
 *
 * @author wws2003
 */
public class ServletUtil {

    /**
     * Set AJAX result to servlet response
     *
     * @param response
     * @param result
     * @throws Exception
     */
    public static void setAjaxResult(HttpServletResponse response, Object result) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objMapper = new ObjectMapper();

        try (PrintWriter writer = response.getWriter()) {
            writer.write(objMapper.writeValueAsString(result));
            writer.flush();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
