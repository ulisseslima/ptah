package com.dvlcube.util;

import com.dvlcube.i18n.I18n;
import com.dvlcube.model.User;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class GmailMailer {

    // Host do Gmail
    private String SMTP_HOST_NAME;
    // Login do usuário
    private String SMTP_AUTH_USER;
    // Senha do usuário
    private String SMTP_AUTH_PWD;
    // E-mail do remetente
    private String emailFromAddress;
    // Título do e-mail
    private String emailSubjectTxt;
    // Conteúdo do e-mail
    private String emailMsgTxt;
    private String passwordChangeMsg;
    private String passwordResetMsg;
    // E-mails dos destinatários
    private String[] emailList;
    private String DOMAIN;
    private String SERVER_NAME;

    public GmailMailer(ServletContext context) {
        SMTP_HOST_NAME = "gmail-smtp.l.google.com";
        String[] mailString = context.getInitParameter("EMail").split("\\?pwd=");
        SMTP_AUTH_USER = mailString[0];
        SMTP_AUTH_PWD = mailString[1];
        DOMAIN = context.getInitParameter("Domain");
        SERVER_NAME = context.getInitParameter("ServerName");
    }

    public String getDefaultMessage(User user, String[] token, HttpServletRequest request)
            throws UnsupportedEncodingException {
        String escapedName = token[0].replace(" ", "+");
        String escapedToken = URLEncoder.encode(token[1], "UTF-8");
        String[] messageKeys = {"mail.welcome.m", "mail.instructions", "mail.end"};
        if (user.getGender().equals("F")) {
            messageKeys[0] = "mail.welcome.f";
        }
        String[] message = I18n.getStringsAsArray(request.getLocale(), messageKeys);
        emailMsgTxt =
                message[0] + " " + SERVER_NAME + ", " + user.getName() + message[1]
                + DOMAIN + "/SignupConfirmation?id=" + escapedName + "@" + escapedToken + "@" + token[2] + "&action=confirm"
                + message[2] + " " + SERVER_NAME;

        return emailMsgTxt;
    }

    public String getPasswordChangeMessage(User user, String requestInfo, HttpServletRequest request) {
        String[] messageKeys = {"user.request.mail.Greeting", "user.request.PasswordChange", "user.request.mail.End"};
        String[] message = I18n.getStringsAsArray(request.getLocale(), messageKeys);
        passwordChangeMsg =
                message[0] + user.getName() + message[1]
                + requestInfo
                + message[2] + " " + SERVER_NAME;
        return passwordChangeMsg;
    }

    public String getPasswordResetMessage(User user, String[] token, String requestInfo, HttpServletRequest request)
            throws UnsupportedEncodingException {
        String escapedName = token[0].replace(" ", "+");
        String escapedToken = URLEncoder.encode(token[1], "UTF-8");
        String[] messageKeys = {"user.request.mail.Greeting", "user.request.PasswordReset", "user.request.passwordReset.Confirm", "user.request.mail.End"};
        String[] message = I18n.getStringsAsArray(request.getLocale(), messageKeys);
        passwordResetMsg =
                message[0] + user.getName() + message[1]
                + requestInfo
                + message[2] + DOMAIN + "/PasswordReset?id=" + escapedName + "@" + escapedToken + "&action=confirm"
                + message[3] + " " + SERVER_NAME;
        return passwordResetMsg;
    }

    public void sendEmail(String[] toAddress, String subjectTxt, String messageTxt) {
        try {
            emailFromAddress = SMTP_AUTH_USER;
            emailList = toAddress;
            emailSubjectTxt = subjectTxt;
            emailMsgTxt = messageTxt;

            boolean debug = true;
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            // Configurando o endereço SMTP do host
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.put("mail.smtp.auth", "true");

            // Caso o e-mail precise de autenticação
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getDefaultInstance(props, auth);

            session.setDebug(debug);

            // Criando a mensagem
            Message msg = new MimeMessage(session);

            // Adicionando o e-mail do remetente
            InternetAddress addressFrom = new InternetAddress(emailFromAddress);
            msg.setFrom(addressFrom);

            // Adicionando os e-mails dos destinatários
            InternetAddress[] addressTo = new InternetAddress[emailList.length];
            for (int i = 0; i < emailList.length; i++) {
                addressTo[i] = new InternetAddress(emailList[i]);
            }
            msg.setRecipients(Message.RecipientType.BCC, addressTo);

            // Adicionando o título do e-mail
            msg.setSubject(emailSubjectTxt);

            // Adicionando o conteúdo do e-mail
            msg.setContent(emailMsgTxt, "text/plain");
            Transport.send(msg);
        } catch (Exception e) {
        }
    }

    /**
     * Autentica o usuário quando o servidor SMTP requisita
     */
    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
}
