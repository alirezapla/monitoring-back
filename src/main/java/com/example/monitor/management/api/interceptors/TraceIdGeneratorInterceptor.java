package com.example.monitor.management.api.interceptors;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdGeneratorInterceptor extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        if (request.getDispatcherType() != DispatcherType.REQUEST) {
            chain.doFilter(request, response);
            return;
        }
//
//        String traceId = UUID.randomUUID().toString();
//        MetaDataThreadLocals.setTraceId(traceId);
//
//        var acceptLanguageLocales = request.getLocales();
//        if (acceptLanguageLocales.hasMoreElements()) {
//            MetaDataThreadLocals.setLocale(acceptLanguageLocales.nextElement());
//        }

        chain.doFilter(request, response);
        return;

    }

}