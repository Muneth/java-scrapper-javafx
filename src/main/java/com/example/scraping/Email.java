package com.example.scraping;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Email {
    public void sendMail(String mail, String filename) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("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");

        try {
            TransactionalEmailsApi api = new TransactionalEmailsApi();
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail("muneetchaudhary1102@gmail.com");
            sender.setName("Muneet");
            List<SendSmtpEmailTo> toList = new ArrayList<>();
            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(mail);
            to.setName("Muneth");
            toList.add(to);
            SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();
            attachment.setName("test.txt");
            byte[] encode = Files.readAllBytes(Paths.get("C:\\Users\\munee\\Desktop\\Projet\\util\\"+filename+".txt"));
            attachment.setContent(encode);
            List<SendSmtpEmailAttachment> attachmentList = new ArrayList<SendSmtpEmailAttachment>();
            attachmentList.add(attachment);
            Properties headers = new Properties();
            headers.setProperty("Some-Custom-Name", "unique-id-1234");
            Properties params = new Properties();
            params.setProperty("parameter", "for your search");
            params.setProperty("subject", "New Search");
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);
            sendSmtpEmail.setHtmlContent("<html><body><h1>Here are the results {{params.parameter}}</h1></body></html>");
            sendSmtpEmail.setSubject("{{params.subject}}");
            sendSmtpEmail.setAttachment(attachmentList);
            sendSmtpEmail.setHeaders(headers);
            sendSmtpEmail.setParams(params);
            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("Exception occurred:- " + e.getMessage());
        }
    }
}
