package cn.latiny.error;

import cn.latiny.converter.Result;
import cn.latiny.converter.Result.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result handle(Exception exception) {
       Builder builder = Result.newBuilder();
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException)exception;
            int code = businessException.getCode();
            String message = businessException.getMessage();
            builder.fail(code, message);
            logger.error("Business exception. code: {}, message: {}", code, message);
            return builder.build();
        } else if (exception instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException)exception).getBindingResult();
            ObjectError objectError = (ObjectError)bindingResult.getAllErrors().get(0);
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError)objectError;
                String message = String.format(SystemErrorCodeEnum.ILLEGAL_PARAM.getMessage(), fieldError.getField(), fieldError.getDefaultMessage());
                builder.fail(Integer.valueOf(400), message);
            } else {
                builder.fail(Integer.valueOf(400), objectError.getDefaultMessage());
            }

            logger.error("Argument valid exception: {}", objectError);
            return builder.build();
        } else {
            builder.fail(Integer.valueOf(500));
            logger.error("Unknown exception. please review your code.", exception);
            return builder.build();
        }
    }
}
