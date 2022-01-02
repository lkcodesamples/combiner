/*
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
import java.util.Scanner;

public class CombinerMain {

    public static void main(String[] args){
       sampleUsageWithStrings();
    }

    private static void sampleUsageWithStrings(){

        Combiner<String> combiner = new Combiner<>();
        Scanner scanner = new Scanner(System.in);

        List<List<String>> input = new ArrayList<>();
        System.out.println("Please provide three lists of comma separated strings.");

        for(int i=0; i<3; i++){
            System.out.print("Provide a list of comma separated strings and press ENTER:");
            input.add(new ArrayList<>(Arrays.asList(scanner.nextLine().split(","))));
        }
        scanner.close();

        List<List<String>> output = combiner.combine(input);
        System.out.println("\nBelow the output:");
        System.out.println(output);
    }
}
