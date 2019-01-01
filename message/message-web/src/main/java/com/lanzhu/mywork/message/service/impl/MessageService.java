package com.lanzhu.mywork.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.lanzhu.mywork.master.common.utils.DateUtils;
import com.lanzhu.mywork.master.common.utils.StringTemplateUtils;
import com.lanzhu.mywork.master.error.BizException;
import com.lanzhu.mywork.master.error.ErrorCode;
import com.lanzhu.mywork.message.client.EmailClient;
import com.lanzhu.mywork.message.common.MessageErrorCode;
import com.lanzhu.mywork.message.common.enums.MessageType;
import com.lanzhu.mywork.message.common.enums.TemplateStatus;
import com.lanzhu.mywork.message.data.entity.EmailTemplateInfo;
import com.lanzhu.mywork.message.data.entity.MailInfo;
import com.lanzhu.mywork.message.data.entity.MessageLog;
import com.lanzhu.mywork.message.data.entity.MobileTemplateInfo;
import com.lanzhu.mywork.message.data.mapper.EmailTemplateInfoMapper;
import com.lanzhu.mywork.message.data.mapper.MessageLogMapper;
import com.lanzhu.mywork.message.data.mapper.MobileTemplateInfoMapper;
import com.lanzhu.mywork.message.service.IMessageService;
import com.lanzhu.mywork.message.vo.request.BatchEntityArg;
import com.lanzhu.mywork.message.vo.request.BatchMessageArg;
import com.lanzhu.mywork.message.vo.request.EmailMessageArg;
import com.lanzhu.mywork.message.vo.request.MobileMessageArg;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2019-01-01
 */
@Log4j2
@Service
public class MessageService implements IMessageService {

    @Autowired
    private EmailTemplateInfoMapper emailTemplateInfoMapper;
    @Autowired
    private MobileTemplateInfoMapper mobileTemplateInfoMapper;
    @Autowired
    private MessageLogMapper messageLogMapper;
    @Autowired
    private EmailClient emailClient;

    @Override
    public void sendEmailMessage(EmailMessageArg request) {
        String template = request.getTemplate();
        Long userId = request.getUserId();
        String email = request.getEmail();
        EmailTemplateInfo templateInfo = emailTemplateInfoMapper.getByTemplate(template);
        if (templateInfo == null) {
            log.warn("获取不到对应的邮件模本. userId:{} email:{} template:{}", userId, email, template);
            throw new BizException(MessageErrorCode.TEMPLATE_NOT_FOUND);
        }
        if (TemplateStatus.NORMAL != templateInfo.getStatus()) {
            log.warn("模本不可用. userId:{} email:{} template:{}", userId, email, template);
            throw new BizException(MessageErrorCode.TEMPLATE_NOT_NORMAL);
        }
        log.info("request send email. userId:{} email:{} template:{}", userId, email, template);

        MessageLog messageLog = MessageLog.buildMessageLog(request);
        final String subject = StringTemplateUtils.render(templateInfo.getEmailSubject(), request.getParams());
        final String content = StringTemplateUtils.render(templateInfo.getEmailContent(), request.getParams());
        sendSignEmail(userId, email, template, messageLog, subject, content, false);
    }

    private void sendSignEmail(Long userId, String email, String template, MessageLog messageLog, String subject, String content, boolean isAync) {
        int count = messageLogMapper.insert(messageLog);
        if (count <= 0 || messageLog.getId() == null) {
            log.error("save email log fail. userId:{} email:{} template:{}", userId, email, template);
            throw new BizException(ErrorCode.SYS_ERROR);
        }
        MailInfo mailInfo = new MailInfo();
        mailInfo.setMessageLogId(messageLog.getId());
        mailInfo.setToAddresses(Arrays.asList(email));
        mailInfo.setSubject(subject);
        mailInfo.setContent(content);
        if (isAync) {
            emailClient.sendEmail(userId, template, mailInfo);
        }else {
            emailClient.sendEmailAsyn(userId, template, mailInfo);
        }
    }

