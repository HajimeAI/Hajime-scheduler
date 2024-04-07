package ai.hajimebot.global;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * Global Exception Processor
 *
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * The permission code is abnormal
     */
    @ExceptionHandler(NotPermissionException.class)
    public R<Void> handleNotPermissionException(NotPermissionException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}', permission code verification failed '{}'", requestURI, e.getMessage());
        return R.fail(HttpStatus.HTTP_FORBIDDEN, "No Access");
    }

    /**
     * The role permission is abnormal
     */
    @ExceptionHandler(NotRoleException.class)
    public R<Void> handleNotRoleException(NotRoleException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}', role permission verification failed '{}'", requestURI, e.getMessage());
        return R.fail(HttpStatus.HTTP_FORBIDDEN, "No Access");
    }

    /**
     * Authentication failed
     */
    @ExceptionHandler(NotLoginException.class)
    public R<Void> handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}', authentication failed '{}', system resources cannot be accessed", requestURI, e.getMessage());
        return R.fail(HttpStatus.HTTP_UNAUTHORIZED, "Authentication failed");
    }

    /**
     * The request method is not supported
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                       HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}', '{}' request is not supported", requestURI, e.getMethod());
        return R.fail(e.getMessage());
    }

    /**
     * The required path variable is missing from the request path
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public R<Void> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("The required path variable '{}' is missing in the request path, a system exception has occurred.", requestURI);
        return R.fail(String.format("A required path variable is missing from the request path [%s]", e.getVariableName()));
    }

    /**
     * The request parameter types do not match
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("The request parameter type does not match '{}', a system exception occurs.", requestURI);
        return R.fail(String.format("The request parameter type does not match, the parameter [%s] requires the type to be: '%s', but the input value is: '%s'", e.getName(), e.getRequiredType().getName(), e.getValue()));
    }

    /**
     * Intercept unknown runtime exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    public R<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}', an unknown exception has occurred.", requestURI, e);
        return R.fail(e.getMessage());
    }

    /**
     * system exception
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}', system exception has occurred.", requestURI, e);
        return R.fail(e.getMessage());
    }


    /**
     * Custom validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return R.fail(message);
    }

}
