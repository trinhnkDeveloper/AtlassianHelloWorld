package jp.co.kodnet.plugins.sample.allspace;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.spaces.SpaceStatus;
import com.atlassian.confluence.util.i18n.I18NBean;
import com.atlassian.spring.container.ContainerManager;
import com.atlassian.user.User;
import com.atlassian.user.UserManager;
import com.opensymphony.xwork.ActionContext;
import java.util.List;

/**
 *
 * @author trinhnk
 */
public class CreateSpace extends ConfluenceActionSupport {

    private SpaceManager spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");
    private UserManager userManager;
    private I18NBean i18nBean;

    public I18NBean getI18nBean() {
        return i18nBean;
    }

    public void setI18nBean(I18NBean i18nBean) {
        this.i18nBean = i18nBean;
    }

    public SpaceManager getSpaceManager() {
        return spaceManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    private String getParameter(ActionContext context, String key) {
        Object value = context.getParameters().get(key);
        if (value instanceof String[] && ((String[]) value).length != 0) {
            return ((String[]) value)[0];
        } else if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    @Override
    public String execute() throws Exception {
        ActionContext context = ActionContext.getContext();
        String spaceName = getParameter(context, "txtSpaceName");
        String spaceKey = getParameter(context, "txtSpaceKey");
        if (Space.isValidSpaceKey(spaceKey)) {
            List<String> spaceKeys = (List<String>) spaceManager.getAllSpaceKeys(SpaceStatus.CURRENT);
            for (String temp : spaceKeys) {
                if (temp.equalsIgnoreCase(spaceKey)) {
                    SpaceList.setMessage("helloworld.lang.errorMessage.dulicate");
                    return ERROR;
                }
            }
            User user = getAuthenticatedUser();
            Space freshSpace = spaceManager.createSpace(spaceKey, spaceName, "", user);
            return SUCCESS;
        }
        SpaceList.setMessage("helloworld.lang.errorMessage.invalid");
        return ERROR;
    }
}
