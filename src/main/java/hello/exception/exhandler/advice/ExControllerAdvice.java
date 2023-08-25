package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
// 대상 컨트롤러를 지정하지 않으면 글로벌로 적용이 된다!
// [대상 컨트롤러 지정 방법]
// 1) annotations = XXController.class
// 2) basePackages = "[패키지 경로]"
// 3) assignableTypes = {[XXController.class], ...}
@RestControllerAdvice(basePackages = "hello.exception.api")
public class ExControllerAdvice {

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
}
