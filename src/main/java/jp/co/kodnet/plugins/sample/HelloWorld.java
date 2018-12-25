package jp.co.kodnet.plugins.sample;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.spring.container.ContainerManager;


/**
 *
 * @author trinhnk
 */
public class HelloWorld extends ConfluenceActionSupport{
    private SettingsManager settingsManager = (SettingsManager) ContainerManager.getComponent("settingsManager");

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
}
