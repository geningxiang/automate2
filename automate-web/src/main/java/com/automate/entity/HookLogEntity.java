package com.automate.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:21
 */
@Entity
@Table(name = "CA2_HOOK_LOG")
public class HookLogEntity {

    public enum HandleStatus{
        /**
         * 忽略   (版本控制的地址还没有添加到项目)
         */
        IGNORE,
        /**
         * 已处理
         */
        PROCESSED,
        /**
         * 发生错误 (一般不会出现该状态)
         */
        ERROR
    }

    private Integer id;
    /**
     * 来源   从 user-agent 读取
     * GitHub-Hookshot
     * GogsServer
     */
    private String source;
    /**
     * 编号
     * x-github-delivery
     * x-gogs-delivery
     */
    private String delivery;
    /**
     * 收到的请求头部
     */
    private String requestHeader;
    /**
     * 收到的请求主体
     */
    private String requestBody;
    /**
     * 返回的数据  暂时没啥用
     */
    private String response;

    /**
     * 事件名称 不同的源 命名会不一样
     */
    private String event;

    /**
     * 处理状态
     */
    private Byte handleStatus;
    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 关联的项目    如果可以关联上
     */
    private Integer projectId;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SOURCE", nullable = true, length = 32)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "DELIVERY", nullable = true, length = 64)
    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    @Basic
    @Column(name = "REQUEST_HEADER", nullable = true, length = 1024)
    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    @Basic
    @Column(name = "REQUEST_BODY", nullable = true, length = -1)
    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Basic
    @Column(name = "RESPONSE", nullable = true, length = 1024)
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Basic
    @Column(name = "HANDLE_STATUS", nullable = true)
    public Byte getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Byte handleStatus) {
        this.handleStatus = handleStatus;
    }

    public void setHandleStatus(HandleStatus handleStatus) {
        this.handleStatus = (byte)handleStatus.ordinal();
    }

    @Basic
    @Column(name = "HANDLE_RESULT", nullable = true, length = 64)
    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    @Basic
    @Column(name = "EVENT", nullable = true, length = 32)
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Basic
    @Column(name = "PROJECT_ID", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}
