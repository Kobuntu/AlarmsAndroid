package com.proryv.alarmnotification.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by IG on 08.08.13.
 */
public class HierarchyElement  implements Parcelable {

    public EnumMultiChoiceType multiChoiceType;
    public ArrayList<MultiChoiceElement> items;

    public HierarchyElement(EnumMultiChoiceType multiChoiceType, ArrayList<MultiChoiceElement> items)
    {
        this.multiChoiceType = multiChoiceType;
        this.items = items;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeInt(multiChoiceType.getValue());

        Integer size = items == null ? 0 : items.size();
        Parcelable[] pList = new Parcelable[size];
        if (items!=null)
        {
            for (Integer i = 0; i < size; i++)
            {
                pList[i] = items.get(i);
            }
        }
        parcel.writeParcelableArray(pList, flag);
    }

    public static final Parcelable.Creator<HierarchyElement> CREATOR = new Parcelable.Creator<HierarchyElement>() {
        // распаковываем объект из Parcel
        public HierarchyElement createFromParcel(Parcel in) {
            return new HierarchyElement(in);
        }

        public HierarchyElement[] newArray(int size) {
            return new HierarchyElement[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private HierarchyElement(Parcel parcel) {
        try
        {
            multiChoiceType = EnumMultiChoiceType.values()[parcel.readInt()];
        }
        catch (Exception ex)
        {

        }

        Parcelable[] pList =parcel.readParcelableArray(MultiChoiceElement.class.getClassLoader());
        if (pList !=null)
        {
            items = new ArrayList<MultiChoiceElement>();
            for (Integer i = 0; i < pList.length; i++)
            {
                items.add((MultiChoiceElement) pList[i]);
            }
        }
    }
}
