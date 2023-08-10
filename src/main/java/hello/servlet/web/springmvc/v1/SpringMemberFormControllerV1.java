package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller 애노테이션 덕분에!!
 * (Component, RequestMapping 두 애노테이션을 클래스 레벨에서 사용함으로써 대체 가능)
 * 컴포넌트 스캔의 대상이 됨
 * 핸들러 매핑 : RequestMappingHandlerMapping
 * 핸들러 어댑터 : RequestMappingHandlerAdapter
 */
@Controller
//@Component
//@RequestMapping
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
