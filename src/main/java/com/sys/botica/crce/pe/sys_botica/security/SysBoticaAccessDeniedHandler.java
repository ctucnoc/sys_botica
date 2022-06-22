package com.sys.botica.crce.pe.sys_botica.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@Component
public class SysBoticaAccessDeniedHandler implements AccessDeniedHandler  {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        SysBoticaError wallexError = new SysBoticaError(HttpStatus.FORBIDDEN,
                SysBoticaConstant.PREFIX_CLIENT_ERROR);
        wallexError.setMessage("Forbidden");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        OutputStream outputStream=response.getOutputStream();
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValue(outputStream, wallexError);
        outputStream.flush();
    }

}
