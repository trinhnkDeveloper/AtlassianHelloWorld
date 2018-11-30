package jp.co.kodnet.plugins.sample.allspace;

import aQute.bnd.annotation.component.Component;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.spring.container.ContainerManager;
import com.atlassian.user.User;
import com.atlassian.user.UserManager;
import com.opensymphony.xwork.ActionContext;
import java.util.Iterator;

/**
 *
 * @author trinhnk
 */
@Scanned
@Component
public class CreateSpace extends ConfluenceActionSupport {

    private SpaceManager spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");
    private UserManager userManager = (UserManager) ContainerManager.getComponent("userManager");

    public SpaceManager getSpaceManager() {
        return spaceManager;
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
        
        Iterator<User> users = userManager.getUsers().iterator();
        User user = null;
        while (users.hasNext()) {
            user = users.next();
        }
        if (user != null) {
            spaceManager.createSpace(spaceKey, spaceName, "new Space", user);
        } else{
            return ERROR;
        }
        return SUCCESS;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
