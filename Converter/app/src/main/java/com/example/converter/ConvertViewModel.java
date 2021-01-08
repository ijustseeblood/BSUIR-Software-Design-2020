package com.example.converter;

import android.content.ClipData;
import android.content.ClipboardManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class ConvertViewModel extends ViewModel {

    private final MutableLiveData<String> fromData;
    private final MutableLiveData<Double> percent;
    private final MutableLiveData<String> toData;

    public ConvertViewModel() {
        fromData = new MutableLiveData<>();
        percent = new MutableLiveData<>();
        toData = new MutableLiveData<>();
    }

    public LiveData<String> getNum() {
        return fromData;
    }

    public void setItem(String item) {
        if (fromData.getValue() == null || fromData.getValue().isEmpty()) {
            if (item.equals(".")) {
                fromData.setValue("0.");
            } else {
                fromData.setValue(item);
            }
        } else if (fromData.getValue().equals("0")) {
            if (item.equals(".")) {
                fromData.setValue(fromData.getValue() + item);
            } else if (!item.equals("0")) {
                fromData.setValue(item);
            }
        } else if (fromData.getValue().contains(".") && item.equals(".")) {
            return;
        } else {
            fromData.setValue(fromData.getValue() + item);
        }
    }

    public void setPercent(String from, String to) {
        if (from.equals(to)) {
            percent.setValue(1.0);
            return;
        }
        switch (from) {
            case "kg": {
                switch (to) {
                    case "lb":
                        percent.setValue(2.205);
                        break;
                    case "oz":
                        percent.setValue(35.274);
                        break;
                }
            }
            break;
            case "lb": {
                switch (to) {
                    case "kg":
                        percent.setValue(0.454);
                        break;
                    case "oz":
                        percent.setValue(16.0);
                        break;
                }
            }
            break;
            case "oz": {
                switch (to) {
                    case "kg":
                        percent.setValue(0.0283);
                        break;
                    case "lb":
                        percent.setValue(0.0625);
                        break;
                }
            }
            break;
            case "km": {
                switch (to) {
                    case "mile":
                        percent.setValue(0.621);
                        break;
                    case "ft":
                        percent.setValue(3280.84);
                        break;
                }
            }
            break;
            case "mile": {
                switch (to) {
                    case "km":
                        percent.setValue(1.609);
                        break;
                    case "ft":
                        percent.setValue(5280.0);
                        break;
                }
            }
            break;
            case "ft": {
                switch (to) {
                    case "km":
                        percent.setValue(0.000305);
                        break;
                    case "mile":
                        percent.setValue(0.0001894);
                        break;
                }
            }
            break;
            case "rub": {
                switch (to) {
                    case "euro":
                        percent.setValue(0.011);
                        break;
                    case "usd":
                        percent.setValue(0.013);
                        break;
                }
            }
            break;
            case "euro": {
                switch (to) {
                    case "rub":
                        percent.setValue(90.97);
                        break;
                    case "usd":
                        percent.setValue(1.23);
                        break;
                }
            }
            break;
            case "usd": {
                switch (to) {
                    case "rub":
                        percent.setValue(74.23);
                        break;
                    case "euro":
                        percent.setValue(0.82);
                        break;
                }
            }
            break;
        }
    }

    public void convert() {
        if (fromData.getValue() != null) {
            if (fromData.getValue().equals("0")) {
                toData.setValue("0");
            } else {
                toData.setValue(Double.toString(Double.parseDouble(Objects.requireNonNull(fromData.getValue())) * Objects.requireNonNull(percent.getValue())));
            }
        }
    }

    public LiveData<String> getResult() {
        return toData;
    }

    public void delete() {
        if (toData.getValue() != null && fromData.getValue() != null) {
            toData.setValue("0");
            fromData.setValue("0");
        }
        convert();
    }

    public void swap() {
        if (toData.getValue() != null && fromData.getValue() != null) {
            String temp = fromData.getValue();
            fromData.setValue(toData.getValue());
            toData.setValue(temp);
        }
    }

    public void copyInBufferDataFrom(ClipboardManager clipboardManager) {
        if (fromData.getValue() != null) {
            ClipData clipData = ClipData.newPlainText("from", fromData.getValue());
            clipboardManager.setPrimaryClip(clipData);
        }
    }

    public void copyInBufferDataTo(ClipboardManager clipboardManager) {
        if (toData.getValue() != null) {
            ClipData clipData = ClipData.newPlainText("to", toData.getValue());
            clipboardManager.setPrimaryClip(clipData);
        }
    }
}
