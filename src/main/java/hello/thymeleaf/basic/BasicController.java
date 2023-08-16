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

import java.time.LocalDateTime;
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

    // 유틸리티 객체와 날짜
    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());

        return "basic/date";
    }

    // URL 링크
    @GetMapping("link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");

        return "basic/link";
    }

    // 리터럴
    // 타임리프에서 문자 리터럴은 항상 ''으로 감싸야한다.
    // 하지만 매번 ''를 사용해서 감싸주는건 귀찮다.
    // 그러므로 공백없이 쭉 이어진 문자라면 ''를 생략할 수 있다.
    // 공백이 있는 문자들을 써야할 땐, ||(리터럴 대체 문법)을 사용하면 편하게 쓸 수 있다.
    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring!");

        return "basic/literal";
    }

    // 연산
    // 비교연산 : HTML 엔티티를 사용해야 하는 부분을 주의
    // 조건식 : 자바와 비슷함 (삼항 연산자)
    // Elvis 연산자 : ${data} ?: '없음' -> data가 있다면 data 출력, 없다면(null) 없음 출력
    // No-Operation : '_' 인 경우 타임리프가 실행되지 않는것처럼 동작함.
    // ex) <p th:text="${null} ?: _">없음</p>
    // null 이기 때문에 타임리프가 실행되지 않는 것처럼 기본 지정된 텍스트인 '없음'이 출력됨
    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");

        return "basic/operation";
    }

    // 속성 값 변경
    // th:attrappend -> 지정한 속성을 앞에 추가
    // th:attrprepend -> 지정한 속성을 뒤에 추가
    // th:classappend -> 클래스 뒤에 추가
    // th:checked -> 값이 true면 check, false면 uncheck
    // 원래 HTML에서 checked 옵션은 값이 true든 false든 checked 옵션이 존재하면 체크하는데,
    // 타임리프의 th:checked는 false라면 아예 checked 속성 자체를 지워버린다.
    @GetMapping("/attribute")
    public String attribute() {
        return "basic/attribute";
    }

    // 반복
    // th:each -> th:each="[지역변수] : ${data}" 형식으로 사용한다.
    // 반복되고 있는 현재 컬렉션에 대한 정보를 얻으려면 [지역변수]+Stat 이름의 변수로 접근 가능하다.
    // 제공되는 정보 : index/count/size/even/odd/first/last/current
    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);

        return "basic/each";
    }

    private void addUsers(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("UserA", 10));
        list.add(new User("UserB", 20));
        list.add(new User("UserC", 30));

        model.addAttribute("users", list);
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
