
import java.util.Random;

public class Utils {
    public String getRandomStr(int i) {
        Random random=new Random();
        int x = (int) Math.pow(10, i);
        return (random.nextInt(9*(x)) +(1*(x)))+"";
    }
}
