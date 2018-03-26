package shadattonmoy.ams;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class TakeAttendanceFragmentForUserManual extends Fragment {
    private View view;
    private WebView webView;

    public TakeAttendanceFragmentForUserManual() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_take_attendance_fragment_for_user_manual, container, false);
        webView = (WebView) view.findViewById(R.id.take_attendance_web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl("file:///android_asset/take_attendance.html");

        return view;
    }

}
