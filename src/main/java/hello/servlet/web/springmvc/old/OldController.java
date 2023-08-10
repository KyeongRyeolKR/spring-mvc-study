package hello.servlet.web.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 핸들러 매핑 : BeanNameUrlHandlerMapping
 * 핸들러 어댑터 : SimpleControllerHandlerAdapter
 */
@Component("/springmvc/old-controller") // 핸들러 매핑을 위한 스프링 빈 이름 지정(url 형식)
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form"); // 논리 이름
        // 위처럼 논리 이름만 적어도 application.properties에 설정함으로써
        // 물리 이름의 prefix, suffix가 자동으로 설정된다.
    }
}
