package ua.com.alevel.web.controller.contact;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.alevel.web.dto.request.contact.ContactRequestDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
public class ContactController {

    private final JavaMailSender mailSender;

    public ContactController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/contact")
    public String redirectToContactPage(Model model) {
        model.addAttribute("contact", new ContactRequestDto());
        return "pages/contact/contact_us";
    }

    @GetMapping("/contact/success")
    public String redirectToSuccessPage() {
        return "pages/contact/success";
    }

    @PostMapping("/contact")
    public String submitContact(@ModelAttribute("contact") ContactRequestDto contactRequestDto) {
        String from = "doctor.all.project@gmail.com";
        String to = "liliia.pohorielova@nure.ua";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(contactRequestDto.getName() + " has sent a message");
            helper.setText("<b>Name: </b>" + contactRequestDto.getName()+ "<br>" +
                    "<b>E-mail: </b>" + contactRequestDto.getMail() + "<br>" +
                    "<b>Subject: </b>" + contactRequestDto.getSubject() + "<br>" +
                    "<b>Message: </b>" + contactRequestDto.getText() + "<br>" +
                    "<br><b>© DoctorAll </b>", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "redirect:/contact/success";
    }
}
