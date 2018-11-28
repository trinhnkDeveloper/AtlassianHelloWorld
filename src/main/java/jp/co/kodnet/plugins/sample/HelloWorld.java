package jp.co.kodnet.plugins.sample;

import com.atlassian.confluence.core.ConfluenceActionSupport;

/**
 *
 * @author trinhnk
 */
public class HelloWorld extends ConfluenceActionSupport{

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
}
