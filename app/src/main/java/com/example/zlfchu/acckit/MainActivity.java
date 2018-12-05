package com.example.zlfchu.acckit;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;


public class MainActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 99;

    Button btn_sms, btn_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sms = (Button)findViewById(R.id.smsbtn);
        btn_email=(Button)findViewById(R.id.emailbtn);

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage(LoginType.EMAIL);
            }
        });

        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage(LoginType.PHONE);
            }
        });
    }

    private void startLoginPage(LoginType loginType) {
        if(loginType == LoginType.EMAIL){
            Intent intent = new Intent(this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL,
                            AccountKitActivity.ResponseType.TOKEN);
            UIManager uiManager;
            uiManager = new SkinManager(SkinManager.Skin.CONTEMPORARY, ContextCompat.getColor(this, R.color.colorPrimaryDark));
            configurationBuilder.setUIManager(uiManager);
            intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
            startActivityForResult(intent, APP_REQUEST_CODE);
        }else if (loginType == LoginType.PHONE){
            Intent intent = new Intent(this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                            AccountKitActivity.ResponseType.TOKEN);
            UIManager uiManager;
            uiManager = new SkinManager(SkinManager.Skin.CONTEMPORARY, ContextCompat.getColor(this, R.color.colorPrimaryDark));
            configurationBuilder.setUIManager(uiManager);
            intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
            startActivityForResult(intent, APP_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == APP_REQUEST_CODE){
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if(result.getError() != null){
                Toast.makeText(this, ""+result.getError().getErrorType().getMessage(),Toast.LENGTH_SHORT).show();
                return;
            } else if(result.wasCancelled()){
                Toast.makeText(this, "Cancel",Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (result.getAccessToken() !=null)
                    Toast.makeText(this,"Success! "+result.getAccessToken().getAccountId(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Success! "+result.getAuthorizationCode().substring(0,10), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, success.class));
            }
        }
    }


}
