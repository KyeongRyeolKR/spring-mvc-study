package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    // 템플릿 조각
    // html css의 공통 처리를 위한 기능이다.
    // th:insert -> div 태그 다음에 조각을 넣는다.
    // th:replace -> div 태그 대신 조각을 넣는다.
    // th:fragment 에서 단순히 조각 이름만 지정할수도 있지만, 파라미터도 같이 넘길 수 있다.
    @GetMapping("/fragment")
    public String template() {
        return "template/fragment/fragmentMain";
    }

    // 템플릿 레이아웃
    @GetMapping("/layout")
    public String layout() {
        return "template/layout/layoutMain";
    }
}
