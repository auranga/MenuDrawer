package layout;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uranga_pc.menudrawer.MainActivity;
import com.example.uranga_pc.menudrawer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento3 extends Fragment {


    public Fragmento3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Info. Sobre Las Camaras");
        ((MainActivity) getActivity()).getSupportActionBar().setLogo(R.drawable.info);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento3, container, false);
    }


}
