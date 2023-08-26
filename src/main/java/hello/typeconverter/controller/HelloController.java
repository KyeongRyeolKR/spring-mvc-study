package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data"); // 문자열 타입 조회
        Integer intValue = Integer.valueOf(data); // 숫자 타입으로 변경

        System.out.println(intValue);

        return "ok";
    }

    // 포맷터가 적용되고나서 "1,000"을 파라미터로 넘기면 포맷팅 처리를 통해 1000을 data에 담는다.
    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);

        return "ok";
    }

    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort) {
        System.out.println("ipPort IP = " + ipPort.getIp());
        System.out.println("ipPort PORT = " + ipPort.getPort());

        return "ok";
    }
}
