package com.proryv.alarmnotification.adapters;

import android.content.Context;

import com.proryv.alarmnotification.R;

public enum EnumMultiChoiceType
{
    Alarm(0),
    Workflow(1),

    Formula(2),
    BalancePS(3),
    PS(4),
    TI(5),
    Slave61968(6);

    private final int value;
    private EnumMultiChoiceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName(Context ctx)
    {
        switch (this)
        {
            case  TI: return ctx.getString(R.string.ti);
            case PS: return ctx.getString(R.string.object);
            case BalancePS: return ctx.getString(R.string.balance);
            case Formula: return ctx.getString(R.string.formula);
            case Slave61968: return ctx.getString(R.string.slave61968);
        }

        return "";
    }
}
