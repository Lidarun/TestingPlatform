package kg.dpa.gov.evaluation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pdf")
public class PDFGeneratorController {

    @GetMapping
    private String showPage(){
        return "pdf";
    }
}
