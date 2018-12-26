package jp.co.kodnet.plugins.sample.allspace;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.spring.container.ContainerManager;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import jp.co.kodnet.plugins.sample.entities.AttachmentHistory;
import jp.co.kodnet.plugins.sample.entities.DownloadHistory;

/**
 *
 * @author trinhnk
 */
public class AttachmentDownload extends ConfluenceActionSupport {

    private DownloadHistory downloadService;
    private PageManager pageManager = (PageManager) ContainerManager.getComponent("pageManager");

    public void setDownloadService(DownloadHistory downloadService) {
        this.downloadService = downloadService;
    }

    @Override
    public String execute() throws Exception {
        saveAttachmentHistory();
        return SUCCESS;
    }

    /**
     * Luu du lieu cua attachment khi duoc download vao db, du lieu duoc luu
     * gom: ten attachment, user download, download time
     */
    public void saveAttachmentHistory() {
        ActionContext context = ActionContext.getContext();
        String attachmentName = getParameter(context, "attachmentName");
        String userDownload = getParameter(context, "userName");
        long pageID = Long.parseLong(getParameter(context, "pageID"));
        Date downloadTime = new Date();
        downloadService.add(attachmentName, userDownload, downloadTime, pageID);
    }

    /**
     * download file csv, du lieu download gom user download va download time
     *
     * @throws IOException
     */
    public void doDownloadInfo() throws IOException {
        createCSV();
    }

    /**
     * Tao file csv
     *
     * @return chuoi string la noi dung cua file csv
     */
    private void createCSV() throws IOException {
        String header = "userDownload,downloadTime\n";
        ActionContext context = ActionContext.getContext();
        String selectedAttach = getParameter(context, "selectedAttach");
        long pageID = Long.parseLong(getParameter(context, "pageid"));
        String body = "";
        for (AttachmentHistory attachment : downloadService.findByAttachmentName(selectedAttach, pageID)) {
            body += attachment.getUserDownload() + "," + attachment.getDownloadTime() + ",\n";
        }
        String csv = header + body;

        Page page = pageManager.getPage(pageID);
        String pageName = page.getDisplayTitle();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        Date now = new Date();
        String fileName = pageName + "_" + selectedAttach + "_" + df.format(now) + ".csv";

        downloadCSV(csv, fileName);
    }

    /**
     * Tao ra download link
     *
     * @param csv: file csv can duoc download
     * @throws IOException
     */
    private void downloadCSV(String csv, String fileName) throws IOException {
        byte[] bytes = csv.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpServletResponse res = ServletActionContext.getResponse();
        res.setContentType("text/txt");
        res.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        ServletOutputStream sos = res.getOutputStream();
        baos.write(bytes);
        baos.writeTo(sos);
        sos.flush();
        sos.close();
    }

    /**
     * Lay parameter
     *
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
