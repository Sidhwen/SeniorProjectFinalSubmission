package com.NUWC_ETJ.models;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sidhwen on 4/9/2017.
 */

public class ExpandableTextView extends TextView {

    private Boolean Expanded = false;
    public TextView myChild;
    public View Line;
    public View EndLine;

    public ExpandableTextView(Context myContext)
    {
        super(myContext);
        setTextSize(24);
        setTextColor(Color.BLACK);
        setMaxLines(this.getLineHeight());
        Line = new View(myContext);
        Line.setBackgroundColor(Color.BLACK);
        Line.setMinimumWidth(100);
        Line.setMinimumHeight(4);
        EndLine = new View(myContext);
        EndLine.setBackgroundColor(Color.BLACK);
        EndLine.setMinimumWidth(100);
        EndLine.setMinimumHeight(4);
        myChild = new TextView(myContext);
        myChild.setTextSize(20);
        myChild.setVisibility(View.INVISIBLE);
        myChild.setMaxLines(0);
        myChild.setTextColor(Color.BLACK);
        setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                if (!Expanded)
                {
                    Expanded = true;
                    myChild.setVisibility(View.VISIBLE);
                    myChild.setMaxLines(myChild.getLineCount());
                   // setMaxLines(ExpandLength);
                   // setTextSize(12);
                }
                else
                {
                    Expanded = false;
                    myChild.setVisibility(View.INVISIBLE);
                    myChild.setMaxLines(0);
                    //setMaxLines(1);
                    //setTextSize(24);
                }
            }
        });


    }
        void HideReveal (Boolean SearchResult)
        {
            myChild.setVisibility(View.GONE);
            myChild.setMaxLines(0);
            Expanded = false;
            if (!SearchResult)
            {
                this.setVisibility(View.GONE);
                this.setMaxLines(0);
                Line.setVisibility(View.GONE);
                Line.setMinimumHeight(0);
            }
            else
            {
                this.setVisibility(View.VISIBLE);
                this.setMaxLines(this.getLineCount());
                Line.setMinimumHeight(4);
                Line.setVisibility(View.VISIBLE);
            }
        }
    }

