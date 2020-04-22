package com.example.assignment1;
import java.text.NumberFormat;
import java.util.Random;


public class RandomClass {


    int hour;
    int choose_background(float x, int y, boolean z){
        if (x < 0){

            if ( 7 <= y && y <= 18){
                return 0;
            }else{
                return 1;
            }
        } else{
            if (!z){

                if (7 <= y && y <= 18){
                    return 2;
                }else{
                    return 3;
                }
            }else{

                if (7 <= y && y <= 18){
                    return 4;
                }else{
                    return 5;
                }
            }
        }
    }

    int normal_rd(int x){
        Random random = new Random();
        return random.nextInt(x);
    }

//    int get_rd(int min, int max) {
//        return min + (int)(Math.random() * ((max - min) + 1));
//    }
//
//    String get_time(){
//        hour = (int) (Math.random() * ((24) + 1));
//        minute = (int) (Math.random() * ((60) + 1));
//        String str_hour = String.format("%02d", hour);
//        String str_minute = String.format("%02d", minute);
//        return str_hour + ":" + str_minute;
//    }

//    Boolean rain_rd(){
//        Random rd = new Random();
//        return rd.nextBoolean();
//    }



}
