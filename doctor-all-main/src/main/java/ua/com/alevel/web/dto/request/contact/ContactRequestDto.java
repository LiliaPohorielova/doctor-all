package ua.com.alevel.web.dto.request.contact;

import ua.com.alevel.web.dto.request.RequestDto;

public class ContactRequestDto extends RequestDto {

    private String name;
    private String mail;
    private String subject;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
