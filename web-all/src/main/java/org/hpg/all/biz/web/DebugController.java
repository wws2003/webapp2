/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.all.biz.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for debug purpose
 *
 * @author trungpt
 */
@Controller
@RequestMapping("debug")
public class DebugController {

    @GetMapping("/")
    @ResponseBody
    public String debug() {
        return "Some string to debug";
    }

    @GetMapping("/sample")
    public String sample() {
        return "debug";
    }
}