    @Override
    public void sendEmailBatch(BatchMessageArg messageArg) {
        if (messageArg.getBatchEntityArgs() == null || messageArg.getBatchEntityArgs().isEmpty()) {
            throw new BizException(ErrorCode.SYS_PARAMS_VALID);
        }
        if (StringUtils.isBlank(messageArg.getTemplate())) {
            throw new BizException(ErrorCode.SYS_PARAMS_VALID);
        }
        final String template = messageArg.getTemplate();
        EmailTemplateInfo templateInfo = emailTemplateInfoMapper.getByTemplate(template);
        if (template == null) {
            log.warn("邮件模板不存在, template:{}", template);
            throw new BizException(MessageErrorCode.TEMPLATE_NOT_FOUND);
        }
        if (TemplateStatus.NORMAL != templateInfo.getStatus()) {
            log.warn("邮件模板不可用, template:{}", template);
            throw new BizException(MessageErrorCode.TEMPLATE_NOT_NORMAL);
        }
        final String paramsJson = JSON.toJSONString(messageArg.getParams());
        final String subject = StringTemplateUtils.render(templateInfo.getEmailSubject(), messageArg.getParams());
        final String content = StringTemplateUtils.render(templateInfo.getEmailContent(), messageArg.getParams());
        List<BatchEntityArg> entityArgs = messageArg.getBatchEntityArgs();
        log.info("批量发送邮件模本信息：template:{} size:{}", template, entityArgs.size());
        entityArgs.stream().forEach(item -> {
            Long userId = item.getUserId();
            String email = item.getAddress();
            try {
                MessageLog messageLog = new MessageLog();
                messageLog.setTemplate(template);
                messageLog.setMessageType(MessageType.EMAIL);
                messageLog.setUserId(userId);
                messageLog.setToAddress(email);
                messageLog.setIsSuccess(null);
                messageLog.setParams(paramsJson);
                messageLog.setCreateTime(DateUtils.getNewDate());
                this.sendSignEmail(userId, email, template, messageLog, subject, content, true);
            }catch (Exception e) {
                log.error("send batch email for one error: userId:{} email:{} template:{}",
                        userId, email, template, e);
            }
        });

    }

    @Override
    public void sendMobileMessage(MobileMessageArg request) {
        String template = request.getTemplate();
        String mobile = request.getMobile();
        Long userId = request.getUserId();
        MobileTemplateInfo templateInfo = mobileTemplateInfoMapper.getByTemplate(template);
        if (templateInfo == null) {
            log.warn("短信模本不存在, userId:{} mobile:{} template:{}", userId, mobile, template);
            throw new BizException(MessageErrorCode.TEMPLATE_NOT_FOUND);
        }
        if (TemplateStatus.NORMAL != templateInfo.getStatus()) {
            log.warn("短信模本不可用, userId:{} mobile:{} template:{}", userId,mobile, template);
            throw new BizException(MessageErrorCode.TEMPLATE_NOT_NORMAL);
        }
        log.info("request send sms. userId:{} mobile:{} template:{}", userId, mobile, template);
        MessageLog messageLog = MessageLog.buildMessageLog(request);
        int count = messageLogMapper.insert(messageLog);
        if (count <= 0 || messageLog.getId() == null) {
            log.error("save mobile log fail. userId:{} mobile:{} template:{}", userId, mobile, template);
            throw new BizException(ErrorCode.SYS_ERROR);
        }
        String message = StringTemplateUtils.render(templateInfo.getContent(), request.getParams());
        // TODO 添加对应发送短信的客户端信息发送短信
        log.info("send mobile message: userId:{} mobile:{} template:{} message:{}",
                userId, mobile, template, message);
        messageLogMapper.updateIsSuccess(messageLog.getId(), true);
    }

    @Override
    public void sendMobileBatch(BatchMessageArg messageArg) {
        if (messageArg.getBatchEntityArgs() == null || messageArg.getBatchEntityArgs().isEmpty()) {
            throw new BizException(ErrorCode.SYS_PARAMS_VALID);
        }
        if (StringUtils.isBlank(messageArg.getTemplate())) {
            throw new BizException(ErrorCode.SYS_PARAMS_VALID);
        }
        final String template = messageArg.getTemplate();
        MobileTemplateInfo templateInfo = mobileTemplateInfoMapper.getByTemplate(template);
        if (template == null) {
            log.warn("短信模板不存在, template:{}", template);
            throw new BizException(MessageErrorCode.TEMPLATE_NOT_FOUND);
        }
        if (TemplateStatus.NORMAL != templateInfo.getStatus()) {
            log.warn("短信模板不可用, template:{}", template);
            throw new BizException(MessageErrorCode.TEMPLATE_NOT_NORMAL);
        }
        final String paramsJson = JSON.toJSONString(messageArg.getParams());
        final String content = StringTemplateUtils.render(templateInfo.getContent(), messageArg.getParams());
        List<BatchEntityArg> entityArgs = messageArg.getBatchEntityArgs();
        log.info("批量发送短信模本信息：template:{} size:{}", template, entityArgs.size());
        entityArgs.stream().forEach(item -> {
            Long userId = item.getUserId();
            String mobile = item.getAddress();
            try {
                MessageLog messageLog = new MessageLog();
                messageLog.setTemplate(template);
                messageLog.setMessageType(MessageType.MOBILE);
                messageLog.setUserId(userId);
                messageLog.setToAddress(mobile);
                messageLog.setIsSuccess(null);
                messageLog.setParams(paramsJson);
                messageLog.setCreateTime(DateUtils.getNewDate());
                messageLogMapper.insert(messageLog);
                // TODO 添加对应发送短信的客户端信息发送短信
                log.info("send mobile message: userId:{} mobile:{} template:{} content:{}",
                        userId, mobile, template, content);
                messageLogMapper.updateIsSuccess(messageLog.getId(), true);
            }catch (Exception e) {
                log.error("batch send sms error for one. userId:{} mobile:{} template:{}",
                        userId, mobile, template, e);
            }
        });
    }

}
