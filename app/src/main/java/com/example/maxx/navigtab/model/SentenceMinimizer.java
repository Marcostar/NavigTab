package com.example.maxx.navigtab.model;

import java.text.BreakIterator;
import java.util.Locale;

/**
 * Created by Dzeko on 9/11/2015.
 */
public class SentenceMinimizer {

    private String para;
    private  int[] sent_array = new int[4];
    private StringBuilder short1 = new StringBuilder();
    private int i =0 , j =0 ;


    public SentenceMinimizer() {
       // this.para = para;
    }

    public String getPara() {
        return minimize();
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String minimize()
    {
        Locale locale = Locale.US;
        BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);

        breakIterator.setText(para);

        int boundaryIndex = breakIterator.first();
        while((i!=2) && (boundaryIndex != BreakIterator.DONE))
        {

            boundaryIndex = breakIterator.next();
            if(boundaryIndex == -1)
            {
                break;
            }
            sent_array[i] = boundaryIndex;
            i++;

        }
        while(j!=sent_array[i-1])
        {
            char tmp = para.charAt(j);
            short1.append(tmp);
            j++;
        }
        return short1.toString();
    }
}
