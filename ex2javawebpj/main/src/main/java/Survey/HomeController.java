package Survey;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final CsvStore store;
    public HomeController(CsvStore store) { this.store = store; }

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
            return "index";
        }
        store.save(form);
        model.addAttribute("form", form);
        return "thanks";
    }
}
