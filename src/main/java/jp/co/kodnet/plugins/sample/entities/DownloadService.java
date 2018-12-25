package jp.co.kodnet.plugins.sample.entities;

import java.util.Date;
import java.util.List;

/**
 *
 * @author trinhnk
 */
public interface DownloadService {

    AttachmentHistory add(String attachmentName, String userDownload, Date downloadTime);

    List<AttachmentHistory> all();
}
