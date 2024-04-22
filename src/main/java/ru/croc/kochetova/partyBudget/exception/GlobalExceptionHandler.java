package ru.croc.kochetova.partyBudget.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ApplicationCreatedException.class})
    public ModelAndView handleException(ApplicationCreatedException ex) {
        return generalLogicException(ex.getMessage());
    }
    @ExceptionHandler(UpdateReceivingMoneyApplicationException.class)
    public ModelAndView handleException(UpdateReceivingMoneyApplicationException ex) {
        return generalLogicException(ex.getMessage());
    }
    private ModelAndView generalLogicException(String ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", ex);
        modelAndView.setViewName("application_error");
        return modelAndView;
    }
}
