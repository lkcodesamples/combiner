/*
 *    Copyright 2021, 2022 Lukasz Kowalczyk
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lk.cs.combiner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CombinerTest {

    Combiner<String> combiner;
    List<List<String>> data;

    public static final long EXPECTED_OUTPUT_SIZE = 18;
    public static final long EXPECTED_OUTPUT_SUBSET_EVERY_SECOND_SIZE = 9;
    public static final long EXPECTED_OUTPUT_SUBSET_EVERY_THIRD_SIZE = 6;

    @BeforeEach
    public void prepareData(){
        this.data = new ArrayList<>();
        this.data.add(Arrays.asList("a1","a2","a3"));
        this.data.add(Arrays.asList("b1","b2"));
        this.data.add(Arrays.asList("c1","c2","c3"));

        combiner = new Combiner<>();
    }

    @Test
    public void combinerNotNullTest(){
        Assertions.assertNotNull(combiner, "combiner is null!");
    }

    @Test
    public void combineTestOutputSize(){
        List<List<String>> output = this.combiner.combine(this.data);
        Assertions.assertEquals(EXPECTED_OUTPUT_SIZE, output.size(), "output size is different than expected!");
    }

    @Test
    public void combineTestExpectedOutput(){
        List<List<String>> output = this.combiner.combine(this.data);
        List<List<String>> expectedOutput = new ArrayList<>();

        expectedOutput.add(Arrays.asList("a1","b1","c1"));
        expectedOutput.add(Arrays.asList("a1","b1","c2"));
        expectedOutput.add(Arrays.asList("a1","b1","c3"));
        expectedOutput.add(Arrays.asList("a1","b2","c1"));
        expectedOutput.add(Arrays.asList("a1","b2","c2"));
        expectedOutput.add(Arrays.asList("a1","b2","c3"));
        expectedOutput.add(Arrays.asList("a2","b1","c1"));
        expectedOutput.add(Arrays.asList("a2","b1","c2"));
        expectedOutput.add(Arrays.asList("a2","b1","c3"));
        expectedOutput.add(Arrays.asList("a2","b2","c1"));
        expectedOutput.add(Arrays.asList("a2","b2","c2"));
        expectedOutput.add(Arrays.asList("a2","b2","c3"));
        expectedOutput.add(Arrays.asList("a3","b1","c1"));
        expectedOutput.add(Arrays.asList("a3","b1","c2"));
        expectedOutput.add(Arrays.asList("a3","b1","c3"));
        expectedOutput.add(Arrays.asList("a3","b2","c1"));
        expectedOutput.add(Arrays.asList("a3","b2","c2"));
        expectedOutput.add(Arrays.asList("a3","b2","c3"));

        for(List<String> ls : expectedOutput){
            Assertions.assertTrue(output.contains(ls));
        }
    }

    @Test
    public void combineTestWrongOutput(){
        List<List<String>> output = this.combiner.combine(data);
        List<List<String>> expectedOutput = new ArrayList<>();

        expectedOutput.add(Arrays.asList("b1","b1","c1"));
        expectedOutput.add(Arrays.asList("af","231","c2"));
        expectedOutput.add(Arrays.asList("a1","b1q","c3"));
        expectedOutput.add(Arrays.asList("a1","b2d","cc"));

        for(List<String> ls : expectedOutput){
            Assertions.assertFalse(output.contains(ls));
        }
    }

    @Test
    public void combinerTestResultSubsetSize(){
        List<List<String>> output = this.combiner.combine(this.data, 2);
        Assertions.assertEquals(EXPECTED_OUTPUT_SUBSET_EVERY_SECOND_SIZE, output.size(), "output subset size is different than expected!");
    }

    @Test
    public void combinerTestResultSubset(){
        List<List<String>> output = this.combiner.combine(data, 2);

        List<List<String>> expectedOutput = new ArrayList<>();

        expectedOutput.add(Arrays.asList("a1","b1","c2"));
        expectedOutput.add(Arrays.asList("a1","b2","c1"));
        expectedOutput.add(Arrays.asList("a1","b2","c3"));
        expectedOutput.add(Arrays.asList("a2","b1","c2"));
        expectedOutput.add(Arrays.asList("a2","b2","c1"));
        expectedOutput.add(Arrays.asList("a2","b2","c3"));
        expectedOutput.add(Arrays.asList("a3","b1","c2"));
        expectedOutput.add(Arrays.asList("a3","b2","c1"));
        expectedOutput.add(Arrays.asList("a3","b2","c3"));

        for(List<String> ls : expectedOutput){
            Assertions.assertTrue(output.contains(ls));
        }

    }

    @Test
    public void combinerTestResultSubsetEveryThirdSize(){
        List<List<String>> output = this.combiner.combine(this.data, 3);
        Assertions.assertEquals(EXPECTED_OUTPUT_SUBSET_EVERY_THIRD_SIZE, output.size(), "output subset size is different than expected!");
    }

    @Test
    public void combinerTestResultSubsetEveryThird(){
        List<List<String>> output = this.combiner.combine(data, 3);

        List<List<String>> expectedOutput = new ArrayList<>();

        expectedOutput.add(Arrays.asList("a1","b1","c3"));
        expectedOutput.add(Arrays.asList("a1","b2","c3"));
        expectedOutput.add(Arrays.asList("a2","b1","c3"));
        expectedOutput.add(Arrays.asList("a2","b2","c3"));
        expectedOutput.add(Arrays.asList("a3","b1","c3"));
        expectedOutput.add(Arrays.asList("a3","b2","c3"));

        for(List<String> ls : expectedOutput){
            Assertions.assertTrue(output.contains(ls));
        }

    }
}
