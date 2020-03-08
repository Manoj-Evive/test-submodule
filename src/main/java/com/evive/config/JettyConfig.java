package com.evive.config;//package com.evive.config;
//
//import java.util.Arrays;
//import java.util.TimeZone;
//import org.eclipse.jetty.server.AsyncNCSARequestLog;
//import org.eclipse.jetty.server.ForwardedRequestCustomizer;
//import org.eclipse.jetty.server.HttpConnectionFactory;
//import org.eclipse.jetty.server.handler.HandlerCollection;
//import org.eclipse.jetty.server.handler.RequestLogHandler;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
//import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
//import org.springframework.boot.web.servlet.ErrorPage;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//
///**
// * @author Wasif Aleem (wasif@evivehealth.com).
// */
//@Configuration
//public class JettyConfig implements EmbeddedServletContainerCustomizer {
//
//  @Override
//  public void customize(ConfigurableEmbeddedServletContainer container) {
//    container.addErrorPages(
//        new ErrorPage(HttpStatus.NOT_FOUND, "/error404"),
//        new ErrorPage(Throwable.class, "/error"),
//        new ErrorPage(HttpStatus.BAD_REQUEST, "/error"),
//        new ErrorPage(HttpStatus.UNAUTHORIZED, "/error"),
//        new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error"),
//        new ErrorPage(HttpStatus.FORBIDDEN, "/error"),
//        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error"),
//        new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "/error")
//    );
//
//    // checks whether the container is Jetty
//    if (container instanceof JettyEmbeddedServletContainerFactory) {
//      ((JettyEmbeddedServletContainerFactory) container)
//          .addServerCustomizers(jettyServerCustomizer());
//    }
//  }
//
//  @Bean
//  public JettyServerCustomizer jettyServerCustomizer() {
//    return server -> {
//      RequestLogHandler requestLogHandler = new RequestLogHandler();
//      AsyncNCSARequestLog requestLog = new AsyncNCSARequestLog("logs/testingAutomation-access.log");
//      requestLog.setRetainDays(Integer.MAX_VALUE);
//      requestLog.setLogTimeZone(TimeZone.getDefault().getID());
//      requestLog.setAppend(true);
//      requestLog.setLogServer(true);
//      requestLog.setLogCookies(true);
//      requestLog.setLogLatency(true);
//      requestLog.setExtended(true);
//      requestLogHandler.setRequestLog(requestLog);
//
//      HandlerCollection handlers = new HandlerCollection();
//      handlers.addHandler(server.getHandler());
//
//      requestLogHandler.setHandler(handlers);
//
//      server.setHandler(requestLogHandler);
//
//      Arrays.stream(server.getConnectors())
//          .flatMap(connector -> connector.getConnectionFactories().stream())
//          .filter(connectionFactory -> connectionFactory instanceof HttpConnectionFactory)
//          .findFirst()
//          .map(connectionFactory -> ((HttpConnectionFactory) connectionFactory)
//              .getHttpConfiguration())
//          .ifPresent(httpConfiguration -> httpConfiguration
//              .addCustomizer(new ForwardedRequestCustomizer()));
//    };
//  }
//}
