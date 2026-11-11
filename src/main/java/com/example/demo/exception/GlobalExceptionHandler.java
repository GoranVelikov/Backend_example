package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ProblemDetail handleRestaurantNotFound(
            RestaurantNotFoundException exception) {

        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.NOT_FOUND
        );

        problem.setTitle("Restaurant Not Found");
        problem.setDetail(exception.getMessage());
        problem.setProperty("restaurantId",exception.getId());

        return problem;
    }

    @ExceptionHandler(MenuItemNotFoundException.class)
    public ProblemDetail handleMenuItemNotFound(
            MenuItemNotFoundException exception) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setTitle("Menu Item Not Found");
        problem.setDetail(exception.getMessage());

        return problem;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existing, replacement) -> existing
                ));

        ProblemDetail problem = ProblemDetail.forStatus(
                HttpStatus.BAD_REQUEST
        );

        problem.setTitle("Validation Failed");
        problem.setDetail("One or more fields are invalid.");
        problem.setProperty("errors", errors);

        return problem;
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLockingFailure(
            ObjectOptimisticLockingFailureException exception) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.CONFLICT);

        problem.setTitle("Optimistic Locking Conflict");
        problem.setDetail(
                "The resource was modified by another request. " +
                        "Please retrieve it again and retry."
        );

        return problem;
    }

    @ExceptionHandler(ETagMismatchException.class)
    public ProblemDetail handleETagMismatch(
            ETagMismatchException exception) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.PRECONDITION_FAILED);

        problem.setTitle("Precondition Failed");
        problem.setDetail(exception.getMessage());

        return problem;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(
            MethodArgumentTypeMismatchException exception) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Invalid Parameter");
        problem.setDetail(
                "Parameter '" +
                        exception.getName() +
                        "' has an invalid value."
        );

        problem.setProperty("parameter", exception.getName());
        problem.setProperty("value", exception.getValue());

        return problem;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleMessageNotReadable(
            HttpMessageNotReadableException exception) {

        ProblemDetail problem =ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Message Not Readable");
        problem.setDetail("The request body could not be read.");

        return problem;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingParameter(
            MissingServletRequestParameterException exception) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Missing Request Parameter");
        problem.setDetail(
                "Required parameter '" +
                        exception.getParameterName() +
                        "' is missing."
        );

        problem.setProperty(
                "parameter",
                exception.getParameterName()
        );

        return problem;
    }
}
