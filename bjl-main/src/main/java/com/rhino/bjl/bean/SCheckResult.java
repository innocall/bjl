package com.rhino.bjl.bean;

/**
 * 数据查询返回值
 */
public class SCheckResult {

    /**
     * resultStatus : 1
     * msg :
     * object : {"a":0,"b":0,"c":0,"type":""}
     */

    private int resultStatus;
    private String msg;
    private ObjectBean object;

    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * a : 0
         * b : 0
         * c : 0
         * type :
         */

        private int a;
        private int b;
        private int c;
        private String type;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
