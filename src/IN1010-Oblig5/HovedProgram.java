import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import org.apache.commons.math3.stat.inference.BinomialTest;
import org.apache.commons.math3.stat.inference.AlternativeHypothesis;

public class HovedProgram {
    //antall tråder som leser filene.
    public static final int K = 8;

    //lengde på subsekvensene
    public static final int subStringLengde = 3;

    //pathen til datafilene. må endres hvis programmet skal kjøres med en annen struktur.
    public static final String filePrefix = "Data/";

    public static void main(String[] args){
        try {
            //antall kombinator-tråder
            int numCombinerThreads = Integer.parseInt(args[0]);

            //det må være minst en tråd per HashMapContainer
            if(numCombinerThreads <2){
                System.out.println("Minimum 2 kombinator-tråder.");
                System.exit(0);
            }

            //containere for positive og negative personer
            HashMapContainer positive = new HashMapContainer();
            HashMapContainer negative = new HashMapContainer();

            //BufferedReader er thread-safe
            BufferedReader fil = new BufferedReader(new FileReader(filePrefix+"metadata.csv"));
            fil.readLine();

            //teller antall ferdige leser-tråder
            CountDownLatch readerFinished = new CountDownLatch(K);

            //starter leser-trådene
            for (int i = 0; i<K; i++) {
                new Thread(new FileHandler(fil, positive, negative, subStringLengde,filePrefix , readerFinished)).start();
            }

            //array med trådene som skal manuelt avbrytes
            Thread[] threads = new Thread[numCombinerThreads];

            //teller antall ferdige kombinator-tråder
            CountDownLatch combinerFinished = new CountDownLatch(numCombinerThreads);


            //starter kombinator-trådene.
            for (int i = 0; i< numCombinerThreads; i++){
                if (i< numCombinerThreads /2) {
                    threads[i] = new Thread(new Combiner(positive, combinerFinished));
                } else {
                    threads[i] = new Thread(new Combiner(negative, combinerFinished));
                }
                threads[i].start();
            }



            //venter på at alle leser-tråder skal bli ferdige
            readerFinished.await();



            //forteller kombinator-trådene at leser-trådene er ferdige
            for (Thread thread : threads) {
                thread.interrupt();
            }


            //venter på at kombinator-trådene blir ferdige
            combinerFinished.await();


            HashMap<String, SubSekvens> phm = positive.taUtHashMap();
            HashMap<String, SubSekvens> nhm = negative.taUtHashMap();

            //enkel test
            /*
            phm.forEach((key, val) -> {
                int v1 = val.getAntall();
                int v2 = nhm.containsKey(key)?nhm.get(key).getAntall():0;
                if(v1-v2>=5){
                    System.out.println(key+" "+v1+" "+v2+" "+(v1-v2));
                }
            });
            */

            //binomial test
            BinomialTest binom = new BinomialTest();
            phm.forEach((key, val) -> {
                int v1 = val.getAntall();
                int v2 = nhm.containsKey(key)?nhm.get(key).getAntall():0;
                double p = binom.binomialTest((v1+v2),v1,0.5, AlternativeHypothesis.GREATER_THAN);
                if (p<0.05){
                    System.out.println(key+" "+v1);
                }
            });


        } catch (InterruptedException | IOException e){
            System.out.println(e.getMessage());
        }

    }

}
