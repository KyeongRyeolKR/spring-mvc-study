package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("call resolver", ex);

        try {
            // 해당 부분에서 발생했던 예외를 처리해버린다!
            if(ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");

                // 예외가 터진걸 sendError로 바꿔치기!
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());

                // 빈 ModelAndView를 반환
                // 이런 ModelAndView를 반환하면 뷰를 랜더링 하지 않고 정상 흐름으로 서블릿이 리턴된다.
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        // null이 반환될 경우 예외가 처리되지 않았으므로 계속 예외가 던져진다.
        return null;
    }
}
