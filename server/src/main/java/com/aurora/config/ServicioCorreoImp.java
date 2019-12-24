package com.aurora.config;

import com.aurora.pos.entidades.Emisor;
import com.aurora.pos.servicios.ServiceException;
import com.aurora.pos.servicios.ServicioCorreo;
import com.aurora.pos.servicios.ServicioEmisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Properties;


@Component
public class ServicioCorreoImp implements ServicioCorreo {

    @Autowired
    public ServicioEmisor servicioEmisor;


    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, List<Attachment> attachments) throws ServiceException {

        try {
            MimeMessage message = getJavaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
            for (Attachment attachment : attachments)
            {
                helper.addAttachment(attachment.getFileName(),attachment.getFile(),attachment.getContentType());
            }
            getJavaMailSender().send(message);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) throws ServiceException {
        try
        {
            MimeMessage message = getJavaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
            getJavaMailSender().send(message);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }



    private JavaMailSender getJavaMailSender()
    {
        Emisor emisor = servicioEmisor.getEmisor();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        if(emisor.getCorreo()!=null) {

            mailSender.setHost(emisor.getCorreo().getHost());
            mailSender.setPort(emisor.getCorreo().getPort());

            mailSender.setUsername(emisor.getCorreo().getUsername());
            mailSender.setPassword(emisor.getCorreo().getPassword());

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", emisor.getCorreo().isAuth());
            props.put("mail.smtp.starttls.enable", emisor.getCorreo().isStarttlsEnable());
            props.put("mail.debug", "true");

        }

        return mailSender;
    }

}
