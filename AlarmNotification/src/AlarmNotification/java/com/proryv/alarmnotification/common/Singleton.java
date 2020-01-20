package com.proryv.alarmnotification.common;

import com.proryv.alarmnotification.LastState;

/**
 * Синглтон для всего приложения
 * Created by IG on 15.08.13.
 */
public class Singleton {
    private static volatile Singleton instance;
    public LastState lastState;
    private Singleton()
    {
        lastState = new LastState();
    }

    public static Singleton getInstance(){
        if (instance == null) {
            synchronized ( (Singleton.class)){
                if (instance == null)
                {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
