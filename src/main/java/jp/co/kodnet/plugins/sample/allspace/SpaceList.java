package jp.co.kodnet.plugins.sample.allspace;

import com.atlassian.confluence.core.Beanable;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.pages.Attachment;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.util.i18n.I18NBean;
import com.atlassian.spring.container.ContainerManager;
import com.opensymphony.xwork.ActionContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author trinhnk
 */
public class SpaceList extends ConfluenceActionSupport implements Beanable {

    private SpaceManager manager = (SpaceManager) ContainerManager.getComponent("spaceManager");
    private I18NBean i18nBean;
    private PageManager pageManager = (PageManager) ContainerManager.getComponent("pageManager");
    private String action;
    private List<Map<String, String>> jsonResult;
    private static String message;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        SpaceList.message = message;
    }

    @Override
    public String execute() throws Exception {
        ActionContext context = ActionContext.getContext();
        if (manager == null) {
            return ERROR;
        }
        checkOptions(context);
        return SUCCESS;
    }

    @Override
    public I18NBean getI18n() {
        return i18nBean;
    }

    @Override
    public void setI18NBean(I18NBean i18NBean) {
        this.i18nBean = i18NBean;
    }

    public SpaceManager getManager() {
        return manager;
    }

    public PageManager getPageManager() {
        return pageManager;
    }

    public String getAction() {
        return action;
    }

    @Override
    public Object getBean() {
        return jsonResult;
    }

    public String getParameter(ActionContext context, String key) {
        Object obj = context.getParameters().get(key);
        if (obj instanceof String[] && ((String[]) obj).length != 0) {
            return ((String[]) obj)[0];
        } else if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    public void checkOptions(ActionContext context) {
        String option = getParameter(context, "option");
        if (option == null) {
            return;
        }
        switch (option) {
            case "1":
                handleOption1(context);
                break;
            case "2":
                handleOption2(context);
                break;
            case "3":
                handleOption3(context);
                break;
            default:
        }
    }

    /**
     * Get all pages in space
     *
     * @param context
     */
    public void handleOption1(ActionContext context) {
        jsonResult = new ArrayList<>();
        String spaceKey = getParameter(context, "spacekey");
        Space space = manager.getSpace(spaceKey);
        List<Page> pages = pageManager.getPages(space, true);
        Map<String, String> pagesMap = new HashMap<>();
        for (Page temp : pages) {
            pagesMap.put(String.valueOf(temp.getId()), temp.getDisplayTitle());
        }
        jsonResult.add(pagesMap);
        action = "pages";
    }

    /**
     * Get all attachments in page
     *
     * @param context
     */
    public void handleOption2(ActionContext context) {
        jsonResult = new ArrayList<>();
        String pageId = getParameter(context, "pageid");
        Page page = pageManager.getPage(Long.parseLong(pageId));
        List<Attachment> attachmentList = page.getAttachments();
        for (Attachment temp : attachmentList) {
            Map<String, String> attachment = new LinkedHashMap<>();
            attachment.put("name", temp.getDisplayTitle());
            attachment.put("size", String.valueOf(temp.getFileSize()));
            if (temp.getCreatorName() == null) {
                attachment.put("creator", "");
            } else {
                attachment.put("creator", temp.getCreatorName());
            }
            attachment.put("creationDate", String.valueOf(temp.getCreationDate()));
            attachment.put("downloadPath", temp.getDownloadPath());
            jsonResult.add(attachment);
        }
    }

    /**
     * Delete space
     *
     * @param context
     */
    public void handleOption3(ActionContext context) {
        String spaceKey = getParameter(context, "spacekey");
        Space delSpace = manager.getSpace(spaceKey);
        manager.removeSpace(delSpace);
    }
}
