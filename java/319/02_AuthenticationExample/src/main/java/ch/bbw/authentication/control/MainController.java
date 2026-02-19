package ch.bbw.authentication.control;

import ch.bbw.authentication.model.AuthenticationData;
import ch.bbw.authentication.model.DatabaseService;
import ch.bbw.authentication.userauthentication.UserAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("authenticationData", new AuthenticationData());
        model.addAttribute("message", "");
        return "index";
    }

    @PostMapping("/signIn")
    public String signIn(@ModelAttribute AuthenticationData authenticationData,
                         Model model) {
        System.out.println(authenticationData.getUsername());

        if (UserAuthentication.validatePassword(authenticationData.getUsername(), authenticationData.getPassword()) == true){
            // Fall: Benutzer korrekt authentifiziert
            model.addAttribute("authenticationData", new AuthenticationData());
            model.addAttribute("message", "");
            return "success";
        }

        

        // im Fehlerfall
        model.addAttribute("authenticationData", new AuthenticationData());
        model.addAttribute("message", "authentication failed");
        return "index";
    }
}
