package jp.co.kodnet.plugins.sample.entities;

import com.atlassian.activeobjects.external.ActiveObjects;
import java.util.Date;
import java.util.List;
import static com.google.common.collect.Lists.newArrayList;

/**
 *
 * @author trinhnk
 */
public class DownloadServiceImpl implements DownloadService {
    private ActiveObjects ao;

    public void setAo(ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public AttachmentHistory add(String attachmentName, String userDownload, Date downloadTime) {
        if(ao == null){
            System.out.println("null");
        }
        final AttachmentHistory da = ao.create(AttachmentHistory.class);
        da.setAttachmentName(attachmentName);
        da.setDownloadTime(downloadTime);
        da.setUserDownload(userDownload);
        da.save();
        return da;
    }

    @Override
    public List<AttachmentHistory> all() {
        return newArrayList(ao.find(AttachmentHistory.class));
    }

}
