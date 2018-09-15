package com.dgs.springbootemail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private EmailCfg emailCfg;

    public FeedbackController(EmailCfg emailCfg) {
        this.emailCfg = emailCfg;
    }

    @PostMapping
    public void sendFeedback(@RequestBody Feedback feedback, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException("Feedback is not valid");
        }

        // Create a mail sender

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emailCfg.getHost());
        mailSender.setPort(emailCfg.getPort());
        mailSender.setUsername(emailCfg.getUsername());
        mailSender.setPassword(emailCfg.getPassword());

        // Create an email instance

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedback.getEmail());
        mailMessage.setTo("dragos.sarpe84@gmail.com");
        mailMessage.setSubject("New feedback from " + feedback.getName());
        mailMessage.setText(feedback.getFeedback());

        // Send mail

        mailSender.send(mailMessage);
    }
}
