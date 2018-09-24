/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.misc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hpg.common.WebCommonTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for small stuffs
 *
 * @author trungpt
 */
@RunWith(SpringRunner.class)
public class MiscTest extends WebCommonTestBase {

    @Test
    public void nonUniqueListToSet() {
        List<Integer> list = Arrays.asList(1, 1, 2, 3, 2);
        Set<Integer> set = list.stream().collect(Collectors.toSet());
        assert (set.size() == 3);
        assert (set.contains(1));
        assert (set.contains(2));
        assert (set.contains(3));
        assert (!set.contains(4));
    }
}
