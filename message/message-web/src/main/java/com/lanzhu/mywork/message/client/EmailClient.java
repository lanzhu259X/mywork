package com.lanzhu.mywork.message.client;

import com.lanzhu.mywork.master.common.utils.AsyncTaskExecutorUtils;
import com.lanzhu.mywork.master.error.BizException;
import com.lanzhu.mywork.message.common.MessageErrorCode;
import com.lanzhu.mywork.message.data.entity.MailInfo;
import com.lanzhu.mywork.message.data.mapper.MessageLogMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * description:
 * 邮件客户端，用于发送邮件消息
 * @author lanzhu259X
 * @date 2019-01-01
 */
@Log4j2
@Component
public class EmailClient {

    @Autowired
    private MessageLogMapper messageLogMapper;

    @Value("${email.smtp.host}")
    private String emailSmtpHost;
    @Value("${email.send.from}")
    private String formEmail;
    @Value("${email.auth.code}")
    private String clientAuthCode;

    private HtmlEmail buildHtmlEmail() throws Exception {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(emailSmtpHost);
        htmlEmail.setCharset("UTF-8");
        htmlEmail.setAuthentication(formEmail, clientAuthCode);
        htmlEmail.setFrom(formEmail);
        return htmlEmail;
    }

    /**
     * 发送邮件 异步发送
     * @param template
     * @param mailInfo
     */
    public void sendEmailAsyn(final Long userId, final String template, final MailInfo mailInfo) {
        AsyncTaskExecutorUtils.execute(() -> {
            this.sendEmail(userId, template, mailInfo);
        });
    }

    /**
     * 发送邮件
     * @param template
     * @param mailInfo
     */
    public void sendEmail(final Long userId, final String template, final MailInfo mailInfo) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            if (mailInfo == null || mailInfo.getToAddresses() == null || mailInfo.getToAddresses().isEmpty()) {
                log.info("收件人地址为空，不进行发送邮件. userId:{} template:{}", userId, template);
                return;
            }
            HtmlEmail email = buildHtmlEmail();
            email.setSubject(mailInfo.getSubject());
            email.setHtmlMsg(mailInfo.getContent());
            //添加附件
            if (mailInfo.getAttachments() != null && !mailInfo.getAttachments().isEmpty()) {
                for (EmailAttachment attachment : mailInfo.getAttachments()) {
                    email.attach(attachment);
                }
            }
            //收件人
            for (String toAddress : mailInfo.getToAddresses()) {
                email.addTo(toAddress);
            }
            //抽送人
            if (mailInfo.getCcAddresses() != null && !mailInfo.getCcAddresses().isEmpty()) {
                for (String ccAddress : mailInfo.getCcAddresses()) {
                    email.addCc(ccAddress);
                }
            }
            //密送人
            if (mailInfo.getBcAddresses() != null && !mailInfo.getBcAddresses().isEmpty()) {
                for (String bcAddress : mailInfo.getBcAddresses()) {
                    email.addBcc(bcAddress);
                }
            }
            email.send();
            log.info("邮件发送成功: userId:{} template:{}", userId, template);
            // 修改发送日志的成功标识
            messageLogMapper.updateIsSuccess(mailInfo.getMessageLogId(), true);
        }catch (Exception e) {
            log.error("send email error: userId:{} template:{}", userId, template, e);
            messageLogMapper.updateIsSuccess(mailInfo.getMessageLogId(), false);
            throw new BizException(MessageErrorCode.EMAIL_SEND_FAIL);
        }finally {
            stopWatch.stop();
            log.info("发送邮件耗时: userId:{} template:{} time:{}s", userId, template, stopWatch.getTotalTimeSeconds());
        }
    }
}
