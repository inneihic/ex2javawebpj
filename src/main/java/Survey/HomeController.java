package Survey;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final CsvStore store;

    public HomeController(CsvStore store) {
        this.store = store;
    }

    @GetMapping({"/", "/survey"})
    public String index(Model model) {
        model.addAttribute("form", new SurveyForm());
        return "index";
    }

    @PostMapping("/submit")
    public String submit(@Valid @ModelAttribute("form") SurveyForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("Form validation failed: {}", bindingResult.getAllErrors());
            return "index";
        }
        try {
            store.save(form);
            model.addAttribute("form", form);
            return "thanks";
        } catch (Exception e) {
            logger.error("Failed to save survey form", e);
            model.addAttribute("error", "An error occurred while saving your submission. Please try again.");
            return "index";
        }
    }
}