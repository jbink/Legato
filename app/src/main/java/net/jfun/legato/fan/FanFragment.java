package net.jfun.legato.fan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import net.jfun.legato.MyData;
import net.jfun.legato.R;

import java.io.IOException;

public class FanFragment extends Fragment {

    TextView mTvRoastingTotalCount;


    public FanFragment newInstance() {
//    public CoffeeFragment newInstance(int page) {
        FanFragment fragment = new FanFragment();

//        Bundle bundle = new Bundle();
//        bundle.putInt("page" , page);
//        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            Log.d("where", "bundle이 있다.");
//            mStartPage = bundle.getInt("page");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_fan, container, false);

        mTvRoastingTotalCount = rootView.findViewById(R.id.roasting_total_count);
        mTvRoastingTotalCount.setText(String.valueOf(MyData.getInstance().getUseCount()) + "회");

        return rootView;
    }
}
