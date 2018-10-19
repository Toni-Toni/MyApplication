package com.example.lixy.myapplication;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/1/516:32
 */
public class Test {
    private boolean stop = false;
    public void recurse(){
        try {

            System.out.println("recurse");
            if (!stop) {
                recurse();
            }
        } catch (Throwable e) {
            //e.printStackTrace();
            stop = true;
            System.out.println("\nstoped");
        }
    }

    public void  boolTest(){
        int i = -1;

    }

    private Singleton<Model> modelSingleton = new Singleton<Model>() {
        @Override
        protected Model create() {
            return new Model();
        }
    };

}
