package jp.co.kodnet.plugins.sample.allspace;

import net.java.ao.Entity;

/**
 *
 * @author trinhnk
 */
public interface Comments extends Entity{
    String getDescription();
    void setDescription();
    boolean isComplete();
    void setComplete();
}
