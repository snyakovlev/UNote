package snyakovlev.unote;

import android.graphics.Paint;

class Pointt
{

    Pointt(float x, float y, Paint p, boolean move)
    {
        this.x=x;
        this.y=y;
        this.move=move;
        this.p=new Paint();
        this.p.setAlpha(p.getAlpha());
        this.p.setColor(p.getColor());
        this.p.setStrokeWidth(p.getStrokeWidth());
        this.p.setStyle(Paint.Style.STROKE);
        this.p.setAntiAlias(true);

    }

    float x,y;
    Paint p;
    boolean move;
}
