package jp.co.kodnet.plugins.sample.macros;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.confluence.util.i18n.I18NBean;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.spring.container.ContainerManager;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author trinhnk
 */
public class PagesTitleMacro implements Macro {

    private static final String PAGE_TITLE_MACRO = "view/pages-title-macro.vm";
    private PageManager pageManager = (PageManager) ContainerManager.getComponent("pageManager");
    private SpaceManager spaceManager = (SpaceManager) ContainerManager.getComponent("spaceManager");
    private I18NBean i18nBean;

    public I18NBean getI18nBean() {
        return i18nBean;
    }

    public void setI18nBean(I18NBean i18nBean) {
        this.i18nBean = i18nBean;
    }

    @Override
    public String execute(Map<String, String> map, String string, ConversionContext cc) throws MacroExecutionException {
        Map<String, Object> context = null;
        String param = map.get("Pages");

        if (param != null) {
            if (isContainColon(param)) {
                context = doGetPagesTitle(param, cc);
            } else {
                context = getPagesTitle(param, cc);
            }
        }else{
            context = MacroUtils.defaultVelocityContext();
            context.put("notFound", 1);
        }
        return VelocityUtils.getRenderedTemplate(PAGE_TITLE_MACRO, context);
    }

    private Map<String, String> getPagesMap(Page page) {
        Map<String, String> pagesMap = new HashMap<>();
        pagesMap.put(String.valueOf(page.getId()), page.getDisplayTitle());
        if (page.hasChildren()) {
            for (Page child : page.getChildren()) {
                pagesMap.put(String.valueOf(child.getId()), child.getDisplayTitle());
            }
        }
        return pagesMap;
    }

    //get pages title when there are no space key in parameter
    public Map<String, Object> getPagesTitle(String param, ConversionContext cc) {
        Map<String, Object> context = MacroUtils.defaultVelocityContext();
        String spaceKey = cc.getSpaceKey();
        Page page = pageManager.getPage(spaceKey, param);
        if (page != null) {
            context.put("pages", getPagesMap(page));
        } else {
            context.put("notFound", 1);
        }
        return context;
    }

    public Map<String, Object> doGetPagesTitle(String param, ConversionContext cc) {
        if (isPageOfCurrentSpace(param, cc)) {
            return getPagesTitle(param, cc);
        } else {
            return getPageInOtherSpace(param, cc);
        }
    }

    public boolean isPageOfCurrentSpace(String param, ConversionContext cc) {
        String spaceKey = cc.getSpaceKey();
        Page page = pageManager.getPage(spaceKey, param);
        if (page != null) {
            return true;
        }
        return false;
    }

    public Map<String, Object> getPageInOtherSpace(String param, ConversionContext cc) {
        Map<String, Object> context = MacroUtils.defaultVelocityContext();
        String key = param.substring(0, param.indexOf(":"));
        String pageTitle = param.substring(param.indexOf(":") + 1);
        Space space = spaceManager.getSpace(key);
        if (space != null) {
            Page page = pageManager.getPage(key, pageTitle);
            if (page != null) {
                context.put("pages", getPagesMap(page));
            } else {
                context.put("notFound", 1);
            }
            return context;
        }else{
            context.put("spaceNotFound", 1);
            return context;
        }
    }

    public boolean isContainColon(String param) {
        return param.contains(":");
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}
