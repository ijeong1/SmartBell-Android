package com.mikelady.smartbell.barpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class BarPathView extends View {

        private static Paint paint;
        private int screenW, screenH;
        private float X, Y;
        private int t = 0;
//        private float[] xt = {0, 100, 10,};
        private float[] yt = {0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10};
        private Path path;
        private float initialScreenW;
        private float initialX, plusX;
        private float TX;
        private boolean translate;
        private int flash;
        private Context context;

        //call by using setContentView(new BarPathView(this));
        public BarPathView(Context context) {
            super(context);

            this.context=context;

            paint = new Paint();
            paint.setColor(Color.argb(0xff, 0x99, 0x00, 0x00));
            paint.setStrokeWidth(10);
            paint.setAntiAlias(true);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            paint.setShadowLayer(7, 0, 0, Color.RED);


            path= new Path();
            TX=0;
            translate=false;

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
        public void onDraw(Canvas canvas) {
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

            path.lineTo(X,Y);


            //get next point in path
            t++;
            if(t < yt.length){
                
//                X += xt[t];
            	if(yt[t] < 0)
            		X+= 1;
                Y += yt[t];
            }
            
            canvas.drawPath(path, paint);


            //canvas.restore(); 

            invalidate();
        }
    }
