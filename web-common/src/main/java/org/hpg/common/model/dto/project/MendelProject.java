/*
 * To change this license header, choose License Headers in MendelProject Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.dto.project;

import java.io.Serializable;
import java.util.List;
import org.hpg.common.constant.MendelProjectStatus;
import org.hpg.common.constant.MendelReferScope;

/**
 * DTO class for project
 *
 * @author trungpt
 */
public class MendelProject implements Serializable {

    /**
     * Project code
     */
    private String code;

    /**
     * Project name
     */
    private String name;

    /**
     * Project description
     */
    private String description;

    /**
     * IDs of users to be assigned to the project
     */
    private List<Long> memberIDs;

    /**
     * Scope
     */
    private MendelReferScope referScope;

    /**
     * Status
     */
    private MendelProjectStatus status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getMemberIDs() {
        return memberIDs;
    }

    public void setMemberIDs(List<Long> memberIDs) {
        this.memberIDs = memberIDs;
    }

    public MendelReferScope getReferScope() {
        return referScope;
    }

    public void setReferScope(MendelReferScope referScope) {
        this.referScope = referScope;
    }

    public MendelProjectStatus getStatus() {
        return status;
    }

    public void setStatus(MendelProjectStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MendelProject{" + "code=" + code + ", name=" + name + ", description=" + description + ", memberIDs=" + memberIDs + ", referScope=" + referScope + ", status=" + status + '}';
    }
}
