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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class implementing ICombiner<T> interface
 * @param <T>
 */
public class Combiner<T> implements ICombiner<T> {

    /**
     * Combines objects from the given input according to the specific pattern, e.g.:
     * input: [[a1,a2],[b1,b2],[c1,c2]]
     * output: [[a1,b1,c1],[a1,b1,c2],[a1,b2,c1],[a1,b2,c2],[a2,b1,c1],[a2,b1,c2],[a2,b2,c1],[a2,b2,c2]]
     *
     * @param input
     * @return List<List<T>>
     */
    @Override
    public List<List<T>> combine(List<List<T>> input) {
        return combine(input, 1);
    }

    /**
     *  Combines objects from the given input according to the specific pattern, e.g.:
     *  input: [[a1,a2],[b1,b2],[c1,c2]]
     *  output: [[a1,b1,c1],[a1,b1,c2],[a1,b2,c1],[a1,b2,c2],[a2,b1,c1],[a2,b1,c2],[a2,b2,c1],[a2,b2,c2]]
     *  and returns only every k element from the resulting list
     *
     * @param input
     * @param k
     * @return
     */
    @Override
    public List<List<T>> combine(List<List<T>> input, int k) {
        inputNotNullCheck(input);

        int[] lengths = calculateLengths(input);
        int[] counters = initializeCounters(input);

        return doCombine(input, counters, lengths, k);
    }

    private List<List<T>> doCombine(List<List<T>> input, int[] counters, int[] lengths, int k){
        long total = connectionsNumber(lengths);

        List<List<T>> output = new ArrayList<>();
        List<T> line = new ArrayList<>();

        for(int c=1; c<=total; c++){
            if(c % k == 0) {
                for (int i = 0; i < input.size(); i++) {
                    line.add(input.get(i).get(counters[i]));
                }
                output.add(new ArrayList<>(line));
                line.clear();
            }

            for(int j=input.size()-1; j>=0; j--){
                if(counters[j]<lengths[j]-1){
                    counters[j]++;
                    break;
                } else {
                    counters[j]=0;
                }
            }
        }
        return output;
    }

    private void inputNotNullCheck(List<List<T>> input){
        if(input==null) {
            throw new IllegalArgumentException("input must be a list of lists of objects and cannot be null!");
        }
    }

    private int[] calculateLengths(List<List<T>> input){
        int[] lengths = new int[input.size()];

        for(int i=0; i<input.size(); i++){
            lengths[i] = input.get(i).size();
        }

        return lengths;
    }

    private int[] initializeCounters(List<List<T>> input){
        return new int[input.size()];
    }

    private long connectionsNumber(int[] lengths){
        return Arrays.stream(lengths).asLongStream().reduce((a, c)->a*c).getAsLong();
    }
}
