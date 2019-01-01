package com.lanzhu.mywork.message.data.entity;

import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.mail.EmailAttachment;

import java.util.List;

/**
 * description:
 *  邮件信息
 * @author lanzhu259X
 * @date 2019-01-01
 */
@Setter
@Getter
public class MailInfo extends ToString {
    private static final long serialVersionUID = -2872169632797514666L;

    /**
     * 发送成功后用于修改记录状态
     */
    private Long messageLogId;

    /**
     * 收件人地址
     */
    private List<String> toAddresses = null;

    /**
     * 抄送人地址
     */
    private List<String> ccAddresses = null;

    /**
     * 密送人
     */
    private List<String> bcAddresses = null;

    /**
     * 主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件
     */
    private List<EmailAttachment> attachments = null;

}
