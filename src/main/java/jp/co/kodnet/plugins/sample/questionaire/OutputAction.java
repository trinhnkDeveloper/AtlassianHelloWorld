package jp.co.kodnet.plugins.sample.questionaire;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.opensymphony.xwork.ActionContext;

/**
 *
 * @author trinhnk
 */
public class OutputAction extends ConfluenceActionSupport{
    private String name;
    
    private String getParameterValue(ActionContext context, String key){
        Object value = context.getParameters().get(key);
        System.out.println(value);
        if(value instanceof String){
            return (String)value;
        }
        return null;
    }

    @Override
    public String execute() throws Exception {
        ActionContext context = ActionContext.getContext();
        this.name = this.getParameterValue(context, "name");
        return SUCCESS;
    }
}
