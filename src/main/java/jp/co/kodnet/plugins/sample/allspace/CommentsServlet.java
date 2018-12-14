package jp.co.kodnet.plugins.sample.allspace;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.google.common.base.Preconditions;
import javax.inject.Inject;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author trinhnk
 */
@Scanned
public final class CommentsServlet extends HttpServlet {
    @ComponentImport
    private final ActiveObjects aos;
    
    @Inject
    public CommentsServlet(ActiveObjects aos){
        this.aos = Preconditions.checkNotNull(aos);
    }
}
