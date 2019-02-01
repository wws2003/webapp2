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
import org.springframework.web.context.request.async.DeferredResult;

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

    @GetMapping("/testAsync")
    @ResponseBody
    public DeferredResult<String> testAsync() {
        // Work without any problem, why does not work in project module ?
        DeferredResult<String> ret = new DeferredResult(100L);
        try {
            ret.setResult("1234");
        } catch (Exception e) {
            ret.setErrorResult(e);
        }
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
