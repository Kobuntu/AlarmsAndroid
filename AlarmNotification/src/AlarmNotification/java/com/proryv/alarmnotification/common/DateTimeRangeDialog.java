package com.proryv.alarmnotification.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.proryv.alarmnotification.R;
import com.proryv.alarmnotification.adapters.AlarmHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.getInstance;

/**
 * Created by Ig on 06.07.13.
 */
public class DateTimeRangeDialog extends LinearLayout implements DatePickerDialog.OnDateSetListener,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public Calendar _dtStart;
    public Calendar _dtEnd;
    DateTimeRangeEvent dateTimeRangeEvent;
    Boolean _isAlarmDateTimeChanged;
    Boolean _isAlwaysNeedUpdate;
    FragmentManager _fragmentManager;

    final Locale locale = new Locale("ru","RU");

    final private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", locale);
    final private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", locale);

    public DateTimeRangeDialog(Context context) {
        super(context);
    }

    public DateTimeRangeDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTimeRangeDialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void Init(Calendar dtStart, Calendar dtEnd, DateTimeRangeEvent listener,  Boolean isAlarmDateTimeChanged, Boolean isAlwaysNeedUpdate, FragmentManager fragmentManager)
    {
        findViewById(R.id.tvFilterStartDate).setOnClickListener(this);
        findViewById(R.id.tvFilterStartTime).setOnClickListener(this);
        findViewById(R.id.tvFilterFinishDate).setOnClickListener(this);
        findViewById(R.id.tvFilterFinishTime).setOnClickListener(this);

        ((CheckBox)findViewById(R.id.cbDateRangeStart)).setOnCheckedChangeListener(this);
        ((CheckBox)findViewById(R.id.cbDateRangeFinish)).setOnCheckedChangeListener(this);

        b = false;
        _isAlwaysNeedUpdate = isAlwaysNeedUpdate;

        setDateStart(dtStart);
        setDateEnd(dtEnd);


        ((ImageButton)findViewById(R.id.ibSelectRange)).setOnClickListener(this);

        //Подписываемся на изменение
        dateTimeRangeEvent =  listener;

        _isAlarmDateTimeChanged = isAlarmDateTimeChanged;

        _fragmentManager = fragmentManager;
    }

    boolean b = true;

    private void setDateStart(Calendar dtStart)
    {
        EditText ts =(EditText)findViewById(R.id.tvFilterStartDate);
        EditText tf =(EditText)findViewById(R.id.tvFilterStartTime);

        if (dtStart == null)
        {
            ts.setText("");
            tf.setText("");
            _dtStart = dtStart;
            return;
        }

        try
        {
            ts.setText(dateFormat.format(dtStart.getTime()));
            tf.setText(timeFormat.format(dtStart.getTime()));
            b = true;
            ((CheckBox)findViewById(R.id.cbDateRangeStart)).setChecked(true);
            b = false;
            _dtStart = dtStart;

            if (_dtEnd !=null && _dtStart.getTimeInMillis() > _dtEnd.getTimeInMillis())
            {
                setDateEnd(_dtStart);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setDateEnd(Calendar dtEnd)
    {
        EditText ts =(EditText)findViewById(R.id.tvFilterFinishDate);
        EditText tf =(EditText)findViewById(R.id.tvFilterFinishTime);

        if (dtEnd == null)
        {
            ts.setText("");
            tf.setText("");
            _dtEnd = dtEnd;
            return;
        }

        try
        {
            ts.setText(dateFormat.format(dtEnd.getTime()));
            tf.setText(timeFormat.format(dtEnd.getTime()));
            b = true;
            ((CheckBox)findViewById(R.id.cbDateRangeFinish)).setChecked(true);
            b = false;
            _dtEnd = dtEnd;

            if (_dtStart!=null && _dtStart.getTimeInMillis() > _dtEnd.getTimeInMillis())
            {
                setDateStart(_dtEnd);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (isInEditMode())
        {
            if (_dtStart==null)
            {
                _dtStart = getInstance();
                _dtStart.set(Calendar.HOUR_OF_DAY, 0);
                _dtStart.set(Calendar.MINUTE, 0);
                _dtStart.set(Calendar.SECOND, 0);
            }

            _dtStart.set(year, monthOfYear, dayOfMonth);
        }
    }

    @Override
    public void onClick(View v) {
        Context ctx = getContext();
        if (ctx == null) return;

        Resources r = getResources();
        if (r == null) return;

        View child;
        Calendar defaultDt = getInstance();
        int id = v.getId();
        switch (id)
        {
            case R.id.ibSelectRange: //Диапазон
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                if (inflater!=null)
                {
                    child = inflater.inflate( R.layout.dialog_date_range_select, null);
                    showDialog(ctx.getString(R.string.select_range), id, child);
                    //showFragmentDialog(ctx.getString(R.string.select_range), id, child);
                }
                break;
            case R.id.tvFilterStartDate: //Начальная дата
                DatePicker dp = new DatePicker(ctx);
                dp.setCalendarViewShown(false);
                dp.setId(R.id.dialog_child_id);
                dp.setTag(id);
                if (_dtStart!=null)
                {
                    dp.updateDate(_dtStart.get(Calendar.YEAR), _dtStart.get(Calendar.MONTH), _dtStart.get(Calendar.DAY_OF_MONTH));
                }
                else
                {
                    dp.updateDate(defaultDt.get(Calendar.YEAR), defaultDt.get(Calendar.MONTH), defaultDt.get(Calendar.DAY_OF_MONTH));
                }
                child = dp;
                showDialog(r.getString(R.string.select_start_date), id, child);
                break;
            case R.id.tvFilterFinishDate: //Конечная дата
                dp = new DatePicker(ctx);
                dp.setCalendarViewShown(false);
                dp.setId(R.id.dialog_child_id);
                dp.setTag(id);
                if (_dtEnd !=null)
                {
                    dp.updateDate(_dtEnd.get(Calendar.YEAR), _dtEnd.get(Calendar.MONTH), _dtEnd.get(Calendar.DAY_OF_MONTH));
                }
                else
                {
                    dp.updateDate(defaultDt.get(Calendar.YEAR), defaultDt.get(Calendar.MONTH), defaultDt.get(Calendar.DAY_OF_MONTH));
                }
                child = dp;
                showDialog(r.getString(R.string.select_end_date), id, child);
                break;
            case R.id.tvFilterStartTime:
                TimePicker tp = new TimePicker(ctx);
                tp.setId(R.id.dialog_child_id);
                tp.setTag(id);
                tp.setIs24HourView(true);
                if (_dtStart!=null)
                {
                    tp.setCurrentHour(_dtStart.get(Calendar.HOUR_OF_DAY));
                    tp.setCurrentMinute(_dtStart.get(Calendar.MINUTE));
                }
                else
                {
                    tp.setCurrentHour(defaultDt.get(Calendar.HOUR_OF_DAY));
                    tp.setCurrentMinute(defaultDt.get(Calendar.MINUTE));
                }
                child = tp;
                showDialog(r.getString(R.string.select_start_time), id, child);
                break;
            case R.id.tvFilterFinishTime:
                tp = new TimePicker(ctx);
                tp.setId(R.id.dialog_child_id);
                tp.setIs24HourView(true);
                tp.setTag(id);
                if (_dtEnd!=null)
                {
                    tp.setCurrentHour(_dtEnd.get(Calendar.HOUR_OF_DAY));
                    tp.setCurrentMinute(_dtEnd.get(Calendar.MINUTE));
                }
                else
                {
                    tp.setCurrentHour(defaultDt.get(Calendar.HOUR_OF_DAY));
                    tp.setCurrentMinute(defaultDt.get(Calendar.MINUTE));
                }
                child = tp;
                showDialog(r.getString(R.string.select_end_time), id, child);
                break;
         }

    }

    private void showDialog(String title, int idFrom, View child)
    {
        //Запрет на повор экрана
        AlarmHelper.disableOrientationChange(((Activity) getContext()));

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (inflater!=null)
        {
            View view = inflater.inflate( R.layout.dialog_custom, null );
            ((TextView)view.findViewById(R.id.tvTitle)).setText(title);

            //Содержимое
            child.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            ((LinearLayout)view.findViewById(R.id.linearLayout1)).addView(child);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            if (!_isAlwaysNeedUpdate)
            {
                builder.setPositiveButton(getResources().getString(R.string.update), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onDialogClosed(dialog, true);
                    }
                });
            }

            builder.setNeutralButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    onDialogClosed(dialog, _isAlwaysNeedUpdate);
                }
            });

            if (idFrom == R.id.ibSelectRange)
            {
                //Если это выбор диапазина, выставляем галочку на ранее выбранном
                Integer idRangeSelected = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("rangeRadioButtonID", R.id.rbDataRangePrevDay);
                RadioButton rb = (RadioButton)child.findViewById(idRangeSelected);
                if (rb!=null)
                {
                    rb.setChecked(true);
                }
            }

            // устанавливаем ее, как содержимое тела диалога
            builder.setView(view);
            //builder.setCancelable(false);
            AlertDialog dialog =  builder.create();

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    ((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                }
            });

            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.alertdialogbutton_background);
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
            if (!_isAlwaysNeedUpdate)
            {
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setBackgroundResource(R.drawable.alertdialogbutton_background);
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize(16);
            }
        }
    }

    private void onDialogClosed(DialogInterface dialog, Boolean isUpdate)
    {
        AlertDialog ad = (AlertDialog)dialog;
        if (ad!=null)
        {
            View child = ad.findViewById(R.id.dialog_child_id);
            onFinishEditDialog(ad, child, isUpdate);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (b) return;

        switch (buttonView.getId())
        {
            case R.id.cbDateRangeStart:
                if (isChecked)
                {
                    Calendar defaultDt = getInstance(locale);
                    setDateStart(defaultDt);
                }
                else
                {
                    setDateStart(null);
                }

                break;
            case R.id.cbDateRangeFinish:
                if (isChecked)
                {
                    Calendar defaultDt = getInstance(locale);
                    setDateEnd(defaultDt);
                }
                else
                {
                    setDateEnd(null);
                }

                break;
        }

        fireOnRangeChangeEvent(_dtStart, _dtEnd);
    }

    private void selectDateRange(RadioGroup rg)
    {
        if (rg==null) return;

        Calendar dtStart = getInstance(locale);
        Calendar dtEnd = getInstance(locale);

        AlarmHelper.floorCalendar(dtStart);
        AlarmHelper.ceilingCalendar(dtEnd);

        int radioButtonID = rg.getCheckedRadioButtonId();
        switch (radioButtonID)
        {
            case R.id.rbDataRangePrevDay:
                dtStart.roll(Calendar.DAY_OF_MONTH, -1);
                dtEnd.roll(Calendar.DAY_OF_MONTH, -1);
                setDateStart(dtStart);
                setDateEnd(dtEnd);
                break;
            case R.id.rbDataRangeCurrMonth:
                dtStart.roll(Calendar.DAY_OF_MONTH, -dtStart.get(Calendar.DAY_OF_MONTH) + 1);
                setDateStart(dtStart);
                setDateEnd(dtEnd);
                break;
            case R.id.rbDataRangePrevMonth:
                dtStart.roll(Calendar.DAY_OF_MONTH, -dtStart.get(Calendar.DAY_OF_MONTH) + 1);
                dtStart.roll(MONTH,  -1);
                dtEnd.add(DAY_OF_MONTH, -dtEnd.get(DAY_OF_MONTH));
                setDateStart(dtStart);
                setDateEnd(dtEnd);
                break;
        }

        //Сохранение выбранного
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putInt("rangeRadioButtonID", radioButtonID);
        editor.commit();
    }

    private void fireOnRangeChangeEvent(Calendar dtStart, Calendar dtEnd)
    {
        if (dateTimeRangeEvent!=null)
        {
            dateTimeRangeEvent.OnRangeSelected(dtStart, dtEnd, _isAlarmDateTimeChanged);
        }
    }

    public void onFinishEditDialog(AlertDialog dialog, View child, Boolean isUpdate) {
        Calendar defaultDt = getInstance(locale);
        if (child!=null)
        {
            Integer id = (Integer)child.getTag();
            switch (id)
            {
                case R.id.tvFilterStartDate:
                    DatePicker dp = (DatePicker)child;
                    if (dp!=null)
                    {
                        Calendar dtStart = getInstance(locale);
                        if (_dtStart!=null)
                        {
                            dtStart.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), _dtStart.get(Calendar.HOUR_OF_DAY), _dtStart.get(Calendar.MINUTE), 0);
                        }
                        else
                        {
                            dtStart.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), defaultDt.get(Calendar.HOUR_OF_DAY), defaultDt.get(Calendar.MINUTE), 0);
                        }
                        setDateStart(dtStart);
                    }
                    break;
                case R.id.tvFilterFinishDate:
                    dp = (DatePicker)child;
                    if (dp!=null)
                    {
                        Calendar dtEnd = getInstance(locale);
                        if (_dtEnd!=null)
                        {
                            dtEnd.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), _dtEnd.get(Calendar.HOUR_OF_DAY), _dtEnd.get(Calendar.MINUTE), 0);
                        }
                        else
                        {
                            dtEnd.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), defaultDt.get(Calendar.HOUR_OF_DAY), defaultDt.get(Calendar.MINUTE), 0);
                        }
                        setDateEnd(dtEnd);
                    }
                    break;
                case R.id.tvFilterStartTime:
                    TimePicker tp = (TimePicker)child;
                    if (tp!=null)
                    {
                        Calendar dtStart = getInstance(locale);
                        if (_dtStart!=null)
                        {
                            dtStart.set(_dtStart.get(Calendar.YEAR), _dtStart.get(Calendar.MONTH), _dtStart.get(Calendar.DAY_OF_MONTH)
                                    , tp.getCurrentHour(), tp.getCurrentMinute(), 0);
                        }
                        else
                        {
                            dtStart.set(defaultDt.get(Calendar.YEAR), defaultDt.get(Calendar.MONTH), defaultDt.get(Calendar.DAY_OF_MONTH)
                                    , tp.getCurrentHour(), tp.getCurrentMinute(), 0);
                        }
                        setDateStart(dtStart);
                    }
                    break;
                case R.id.tvFilterFinishTime:
                    tp = (TimePicker)child;
                    if (tp!=null)
                    {
                        Calendar dtEnd = getInstance(locale);
                        if (_dtEnd!=null)
                        {
                            dtEnd.set(_dtEnd.get(Calendar.YEAR), _dtEnd.get(Calendar.MONTH), _dtEnd.get(Calendar.DAY_OF_MONTH)
                                    , tp.getCurrentHour(), tp.getCurrentMinute(), 0);
                        }
                        else
                        {
                            dtEnd.set(defaultDt.get(Calendar.YEAR), defaultDt.get(Calendar.MONTH), defaultDt.get(Calendar.DAY_OF_MONTH)
                                    , tp.getCurrentHour(), tp.getCurrentMinute(), 0);
                        }
                        setDateEnd(dtEnd);
                    }
                    break;
            }
        }
        else
        {
            RadioGroup rg = (RadioGroup)dialog.findViewById(R.id.rgDateRangeGroup);
            selectDateRange(rg);
        }

        if (isUpdate)
        {
            fireOnRangeChangeEvent(_dtStart, _dtEnd);
        }
    }

}
