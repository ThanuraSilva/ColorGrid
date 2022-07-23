package com.thanu.home;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class ColorProcess {
    private HashMap<String, List<Pair<Integer, Integer>>> getNodeMap(Node[] nodeArray){
        HashMap<String, List<Pair<Integer, Integer>>> colorMap = new HashMap<>();
        for (Node node: nodeArray){
            if(colorMap.get(node.getColor())!=null){
                List<Pair<Integer, Integer>> pairList = colorMap.get(node.getColor());
                pairList.add(new Pair<>(node.getX(), node.getY()));
            }else{
                colorMap.put(
                        node.getColor(),
                        Collections.singletonList(new Pair<>(node.getX(), node.getY()))
                );
            }
        }
        return colorMap;
    }

    private HashMap<String, Integer> readMap(int col, int row, HashMap<String, List<Pair<Integer, Integer>>> colorMap){
        HashMap<String, Integer> neighboursVsColorMap = new HashMap<>();
        for(String color: colorMap.keySet()){
            neighboursVsColorMap.put(color, 0);
            int colorNodeCount = colorMap.get(color).size();
            List<Pair<Integer, Integer>> axisList = colorMap.get(color);
            for (Pair<Integer, Integer> pair: axisList){
                List<Pair<Integer, Integer>> adjacentNodes = getAdjacentNodes(pair, col, row);
                try{
                    Pair<Integer, Integer> neibour = adjacentNodes.stream()
                            .filter(node -> node.getKey() == pair.getKey() && node.getValue() == pair.getValue() )
                            .findFirst()
                            .get();
                    if (neibour!=null){
                        int neighboursCount= neighboursVsColorMap.get(color);
                        neighboursVsColorMap.put(color, neighboursCount+=1);
                    }
                }catch (NullPointerException e){
                    // No such neibour in the list
                }

            }
        }
        return neighboursVsColorMap;
    }

    private List<Pair<Integer, Integer>> getAdjacentNodes(Pair<Integer, Integer> pair, int col, int row){
        List<Pair<Integer, Integer>> adjacentList = new ArrayList<>();
        if(pair.getKey() == 0 && pair.getValue()  == 0){ // Top Left
            adjacentList.add(new Pair<>(0, 1));
            adjacentList.add(new Pair<>(1, 0));
        }else if(pair.getKey() == 0 && pair.getValue()  == col){ // Top Right
            adjacentList.add(new Pair<>(0, col-1));
            adjacentList.add(new Pair<>(1, col));
        }else if (pair.getKey() == 0 && pair.getValue()  == row){ // Bottom Left
            adjacentList.add(new Pair<>(0, row-1));
            adjacentList.add(new Pair<>(1,row));
        }else if (pair.getKey() == row && pair.getValue() == col){ // Bottom right
            adjacentList.add(new Pair<>(col, row-1));
            adjacentList.add(new Pair<>(col-1,row));
        } else if (pair.getKey() == 0) { // Top edge
            adjacentList.add(new Pair<>(pair.getKey()-1, pair.getValue() ));
            adjacentList.add(new Pair<>(pair.getKey()+1, pair.getValue() ));
            adjacentList.add(new Pair<>(pair.getKey(), pair.getValue() +1));
        }else if ((pair.getKey() == row )) { // Bottom edge
            adjacentList.add(new Pair<>(pair.getKey()-1, pair.getValue() ));
            adjacentList.add(new Pair<>(pair.getKey()+1, pair.getValue() ));
            adjacentList.add(new Pair<>(pair.getKey(), pair.getValue() -1));
        }else if ((pair.getValue()  == 0 )) { // Left edge
            adjacentList.add(new Pair<>(pair.getKey(), pair.getValue() -1));
            adjacentList.add(new Pair<>(pair.getKey(), pair.getValue() +1));
            adjacentList.add(new Pair<>(pair.getKey()+1, pair.getValue() ));
        }else if ((pair.getKey() == col )) { // Right edge
            adjacentList.add(new Pair<>(pair.getKey(), pair.getValue() -1));
            adjacentList.add(new Pair<>(pair.getKey(), pair.getValue() +1));
            adjacentList.add(new Pair<>(pair.getKey()-1, pair.getValue() ));
        }else { // Center
            adjacentList.add(new Pair<>(pair.getKey(), pair.getValue() -1));
            adjacentList.add(new Pair<>(pair.getKey()+1, pair.getValue() ));
            adjacentList.add(new Pair<>(pair.getKey()-1, pair.getValue() ));
            adjacentList.add(new Pair<>(pair.getKey()-1, pair.getValue() ));
        }
        return adjacentList;
    }

    private String getLargestConnectingBlock(HashMap<String, Integer> neighboursVsColorMap){
        return neighboursVsColorMap.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
    }

    public String findColor(Node[] nodeArray, int col, int row){
        HashMap<String, Integer> ColoNeighbourCountList = readMap(col, row, getNodeMap(nodeArray));
        return getLargestConnectingBlock(ColoNeighbourCountList);
    }

    /*
     * Main method is optional and for testing purpose
     * findColor() is the utility function that can be accessed from outside.
     * It will get input a color grid and return a largest connected block
     * */
    public static void main(String[] args) {
        ColorProcess a = new ColorProcess();
//        System.out.println("Color which have max neighbours: " + a.findColor());
    }
}

