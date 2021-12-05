package com.sara.project.common.nettyutil;

public interface IClient<T> {
    /**
     * connect
     *
     * @throws Exception exception
     */
    void connect() throws Exception;

    /**
     * disconnect
     */
    void disconnect();

    /**
     * send T to Weight Module
     *
     * @param data send data
     * @throws Exception exception
     */
    void send(T data) throws Exception;
}
