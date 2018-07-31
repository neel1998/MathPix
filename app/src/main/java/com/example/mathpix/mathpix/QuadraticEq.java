package com.example.mathpix.mathpix;

import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Admin on 24-05-2018.
 */

public class QuadraticEq {
    private String eq;
    Integer x_coef=1;
    Integer y_coef=1;
    Integer y_pow=1;
    Integer x_pow=1;
    Integer constant=1;
    public QuadraticEq(String eqn)
    {
        eq=eqn;
        Integer x_pos=eq.indexOf("x");
        Integer y_pos=eq.indexOf("y");
        Integer eq_pos=eq.indexOf("=");
        constant=Integer.parseInt(eq.substring(eq.indexOf("=")+1,eq.length()));
        if(eq.charAt(x_pos+1)=='^')
        {
            x_pow=Character.getNumericValue(eq.charAt(x_pos+2));
        }
        if(eq.charAt(y_pos+1)=='^')
        {
            y_pow=Character.getNumericValue(eq.charAt(y_pos+2));
        }
        if(x_pos<y_pos)
        {
            try {
                x_coef = Integer.parseInt(eq.substring(0, x_pos));
            } catch (Exception e) {
            }
            try {
                y_coef = Integer.parseInt(eq.substring(x_pos + 4, y_pos));
            } catch (Exception e) {
            }
        }
        else
        {
            try{
                y_coef=Integer.parseInt(eq.substring(0,y_pos));}catch (Exception e){}
            try{
                x_coef=Integer.parseInt(eq.substring(y_pos+4,x_pos));}catch (Exception e){}
        }
        if(x_pow==1) {
            if (eq.charAt(x_pos + 1) == '-') {
                y_coef = -y_coef;
            }
        }
        else{
            if (eq.charAt(x_pos + 3) == '-') {
                y_coef = -y_coef;
            }
        }
    }

}
