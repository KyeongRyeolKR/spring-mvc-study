package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        // 예외가 터지는 것이 아닌 정상 로직으로 해결되기 때문에 200 OK 상태코드로 응답한다.
        // 우리는 해당 예외가 터졌을 땐 400 에러로 처리해주기 위해 @ResponseStatus를 사용해서 상태코드를 변경한다.
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler // 애노테이션 옵션에 넣을 예외 타입과 파라미터의 예외가 같다면 생략 가능!
    public ResponseEntity<ErrorResult> userExceptionHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);

        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exceptionHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);

        return new ErrorResult("EX", "내부 오류");
    }

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        // 예외(ex)가 터진 상황이라면
        if(id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        // 클라이언트가 잘못된 값을 보냈을 경우
        if(id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if(id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
