package com.sara.project.common.nettyutil;

public abstract class AbstractClient <T> implements IClient<T> {
    /**
     * return connected status
     *
     * @return true/false
     */
    public abstract boolean isConnected();

}
