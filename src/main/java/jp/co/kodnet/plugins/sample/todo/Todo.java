package jp.co.kodnet.plugins.sample.todo;

import net.java.ao.Entity;

/**
 *
 * @author trinhnk
 */
public interface Todo extends Entity{
    String getComment();
    void setComment(String comment);
}
