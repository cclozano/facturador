package com.aurora.framework.vaadinext.crud;

import java.io.Serializable;

/**
 * Created by carlos on 13/06/17.
 */
public interface EditEndListener<T> extends Serializable {

    public void editEnd(T item);

}