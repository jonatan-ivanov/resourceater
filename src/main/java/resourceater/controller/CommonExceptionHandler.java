package resourceater.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.util.WebUtils.ERROR_EXCEPTION_ATTRIBUTE;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    ProblemDetail handleThrowable(HttpServletRequest request, Throwable error) {
        logger.error(ExceptionUtils.getStackTrace(error));
        request.setAttribute(ERROR_EXCEPTION_ATTRIBUTE, error);

        return createProblemDetail(INTERNAL_SERVER_ERROR, error);
    }

    @SuppressWarnings("SameParameterValue")
    private ProblemDetail createProblemDetail(HttpStatus status, Throwable error) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setDetail(error.toString());
        problemDetail.setProperty("series", status.series());
        problemDetail.setProperty("rootCause", ExceptionUtils.getRootCause(error).toString());

        return problemDetail;
    }
}
