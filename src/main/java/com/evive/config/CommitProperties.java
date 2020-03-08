package com.evive.config;

import com.google.common.base.MoreObjects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommitProperties {
    @Value("${git.commit.id.describe:NA}")
    private String describe;
    @Value("${git.commit.id.describe-short:NA}")
    private String describeShort;
    @Value("${git.commit.message.full:NA}")
    private String fullMessage;
    @Value("${git.commit.id:NA}")
    private String id;
    @Value("${git.commit.id.abbrev:NA}")
    private String idAbbrev;
    @Value("${git.commit.message.short:NA}")
    private String shortMessage;
    @Value("${git.commit.time:NA}")
    private String time;
    @Value("${git.commit.user.email:NA}")
    private String userEmail;
    @Value("${git.commit.user.name:NA}")
    private String userName;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescribeShort() {
        return describeShort;
    }

    public void setDescribeShort(String describeShort) {
        this.describeShort = describeShort;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAbbrev() {
        return idAbbrev;
    }

    public void setIdAbbrev(String idAbbrev) {
        this.idAbbrev = idAbbrev;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("idAbbrev", idAbbrev)
                .add("describe", describe)
                .add("describeShort", describeShort)
                .add("fullMessage", fullMessage)
                .add("shortMessage", shortMessage)
                .add("time", time)
                .add("userEmail", userEmail)
                .add("userName", userName)
                .toString();
    }
}
