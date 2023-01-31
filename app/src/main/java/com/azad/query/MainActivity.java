package com.azad.query;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@192.168.0.1:1523:ORCL";    //Add your database ip here
    private static String DEFAULT_USERNAME = "DB_NAME";                                     //Add your db schema name
    private static String DEFAULT_PASSWORD = "123";                                         //DB schema password

    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Database query
        String secIdQuery = "select EMP_NAME from EMPLOYEE_TABLE where EMPLOYEE_NUMBER = '12345'";


        TextView tv = (TextView) findViewById(R.id.hello);
        String empName = "Name";
        try {
            this.connection = createConnection();
            Toast.makeText(MainActivity.this, "Connected",Toast.LENGTH_SHORT).show();

            Statement statement=connection.createStatement();
            ResultSet resultSetSectionId = statement.executeQuery(secIdQuery);

            while (resultSetSectionId.next()) {
                empName = resultSetSectionId.getString(1);
            }
            tv.setText(empName);
            connection.close();
        }
        catch (Exception e) {

            Toast.makeText(MainActivity.this, "Err"+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
}