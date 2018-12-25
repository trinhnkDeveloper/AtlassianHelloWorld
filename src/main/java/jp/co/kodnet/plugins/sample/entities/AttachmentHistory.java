package jp.co.kodnet.plugins.sample.entities;

import java.util.Date;
import net.java.ao.Entity;
import net.java.ao.Preload;

/**
 *
 * @author trinhnk
 */
@Preload
public interface AttachmentHistory extends Entity {

    String getAttachmentName();

    void setAttachmentName(String attachmentName);

    String getUserDownload();

    void setUserDownload(String userDownload);

    Date getDownloadTime();

    void setDownloadTime(Date downloadTime);
}
