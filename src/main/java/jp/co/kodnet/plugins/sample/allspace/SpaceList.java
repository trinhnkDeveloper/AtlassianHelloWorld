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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author trinhnk
 */
public class SpaceList extends ConfluenceActionSupport implements Beanable{

    private SpaceManager manager = (SpaceManager) ContainerManager.getComponent("spaceManager");
    private I18NBean i18nBean;
    private PageManager pageManager = (PageManager) ContainerManager.getComponent("pageManager");
    private String action;
    private List<Page> pages;
    private Map<String, Map> attachments;
    
    @Override
    public String execute() throws Exception {
        ActionContext context = ActionContext.getContext();
        System.out.println("API js");
        checkOptions(context);
        if (manager == null) {
            return ERROR;
        }
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
    
    public PageManager getPageManager(){
        return pageManager;
    }
    
    public String getAction() {
        return action;
    }

    public List<Page> getPages() {
        return pages;
    }
    
    public String getParameter(ActionContext context, String key){
        Object obj = context.getParameters().get(key);
        if(obj instanceof String[] && ((String[]) obj).length != 0){
            return ((String[]) obj)[0];
        }else if(obj instanceof String){
            return (String) obj;
        }
        return null;
    }
    
    
    public void checkOptions(ActionContext context){
        String option = getParameter(context, "option");
        if(option == null){
            return;
        }
        switch(option){
            case "1":
                String spaceKey = getParameter(context, "spacekey");
                Space space = manager.getSpace(spaceKey);
                pages = pageManager.getPages(space, true);
                action = "pages";
                break;
            case "2":
                System.out.println("option 2");
                int i = 1;
                String pageKey = getParameter(context, "pagekey");
                System.out.println(pageKey);
                Page page = pageManager.getPage(Long.parseLong(pageKey));
                List<Attachment> attachList = page.getAttachments();
                for(Attachment a : attachList){
                    System.out.println("attach");
                    Map<String, String> attachment = new HashMap<>();
                    attachment.put("name", a.getDisplayTitle());
                    attachment.put("size", String.valueOf(a.getFileSize()));
                    attachment.put("creator", a.getCreator().getName());
                    attachment.put("creationdate", String.valueOf(a.getCreationDate()));
                    attachment.put("label", String.valueOf(a.getLabelCount()));
                    attachments.put("attachment" + i++, attachment);
                }
                break;
            default:
        }
    }

    @Override
    public Object getBean() {
        return attachments;
    }
}
