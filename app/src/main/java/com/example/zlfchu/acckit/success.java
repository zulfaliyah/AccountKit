package com.example.zlfchu.acckit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;

public class success extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Button btnLogOut = (Button)findViewById(R.id.logoutbtn);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountKit.logOut();
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                EditText edtUserId, edtPhone, edtEmail;

                edtUserId = (EditText)findViewById(R.id.edtUserId);
                edtUserId.setText(String.format("User Id %s", account.getId()));

                edtPhone = (EditText)findViewById(R.id.edtPhone);
                edtPhone.setText(String.format("Phone %s", account.getPhoneNumber() == null ? "" : account.getPhoneNumber().toString()));

                edtEmail = (EditText)findViewById(R.id.edtEmail);
                edtEmail.setText(String.format("E-mail %s", account.getEmail() == null ? "":account.getEmail()));
            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }
}
