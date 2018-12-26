package jp.co.kodnet.plugins.sample.entities;

import com.atlassian.activeobjects.external.ActiveObjects;
import java.util.Date;
import java.util.List;
import net.java.ao.Query;
import static com.google.common.collect.Lists.newArrayList;

/**
 *
 * @author trinhnk
 */
public class DownloadHistoryImpl implements DownloadHistory {
    private ActiveObjects ao;

    public void setAo(ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public AttachmentHistory add(String attachmentName, String userDownload, Date downloadTime, long pageID) {
        final AttachmentHistory da = ao.create(AttachmentHistory.class);
        da.setAttachmentName(attachmentName);
        da.setDownloadTime(downloadTime);
        da.setUserDownload(userDownload);
        da.setPageID(pageID);
        da.save();
        return da;
    }

    @Override
    public List<AttachmentHistory> all() {
        return newArrayList(ao.find(AttachmentHistory.class));
    }

    @Override
    public List<AttachmentHistory> findByAttachmentName(String attachmentName, long pageID) {
        List<AttachmentHistory> history = newArrayList(ao.find(AttachmentHistory.class, Query.select().where("ATTACHMENT_NAME = ? AND PAGE_ID = ?", attachmentName, pageID)));
        return history;
    }
}
