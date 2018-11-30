package jp.co.kodnet.plugins.sample.allspace;

import aQute.bnd.annotation.component.Component;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.user.ConfluenceUser;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.spring.container.ContainerManager;
import com.opensymphony.xwork.ActionContext;

/**
 *
 * @author trinhnk
 */
@Scanned
@Component
public class CreateSpace extends ConfluenceActionSupport {

    private SpaceManager spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");

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

        ConfluenceUser user = getAuthenticatedUser();
        spaceManager.createSpace(spaceKey, spaceName, "new space", user);
        return SUCCESS;
    }

    @Override
    public ConfluenceUser getAuthenticatedUser() {
        return super.getAuthenticatedUser();
    }
    
}
