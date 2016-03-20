package com.bignerdranch.remotecontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by dell on 2016/3/20.
 */
public class RemoteControlFragment extends Fragment{
    private TextView mSelectedTextView;
    private TextView mWorkingTextView;

    /**
     * 在OnCreateView，inflate布局，实例化控件，加监听器
     */
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_remote_control,parent,false);
        mSelectedTextView=(TextView) v.findViewById(R.id.fragment_remote_control_selectedTextView);
        mWorkingTextView=(TextView) v.findViewById(R.id.fragment_remote_control_workingTextView);

        /**
         * 数字按钮的统一的监听器，获取数字按钮控件实例
         */
        View.OnClickListener numberButtonListener= new View.OnClickListener(){
            public void onClick(View v){
                TextView textView=(TextView) v;
                String working = mWorkingTextView.getText().toString();
                String text=textView.getText().toString();//获取按钮的text，转为String
                if(working.equals("0")){
                    mWorkingTextView.setText(text);//如果输入栏是0，输入栏显示：text
                } else{
                    mWorkingTextView.setText(working+text);//如果输入栏不是0，输入栏显示：目前数字连同text
                }
            }
        };

        /**
         * 跳过2个文本视图，然后遍历第一、二、三排的每个按钮，给按钮赋值和加监听器
         */
        TableLayout tableLayout = (TableLayout)v
                .findViewById(R.id.fragment_remote_control_tableLayout);
        int number=1;
        for(int i =2; i<tableLayout.getChildCount()-1;i++){
            TableRow row=(TableRow) tableLayout.getChildAt(i);//获得该layout下的第几个row
            for(int j=0; j<row.getChildCount(); j++){
                Button button= (Button) row.getChildAt(j);//获得该row下的第几个button
                button.setText(""+number);
                button.setOnClickListener(numberButtonListener);
                number++;
            }
        }

        /**
         * 最后一排按钮的特殊处理，逐个赋值，加监听器
         */
        TableRow bottomRow=(TableRow) tableLayout
                .getChildAt(tableLayout.getChildCount()-1);

        Button deleteButton= (Button) bottomRow.getChildAt(0);//第一个是delete键
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mWorkingTextView.setText("0");
            }
        });

        Button zeroButton =(Button) bottomRow.getChildAt(1);//第二个是0键
        zeroButton.setText("0");
        zeroButton.setOnClickListener(numberButtonListener);

        Button enterButton=(Button) bottomRow.getChildAt(2);//第三个是Enter键
        enterButton.setText("Enter");
        enterButton.setOnClickListener (new View.OnClickListener(){
            public void onClick(View v){
                CharSequence working=mWorkingTextView.getText();
                if(working.length()>0)
                    mSelectedTextView.setText(working);//将输入栏的文本放到目前频道
                mWorkingTextView.setText("0");
            }
        });

        return v;
    }
}

