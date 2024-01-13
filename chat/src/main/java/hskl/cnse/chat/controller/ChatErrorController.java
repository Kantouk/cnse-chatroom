package hskl.cnse.chat.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class ChatErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Get the error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404"; // Custom 404 page
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500"; // Custom 500 page
            }
        }

        // Default error handling
        return "error";
    }

    /*
     * TODO: Implement this method
     * 
     * @Override
     * public String getErrorPath() {
     * }
     */
}
