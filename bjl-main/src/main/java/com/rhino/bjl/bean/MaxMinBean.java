package com.rhino.bjl.bean;

public class MaxMinBean {
    private Integer maxCount;
    private Integer minCount;

    public MaxMinBean(Integer maxCount,Integer minCount) {
        this.maxCount = maxCount;
        this.minCount = minCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }


    public Integer getMinCount() {
        return minCount;
    }

}
