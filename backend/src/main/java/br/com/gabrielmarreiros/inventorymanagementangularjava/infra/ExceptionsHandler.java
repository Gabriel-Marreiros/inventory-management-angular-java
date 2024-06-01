package br.com.gabrielmarreiros.inventorymanagementangularjava.infra;

import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCategoryNotFoundException(HttpServletRequest request){
        return new ErrorResponse()
                    .withTitle("Categória não encontrada")
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withDescription("A categoria solicitada não foi encontrada.")
                    .withInstance(request.getRequestURI());
    }

    @ExceptionHandler(InvalidSortException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidSortException(HttpServletRequest request){
        return new ErrorResponse()
                    .withTitle("Ordenação inválida")
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withDescription("A ordenação solicitada é inválida")
                    .withInstance(request.getRequestURI());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(HttpServletRequest request){
        return new ErrorResponse()
                    .withTitle("Produto não encontrado")
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withDescription("O produto solicitado não foi encontrado ou não existe.")
                    .withInstance(request.getRequestURI());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExistsException(HttpServletRequest request){
        return new ErrorResponse()
                    .withTitle("Usuário ja existe.")
                    .withStatus(HttpStatus.CONFLICT)
                    .withDescription("O usuário já existe no sistema.")
                    .withInstance(request.getRequestURI());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(HttpServletRequest request){
        return new ErrorResponse()
                    .withTitle("Usuário não encontrado.")
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withDescription("O usuário solicitado não foi encontrado.")
                    .withInstance(request.getRequestURI());
    }
}
