package de.jkrech.tutorial.chatty.ports.html

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
@Suppress("unused")
class TemplateController {

    @GetMapping("/chatty")
    fun showChattyPage(): String {
        return "chatty"
    }

    @GetMapping("/vocab")
    fun showVocabPage(): String {
        return "vocab"
    }
}