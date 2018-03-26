package shadattonmoy.ams;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;


public class SetupSystemFragmentForUserManual extends Fragment {
    private View view;
    private WebView webView;
    private Context context;


    public SetupSystemFragmentForUserManual() {
        // Required empty public constructor
        context = getActivity().getApplicationContext();
    }

    public SetupSystemFragmentForUserManual(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_setup_system_fragment_for_user_manual, container, false);
        webView = (WebView) view.findViewById(R.id.setup_system_web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        AppWebViewClients appWebViewClients = new AppWebViewClients(context);
        webView.setWebViewClient(appWebViewClients);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl("file:///android_asset/setup_system.html");

        return view;
    }
}
class AppWebViewClients extends WebViewClient {
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    public AppWebViewClients(Context context) {
        this.progressBar=progressBar;
        //progressBar.setVisibility(View.VISIBLE);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO Auto-generated method stub
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);
        //progressBar.setVisibility(View.GONE);
        progressDialog.dismiss();
    }
}
