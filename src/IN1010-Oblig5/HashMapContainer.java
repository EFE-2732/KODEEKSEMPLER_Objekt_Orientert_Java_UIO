import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HashMapContainer {
    private ArrayList<HashMap<String, SubSekvens>> liste = new ArrayList<>();
    private Lock laas = new ReentrantLock();
    private Condition kombinerbar = laas.newCondition();

    public void leggTilHashMap(HashMap<String, SubSekvens> hashMap){
        laas.lock();
        try {
            liste.add(hashMap);
            if (lengde() >= 2) {
                kombinerbar.signal();
            }
        } finally {
            laas.unlock();
        }
    }

    public HashMap<String, SubSekvens> taUtHashMap(){
        laas.lock();
        try {
            return liste.remove(0);
        } finally {
            laas.unlock();
        }
    }


    public HashMap<String, SubSekvens>[] hashMapSubList(int maps) throws IndexOutOfBoundsException, InterruptedException{
        laas.lock();

        try {
            while (lengde()<2){
                kombinerbar.await();
            }
            HashMap<String, SubSekvens>[] hashMaps = new HashMap[maps];
            for (int i = 0; i<maps;i++){
                hashMaps[i] = taUtHashMap();
            }
            return hashMaps;

        } finally {
            laas.unlock();
        }
    }


    public int lengde(){
        laas.lock();
        try {
            return liste.size();
        } finally {
            laas.unlock();
        }

    }


}
