package com.aurora.pos.servicios;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface ServicioCorreo {

    void sendMessageWithAttachment(String to, String subject, String text, List<Attachment> attachments) throws  ServiceException;
    void sendSimpleMessage(String to, String subject, String text) throws ServiceException;


    @Getter @Setter
    class Attachment
    {
        private String fileName;
        private ByteArrayResource file;
        private String contentType;
    }
}
