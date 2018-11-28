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
        System.out.println("i was here");
        System.out.println("this is value: " + value.toString());
       if(value instanceof String[] && ((String[])value).length != 0){
           System.out.println("but i can go here");
           return ((String[])value)[0];
       }else if(value instanceof String){
           System.out.println("no i go here");
           return (String)value;
       }
        return null;
    }

    @Override
    public String execute() throws Exception {
        ActionContext context = ActionContext.getContext();
        this.name = this.getParameterValue(context, "txtName");
        System.out.println("this is name: " + this.name);
        if(this.name == null || "".equals(this.name)){
            return ERROR;
        }
        return SUCCESS;
    }
    
    public String getName(){
        return name;
    }
}
