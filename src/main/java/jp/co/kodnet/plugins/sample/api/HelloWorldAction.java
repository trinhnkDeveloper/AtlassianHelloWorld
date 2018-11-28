package jp.co.kodnet.plugins.sample.api;

import com.atlassian.confluence.core.ConfluenceActionSupport;

/**
 *
 * @author trinhnk
 */
public class HelloWorldAction extends ConfluenceActionSupport {

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
}
