package src;

import IA.Comparticion.Usuarios;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.util.Pair;
import src.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import aima.util.Pair;



public class Main {

    public static ArrayList<Boolean> isConductor = new ArrayList<>(); //This must be global
    public static Usuarios nouUsuaris;    //this must be global
    public static int n = 100;
    public static int m = 50;
    public static int seed = 2;


    public static void main(String[] args) {
        nouUsuaris = new Usuarios(n, m, seed);
        fillDrivers();
        Map a = new Map();

        System.out.println("He creat un map");
        a.assignacioBasica();
        System.out.println("Abans de fer remove");

        for (int i=0; i<a.getEstatConductors().size();++i) System.out.print((int)((Pair) a.getEstatConductors().get(i).getFirst()).getSecond());
        ArrayList<Integer> aux = new ArrayList<Integer>();
        Pair a1 = new Pair(a.getEstatConductors().get(1).getFirst(),aux);
        a.getEstatConductors().set(0,a1);
        a.removeDriver(0);
        System.out.println();
        for (int i=0; i<a.getEstatConductors().size();++i) System.out.print((int)((Pair) a.getEstatConductors().get(i).getFirst()).getSecond());

        MapSuccesors ms = new MapSuccesors();
        ms.getSuccessors(a);



        a.getEstatConductors();
        MapHillClimbing1(a);
    }


    private static void MapHillClimbing1(Map m) {

        try {
            Problem problem = new Problem(m, new MapSuccesors(), new MapGoal(), new Heuristic1());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            printSolution((Map) search.getGoalState());

            System.out.println(checkSolution((Map) search.getGoalState()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printSolution(Map a) {
        ArrayList<Pair> b = a.getEstatConductors();
        for (Pair c : b) {
            Pair d = (Pair) c.getFirst();
            System.out.println("El conductor " + d.getSecond() + " fa " + d.getFirst());
            System.out.println("Ordre de recollida/arribada de passatgers");
            ArrayList<Integer> e = (ArrayList<Integer>) c.getSecond();
            for (Integer p : e)
                System.out.print(p + " ");
            System.out.println("\n");
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        System.out.println(actions.size());
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }


    /**
     * Private methods
     **/
    private static void fillDrivers() {
        for (int i = 0; i < n; ++i)
            isConductor.add(nouUsuaris.get(i).isConductor());
    }

    private static boolean checkSolution(Map a) {
        ArrayList<Pair> b = a.getEstatConductors();
        for (Pair c : b)    //iterate over all drivers
        {
            Pair d = (Pair) c.getFirst();
            if ((Integer) d.getFirst() > 300) return false;
        }

        ArrayList<Boolean> c = a.getEstaRecullit();
        for (Boolean r : c)
            if (!r) return false;

        return true;
    }
}