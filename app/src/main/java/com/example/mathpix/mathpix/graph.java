package com.example.mathpix.mathpix;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

public class graph extends AppCompatActivity {


    Integer x_coef=1;
    Integer y_coef=1;
    Integer x_pow=1;
    Integer y_pow=1;
    Integer constant;
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint1=new Paint();
    Paint paint2=new Paint();
    Paint paint3=new Paint();
    int sx=10;
    int sy=10;
    int tx,ty;
    final int textSize=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        imageView=(ImageView)findViewById(R.id.img);
        Button zi=(Button) findViewById(R.id.zi);
        Button zo=(Button)findViewById(R.id.zo);
        Button graph=(Button)findViewById(R.id.graph);
        Button right=(Button)findViewById(R.id.right);
        Button left=(Button)findViewById(R.id.left);
        Button up=(Button)findViewById(R.id.up);
        Button down=(Button)findViewById(R.id.down);

        Bundle extra=getIntent().getExtras();
        String eq=extra.getString("final_eq");//5x +   14y= 23
        eq=eq.replaceAll("\\s","").toLowerCase();//5x+14y=23

        if(!eq.contains("^"))//Linear Equeation
        {
            Log.d("","linear called");
            LinearEq linearEq=new LinearEq(eq);
            x_coef=linearEq.getXCoef();
            y_coef=linearEq.getYcoef();
            constant=linearEq.getConst();
        }
        else
        {
            eq=eq.replaceAll("\\{","");
            eq=eq.replaceAll("\\}","");
            QuadraticEq quadraticEq=new QuadraticEq(eq);
            x_coef=quadraticEq.x_coef;
            y_coef=quadraticEq.y_coef;
            x_pow=quadraticEq.x_pow;
            y_pow=quadraticEq.y_pow;
            constant=quadraticEq.constant;
        }
        zi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sx+=10;
                sy+=10;
                draw(imageView);
            }
        });
        zo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sx-=10;
                sy-=10;
                draw(imageView);
            }
        });
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx=imageView.getWidth()/2;
                ty=imageView.getHeight()/2;
                draw(imageView);
            }
        });
        right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tx-=10;
                draw(imageView);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx+=10;
                draw(imageView);
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ty-=10;
                draw(imageView);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ty+=10;
                draw(imageView);
            }
        });
    }
    public void draw(View view){

        bitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        imageView.setImageBitmap(bitmap);
        canvas=new Canvas(bitmap);

        paint1.setColor(Color.CYAN);
        paint1.setTextSize(textSize);
        paint2.setTextSize(sx/sx);
        paint3.setColor(Color.RED);

        canvas.translate(tx,ty);
        canvas.scale(sx,sy);


        if(x_pow==1 && y_pow==1) {
            float startX = 0 - view.getWidth() / 2;
            float endX = 0 + view.getWidth() / 2;
            canvas.drawLine(startX, -(constant - (x_coef * startX)) / y_coef, endX, -(constant - (x_coef * endX)) / y_coef, paint3);
        }
        else {
            if(y_pow%2==0){
                double x;
                Long total_points=50000L;
                if(constant!=0) {
                    x = -Math.pow(constant/x_coef, (double) 1 / x_pow);
                    total_points=-(4000)*Math.round(x);
                }
                else {
                    x=0;
                }
                for(int i=0;i<total_points;i++)
                {
                    try {
                        canvas.drawPoint((float)x, (float) Math.pow((constant - x_coef * Math.pow(x, x_pow)) / y_coef, (double) 1 / y_pow), paint3);
                        canvas.drawPoint((float)x, -(float) Math.pow((constant - x_coef * Math.pow(x, x_pow)) / y_coef, (double) 1 / y_pow), paint3);
                    }catch (Exception e){}
                    x=x+0.001;
                }
            }
            else{

            }
        }
        for(int i=-view.getWidth()/2;i<=view.getWidth()/2;i+=1){
            canvas.drawLine(i,-view.getHeight()/2,i,view.getHeight()/2,paint2);
        }
        for(int i=-view.getHeight()/2;i<=view.getHeight()/2;i+=1){
            canvas.drawLine(-view.getWidth()/2,i,view.getWidth()/2,i,paint2);
        }
        canvas.drawLine(-view.getWidth()/2,0,view.getWidth()/2,0,paint1);
        canvas.drawLine(0,-view.getHeight()/2,0,view.getHeight()/2,paint1);
    }
}
