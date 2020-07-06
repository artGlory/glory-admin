package com.spring.webapi.handle;

import com.spring.common.exception.ServiceException;
import com.spring.common.utils.PrintUtil;
import com.spring.webapi.constant.CommonConstants;
import com.spring.webapi.vo.ResponseDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * glory
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {
    @Value(value = "${self.isShowException.validate.message:false}")
    private boolean isShowValidateExceptionMessge;
    @Value(value = "${self.isShowException.validate.stackTrace:false}")
    private boolean isShowValidateExceptionStackTrace;
    @Value(value = "${self.isShowException.IllegalArgument.message:false}")
    private boolean isShowIllegalArgumentExceptionMessge;
    @Value(value = "${self.isShowException.IllegalArgument.stackTrace:false}")
    private boolean isShowIllegalArgumentExceptionStackTrace;
    @Value(value = "${self.isShowException.ServiceException.message:true}")
    private boolean isShowServiceExceptionMessge;
    @Value(value = "${self.isShowException.ServiceException.stackTrace:false}")
    private boolean isShowServiceExceptionStackTrace;
    @Value(value = "${self.isShowException.Throwable.message:true}")
    private boolean isShowThrowableMessge;
    @Value(value = "${self.isShowException.Throwable.stackTrace:false}")
    private boolean isShowThrowableStackTrace;

    /**
     * valid  在方法里面校验
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseDate<String> handConstraintViolationException(HttpServletRequest request
            , HttpServletResponse response, ConstraintViolationException e) {
        String errorMessage = "【参数未通过验证】";
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(";");
            }
            errorMessage = msgBuilder.toString();
        }
        String requestUuid = request.getAttribute(CommonConstants.http_uuid).toString();
        response.setHeader(CommonConstants.success_response, "false");//true:不记录参数日志 ;false 记录日志

        if (isShowValidateExceptionStackTrace) {
            log.error(requestUuid + " " + PrintUtil.getStackTrace(e));
        } else if (isShowValidateExceptionMessge) {
            log.error(requestUuid + " " + PrintUtil.getMessge(e));
        }

        return ResponseDate.<String>builder()
                .success(false)
                .message(errorMessage)
                .responseUUID(requestUuid)
                .build();
    }

    /**
     * valid  在bean对象里面校验
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDate<String> handleMethodArgumentNotValidException(HttpServletRequest request
            , HttpServletResponse response, MethodArgumentNotValidException e) {
        String errorMessage = "参数未通过验证";
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(";");
            }
            errorMessage = msgBuilder.toString();
        }
        String requestUuid = request.getAttribute(CommonConstants.http_uuid).toString();
        response.setHeader(CommonConstants.success_response, "false");//true:不记录参数日志 ;false 记录日志

        if (isShowValidateExceptionStackTrace) {
            log.error(requestUuid + " " + PrintUtil.getStackTrace(e));
        } else if (isShowValidateExceptionMessge) {
            log.error(requestUuid + " " + PrintUtil.getMessge(e));
        }
        return ResponseDate.<String>builder()
                .success(false)
                .message(errorMessage)
                .responseUUID(requestUuid)
                .build();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseDate<String> handleIllegalArgumentException(HttpServletRequest request
            , HttpServletResponse response, IllegalArgumentException e) {
        String requestUuid = request.getAttribute(CommonConstants.http_uuid).toString();
        response.setHeader(CommonConstants.success_response, "false");

        if (isShowIllegalArgumentExceptionStackTrace) {
            log.error(requestUuid + " " + PrintUtil.getStackTrace(e));
        } else if (isShowValidateExceptionMessge) {
            log.error(requestUuid + " " + PrintUtil.getMessge(e));
        }
        return ResponseDate.<String>builder()
                .success(false)
                .message(e.getMessage())
                .responseUUID(requestUuid)
                .build();
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseDate<String> handleServiceException(HttpServletRequest request
            , HttpServletResponse response, ServiceException e) {
        String requestUuid = request.getAttribute(CommonConstants.http_uuid).toString();
        response.setHeader(CommonConstants.success_response, "false");

        if (isShowServiceExceptionStackTrace) {
            log.error(requestUuid + " " + PrintUtil.getStackTrace(e));
        } else if (isShowServiceExceptionMessge) {
            log.error(requestUuid + " " + PrintUtil.getMessge(e));
        }
        return ResponseDate.<String>builder()
                .success(false)
                .message(e.getMessage())
                .responseUUID(requestUuid)
                .build();
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseDate<String> handleThrowableException(HttpServletRequest request
            , HttpServletResponse response, Throwable t) {
        String requestUuid = request.getAttribute(CommonConstants.http_uuid).toString();
        response.setHeader(CommonConstants.success_response, "false");

        if (isShowThrowableStackTrace) {
            log.error(requestUuid + " " + PrintUtil.getStackTrace(t));
        } else if (isShowThrowableMessge) {
            log.error(requestUuid + " " + PrintUtil.getMessge(t));
        }
        return ResponseDate.<String>builder()
                .success(false)
                .responseUUID(requestUuid)
                .message(t.getMessage())
                .build();
    }

}
