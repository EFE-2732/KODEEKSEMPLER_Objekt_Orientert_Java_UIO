import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class FileHandler implements Runnable{
    BufferedReader fil;
    HashMapContainer positive;
    HashMapContainer negative;
    int subStringLengde;
    CountDownLatch finished;
    String filePrefix;
    public FileHandler(BufferedReader fil, HashMapContainer positive, HashMapContainer negative, int subStringLengde,String filePrefix , CountDownLatch finished){
        this.fil = fil;
        this.filePrefix = filePrefix;
        this.positive = positive;
        this.negative = negative;
        this.subStringLengde = subStringLengde;
        this.finished = finished;
    }

    @Override
    public void run() {
        try {
                //itererer gjennom hver linje i metadata
                for (String linje = fil.readLine(); linje != null; linje = fil.readLine()) {
                    try {
                        String[] linjeSplit = linje.split(",");

                        //åpner fil for ny person
                        BufferedReader person = new BufferedReader(new FileReader(filePrefix + linjeSplit[0]));

                        //korrigerer for "amino_acid" på starten av hver
                        person.readLine();

                        HashMap<String, SubSekvens> hashMap = new HashMap<>();

                        //itererer gjennom hver reseptor
                        for (String reseptor = person.readLine(); reseptor != null; reseptor = person.readLine()) {
                            //legger til hver substring
                            for (int i = 0; i <= reseptor.length() - subStringLengde; i++) {
                                String subString = reseptor.substring(i, i + subStringLengde);
                                hashMap.putIfAbsent(subString, new SubSekvens(subString, 1));
                            }
                        }

                        person.close();

                        if (linjeSplit[1].equals("True")) {
                            positive.leggTilHashMap(hashMap);
                        } else {
                            negative.leggTilHashMap(hashMap);
                        }

                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                        System.out.print("| hopper over...");
                    }
                }
            } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finished.countDown();
    }
}
