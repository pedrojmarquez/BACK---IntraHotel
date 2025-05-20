package com.fct.backfct.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void enviarFacturaConAdjunto(String to,
                                        String asunto,
                                        String plantilla,
                                        Context contexto,
                                        String rutaPdf) throws MessagingException {

        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(to);
        helper.setSubject(asunto);

        // Procesar plantilla HTML con Thymeleaf
        String htmlContent = templateEngine.process(plantilla, contexto);
        helper.setText(htmlContent, true); // true = HTML

        // Adjuntar el PDF
        FileSystemResource file = new FileSystemResource(new File(rutaPdf));
        helper.addAttachment("Factura.pdf", file);

        mailSender.send(mensaje);
    }
}
