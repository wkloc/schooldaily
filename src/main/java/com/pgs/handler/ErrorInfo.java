package com.pgs.handler;

/**
 * Created by wkloc on 2017-02-21.
 */
public class ErrorInfo {
    public final String url;
    public final String ex;

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
    }
}
