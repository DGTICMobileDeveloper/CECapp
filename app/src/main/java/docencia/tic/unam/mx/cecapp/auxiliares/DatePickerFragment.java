package docencia.tic.unam.mx.cecapp.auxiliares;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import docencia.tic.unam.mx.cecapp.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    protected int year;
    protected int month;
    protected int day;
    protected  String formattedDate;
    protected EditText editText;

    public void setArgs(EditText editText){
        this.editText = editText;
    }
    //TODO Ver si le cambio el formato a algo más legible (tendría que volver a cambiarlo al enviarlo al sistema)
    public void setArgs(String date, EditText editText){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(sdf.parse(date));
            this.year = c.get(Calendar.YEAR);
            this.month = c.get(Calendar.MONTH);
            this.day = c.get(Calendar.DAY_OF_MONTH);
        }catch (ParseException e){
            e.printStackTrace();
        }
        this.editText = editText;
    }

    public void setArgs(int year, int month, int day, EditText editText){
        this.year = year;
        this.month = month;
        this.day = day;
        this.editText = editText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(year == 0) { // No está inicializado
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), R.style.DialogTheme , this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = sdf.format(c.getTime());
        editText.setText(formattedDate);
    }
}
