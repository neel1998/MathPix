package com.example.mathpix.mathpix;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Admin on 24-05-2018.
 */

public class LinearEq {
    private LineGraphSeries<DataPoint> s=new LineGraphSeries<DataPoint>();
    Integer x_coef=1;
    Integer y_coef=1;
    Integer constant=1;
    String eq;
    public LinearEq(String eqn)
    {
        eq=eqn;
        Integer x_pos=eq.indexOf("x");
        Integer y_pos=eq.indexOf("y");
        Integer eq_pos=eq.indexOf("=");

        constant=Integer.parseInt(eq.substring(eq.indexOf("=")+1,eq.length()));
        if(x_pos<y_pos)

        {
            try {
                x_coef = Integer.parseInt(eq.substring(0, x_pos));
            } catch (Exception e) {
            }
            try {
                y_coef = Integer.parseInt(eq.substring(x_pos + 2, y_pos));
            } catch (Exception e) {
            }
        }
        else
        {
            try{
                y_coef=Integer.parseInt(eq.substring(0,y_pos));}catch (Exception e){}
            try{
                x_coef=Integer.parseInt(eq.substring(y_pos+2,x_pos));}catch (Exception e){}
        }
        if(eq.charAt(x_pos+1)=='-'){
            y_coef=-y_coef;
        }
    }
    public int getXCoef(){return x_coef;}
    public int getYcoef(){return y_coef;}
    public int getConst(){return constant;}
}
