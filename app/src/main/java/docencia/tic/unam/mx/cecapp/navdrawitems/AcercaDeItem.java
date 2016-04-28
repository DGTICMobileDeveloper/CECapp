package docencia.tic.unam.mx.cecapp.navdrawitems;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class AcercaDeItem extends Fragment {
    private Context context;

    public AcercaDeItem() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.acerca_item_fragment, null);
        getActivity().setTitle("Centro de Exposiciones y Congresos UNAM");
        ImageButton ibFb = (ImageButton) rootView.findViewById(R.id.ibFb);
        ibFb.setOnClickListener(onClick);
        ImageButton ibTw = (ImageButton) rootView.findViewById(R.id.ibTw);
        ibTw.setOnClickListener(onClick);
        ImageButton ibYt = (ImageButton) rootView.findViewById(R.id.ibYt);
        ibYt.setOnClickListener(onClick);

        ImageView mapa = (ImageView) rootView.findViewById(R.id.mapaCEC);
        PhotoViewAttacher attacher = new PhotoViewAttacher(mapa);

        return rootView;
    }

    ImageButton.OnClickListener onClick = new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.ibFb:
                    Log.i(">>> AcercaDeItem", "FB");
                    Uri uri = Uri.parse(Constants.CEC_FB);
                    try {
                        ApplicationInfo applicationInfo = getActivity().getPackageManager()
                                .getApplicationInfo("com.facebook.katana", 0);
                        if (applicationInfo.enabled) {
                            uri = Uri.parse("fb://facewebmodal/f?href=" + Constants.CEC_FB);
                        }
                    } catch (PackageManager.NameNotFoundException ignored) {
                    }
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
//                    try {
//                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CEC_FB));
//                        startActivity(intent);
//                    } catch(Exception e) {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CEC_FB)));
//                    }
                    break;
                case R.id.ibTw:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CEC_TW));
                    startActivity(intent);
                    break;
                case R.id.ibYt:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CEC_YT));
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}