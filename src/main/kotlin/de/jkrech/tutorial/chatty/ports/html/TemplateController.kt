package de.jkrech.tutorial.chatty.ports.html

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TemplateController {

    @GetMapping("/chatty")
    fun showChattyPage(): String {
        return "chatty"
    }
}