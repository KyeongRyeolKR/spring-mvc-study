package hello.thymeleaf.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    // 텍스트(text) 문자 출력 - default : escape
    @GetMapping("text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello <b>Spring!<!b>");

        return "basic/text-basic";
    }

    // 텍스트(utext) 문자 출력 - unescape
    @GetMapping("text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hello <b>Spring!<!b>");

        return "basic/text-unescaped";
    }

    // 변수 출력
    // 자주 쓰는 방법 예시 - 프로퍼티 접근법
    // Object : ${user.username}
    // List : ${users[0].username}
    // Map : ${userMap['userA'].username}
    // 지역 변수 만들기 -> th:with 사용
    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    // 타임리프에서 지원하는 기본 객체 및 편의 객체들
    // 스프링 2.x 까지는 기본 객체에 request, response, servletContext 도 있었지만,
    // 스프링 3.x 부터는 지원하지 않는다. 그러므로 직접 모델에 담아서 출력했다.
    // 자주 쓰는 객체 : session, param 등
    // 스프링 빈 접근 : @ + 빈 이름
    @GetMapping("/basic-objects")
    public String basicObjects(Model model,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               HttpSession session
    ) {
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello" + data;
        }
    }

    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
