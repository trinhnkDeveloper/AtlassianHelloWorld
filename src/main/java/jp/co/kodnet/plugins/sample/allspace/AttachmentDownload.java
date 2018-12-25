package jp.co.kodnet.plugins.sample.allspace;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.opensymphony.xwork.ActionContext;
import java.util.Date;
import java.util.List;
import jp.co.kodnet.plugins.sample.entities.DownloadService;
import jp.co.kodnet.plugins.sample.entities.AttachmentHistory;

/**
 *
 * @author trinhnk
 */
public class AttachmentDownload extends ConfluenceActionSupport {

    private DownloadService downloadService;

    public void setDownloadService(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @Override
    public String execute() throws Exception {
        doWhenDownload();
        return SUCCESS;
    }

    public void doWhenDownload() {
        ActionContext context = ActionContext.getContext();
        String attachmentName = getParameter(context, "attachmentName");
        String userDownload = getParameter(context, "userName");
        Date downloadTime = new Date();
        downloadService.add(attachmentName, userDownload, downloadTime);
        doDownloadInfo();
    }

    public void doDownloadInfo() {
        List<AttachmentHistory> attachments = downloadService.all();
        for (AttachmentHistory attachment : attachments) {
            System.out.println(attachment.getAttachmentName());
        }
    }

    private String getParameter(ActionContext context, String key) {
        Object object = context.getParameters().get(key);
        if (object instanceof String[] && ((String[]) object).length != 0) {
            return ((String[]) object)[0];
        } else if (object instanceof String) {
            return (String) object;
        }
        return null;
    }
}
