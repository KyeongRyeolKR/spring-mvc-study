package hello.login.web.argumentResolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        // 파라미터에 @Login 애노테이션이 달려있는지
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        // Member 타입과 파라미터 타입이 같은지
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        // 둘다 만족한다면 true
        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        // 세션이 없으면 null 반환
        if(session == null) {
            return null;
        }

        // 세션이 있으면 member 반환
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
