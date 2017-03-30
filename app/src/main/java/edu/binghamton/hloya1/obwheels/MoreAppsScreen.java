package edu.binghamton.hloya1.obwheels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hloya on 3/29/2017.
 */

public class MoreAppsScreen extends Fragment
{
    private TextView mTextMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.moreapps_screen, container, false);

        mTextMessage = (TextView) view.findViewById(R.id.message);
        mTextMessage.setText(R.string.title_moreApps);
        mTextMessage.setMovementMethod(new ScrollingMovementMethod());

        return view;

    }

}
