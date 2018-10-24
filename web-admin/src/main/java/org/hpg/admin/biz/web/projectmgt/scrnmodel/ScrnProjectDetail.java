/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.admin.biz.web.projectmgt.scrnmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Detail information of one project
 *
 * @author trungpt
 */
public class ScrnProjectDetail implements Serializable {

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

    // TODO Add more fields
}
