package com.diggytech.kinoplasticpremios;

import android.content.Context;

public class ButtonCallBack {
private static ButtonListener listner;
private Context context;
private static ButtonCallBack buttonCallBack;

private ButtonCallBack(Context context) {
this.context = context;
} //private constructor.

public static ButtonCallBack getInstance(Context context) {
if (buttonCallBack == null) { //if there is no instance available... create new one
buttonCallBack = new ButtonCallBack(context);
}
return buttonCallBack;
}

public void onButtonListener() {
if (listner != null) {
listner.onButtonCallBack();
}
}

public void setButtonListener(ButtonListener listner) {
this.listner = listner;
}

public interface ButtonListener {
public void onButtonCallBack();
}
}
