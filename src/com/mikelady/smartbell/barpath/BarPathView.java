package com.mikelady.smartbell.barpath;

import java.util.ArrayList;

import com.mikelady.smartbell.fragment.RecordSetFragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

public class BarPathView extends View {

        private static Paint paint;
        private int screenW, screenH;
        private float X, Y;
        private float cX, cY;
        private int t = 5;
//        private float[] xt = {0, 100, 10,};
        private float[] yt = {0, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10};
        private Path path;
        private float initialScreenW;
        private float initialX, plusX;
        private float TX;
        private boolean translate;
        private int flash;
        private Context context;
        
        ArrayList<Double[]> positions;

        //call by using setContentView(new BarPathView(this));
        public BarPathView(Context context) {
            super(context);

            this.context=context;

            paint = new Paint();
            paint.setColor(Color.argb(0xff, 0x99, 0x00, 0x00));
            paint.setStrokeWidth(5);
            paint.setAntiAlias(true);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            paint.setShadowLayer(1, 0, 0, Color.RED);

            positions = RecordSetFragment.positions;
            path= new Path();
            TX=0;
            translate=false;

            X = screenW/2;
            Y = screenH/2;
            
            cX = X;
            cY = Y;
            
            flash=0;

        }

        @Override
        public void onSizeChanged (int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            screenW = w;
            screenH = h;
            X = screenW/2;
            Y = screenH/2;

            initialScreenW=screenW;
            initialX=((screenW/2)+(screenW/4));
            plusX=0;
            
            path.moveTo(X, Y);

        }



        @Override
        public void onDraw(final Canvas canvas) {
            super.onDraw(canvas);

            //canvas.save();    


//            flash+=1;
//            if(flash<10 || (flash>20 && flash<30))
//            {
//                paint.setStrokeWidth(16);
//                paint.setColor(Color.RED);
//                paint.setShadowLayer(12, 0, 0, Color.RED);
//            }
//            else
//            {
//                paint.setStrokeWidth(10);
//                paint.setColor(Color.argb(0xff, 0x99, 0x00, 0x00));
//                paint.setShadowLayer(7, 0, 0, Color.RED);
//            }
//
//            if(flash==100)
//            {
//                flash=0;
//            }

            if(t < positions.size()){
                
//            	X = t;
//            	Y = t;
                X = -positions.get(t)[0].floatValue()/10 + cX + 300;
            	Y = -positions.get(t)[1].floatValue()/10 + cY + 300;
            	Log.d("ml", "t = "+t+" X = "+X+" Y = "+Y);
            }
            t++;
            
           
            path.lineTo(X,Y);
            //get next point in path
            SystemClock.sleep(100);
            //canvas.restore(); 
            canvas.drawPath(path, paint);
            invalidate();
        }
    }
