package jp.co.kodnet.plugins.sample.todo;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import static com.google.common.base.Preconditions.checkNotNull;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author trinhnk
 */
@Scanned
public final class TodoServlet extends HttpServlet{
    @ComponentImport
    private final ActiveObjects ao;
    
    @Inject
    public TodoServlet(ActiveObjects ao){
        this.ao = checkNotNull(ao);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        final PrintWriter w = res.getWriter();
        w.write("<h1>Todos</h1>");

        // the form to post more TODOs
        w.write("<form method=\"post\">");
        w.write("<input type=\"text\" name=\"task\" size=\"25\"/>");
        w.write("  ");
        w.write("<input type=\"submit\" name=\"submit\" value=\"Add\"/>");
        w.write("</form>");

        w.write("<ol>");
        
        ao.executeInTransaction(new TransactionCallback<Void>(){
            @Override
            public Void doInTransaction() {
                for(Todo todo : ao.find(Todo.class)){
                    w.printf("<li> %s </li>", todo.getComment());
                }
                return null;
            }
        });
        w.write("</ol>");
        w.write("<script language='javascript'>document.forms[0].elements[0].focus();</script>");

        w.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        res.getWriter().write("Todo servlet, doPost");
        res.getWriter().close();
    }
}
