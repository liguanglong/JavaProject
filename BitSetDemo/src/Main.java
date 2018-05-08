import java.util.BitSet;

/**
 * @author LiGuanglong
 * @date 2018/5/8
 */
public class Main {
    public static void main(String[] args) {

        /**
         * 海量数据去重、排序、压缩存储
         * Java平台的BitSet用于存放一个位序列，如果要高效的存放一个位序列，就可以使用位集(BitSet)。由于位集将位包装在字节里，
         * 所以使用位集比使用Boolean对象的List更加高效和更加节省存储空间。
         * BitSet是位操作的对象，值只有0或1即false和true，内部维护了一个long数组，初始只有一个long，所以BitSet最小的size是64，
         * 当随着存储的元素越来越多，BitSet内部会动态扩充，一次扩充64位，最终内部是由N个long来存储。
         * 默认情况下，BitSet的所有位都是false即0。
         */
        BitSet bitSet = new BitSet();
        for (int i = 1600; i > 0; i--) {
            if (i % 5 == 0) {
                bitSet.set(i);
            }
        }

        BitSet bitSet1 = new BitSet();
        for (int j =1600 ; j >0; j--) {
            if (j % 3 == 0) {
                bitSet1.set(j);
            }
        }

        bitSet.or(bitSet1);
        System.out.println(bitSet);

    }
}
