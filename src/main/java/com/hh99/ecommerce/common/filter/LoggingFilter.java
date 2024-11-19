package com.hh99.ecommerce.common.filter;

import static java.util.Collections.enumeration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
public class LoggingFilter implements Filter {

    private static final String NO_CONTENT = "No Content";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Initializing Filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        try {
            loggingRequest(requestWrapper);
            chain.doFilter(requestWrapper, responseWrapper);
        }finally {
            loggingResponse(responseWrapper);

            //응답 내용을 클라이언트로 전달해야 한다. 전달 이후에는 본문을 확인할 수 없다는 것이 특징.
            responseWrapper.copyBodyToResponse();

            //응답 헤더는 전달 이후에 확인 가능하다.
            loggingResponseHeaders(responseWrapper);

        }


    }

    @Override
    public void destroy() {
        log.info("logging filter destroyed");
    }

    private static void loggingRequest(ContentCachingRequestWrapper request) {
        log.info("==== Request Start ====");
        log.info("Request URI : {}", request.getRequestURI());
        log.info("Request Method : {}", request.getMethod());
        log.info("Request Headers : {}", getHeaders(request));
        log.info("Request Parameters : {}", getParameters(request));
        log.info("Request Body : {}", getRequestBody(request));
        log.info("Request Content Type : {}", request.getContentType());
        log.info("==== Request End ====");
    }

    private static void loggingResponse(ContentCachingResponseWrapper response) {
        log.info("==== Response Start ====");
        log.info("Response Status : {}", response.getStatus());
        log.info("Response Body : {}", getResponseBody(response));
    }

    private static void loggingResponseHeaders(ContentCachingResponseWrapper response) {
        log.info("Response Headers : {}", getHeaders(response));
        log.info("==== Response End ====");
    }

    private static String getHeaders(HttpServletRequest request) {
        return getHeadersAsString(request.getHeaderNames(), request::getHeader);
    }

    private static String getHeaders(HttpServletResponse response) {
        return getHeadersAsString(
                enumeration(response.getHeaderNames()),
                name -> String.join(", ", response.getHeaders(name))
        );
    }

    private static String getHeadersAsString(Enumeration<String> headerNames,
                                             UnaryOperator<String> headerResolver) {
        StringBuilder headers = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.append(name).append(": ").append(headerResolver.apply(name)).append(", ");
        }
        // 마지막 콤마와 공백 제거
        if (headers.length() > 2) {
            headers.setLength(headers.length() - 2);
        }
        return headers.toString();
    }

    // 요청 파라미터 가져오기
    private static String getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.isEmpty()) {
            return NO_CONTENT;
        }
        return parameterMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
                .collect(Collectors.joining(", "));
    }

    // 요청 바디 가져오기
    private static String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length == 0) {
            return NO_CONTENT;
        }
        return new String(content, StandardCharsets.UTF_8);
    }

    // 응답 바디 가져오기
    private static String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length == 0) {
            return NO_CONTENT;
        }
        return new String(content, StandardCharsets.UTF_8);
    }
}
