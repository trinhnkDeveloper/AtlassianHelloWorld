package jp.co.kodnet.plugins.sample.entities;

import java.util.Date;
import java.util.List;

/**
 *
 * @author trinhnk
 */
public interface DownloadHistory {

    AttachmentHistory add(String attachmentName, String userDownload, Date downloadTime, long pageID);

    List<AttachmentHistory> all();
    
    List<AttachmentHistory> findByAttachmentName(String attachmentName, long pageID);
}
