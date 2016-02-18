package com.mode.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This is the equivalent to the web.xml for servlet container.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
        implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        initSecurity(servletContext);

        super.onStartup(servletContext);
    }

    /* Add the Spring Security filter. */
    protected void initSecurity(ServletContext servletContext) {
        servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy())
                .addMappingForUrlPatterns(null, false, "/*");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{MybatisConfiguration.class, SecurityConfiguration.class,
                ServiceConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        MultipartFilter mpf = new MultipartFilter();
        mpf.setMultipartResolverBeanName("multipartResolver");
        return new Filter[]{characterEncodingFilter, new HiddenHttpMethodFilter(), mpf};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/", 50000000,
                50000000, 50000000);

        registration.setMultipartConfig(multipartConfigElement);
    }
}