package jp.co.kodnet.plugins.sample.allspace;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
        saveAttachmentHistory();
        return SUCCESS;
    }

    /**
     * Luu du lieu cua attachment khi duoc download vao db, du lieu duoc luu gom:
     * ten attachment, user download, download time
     */
    public void saveAttachmentHistory() {
        ActionContext context = ActionContext.getContext();
        String attachmentName = getParameter(context, "attachmentName");
        String userDownload = getParameter(context, "userName");
        Date downloadTime = new Date();
        downloadService.add(attachmentName, userDownload, downloadTime);

        // log to see the entities has been saved or not.
        List<AttachmentHistory> attachments = downloadService.all();
        for (AttachmentHistory attachment : attachments) {
            System.out.println(attachment.getAttachmentName() + " - " + attachment.getUserDownload() + " - " + attachment.getDownloadTime());
        }
        System.out.println("\n");
    }

    /**
     * download file csv, du lieu download gom user download va download time
     * @throws IOException 
     */
    public void doDownloadInfo() throws IOException {
        String header = "userDownload,downloadTime\n";
        String body = createCSV();

        String csv = header + body;
        downloadCSV(csv);
    }

    /**
     * Tao file csv
     * @return chuoi string la noi dung cua file csv
     */
    private String createCSV() {
        ActionContext context = ActionContext.getContext();
        String selectedAttach = getParameter(context, "selectedAttach");
        String body = "";
        for (AttachmentHistory attachment : downloadService.findByAttachmentName(selectedAttach)) {
            body += attachment.getUserDownload() + "," + attachment.getDownloadTime() + ",\n";
        }
        return body;
    }

    /**
     * Tao ra download link
     * @param csv: file csv can duoc download
     * @throws IOException 
     */
    private void downloadCSV(String csv) throws IOException {
        byte[] bytes = csv.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpServletResponse res = ServletActionContext.getResponse();
        res.setContentType("text/txt");
        String fileName = "attachment-history.csv";
        res.setHeader("Content-Disposition","attachment; filename*=UTF-8''" + fileName);
        
        ServletOutputStream sos = res.getOutputStream();
        baos.write(bytes);
        baos.writeTo(sos);
        sos.flush();
        sos.close();
    }

    /**
     * Lay parameter
     * @param context
     * @param key
     * @return 
     */
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
