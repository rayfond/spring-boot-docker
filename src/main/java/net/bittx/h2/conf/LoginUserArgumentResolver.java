package net.bittx.h2.conf;


import net.bittx.h2.anno.LoginUser;
import net.bittx.h2.controller.LoginUserVo;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 在某些情况下，我们希望将数据绑定到对象，这时我们可能马上联想起来使用 @RequestBody
 * 注解，该注解通常用于获取 POST 请求体，并将其转换相应的数据对象
 *
 * 在实际业务场景中，除了请求体中的数据，我们同样需要请求头中的数据，比如 token,
 * token 中包含当前登陆用户的信息，每一次 RESTful 请求我们都需要从 header 中
 * 获取 token 数据处理实际业务，这种场景，上文提到的 Converter 以及 @RequestBody
 * 显然不能满足我们的需求，此时我们就要换另一种解决方案 : HandlerMethodArgumentResolver
 *
 * Note: ArgumentResolver 需要在WebMvcConfigure 中注册才能生效
 *
 *
 * @see MvcConf
 * @see net.bittx.h2.controller.BindingController#getLoginUserInfo(LoginUserVo)
 *
 */
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * Whether the given {@linkplain MethodParameter method parameter} is
     * supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 参数是否有自定义注解 LoginUser 修饰
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * Resolves a method parameter into an argument value from a given request.
     * A {@link ModelAndViewContainer} provides access to the model for the
     * request. A {@link WebDataBinderFactory} provides a way to create
     * a {@link WebDataBinder} instance when needed for data binding and
     * type conversion purposes.
     *
     * @param parameter     the method parameter to resolve. This parameter must
     *                      have previously been passed to {@link #supportsParameter} which must
     *                      have returned {@code true}.
     * @param mavContainer  the ModelAndViewContainer for the current request
     * @param webRequest    the current request
     * @param binderFactory a factory for creating {@link WebDataBinder} instances
     * @return the resolved argument value, or {@code null} if not resolvable
     * @throws Exception in case of errors with the preparation of argument values
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader("token");
        LoginUserVo loginUserVo = null;
        if (!StringUtils.hasText(token)) {
            // 这里处理token的解析，并将其放入到LoginUserVo对象中
            loginUserVo = new LoginUserVo();
            loginUserVo.setId(100);
            loginUserVo.setName("token");
        }
        return loginUserVo;
    }
}
