package jp.co.kodnet.plugins.sample.macros;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
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
        Map<String, Object> context = MacroUtils.defaultVelocityContext();
        String pageName = map.get("Pages");
        String spaceKey = cc.getSpaceKey();
        if (pageName != null) {
            Map<String, String> pagesMap = getPagesMap(pageName, spaceKey);
            context.put("pages", pagesMap);
        }
        return VelocityUtils.getRenderedTemplate(PAGE_TITLE_MACRO, context);
    }

    private Map<String, String> getPagesMap(String pageName, String spaceKey) {
        Map<String, String> pagesMap = new HashMap<>();
        Page page = pageManager.getPage(spaceKey, pageName);
        if (page != null) {
            pagesMap.put(String.valueOf(page.getId()), page.getDisplayTitle());
            if (page.hasChildren()) {
                for (Page child : page.getChildren()) {
                    pagesMap.put(String.valueOf(child.getId()), child.getDisplayTitle());
                }
            }
        }
        return pagesMap;
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
