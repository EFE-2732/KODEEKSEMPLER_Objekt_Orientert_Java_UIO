import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class Combiner implements Runnable{
    HashMapContainer hashMapContainer;
    CountDownLatch finished;

    public Combiner(HashMapContainer hashMapContainer, CountDownLatch finished){
        this.hashMapContainer = hashMapContainer;
        this.finished = finished;
    }

    static public HashMap<String, SubSekvens> kombinerHashMap(HashMap<String, SubSekvens> h1, HashMap<String, SubSekvens> h2) throws InterruptedException {
        h2.forEach((noekkel, verdi) -> h1.merge(noekkel, verdi, SubSekvens::sum));
        return h1;
    }

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted() || hashMapContainer.lengde()>1) {
                HashMap<String, SubSekvens>[] hashMaps =  hashMapContainer.hashMapSubList(2);
                hashMapContainer.leggTilHashMap(kombinerHashMap(hashMaps[0], hashMaps[1]));
            }
        } catch (InterruptedException ignored){ }
        finished.countDown();
    }
}
