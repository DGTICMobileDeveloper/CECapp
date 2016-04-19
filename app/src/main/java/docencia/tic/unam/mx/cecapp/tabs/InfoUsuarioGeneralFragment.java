package docencia.tic.unam.mx.cecapp.tabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import docencia.tic.unam.mx.cecapp.AsyncHttpRetriever;
import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.Interfaces.CustomFragmentLifeCycle;
import docencia.tic.unam.mx.cecapp.MainActivity;
import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.auxiliares.DatePickerFragment;

public class InfoUsuarioGeneralFragment extends Fragment implements CustomFragmentLifeCycle {
    private static EditText etName;
    private static EditText etLastName;
    private static EditText etProcAcad;
    private static Spinner academicGradeSpinner;
    private static Spinner genderSpinner;
    private static EditText etMail;
    private static EditText etPhone;
    private static EditText etAdress;
    private static EditText etNumCta;
    private static EditText etBirthDate;
    private static SharedPreferences infoUsuario;
    private static Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_info_usuario_general, container, false);
        //Log.i(">>> lugar", "onCreateView");

        etName = (EditText) rootView.findViewById(R.id.etName);
        etLastName = (EditText) rootView.findViewById(R.id.etLastName);
        etProcAcad = (EditText) rootView.findViewById(R.id.etProcAcad);
        academicGradeSpinner = (Spinner) rootView.findViewById(R.id.sAcadGrad);
        genderSpinner = (Spinner) rootView.findViewById(R.id.sGender);
        etMail = (EditText) rootView.findViewById(R.id.etMail);
        etPhone = (EditText) rootView.findViewById(R.id.etPhone);
        etAdress = (EditText) rootView.findViewById(R.id.etAdress);
        etNumCta = (EditText) rootView.findViewById(R.id.etNumCta);
        etBirthDate = (EditText) rootView.findViewById(R.id.etBirthDate);
        final Button bPickBirthDate = (Button) rootView.findViewById(R.id.bBirthDate);

        bPickBirthDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String auxDate = etBirthDate.getText().toString();
                DatePickerFragment picker = new DatePickerFragment();
                if (auxDate.equals(""))
                    picker.setArgs(etBirthDate);
                else
                    picker.setArgs(auxDate, etBirthDate);
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        activity = getActivity();

        ArrayAdapter<CharSequence> acadGradeAdapter = ArrayAdapter.createFromResource(activity,
                R.array.academic_grade_array, android.R.layout.simple_spinner_item);
        acadGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        academicGradeSpinner.setAdapter(acadGradeAdapter);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(activity,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        infoUsuario = activity.getSharedPreferences(
                Constants.USER_INFO_PREFERENCES, Context.MODE_PRIVATE);
        setUserInfo(getListFromPreferences(infoUsuario));
        return rootView;
    }

    @Override
    public void onPauseFragment() {
        //
    }

    @Override
    public void onResumeFragment() {
        //
    }

    public static void saveChanges(){
        List<String> userInfoSave = getUserInfo();
        int y;
        RequestParams params = new RequestParams();
        long auxIMEI = infoUsuario.getLong(Constants.IMEI_MEID, 0);
        long auxID = infoUsuario.getLong(Constants.USER_ID, -1);
        String acadGrad = (String) academicGradeSpinner.getItemAtPosition(Integer.parseInt(userInfoSave.get(6)));

        if(auxID == -1) {  // Primera vez que se registra
            y = 0;
            params.put("param1", auxIMEI);
        } else {    // Ya tiene ID
            y = 1;
            params.put("param1", auxID);
            params.put("param2", auxIMEI);
        }
        for (int x = 0; x < Constants.USER_INFO_PREFS_KEYS.length; x++) {
            if (x == 4 || x == 6) {
                if (x == 4) { // Está así porque puede que mande 0,1 o 2 (no selecc, hombre, mujer)
                    params.put("param" + (x + 2 + y), userInfoSave.get(x));
                } else {  // 6: Pasar el contenido
                    params.put("param" + (x + 2+ y), acadGrad);
                }
            } else
                params.put("param" + (x + 2 + y), userInfoSave.get(x));
        }
        if(auxID == -1) {
            Log.i(">>> auxID == -1", ">");
            AsyncHttpRetriever asyncHttpRetriever = new AsyncHttpRetriever(infoUsuario,
                    Constants.MODE_REGISTER_USER_INFO, params, activity);
            asyncHttpRetriever.postCommand(Constants.BASE_LINK_REGISTER_USER);
        }
        else {
            AsyncHttpRetriever asyncHttpRetriever = new AsyncHttpRetriever(infoUsuario,
                    Constants.MODE_UPDATE_USER_INFO, params, activity);
            Log.i(">>> auxID != -1", ">");
            asyncHttpRetriever.postCommand(Constants.BASE_LINK_UPDATE_USER);
        }
    }

    public static void asyncHttpSave() {
        List<String> infoToSave = getUserInfo();
        for (int x = 0; x < Constants.USER_INFO_PREFS_KEYS.length; x++) {
            infoUsuario.edit().putString(Constants.USER_INFO_PREFS_KEYS[x], infoToSave.get(x)).apply();
        }
    }

    public static ArrayList<String> getListFromPreferences(SharedPreferences prefs){
        ArrayList<String> list = new ArrayList<>();
        for (int x = 0; x < Constants.USER_INFO_PREFS_KEYS.length; x++) {
            if(x==4 || x==6)
                list.add(prefs.getString(Constants.USER_INFO_PREFS_KEYS[x], "0"));
            else
                list.add(prefs.getString(Constants.USER_INFO_PREFS_KEYS[x], ""));
        }
        return list;
    }

    public static List<String> getUserInfo(){
        List<String> userInfo = new ArrayList<>();
        userInfo.add(etName.getText().toString());
        userInfo.add(etLastName.getText().toString());
        userInfo.add(etProcAcad.getText().toString());
        userInfo.add(etBirthDate.getText().toString());
        userInfo.add(Integer.toString(genderSpinner.getSelectedItemPosition()));
        userInfo.add(etNumCta.getText().toString());
        userInfo.add(Integer.toString(academicGradeSpinner.getSelectedItemPosition()));
        userInfo.add(etMail.getText().toString());
        userInfo.add(etPhone.getText().toString());
        userInfo.add(etAdress.getText().toString());
        return userInfo;
    }

    public static void setUserInfo(List<String> userInfo){
        etName.setText(userInfo.get(0));
        etLastName.setText(userInfo.get(1));
        etProcAcad.setText(userInfo.get(2));
        etBirthDate.setText(userInfo.get(3));
        genderSpinner.setSelection(Integer.parseInt(userInfo.get(4)));
        etNumCta.setText(userInfo.get(5));
        academicGradeSpinner.setSelection(Integer.parseInt(userInfo.get(6)));
        etMail.setText(userInfo.get(7));
        etPhone.setText(userInfo.get(8));
        etAdress.setText(userInfo.get(9));
    }
}
