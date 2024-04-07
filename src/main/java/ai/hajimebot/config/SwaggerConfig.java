package ai.hajimebot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Value(value = "${swagger.enabled}")
    private boolean swaggerEnabled;

    @Value(value = "${swagger.title}")
    private String swaggerTitle;

    @Value(value = "${swagger.description}")
    private String swaggerDescription;

    @Value(value = "${swagger.version}")
    private String swaggerVersion;

    @Value(value = "${swagger.package}")
    private String swaggerPackage;

    @Value(value = "${swagger.contact.name}")
    private String swaggerContactName;

    @Value(value = "${swagger.contact.url}")
    private String swaggerContactUrl;

    @Value(value = "${swagger.contact.email}")
    private String swaggerContactEmail;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(swaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerPackage))
                .paths(PathSelectors.any())
                .build();
    }

    //DETAIL_FUNCTION_TO_BUILD_API_DOCUMENTATION
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //pageTitle
                .title(swaggerTitle)
                //CreatedBy
                .contact(new Contact(swaggerContactName, swaggerContactUrl, swaggerContactEmail))
                //versionNumber
                .version(swaggerVersion)
                //description
                .description(swaggerDescription)
                .build();
    }
}
