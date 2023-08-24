package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

// 에러 페이지 커스텀
@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // response.sendError(404) 에러 페이지
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        // response.sendError(500) 에러 페이지
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        // RuntimeException 또는 자식 타입 예외 에러 페이지
        // 런타임 예외도 서버 오류라 생각해서 500 에러 페이지로 처리
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        // 에러 페이지 등록
        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}
