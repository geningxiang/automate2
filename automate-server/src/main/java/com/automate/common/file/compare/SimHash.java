package com.automate.common.file.compare; /**
 * Function: 注：该示例程序暂不支持中文
 * Date:     2013-8-4 下午11:01:45
 * @author june: decli@qq.com
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SimHash {

    private List<String> datas;

    private BigInteger intSimHash;

    private int hashbits = 64;

    public SimHash(List<String> datas) {
        this.datas = datas;
        this.intSimHash = this.simHash();
    }

    public SimHash(List<String> datas, int hashbits) {
        this.datas = datas;
        this.hashbits = hashbits;
        this.intSimHash = this.simHash();
    }


    public BigInteger simHash() {
        // 定义特征向量/数组
        int[] v = new int[this.hashbits];

        for (String data : datas) {
            // 2、将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
            BigInteger t = this.hash(data);
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask =BigInteger.ONE.shiftLeft(i);
                // 3、建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
                // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
                // 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
                if (t.and(bitmask).signum() != 0) {
                    // 这里是计算整个文档的所有特征的向量和
                    // 这里实际使用中需要 +- 权重，而不是简单的 +1/-1，
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }
        BigInteger fingerprint = BigInteger.ZERO;

        for (int i = 0; i < this.hashbits; i++) {
            // 4、最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(BigInteger.ONE.shiftLeft(i));
            } else {

            }
        }
        return fingerprint;
    }

    private BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return BigInteger.ZERO;
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(BigInteger.ONE);
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    public int hammingDistance(SimHash other) {

        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int tot = 0;

        // 统计x中二进制位数为1的个数
        // 我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，对吧，然后，n&(n-1)就相当于把后面的数字清0，
        // 我们看n能做多少次这样的操作就OK了。

        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(BigInteger.ONE));
        }
        return tot;
    }

    public int getDistance(String str1, String str2) {
        int distance;
        if (str1.length() != str2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public List<BigInteger> subByDistance(SimHash simHash, int distance) {
        // 分成几组来检查
        int numEach = this.hashbits / (distance + 1);
        List<BigInteger> characters = new ArrayList();

        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < this.intSimHash.bitLength(); i++) {
            // 当且仅当设置了指定的位时，返回 true
            boolean sr = simHash.intSimHash.testBit(i);
            if (sr) {
                buffer.append("1");
            } else {
                buffer.append("0");
            }
            if ((i + 1) % numEach == 0) {
                // 将二进制转为BigInteger
                BigInteger eachValue = new BigInteger(buffer.toString(), 2);
                buffer.delete(0, buffer.length());
                characters.add(eachValue);
            }
        }

        return characters;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        String s = "This is a test string for testing";
        list.add(s);
        SimHash hash1 = new SimHash(list, 64);

        System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());

        System.out.println(hash1.intSimHash.toString(2));

    }
}