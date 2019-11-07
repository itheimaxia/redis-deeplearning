package com.brianxia.redis.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SplitRedPacket {

//    // 最小红包额度
//    private static final int MINMONEY = 1;
//    // 最大红包额度
//    private static final int MAXMONEY = 90000;
//
//
//    /**
//     * @Description: 随机拆分红包
//     * @Author: liumeng
//     * @Date: 2019/2/27
//     * @Param: [money, count]
//     * @Return: java.util.List<java.lang.Integer>
//     **/
//    public static List<String> splitRedPackets(int money, int count) {
//
//        if(money < count*MINMONEY || money > count* MAXMONEY){
//            System.out.println("不可拆分");
//            return null;
//        }
//        // 先预留出 count 份 minS ， 其余的做随机
//        int moreMoney = money-count*MINMONEY;
//        List<String> list = new ArrayList<String>();
//        for(int i=0; i<count; i++){
//            int one = random(moreMoney,count-i, MINMONEY, MAXMONEY);
//            list.add(String.valueOf(one+MINMONEY));
//            moreMoney = moreMoney-one;
//        }
//        Collections.shuffle(list);
//        return list;
//    }
//    /**
//     * @Description: 随机红包数额（加上minS 为实际金额）
//     * @Author: liumeng
//     * @Date: 2019/2/27
//     * @Param: [money, count]
//     * @Return: java.util.List<java.lang.Integer>
//     **/
//    private static int random(int money,  int count, int minS, int maxS) {
//        // 红包数量为1，直接返回金额
//        if (count == 1) {
//            return money;
//        }
//        // 每次限定随机数值
//        // 首先判断实际最小值
//        int realMinS = money-(maxS-minS)*(count-1);
//        int realRange ;
//        // 如果存在实际最小值，则在实际最小值realMinS 和 maxS-minS 之间 random 数值
//        if(realMinS > 0){
//            realRange = maxS-minS-realMinS + 1;
//        }
//        //  如果不存在实际最小值（也就是说数值可以是minS）
//        else{
//            if(money > maxS-minS){
//                realMinS = 0;
//                realRange = maxS-minS + 1;
//            }else{
//                realMinS = 0;
//                realRange = money + 1;
//            }
//        }
//
//        return  new Random().nextInt(realRange) + realMinS;
//
//
//    }
//
//

    public static List<String> splitRedPackets(Integer totalAmount, Integer totalPeopleNum) {
        List<String> amountList = new ArrayList<>();

        Integer restAmount = totalAmount;

        Integer restPeopleNum = totalPeopleNum;

        Random random = new Random();

        for (int i = 0; i < totalPeopleNum - 1; i++) {
            //随机范围：[1，剩余人均金额的两倍)，左闭右开
            int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
            restAmount -= amount;
            restPeopleNum--;
            amountList.add(String.valueOf(amount));
        }
        amountList.add(String.valueOf(restAmount));

        return amountList;
    }


    public static void main(String[] args) {
        int money = 2500 ;
        int count = 6;
        for(int i=0; i<7; i++) {
            List list = splitRedPackets(money, count);
            if (list != null) {
                System.out.println("随机拆分" + money + "拆分" + count + "份：" + list);
            }
        }
    }
}