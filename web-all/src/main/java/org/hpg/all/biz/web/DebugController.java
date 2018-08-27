/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.all.biz.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return "all/debug";
    }

    @PostMapping("/postTest")
    @ResponseBody
    public Map<String, Integer> postTest(@RequestBody PostForm1 form) {
        Map<String, Integer> ret = new HashMap();
        ret.put("abc", 123);
        return ret;
    }

    /**
     * TEST TEST
     */
    public class PostForm1 {

        private int val;

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }
}
