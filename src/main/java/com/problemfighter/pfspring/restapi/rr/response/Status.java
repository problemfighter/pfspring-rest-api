package com.problemfighter.pfspring.restapi.rr.response;

public enum Status {
    success("success"),
    warning("warning"),
    info("info"),
    error("error"),
    partial("partial");

    public final String label;
    private Status(String label) {
        this.label = label;
    }
}
