package edu.binghamton.hloya1.obwheels;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by hloya on 3/30/2017.
 */

public class CalculationsForCalendarScreen
{
    private EditText editedEditText;
    private EditText toUpdateEditText;
    private EditText alsoToConsiderEditText;
    private EditText egaAsOfEditText;
    private TextView egaTextView;
    private Calendar cal;

    public CalculationsForCalendarScreen() {
    }

    public CalculationsForCalendarScreen(View editedView, View egaAsOfView, View egaView)
    {
        toUpdateEditText = null;

        editedEditText = (EditText) editedView;
        egaAsOfEditText = (EditText) egaAsOfView;
        egaTextView = (TextView) egaView;

        if(egaAsOfEditText.getId() == R.id.editText7 || egaAsOfEditText.getId() == R.id.editText8)
        {
            egaAsOfEditText.addTextChangedListener(egaLMPorSonoUpdated);
        }
    }

    public CalculationsForCalendarScreen(View editedView, View toBeUpdatedView, View egaAsOfView, View egaView)
    {
        editedEditText = (EditText) editedView;
        toUpdateEditText = (EditText) toBeUpdatedView;
        egaAsOfEditText = (EditText) egaAsOfView;
        egaTextView = (TextView) egaView;

        if(editedEditText.getId() == R.id.editText1 || editedEditText.getId() == R.id.editText2)
        {
            editedEditText.addTextChangedListener(EDDCalcFromLMPorSono);
        }
        else if(editedEditText.getId() == R.id.editText5 || editedEditText.getId() == R.id.editText6)
        {
            //  Commenting below because its causing infinite loop
            // need to find a new way to handle the opposite case of above (if required)
            // editedEditText.addTextChangedListener(LMPorSonoCalcFromEDD);
        }
    }

    public CalculationsForCalendarScreen(View editedView, View alsoToConsiderView, View toBeUpdatedView, View egaAsOfView, View egaView)
    {
        editedEditText = (EditText) editedView;
        alsoToConsiderEditText = (EditText) alsoToConsiderView;
        toUpdateEditText = (EditText) toBeUpdatedView;
        egaAsOfEditText = (EditText) egaAsOfView;
        egaTextView = (TextView) egaView;

        if(editedEditText.getId() == R.id.editText3 || editedEditText.getId() == R.id.editText4)
        {
            editedEditText.addTextChangedListener(sonoEGAUpdated);
        }
    }


    private TextWatcher EDDCalcFromLMPorSono = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                String date = s.toString();
                //String date = editedEditText.getText();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

                Calendar calendar = Calendar.getInstance();

                Date inputDate = simpleDateFormat.parse(date);
                calendar.setTime(inputDate);
                calendar.add(Calendar.DATE, 280);
                toUpdateEditText.setText(simpleDateFormat.format(calendar.getTime()));
            } catch (ParseException e) {
                //Error
            }

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            updateTextView();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    };


    //Not Using the below yet
    /*
    private TextWatcher LMPorSonoCalcFromEDD = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                String date = s.toString();
                //String date = editedEditText.getText();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar calendar = Calendar.getInstance();

                Date inputDate = simpleDateFormat.parse(date);
                calendar.setTime(inputDate);
                calendar.add(Calendar.DATE, -280);
                toUpdateEditText.setText(simpleDateFormat.format(calendar.getTime()));
            } catch (ParseException e) {
                //Error
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    };
    */

    private TextWatcher egaLMPorSonoUpdated = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            updateTextView();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    };

    private TextWatcher sonoEGAUpdated = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            //cal.setTime(new Date());
            cal = Calendar.getInstance();
            updateTextView();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    };

    public void updateTextView()
    {
        try
        {
            if(editedEditText.getId() == R.id.editText3 || editedEditText.getId() == R.id.editText4)
            {
                String editText3Data = editedEditText.getText().toString();
                String editText4Data = alsoToConsiderEditText.getText().toString();
                int weekOrDayCount;
                int dayOrWeekCount;
                if(editText3Data.compareTo("") != 0)
                {
                    weekOrDayCount = Integer.parseInt(editText3Data);
                }
                else
                    weekOrDayCount = 0;

                if(editText4Data.compareTo("") != 0)
                {
                    dayOrWeekCount = Integer.parseInt(editText4Data);
                }
                else
                    dayOrWeekCount = 0;

                String temp2 = egaAsOfEditText.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

                Date sonoDate = null;
                Date egaDate = null;

                if (editedEditText.getId() == R.id.editText3)
                {
                    cal.add(Calendar.WEEK_OF_YEAR, -weekOrDayCount);
                    cal.add(Calendar.DAY_OF_YEAR, -dayOrWeekCount);
                }
                else if(editedEditText.getId() == R.id.editText4)
                {
                    cal.add(Calendar.DAY_OF_YEAR, -weekOrDayCount);
                    cal.add(Calendar.WEEK_OF_YEAR, -dayOrWeekCount);
                }

                sonoDate = simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));

                if (!temp2.equals(""))
                {
                    egaDate = simpleDateFormat.parse(temp2);
                }

                if (sonoDate != null && egaDate != null)
                {
                    int dayCount = (int) getDaysBetweenDates(sonoDate, egaDate);
                    int weekCount = dayCount / 7;
                    dayCount = dayCount % 7;

                    egaTextView.setText(weekCount + "Weeks, " + dayCount + "Days");

                    cal.add(Calendar.DATE, 280);
                    toUpdateEditText.setText(simpleDateFormat.format(cal.getTime()));
                }
                else
                {
                    egaTextView.setText(R.string.SONO_EGA);
                    toUpdateEditText.setText("");
                }
            }
            else
            {
                String temp1 = editedEditText.getText().toString();
                String temp2 = egaAsOfEditText.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

                Date lmpDate = null;
                Date egaDate = null;

                if (!temp1.equals("")) {
                    lmpDate = simpleDateFormat.parse(temp1);
                }

                if (!temp2.equals("")) {
                    egaDate = simpleDateFormat.parse(temp2);
                }

                if (lmpDate != null && egaDate != null) {
                    int dayCount = (int) getDaysBetweenDates(lmpDate, egaDate);
                    int weekCount = dayCount / 7;
                    dayCount = dayCount % 7;

                     egaTextView.setText(weekCount + "Weeks, " + dayCount + "Days");
                }
                else
                {
                    if (editedEditText.getId() == R.id.editText1) {
                        egaTextView.setText(R.string.LMP_EGA);
                    } else if (editedEditText.getId() == R.id.editText2) {
                        egaTextView.setText(R.string.SONO_EGA);
                    }
                }
            }
        }
        catch (ParseException e) {
            //Error
        }
    }

    public long getDaysBetweenDates(Date d1, Date d2)
    {
        if (d2.before(d1)) {
            return -getDaysBetweenDates(d2, d1);
        }

        //Condition handling to account for Daylight Savings time if any
        if(d1.getTimezoneOffset() > d2.getTimezoneOffset())
        {
            //Adding 1 day if EGA as of Date is under Daylight saving and Sono/LMP date is not
            return TimeUnit.MILLISECONDS.toDays(d2.getTime() - d1.getTime())+1;
        }
        else if(d1.getTimezoneOffset() < d2.getTimezoneOffset())
        {
            //Removing 1 day if Sono/LMP date is under Daylight saving and EGA as of Date is not
            return TimeUnit.MILLISECONDS.toDays(d2.getTime() - d1.getTime())-1;
        }
        else
        {
            //Case if both times are of same type
            return TimeUnit.MILLISECONDS.toDays(d2.getTime() - d1.getTime());
        }
    }
}