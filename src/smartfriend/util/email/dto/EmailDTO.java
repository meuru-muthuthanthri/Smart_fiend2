package smartfriend.util.email.dto;

/**
 *
 * @author Nilaksha
 */
public class EmailDTO {
    
    private String parentEmailAdd;
    private String msgSubject;
    private String msgBody;
    private String attachmentPath;

    public EmailDTO() {
    }

    public String getParentEmailAdd() {
        return parentEmailAdd;
    }

    public void setParentEmailAdd(String parentEmailAdd) {
        this.parentEmailAdd = parentEmailAdd;
    }

    public String getMsgSubject() {
        return msgSubject;
    }

    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }    
}
