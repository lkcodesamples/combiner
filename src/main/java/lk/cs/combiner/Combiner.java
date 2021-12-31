/**
 *    Copyright 2021 Lukasz Kowalczyk
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
        if(input==null) {
            throw new IllegalArgumentException("input must be a list of lists of objects and cannot be null!");
        }

        int inputSize = input.size();
        int[] lengths = new int[inputSize];
        int[] counters = new int[inputSize];

        for(int i=0; i<inputSize; i++){
            lengths[i] = input.get(i).size();
        }

        return doCombine(input, counters, lengths);
    }

    private List<List<T>> doCombine(List<List<T>> input, int[] counters, int[] lengths){
        long total = connectionsNumber(lengths);

        List<List<T>> output = new ArrayList<>();
        List<T> line = new ArrayList<>();

        for(int c=1; c<=total; c++){
            for(int i=0; i<input.size(); i++){
                line.add(input.get(i).get(counters[i]));
            }
            output.add(new ArrayList<>(line));
            line.clear();

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

    private long connectionsNumber(int[] lengths){
        return Arrays.stream(lengths).asLongStream().reduce((a, c)->a*c).getAsLong();
    }
}
