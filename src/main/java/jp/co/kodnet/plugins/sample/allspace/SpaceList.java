package jp.co.kodnet.plugins.sample.allspace;

import aQute.bnd.annotation.component.Component;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.util.i18n.I18NBean;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.spring.container.ContainerManager;

/**
 *
 * @author trinhnk
 */
@Scanned
@Component
public class SpaceList extends ConfluenceActionSupport {

    private SpaceManager manager = (SpaceManager) ContainerManager.getComponent("spaceManager");
    private I18NBean i18nBean;
    
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
    
    @Override
    public String execute() throws Exception {
        if (manager == null) {
            return ERROR;
        }
        return SUCCESS;
    }
}
