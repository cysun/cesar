package cesar.spring.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cesar.spring.security.SecurityUtils;

@Controller
public class ExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    public ModelAndView resolveException( HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception exception )
    {
        if( !exception.getClass().getName().endsWith( "AccessDeniedException" ) )
            logger.error(
                "Exception caused by user " + SecurityUtils.getUsername(),
                exception );
        return super.resolveException( request, response, handler, exception );
    }

}
