package Mudar.backend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
            SpringApplication.run(BackendApplication.class, args);
    }
        
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        // Enable SSL Trafic
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//
//        // Add HTTP to HTTPS redirect
////        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//
//        return tomcat;
//    }

//    /*
//    We need to redirect from HTTP to HTTPS. Without SSL, this application used
//    port 8082. With SSL it will use port 8443. So, any request for 8082 needs to be
//    redirected to HTTPS on 8443.
//     */
//    private Connector httpToHttpsRedirectConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(8081);
//        return connector;
//    }
}

